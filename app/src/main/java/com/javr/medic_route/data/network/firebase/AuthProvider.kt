package com.javr.medic_route.data.network.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthProvider {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registrer(email:String, password: String): Task<AuthResult>{
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun getId(): String{
        return auth.currentUser?.uid ?: ""
    }
}