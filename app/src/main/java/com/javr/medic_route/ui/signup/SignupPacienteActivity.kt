package com.javr.medic_route.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.javr.medic_route.R
import com.javr.medic_route.core.Global
import com.javr.medic_route.core.Validator
import com.javr.medic_route.databinding.ActivitySignupPacienteBinding
import com.javr.medic_route.ui.toolbar.Toolbar

class SignupPacienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupPacienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initListener()

    }

    private fun initComponents() {
        //Configuracion toolbar
        Toolbar().showToolbar(this, "Registro de Paciente", true)

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

        binding.btnSignup.setOnClickListener {
            goToVistaUsuario()
        }
    }

    private fun goToVistaUsuario() {
        if(validarFormulario()){

        }
    }

    private fun validarFormulario(): Boolean {
        if (binding.etNombres.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilNombres, "No ha ingresado sus nombres")
            return false
        }

        if (binding.etApellidos.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilApellidos, "No ha ingresado sus apellidos")
            return false
        }

        if (binding.etCedula.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilCedula, "No ha ingresado su cédula")
            return false
        }else{
            if (!Validator.isValidCedula(binding.etCedula.text.toString())) {
                Global.setErrorInTextInputLayout(binding.tilCedula, "Cédula invalida")
                return false
            }
        }

        if (binding.etCorreo.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilCorreo, "No ha ingresado su correo")
            return false
        }else{
            if (!Validator.isValidEmail(binding.etCorreo.text.toString())) {
                Global.setErrorInTextInputLayout(binding.tilCorreo, "El correo no tiene el formato correcto: usuario@ejemplo.com")
                return false
            }
        }

        if (binding.etTelefono.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilTelefono, "No ha ingresado su número de teléfono celular")
            return false
        }else{
            if (binding.etTelefono.text.toString().length < 10) {
                Global.setErrorInTextInputLayout(binding.tilTelefono, "Número de teléfono celular invalido")
                return false
            }
        }

        if (binding.etPassword.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilPassword, "No ha ingresado su contraseña")
            return false
        }

        if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
            Global.setErrorInTextInputLayout(binding.tilConfirmPassword, "Las contraseñas no coinciden")
            return false
        }

        return true
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
}