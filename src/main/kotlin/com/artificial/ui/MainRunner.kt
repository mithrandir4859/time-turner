package com.artificial.ui

import com.artificial.util.frame
import com.artificial.util.toLocalDate
import com.toedter.calendar.JCalendar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ClassPathXmlApplicationContext
import java.awt.BorderLayout
import java.time.LocalDate
import java.util.Calendar
import java.util.GregorianCalendar
import javax.swing.JTabbedPane
import javax.swing.UIManager

/**
 * Created by Yurii on 4/11/2015.
 */
public class MainRunner : Function0<Unit> {
    Autowired val dayPanel = DayPanel()
    val jCalendar = JCalendar()
    val mainTabs = JTabbedPane()

    override fun invoke() {

        jCalendar.addPropertyChangeListener ("calendar") {
            val calendar = it.getNewValue() as Calendar
            val localDate = calendar.toLocalDate()
            mainTabs.setSelectedIndex(0)
            println("${it.getPropertyName()} $localDate")
        }

        frame("Scheduler") {
            val contentPane = getContentPane()
            mainTabs.add(dayPanel, "Day")
            mainTabs.add(jCalendar, "Calendar")
            contentPane.add(mainTabs)
            pack()
            setLocationRelativeTo(null)
            setDefaultCloseOperation(3)
            setVisible(true)
        }
    }
}

