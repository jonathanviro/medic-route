package com.javr.medic_route.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.javr.medic_route.core.Global
import com.javr.medic_route.core.Validator
import com.javr.medic_route.databinding.ActivityLoginBinding
import com.javr.medic_route.ui.signup.SignupPacienteActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initListener()
    }

    private fun initComponents() {
        //Watcher Errores
        Global.setErrorInTextInputLayout(binding.etCedula, binding.tilCedula)
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
            //Valida existencia usuario
            //Validar tipo de usuario
        }
    }

    private fun goToSignup() {
        val intent = Intent(this, SignupPacienteActivity::class.java)
        startActivity(intent)
    }

    private fun validarFormulario(): Boolean {
        if (binding.etCedula.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilCedula, "No ha ingresado su cédula")
            return false
        }else{
            if (!Validator.isValidCedula(binding.etCedula.text.toString())) {
                Global.setErrorInTextInputLayout(binding.tilCedula, "Cédula invalida")
                return false
            }
        }

        if (binding.etPassword.text.toString().isNullOrEmpty()) {
            Global.setErrorInTextInputLayout(binding.tilPassword, "No ha ingresado su contraseña")
            return false
        }

        return true
    }
}