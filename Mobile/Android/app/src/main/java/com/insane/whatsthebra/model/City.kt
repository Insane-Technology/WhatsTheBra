package com.insane.whatsthebra.model

import java.io.Serializable

class City (
    val id: Int = 0,
    val name: String? = null,
    val state: State? = null) : Serializable {}