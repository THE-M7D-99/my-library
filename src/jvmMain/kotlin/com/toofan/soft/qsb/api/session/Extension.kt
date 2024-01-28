package com.toofan.soft.qsb.api.session

import org.json.JSONObject

private val token: String
    get() = "token"

internal fun JSONObject.checkToken() {
    if (hasToken) {
        removeToken()?.let { Memory.updateToken(it) }
    }
}

private fun JSONObject.removeToken(): String? {
    return this.remove(token)?.toString()
}

private val JSONObject.hasToken : Boolean
    get() = this.has(token)
