package com.insane.model

import java.io.Serializable
import java.util.*

class Image (
    val id: Int = 0,
    var name: String? = null,
    var created: Date? = null,
    var updated: Date? = null) : Serializable {}