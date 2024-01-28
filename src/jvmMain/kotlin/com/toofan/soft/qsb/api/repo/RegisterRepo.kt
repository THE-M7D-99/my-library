package com.toofan.soft.qsb.api.repo

import com.toofan.soft.qsb.api.ApiExecutor
import com.toofan.soft.qsb.api.Field
import com.toofan.soft.qsb.api.IRequest
import com.toofan.soft.qsb.api.Route
import kotlinx.coroutines.runBlocking

object RegisterRepo {
    @JvmStatic
    fun execute(name: String, email: String?, password: String?) {
        runBlocking {
            val request = Request(name, email, password)
            ApiExecutor.execute(
                route = Route.REGISTER,
                request = request
            )
        }
    }

    private data class Request(
        @Field("name")
        val name: String?,
        @Field("email")
        val email: String?,
        @Field("password")
        val password: String?
    ) : IRequest
}
