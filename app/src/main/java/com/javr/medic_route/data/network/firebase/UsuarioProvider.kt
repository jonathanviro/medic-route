package com.javr.medic_route.data.network.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.javr.medic_route.data.network.model.Usuario

class UsuarioProvider {
    val db = Firebase.firestore.collection("Pacientes")

    fun create(usuario: Usuario): Task<Void>{
        return db.document(usuario.id!!).set(usuario)
    }
}