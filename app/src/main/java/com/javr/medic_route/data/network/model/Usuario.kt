package com.javr.medic_route.data.network.model

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class Usuario (
    val id: String? = null,
    val nombres: String? = null,
    val apellidos: String? = null,
    val cedula: String? = null,
    val email: String? = null,
    val telefono: String? = null,
    val sexo: String? = null,
    val tipoUsuario: String? = null
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Usuario>(json)
    }
}