package com.javr.medic_route.ui.menus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.javr.medic_route.R
import com.javr.medic_route.databinding.ActivityTipoUsuarioBinding
import com.javr.medic_route.ui.signup.SignupConsultorioActivity
import com.javr.medic_route.ui.signup.SignupUserActivity
import com.javr.medic_route.ui.toolbar.Toolbar

class TipoUsuarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipoUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initListener()
    }

    private fun initComponents() {
        Toolbar().showToolbar(this, "Tipo de Usuario", true)
    }

    private fun initListener() {
        binding.cvPaciente.setOnClickListener {
            val intent = Intent(this, SignupUserActivity::class.java)
            intent.putExtra(SignupUserActivity.EXTRA_TIPO_USUARIO, "P")
            startActivity(intent)
        }

        binding.cvMedico.setOnClickListener {
            startActivity(Intent(this, SignupConsultorioActivity::class.java))
        }


    }
}