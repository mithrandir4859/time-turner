package com.artificial.ui

import javax.swing.JPanel
import com.artificial.model.Day
import java.awt.BorderLayout
import java.awt.dnd.DragSource
import java.awt.dnd.DnDConstants
import com.artificial.ui.dndsupport.DragGestureImpl
import com.artificial.ui.task.TaskEditor
import com.artificial.ui.task.TaskListPanel

/**
 * Created by Yurii on 4/4/2015.
 */
public class DayPanel(var day: Day = Day()) : JPanel(BorderLayout()) {
    private val taskEditor = TaskEditor()

    init {
        val dragSource = DragSource()
        dragSource.createDefaultDragGestureRecognizer(taskEditor, DnDConstants.ACTION_COPY, DragGestureImpl())
        add(taskEditor, BorderLayout.NORTH)
        add(TaskListPanel(day), BorderLayout.CENTER)
    }
}