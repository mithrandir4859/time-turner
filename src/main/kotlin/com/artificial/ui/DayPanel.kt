package com.artificial.ui

import javax.swing.JPanel
import com.artificial.model.Day
import java.awt.BorderLayout

/**
 * Created by Yurii on 4/4/2015.
 */
public class DayPanel(var day: Day = Day()) : JPanel(BorderLayout()) {
    private val taskCreatorPanel = TaskPanel();

    {
        add(taskCreatorPanel, BorderLayout.NORTH)
        add(DayTasksPanel(day), BorderLayout.CENTER)
    }



}