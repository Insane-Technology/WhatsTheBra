package com.insane.model

import java.io.Serializable

class State(
    val id: Int = 0,
    val name: String? = null,
    val abbreviation: String? = null,
    val country: Country? = null) : Serializable{}