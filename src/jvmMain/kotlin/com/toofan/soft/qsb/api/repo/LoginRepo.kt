package com.toofan.soft.qsb.api.repo

import com.toofan.soft.qsb.api.ApiExecutor
import com.toofan.soft.qsb.api.Field
import com.toofan.soft.qsb.api.IRequest
import com.toofan.soft.qsb.api.Route
import kotlinx.coroutines.runBlocking

object LoginRepo {
    @JvmStatic
    fun execute(email: String, password: String) {
        runBlocking {
            val request = Request(email, password)
            ApiExecutor.execute(
                route = Route.LOGIN,
                request = request
            )
        }
    }

    private data class Request(
        @Field("email")
        val email: String,
        @Field("password")
        val password: String
    ) : IRequest
}

//suspend fun login(email: String, password: String) {
//    LoginRepo.execute(email, password)
//}

