package com.toofan.soft.qsb.api

interface IRequest {
    val parameters1: String
        get() {
            val parameters = StringBuilder()
            for (field in this.javaClass.declaredFields) {
                if (field.isAnnotationPresent(Field::class.java)) {
                    try {
                        field.isAccessible = true
                        field.getAnnotation(Field::class.java)?.value?.let {
                            val value = field[this]?.toString()
                            parameters.append(it).append("=").append(value).append("&")
                        }
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
            if (parameters.isNotEmpty()) {
                parameters.deleteCharAt(parameters.length - 1)
            }
            return parameters.toString()
        }

    val parameters: HashMap<String, String>
        get() {
            val parameters = HashMap<String, String>()
            for (field in this.javaClass.declaredFields) {
                if (field.isAnnotationPresent(Field::class.java)) {
                    try {
                        field.isAccessible = true
                        field.getAnnotation(Field::class.java)?.value?.let { key ->
                            field[this]?.toString()?.let { value ->
                                parameters.put(key, value)
                            }
                        }
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
            return parameters
        }
}