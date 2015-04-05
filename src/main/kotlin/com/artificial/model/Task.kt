package com.artificial.model

import java.io.Serializable
import java.time.Duration
import java.time.LocalTime

/**
 * Created by Yurii on 4/4/2015.
 */
public class Task : Serializable {
    public var duration: Duration = Duration.ZERO
    public var description: String = ""
    public var startTime: LocalTime? = null
    public val endTime: LocalTime?
        get() = if (startTime == null) {
            null
        } else {
            startTime!! + duration
        }
}