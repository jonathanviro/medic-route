package com.javr.medic_route.ui.toolbar

import androidx.appcompat.app.AppCompatActivity
import com.javr.medic_route.R

class Toolbar {
    fun showToolbar(activities : AppCompatActivity, titulo: String, isBotonVolver: Boolean){
        activities.setSupportActionBar(activities.findViewById(R.id.toolbar))
        activities.supportActionBar?.title = titulo
        activities.supportActionBar?.setDisplayHomeAsUpEnabled(isBotonVolver)
    }
}