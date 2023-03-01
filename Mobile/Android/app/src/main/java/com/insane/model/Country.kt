package com.insane.model

import java.io.Serializable

class Country(
    val id: Int = 0,
    val name: String? = null,
    val abbreviation: String? = null,
    val iso: String? = null, val ddi: Int? = null) : Serializable {}