package com.artificial.ui

import javax.swing.JPanel
import com.artificial.model.Day
import java.awt.BorderLayout
import java.awt.dnd.DragSource
import java.awt.dnd.DnDConstants
import com.artificial.ui.dndsupport.DragGestureImpl

/**
 * Created by Yurii on 4/4/2015.
 */
public class MainPanel(var day: Day = Day()) : JPanel(BorderLayout()) {
    private val taskCreatorPanel = TaskPanel();

    {
        val dragSource = DragSource()
        dragSource.createDefaultDragGestureRecognizer(taskCreatorPanel, DnDConstants.ACTION_COPY, DragGestureImpl())
        add(taskCreatorPanel, BorderLayout.NORTH)
        add(DayTasksPanel(day), BorderLayout.CENTER)
    }
}