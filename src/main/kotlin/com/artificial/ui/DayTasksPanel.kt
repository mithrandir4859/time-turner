package com.artificial.ui

import com.artificial.model.Day
import javax.swing.JPanel
import javax.swing.BoxLayout
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.JLabel
import javax.swing.JList
import com.artificial.model.Task
import javax.swing.DefaultListModel
import javax.swing.ListModel
import javax.swing.ListCellRenderer
import java.awt.Component
import java.time.Duration
import javax.swing.JScrollPane
import javax.swing.DropMode
import javax.swing.UIManager
import javax.swing.TransferHandler
import javax.swing.JComponent
import java.awt.datatransfer.Transferable
import java.awt.dnd.DnDConstants
import java.awt.datatransfer.DataFlavor
import java.awt.GridLayout

/**
 * Created by Yurii on 4/4/2015.
 */
class DayTasksPanel(var day: Day = Day()) : JPanel() {
    val taskListModel = DefaultListModel<Task>()
    val taskList = JList(taskListModel);

    {
        if (day.tasks.isEmpty()) {
            0..10 forEach {
                val task = Task()
                task.description = "Some description $it"
                task.duration = Duration.ofMinutes(it.toLong())
                day.tasks.add(task)
            }
        }

        day.tasks forEach {
            taskListModel addElement it
            //            add(TaskPanel(it))
        }
        val listCellRenderer = taskList.getCellRenderer()
        taskList.setCellRenderer(object : ListCellRenderer<Task> {
            override fun getListCellRendererComponent(list: JList<out Task>, value: Task, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component? {
                val taskPanel = TaskPanel(value)
                val supercomp = listCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
                taskPanel setBackground  supercomp.getBackground()
                taskPanel setForeground  supercomp.getForeground()
                return taskPanel
            }
        })
        taskList setDragEnabled true
        taskList setDropMode DropMode.INSERT
        taskList setTransferHandler ListItemTransferHandler()
        setLayout(GridLayout(1,1))
        add(JScrollPane(taskList))
        setBorder(BorderFactory.createTitledBorder("Tasks"))
    }
}