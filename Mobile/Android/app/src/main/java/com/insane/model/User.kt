package com.insane.model

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class User(
    val id: Int = 0,
    val name: String? = null,
    val email: String? = null,
    val pass: String? = null,
    val birthday: Date? = null,
    val created: Date? = null,
    val updated: Date? = null,
    val city: City? = null,
    val image: Image? = null,
    val favouriteProducts: List<Product> = ArrayList()) : Serializable {}