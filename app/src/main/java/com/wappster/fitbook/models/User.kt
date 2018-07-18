package com.wappster.fitbook.models

import java.util.*

data class User (
        val id : Int?,
        val email : String?,
        val facebookId : String?,
        val googleId : String?,
        val createdAt : Date?,
        val updatedAt : Date?
)