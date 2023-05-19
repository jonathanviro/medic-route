package com.javr.medic_route.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.javr.medic_route.R
import com.javr.medic_route.core.Global
import com.javr.medic_route.core.Validator
import com.javr.medic_route.data.network.firebase.AuthProvider
import com.javr.medic_route.databinding.ActivityLoginBinding
import com.javr.medic_route.ui.map.MapActivity
import com.javr.medic_route.ui.menus.TipoUsuarioActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    val authProvider = AuthProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initListener()
    }

    private fun initComponents() {
        //Watcher Errores
        Global.setErrorInTextInputLayout(binding.etCorreo, binding.tilCorreo)
        Global.setErrorInTextInputLayout(binding.etPassword, binding.tilPassword)
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            gotToVistaUsuario()
        }

        binding.btnSignup.setOnClickListener {
            goToSignup()
        }
    }

    private fun gotToVistaUsuario() {
        if (validarFormulario()) {
            authProvider.login(binding.etCorreo.text.toString(), binding.etPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val tipoUsuario = "P"
                        if (tipoUsuario.equals("PACIENTE")) {
                            goToMap()
                        }
                    } else {
                        Toast.makeText(this, "Error en inicio de sesión", Toast.LENGTH_SHORT).show()
                        Log.d("FIREBASE", "ERROR ${it.exception.toString()}")
                    }
                }
        }
    }

    private fun goToMap() {
        val intent = Intent(this, MapActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun goToSignup() {
        startActivity(Intent(this, TipoUsuarioActivity::class.java))
    }

    private fun validarFormulario(): Boolean {
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

        return true
    }

    override fun onStart() {
        super.onStart()

        if (authProvider.existSession()){
            goToMap()
        }
    }
}