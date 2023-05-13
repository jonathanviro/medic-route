package com.javr.medic_route.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.javr.medic_route.databinding.ActivitySignupConsultorioBinding
import com.javr.medic_route.ui.toolbar.Toolbar

class SignupConsultorioActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupConsultorioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupConsultorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initListener()
    }

    private fun initComponents() {
        Toolbar().showToolbar(this, "Registro de Consultorio Médico", true)
    }

    private fun initListener() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SignupUserActivity::class.java)
            intent.putExtra(SignupUserActivity.EXTRA_TIPO_USUARIO, "M")
            startActivity(intent)
        }
    }
}