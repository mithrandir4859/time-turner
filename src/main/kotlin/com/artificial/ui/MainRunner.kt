package com.artificial.ui

import com.artificial.repository.Dao
import com.artificial.util.frame
import com.artificial.util.toLocalDate
import com.toedter.calendar.JCalendar
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ClassPathXmlApplicationContext
import java.awt.BorderLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.time.LocalDate
import java.util.Calendar
import java.util.GregorianCalendar
import javax.swing.JTabbedPane
import javax.swing.UIManager

/**
 * Created by Yurii on 4/11/2015.
 */
public class MainRunner : Function0<Unit> {
    val LOGGER = LoggerFactory.getLogger(javaClass)
    Autowired val dayPanel = DayPanel()
    val jCalendar = JCalendar()
    Autowired val jtpMainTabs = JTabbedPane()
    Autowired val calendarListener = CalendarListener()
    Autowired val dao = Dao()

    override fun invoke() {
        dao.load()
        Thread.setDefaultUncaughtExceptionHandler(object : Thread.UncaughtExceptionHandler {
            override fun uncaughtException(t: Thread, e: Throwable) {
                LOGGER.error("$t", e)
            }
        })
        dayPanel.day = dao[LocalDate.now()]
        jCalendar.addPropertyChangeListener ("calendar", calendarListener)

        frame("Scheduler") {
            val contentPane = getContentPane()
            jtpMainTabs.add(dayPanel, "Day")
            jtpMainTabs.add(jCalendar, "Calendar")
            contentPane.add(jtpMainTabs)
            pack()
            setLocationRelativeTo(null)
            setDefaultCloseOperation(3)
            setVisible(true)
            addWindowListener(object: WindowAdapter() {
                override fun windowClosing(e: WindowEvent) {
                    dao.save()
                }
            })

        }
    }
}

