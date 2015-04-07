package com.artificial.util

import java.util.ArrayList
import java.time.Duration
import java.time.temporal.TemporalUnit
import java.time.temporal.ChronoUnit
import java.time.LocalTime
import java.time.LocalDate
import org.apache.commons.lang3.StringUtils

/**
 * Created by Yurii on 4/7/2015.
 */
public class ColumnModel {
    public val columns: MutableList<Column> = ArrayList();

    val duration = object: Column("Duration"){
        val minutesPerHour = 60
        public override fun asString(obj: Any): String {
            if (obj is Duration) {
                val totalMinutes = obj.toMinutes()
                val hours = totalMinutes / minutesPerHour
                val minutes = totalMinutes % minutesPerHour
                return "$hours:$minutes"
            } else {
                return "Invalid type"
            }
        }

        public override fun parseString(stringRepresentation: String): Duration {
            if (StringUtils.isNumeric(stringRepresentation)){
                val minutes = stringRepresentation.toLong()
                return Duration.ofMinutes(minutes)
            } else {
                return Duration.ZERO
            }
        }
    }

    {
        columns.add(duration)
    }

}