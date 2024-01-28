package com.toofan.soft.qsb.api

import com.toofan.soft.qsb.api.session.Memory
import com.toofan.soft.qsb.api.session.checkToken
import com.toofan.soft.qsb.api.services.Logger
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApiExecutor {
    suspend fun execute(
        route: Route,
        request: IRequest? = null,
        onResponse: (jsonObject: JSONObject) -> Unit = {}
    ) {
        withContext(Dispatchers.IO) {
            try {
                val url = URL(route.url)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = route.getMethod().value
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                if (route.isAuthorized) {
                    connection.setRequestProperty("Authorization", "Bearer ${Memory.token}")
                }
                connection.doOutput = true

                Logger.log("requestProperties", connection.requestProperties)

                // Set the request parameters
                request?.let {
                    Logger.log("requestParameters", request.parameters1)

                    connection.outputStream.use { os ->
                        val input =
                            request.parameters1.toByteArray(charset("utf-8"))
                        os.write(input, 0, input.size)
                    }
                }

                val responseCode = connection.responseCode
                if (responseCode != 200) {
                    Logger.log("responseCode", responseCode)
                    throw RuntimeException("HttpResponseCode: $responseCode")
                } else {
                    val informationString = StringBuilder()
                    BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            informationString.append(line)
                        }
                    }

                    // Use Gson for parsing JSON
                    val gson = Gson()
                    val dataObject = gson.fromJson(informationString.toString(), Any::class.java)
                    val jsonObject = JSONObject(gson.toJson(dataObject))

                    Logger.log("dataObject", dataObject)
                    Logger.log("jsonObject", jsonObject)
                    jsonObject.checkToken()
                    Logger.log("jsonObject", jsonObject)

                    onResponse(jsonObject)
                }
            } catch (e: Exception) {
                Logger.log("e", e.message)
                throw RuntimeException(e)
            }
        }
    }
}
