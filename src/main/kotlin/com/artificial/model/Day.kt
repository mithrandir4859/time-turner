package com.artificial.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import com.artificial
import java.util.ArrayList

/**
 * Created by Yurii on 4/4/2015.
 */
public class Day : Serializable {
    public var date: LocalDate? = null
    public var startTime: LocalTime = LocalTime.of (6, 0, 0)
    public var endTime: LocalTime = LocalTime.of(22, 0, 0)
    public var tasks: MutableList<Task> = ArrayList()

    public fun schedule(){
        var currentTime = startTime
        tasks forEach {
            it.startTime = currentTime
            currentTime = it.endTime!!
        }
    }
}