package com.artificial.ui.task

import com.artificial.model.Task
import java.time.Duration
import java.time.LocalTime

/**
 * Created by Yurii on 4/5/2015.
 */
public enum class Column(
        public val isEditable: Boolean = true,
        public val clazz: Class<*> = javaClass<Any>()
) {
    DURATION : Column(clazz = javaClass<Duration>()) {
        override fun get(task: Task) = task.duration
        override fun set(task: Task, value: Any) {
            task.duration = value as Duration
        }
    }
    DESCRIPTION : Column(clazz = javaClass<String>()) {
        override fun get(task: Task) = task.description
        override fun set(task: Task, value: Any) {
            task.description = value as String
        }
    }
    START_TIME : Column(false, javaClass<LocalTime>()) {
        override fun get(task: Task) = task.startTime ?: ""
    }
    END_TIME : Column(false, javaClass<LocalTime>()) {
        override fun get(task: Task) = task.endTime ?: ""
    }

    abstract public fun get(task: Task): Any
    open public fun set(task: Task, value: Any) {
        throw UnsupportedOperationException()
    }

}