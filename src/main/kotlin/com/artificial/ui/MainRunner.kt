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
import javax.swing.*

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

        val frame = frame("Time Turner") {
            val contentPane = getContentPane()
            jtpMainTabs.add(dayPanel, "Day")
            jtpMainTabs.add(jCalendar, "Calendar")
            contentPane.add(jtpMainTabs)
            pack()
            setLocationRelativeTo(null)
            setDefaultCloseOperation(3)
            addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent) {
                    dao.save()
                }
            })
            val menuBar = JMenuBar()
            menuBar add createFileMenu()
            menuBar add createTemplateMenu()
            setJMenuBar(menuBar)
        }
        frame setVisible true
    }

    private fun createFileMenu(): JMenu {
        val fileMenu = JMenu("File")

        val jmiSave = JMenuItem("Save to file")
        jmiSave addActionListener { dao.save() }
        fileMenu add jmiSave

        val jmiReload = JMenuItem("Reload from file")
        jmiReload addActionListener {
            dao.load()
            val selectedDate = jCalendar.getCalendar().toLocalDate()
            dayPanel.day = dao[selectedDate]
        }
        fileMenu add jmiReload

        val jmiSetSaveLocation = JMenuItem("Set save location")
        jmiSetSaveLocation setEnabled false
        fileMenu add jmiSetSaveLocation

        return fileMenu
    }

    private fun createTemplateMenu(): JMenu {
        val templateMenu = JMenu("Template")

        val jmiSave = JMenuItem("Save as a template")
        jmiSave addActionListener { }
        templateMenu add jmiSave

        val jmiLoad = JMenuItem("Load from a template")
        jmiLoad addActionListener {  }
        templateMenu add jmiLoad

        return templateMenu
    }
}

