package com.toofan.soft.qsb.api

@Target(AnnotationTarget.FIELD)
@Retention
annotation class Field(
    /**
     * Returns the name.
     *
     * @return the name
     */
    val value: String,
)