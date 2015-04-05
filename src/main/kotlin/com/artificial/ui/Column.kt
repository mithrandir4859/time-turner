package com.artificial.ui

import com.artificial.model.Task
import java.time.LocalTime
import java.time.Duration

/**
 * Created by Yurii on 4/5/2015.
 */
public enum class Column {
    DURATION {
        override fun getValue(task: Task) = task.duration
        override fun setValue(task: Task, value: Any) {
            task.duration = Duration.parse(value as String)
        }
    }
    DESCRIPTION {
        override fun getValue(task: Task) = task.description
        override fun setValue(task: Task, value: Any) {
            task.description = value as String
        }
    }
    START_TIME {
        override fun getValue(task: Task) = task.startTime
        override fun setValue(task: Task, value: Any) {
            task.startTime = LocalTime.of(4,4,4)
        }
    }
    END_TIME{
        override fun getValue(task: Task) = task.endTime
        override fun setValue(task: Task, value: Any){

        }
    };

    abstract public fun getValue(task: Task): Any?
    abstract public fun setValue(task: Task, value: Any)
}