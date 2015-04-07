package com.artificial.util

/**
 * Created by Yurii on 4/7/2015.
 */
public trait Converter {
    public fun asString(obj: Any): String;
    public fun parseString(stringRepresentation: String): Any;
}