package com.javr.medic_route.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.javr.medic_route.R
import com.javr.medic_route.core.Global
import com.javr.medic_route.core.Validator
import com.javr.medic_route.databinding.ActivitySignupUserBinding
import com.javr.medic_route.data.network.firebase.AuthProvider
import com.javr.medic_route.data.network.firebase.UsuarioProvider
import com.javr.medic_route.data.network.model.Usuario
import com.javr.medic_route.ui.toolbar.Toolbar

class SignupUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TIPO_USUARIO = "SignupUserActivity:TipoUsuario"
    }

    private lateinit var binding: ActivitySignupUserBinding
    private lateinit var tipoUsuario: String
    private val authProvider = AuthProvider()
    private val usuarioProvider = UsuarioProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tipoUsuario = intent.getStringExtra(EXTRA_TIPO_USUARIO)!!

        initComponents()
        initListener()

    }

    private fun initComponents() {
        iniWatchers()

        if (tipoUsuario.equals("PACIENTE")) {
            Toolbar().showToolbar(this, "Registro de Usuario", true)
            binding.llUploadPdf.visibility = View.GONE
            binding.llDownloadPdf.visibility = View.GONE
        } else {
            Toolbar().showToolbar(this, "Registro de Médico", true)
        }
    }

    private fun iniWatchers() {
        //Watcher Errores
        Global.setErrorInTextInputLayout(binding.etNombres, binding.tilNombres)
        Global.setErrorInTextInputLayout(binding.etApellidos, binding.tilApellidos)
        Global.setErrorInTextInputLayout(binding.etCedula, binding.tilCedula)
        Global.setErrorInTextInputLayout(binding.etCorreo, binding.tilCorreo)
        Global.setErrorInTextInputLayout(binding.etTelefono, binding.tilTelefono)
        Global.setErrorInTextInputLayout(binding.etPassword, binding.tilPassword)
        Global.setErrorInTextInputLayout(binding.etConfirmPassword, binding.tilConfirmPassword)
    }

    private fun initListener() {
        binding.ivFotoPerfil.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.ivUploadPdf.setOnClickListener {
            selectedPdf()
        }

        binding.btnSignup.setOnClickListener {
            goToVistaUsuario()
        }
    }

    private fun goToVistaUsuario() {
        if (validarFormulario()) {
            authProvider.registrer(
                binding.etCorreo.text.toString(),
                binding.etPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful){
                    val usuario = Usuario(
                        id = authProvider.getId(),
                        nombres = binding.etNombres.text.toString(),
                        apellidos = binding.etApellidos.text.toString(),
                        cedula = binding.etCedula.text.toString(),
                        email = binding.etCorreo.text.toString(),
                        telefono = binding.etTelefono.text.toString(),
                        sexo = binding.tvSexo.text.toString(),
                        tipoUsuario = tipoUsuario)

                    usuarioProvider.create(usuario).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this@SignupUserActivity, "REGISTRO EXITOSO", Toast.LENGTH_LONG).show()
                        }else{
                            Log.e("FIREBASE", "Erro almacenando los datos del usuario. ${it.exception.toString()}")
                        }
                    }
                }else{
                    Log.e("FIREBASE", "Registro fallido ${it.exception.toString()}")
                }
            }
        }
    }

    private fun validarFormulario(): Boolean {
        if (binding.etNombres.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(
                binding.tilNombres,
                this.getString(R.string.not_insert_names)
            )
            return false
        }

        if (binding.etApellidos.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(
                binding.tilApellidos,
                this.getString(R.string.not_insert_lastnames)
            )
            return false
        }

        if (binding.etCedula.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(
                binding.tilCedula,
                this.getString(R.string.not_insert_passport)
            )
            return false
        } else {
            if (!Validator.isValidCedula(binding.etCedula.text.toString())) {
                Global.setErrorInTextInputLayout(
                    binding.tilCedula,
                    this.getString(R.string.invalid_passport)
                )
                return false
            }
        }

        if (binding.etCorreo.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(
                binding.tilCorreo,
                this.getString(R.string.not_insert_email)
            )
            return false
        } else {
            if (!Validator.isValidEmail(binding.etCorreo.text.toString())) {
                Global.setErrorInTextInputLayout(
                    binding.tilCorreo,
                    this.getString(R.string.invalid_email)
                )
                return false
            }
        }

        if (binding.etTelefono.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(
                binding.tilTelefono,
                this.getString(R.string.not_insert_phone)
            )
            return false
        } else {
            if (binding.etTelefono.text.toString().length < 10) {
                Global.setErrorInTextInputLayout(
                    binding.tilTelefono,
                    this.getString(R.string.invalid_phone)
                )
                return false
            }
        }

        if (binding.etPassword.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(
                binding.tilPassword,
                this.getString(R.string.not_insert_password)
            )
            return false
        }

        if (Validator.isValidPassword(binding.etPassword.text.toString())) {
            Global.setErrorInTextInputLayout(
                binding.tilPassword,
                this.getString(R.string.invalid_password)
            )
            return false
        }

        if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
            Global.setErrorInTextInputLayout(
                binding.tilConfirmPassword,
                this.getString(R.string.invalid_confirm_password)
            )
            return false
        }

        return true
    }

    private fun selectedPdf() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }

        openPdfFile.launch(intent)
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivFotoPerfil.setImageURI(uri)
//                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "NO HA SELECCIONADO FOTO", Toast.LENGTH_SHORT).show()
            }
        }

    private val openPdfFile =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                uri?.let { pdfUri ->
                    val cursor = this.contentResolver.query(uri, null, null, null, null)
                    cursor?.let {
                        if (it.moveToFirst()) {
                            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            val fileName =
                                if (nameIndex >= 0) it.getString(nameIndex) else uri.lastPathSegment

                            binding.tvNombrePdf.text = fileName
                        }
                        it.close()
                    }
                }
            }
        }
}