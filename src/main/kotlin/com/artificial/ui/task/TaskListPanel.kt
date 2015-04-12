package com.artificial.ui.task

import com.artificial
import com.artificial.model.Day
import com.artificial.model.Task
import com.artificial.ui.dndsupport.TableRowTransferHandler
import com.artificial.ui.task.Column
import com.artificial.util
import com.artificial.util.getHoursMinutes
import com.artificial.util.toNiceString
import org.slf4j.LoggerFactory
import java.awt.Component
import java.awt.GridLayout
import java.time.Duration
import javax.swing.*
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.DefaultTableColumnModel
import javax.swing.table.TableColumn

/**
 * Created by Yurii on 4/4/2015.
 */
class TaskListPanel(day: Day = Day()) : JPanel() {
    val LOGGER = LoggerFactory.getLogger(javaClass)


    val columnModel = DefaultTableColumnModel()
    val table = util.table(TasksTableModel(day.tasks), columnModel) {
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION)
        setDragEnabled(true)
        setDropMode(DropMode.INSERT_ROWS)
        setTransferHandler(TableRowTransferHandler(this))
        setRowHeight(24)

        setDefaultRenderer(javaClass<Duration>(), object : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component {
                val component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
                component as JLabel
                value as Duration
                component setText value.getHoursMinutes()
                return component
            }
        })

        Column.values() forEach {
            val column = TableColumn(it.ordinal())
            column setHeaderValue it.toNiceString()
            column setCellEditor null
            columnModel addColumn  column
        }

        setDefaultEditor(javaClass<Duration>(), DurationCellEditor())

        val popup = JPopupMenu()
        val jmiRemove = JMenuItem("Remove selected")
        jmiRemove addActionListener {
            try {
                val rows = getSelectedRows()
                val model = getModel()
                model as TasksTableModel
                model remove rows
            } catch (e: Exception){
                LOGGER.error("", e)
            }
        }
        popup add jmiRemove
        setComponentPopupMenu(popup)
    }

    var day = day
    set(day: Day){
        val tasksTableModel = TasksTableModel(day.tasks)
        table setModel tasksTableModel
        tasksTableModel addTableModelListener {
            try {
                day.schedule()
            } catch (e: Exception) {
                LOGGER.error("", e)
            }
        }
    }

    init {
        setLayout(GridLayout(1, 1))
        add(JScrollPane(table))
        setBorder(BorderFactory.createTitledBorder("Tasks"))
        day.schedule()
    }
}