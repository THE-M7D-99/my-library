package com.toofan.soft.qsb.api

import com.toofan.soft.qsb.api.services.Logger
import org.json.JSONObject

interface IResponse {
    fun getResponse(jsonObject: JSONObject): IResponse? {
        return try {
            for (field in this.javaClass.declaredFields) {
                if (field.isAnnotationPresent(Field::class.java)) {
                    setField(field, jsonObject)
                }
            }
            this
        } catch (e: Exception) {
            Logger.log("IResponse.getResponse.catch(Exception)", e.message)
            e.printStackTrace()
            null
        }
    }

    fun setField(field: java.lang.reflect.Field, jsonObject: JSONObject) {
        field.isAccessible = true
        field.getAnnotation(Field::class.java)?.value?.let {
            val value = jsonObject[it]
            try {
                val castedValue = castFieldValue(value, field.type)
                field[this] = castedValue
            } catch (e: IllegalAccessException) {
                Logger.log("IResponse.setField.catch(IllegalAccessException)", e.message)
                e.printStackTrace()
            }
        }
    }

    private fun castFieldValue(value: Any, targetType: Class<*>): Any {
        return when (targetType) {
            Int::class.java -> (value as? Number)?.toInt() ?: 0
            Long::class.java -> (value as? Number)?.toLong() ?: 0
            Double::class.java -> (value as? Number)?.toDouble() ?: 0.0
            String::class.java -> value.toString() ?: ""
            Boolean::class.java -> (value as? Boolean) ?: false
            else -> value
        }
    }
}