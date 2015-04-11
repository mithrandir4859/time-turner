package com.artificial.repository

import com.artificial.model.Day
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import java.time.LocalDate
import java.util.HashMap

/**
 * Created by Yurii on 4/11/2015.
 */
public class Dao {
    private val days = CacheBuilder.newBuilder().build(
            object : CacheLoader<LocalDate, Day>() {
                override fun load(date: LocalDate) = Day()
            })

    public fun get(date: LocalDate): Day = days[date]
    public fun save(){

    }
}