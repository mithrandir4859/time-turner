package com.artificial.repository

import com.artificial.model.Day
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.slf4j.LoggerFactory
import java.io.*
import java.time.LocalDate
import java.util.HashMap

/**
 * Created by Yurii on 4/11/2015.
 */
public class Dao {
    val FILE_NAME = "time-turner.dat"
    val file = File(FILE_NAME)
    val log = LoggerFactory.getLogger(javaClass)

    private var days = CacheBuilder.newBuilder().build(
            object : CacheLoader<LocalDate, Day>() {
                override fun load(date: LocalDate) = Day()
            })

    public fun get(date: LocalDate): Day = days[date]

    public fun save(){
        try {
            val fileOutputStream = FileOutputStream(FILE_NAME)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream writeObject HashMap(days.asMap())
        } catch (e: Exception){
            log.error("", e)
        }
    }

    public fun load(){
        try {
            if (!file.exists()){
                return
            }
            val fileInputStream = FileInputStream(FILE_NAME)
            val objectInputStream = ObjectInputStream(fileInputStream)
            val map = objectInputStream.readObject()
            map as Map<LocalDate, Day>
            days.asMap().putAll(map)
        } catch (e: Exception){
            log.error("", e)
        }
    }
}