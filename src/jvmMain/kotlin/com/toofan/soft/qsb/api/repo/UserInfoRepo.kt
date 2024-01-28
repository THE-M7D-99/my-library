package com.toofan.soft.qsb.api.repo

import com.toofan.soft.qsb.api.ApiExecutor
import com.toofan.soft.qsb.api.Field
import com.toofan.soft.qsb.api.IResponse
import com.toofan.soft.qsb.api.Route
import com.toofan.soft.qsb.api.services.Logger
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

object UserInfoRepo {
    @JvmStatic
    fun execute() {
        runBlocking {
            ApiExecutor.execute(
                route = Route.USERINFO
            ) { jsonObject ->
                val user = jsonObject.getJSONObject(/* key = */ "user")
                val response = Response.map(user)
                Logger.log("repo->response", response)
            }
        }
    }

    private data class Response(
        @Field("id")
        private val id: Int = 0,
        @Field("name")
        private val name: String? = null,
        @Field("email")
        private val email: String? = null
    ) : IResponse {
        companion object {
            private fun getInstance(): Response {
                return Response()
            }

            fun map(data: JSONObject): Response {
                return getInstance().getResponse(data) as Response
            }
        }
    }
}
