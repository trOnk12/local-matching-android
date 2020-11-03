package com.example.local_matching.repository

import com.example.local_matching.model.User
import com.example.local_matching.network.LocalMatchingAPI
import java.lang.Exception
import kotlin.jvm.Throws

class UserRepository(private val localMatchingAPI: LocalMatchingAPI) {

    @Throws(NoUserFoundException)
    fun getLoggedUser(): User {

    }

}

class NoUserFoundException : Exception()