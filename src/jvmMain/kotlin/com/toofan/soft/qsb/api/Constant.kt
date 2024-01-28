package com.toofan.soft.qsb.api

private object Constant {
    private const val URL = "http://192.168.1.14:80/"
    const val HOME = URL + "api"
}

enum class Route(
    private val _name: String,
    private val _method: Method,
    private val _isAuthorized: Boolean = false
) {
    REGISTER("register", Method.POST),
    LOGIN("login", Method.POST),
    USERINFO("userinfo", Method.POST, true);

    internal val isAuthorized: Boolean
        get() = _isAuthorized

    internal val url: String
        get() = "${Constant.HOME}/$_name"

    internal fun getMethod(): Method {
        return _method
    }
}

internal enum class Method(internal val value: String) {
    POST("POST"), GET("GET")
}