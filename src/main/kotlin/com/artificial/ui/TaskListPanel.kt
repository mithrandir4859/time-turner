package com.artificial.ui

import com.artificial.model.Day
import com.artificial.model.Task
import java.awt.Component
import java.time.Duration
import java.awt.datatransfer.Transferable
import java.awt.dnd.DnDConstants
import java.awt.datatransfer.DataFlavor
import java.awt.GridLayout
import javax.swing.table.AbstractTableModel
import com.artificial.ui.dndsupport.TableRowTransferHandler
import com.artificial.util.getHoursMinutes
import org.slf4j.LoggerFactory
import com.artificial.util.table
import org.apache.commons.lang3.StringUtils
import java.time.format.DateTimeParseException
import javax.swing.*
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableColumn
import javax.swing.table.DefaultTableColumnModel

/**
 * Created by Yurii on 4/4/2015.
 */
class TaskListPanel(var day: Day = Day()) : JPanel() {
    val LOGGER = LoggerFactory.getLogger(javaClass)

    val taskListModel = TasksTableModel(day.tasks)

    init {
        if (day.tasks.isEmpty()) {
            0..1 forEach {
                val task = Task()
                task.description = "Some description $it"
                task.duration = Duration.ofMinutes(it.toLong())
                day.tasks.add(task)
            }
        }

        val columnModel = DefaultTableColumnModel()
        val table = table(taskListModel, columnModel) {
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
                column setHeaderValue it
                column setCellEditor null
                columnModel addColumn  column
            }

            setDefaultEditor(javaClass<Duration>(), DurationCellEditor())
        }

        taskListModel addTableModelListener {
            try {
                day.schedule()
            } catch (e: Exception) {
                LOGGER.error("", e)
            }
        }

        setLayout(GridLayout(1, 1))
        add(JScrollPane(table))
        setBorder(BorderFactory.createTitledBorder("Tasks"))
        day.schedule()
    }
}