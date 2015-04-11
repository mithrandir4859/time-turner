package com.artificial.ui

import com.artificial.model.Day
import com.artificial.repository.Dao
import com.artificial.util.toLocalDate
import org.springframework.beans.factory.annotation.Autowired
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.util.Calendar
import javax.swing.JTabbedPane

/**
 * Created by Yurii on 4/11/2015.
 */
public class CalendarListener: PropertyChangeListener {
    Autowired val jtpMainTabs = JTabbedPane()
    Autowired val dao = Dao()
    Autowired val dayPanel = DayPanel()

    override fun propertyChange(evt: PropertyChangeEvent) {
        val calendar = evt.getNewValue() as Calendar
        val currentDate = calendar.toLocalDate()
        dayPanel.day = dao[currentDate]
        jtpMainTabs.setSelectedIndex(0)
        println("${evt.getPropertyName()} $currentDate")
    }
}