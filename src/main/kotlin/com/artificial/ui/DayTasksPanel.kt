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
import javax.swing.JTable
import javax.swing.ListSelectionModel
import com.artificial.ui.dndsupport.TableRowTransferHandler
import org.slf4j.LoggerFactory
import com.artificial.util.table
import javax.swing.AbstractCellEditor
import javax.swing.DefaultCellEditor
import javax.swing.JTextField
import com.artificial.util.ColumnModel
import javax.swing.table.*

/**
 * Created by Yurii on 4/4/2015.
 */
class DayTasksPanel(var day: Day = Day()) : JPanel() {
    val LOGGER = LoggerFactory.getLogger(javaClass)

    val taskListModel = TaskListTableModel(day.tasks);

    fun createColumnModel(): TableColumnModel {
        val columnModel = DefaultTableColumnModel()
        var i = 0
        ColumnModel().columns forEach {
            val column = TableColumn(i++)
            column setHeaderValue it.title

            column setCellRenderer object : DefaultTableCellRenderer() {
                override fun getTableCellRendererComponent(table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component {
                    val component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
                    if (component is JLabel) {
                        component setText (it asString value)
                    }
                    return component
                }
            }

            val textField = JTextField()
            column setCellEditor object : DefaultCellEditor(textField) {
                override fun getCellEditorValue(): Any {
                    return it parseString textField.getText()
                }
            }

            columnModel addColumn column
        }
        return columnModel
    }

    {
        if (day.tasks.isEmpty()) {
            0..1 forEach {
                val task = Task()
                task.description = "Some description $it"
                task.duration = Duration.ofMinutes(it.toLong())
                day.tasks.add(task)
            }
        }

        object : AbstractCellEditor() {
            override fun getCellEditorValue(): Any? {
                throw UnsupportedOperationException()
            }
        }

        val editor = object : DefaultCellEditor(JTextField()) {
            override fun getCellEditorValue(): Any? {
                throw UnsupportedOperationException()
            }
        }

        val table = table(taskListModel, createColumnModel()) {
            setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION)
            setDragEnabled(true)
            setDropMode(DropMode.INSERT_ROWS)
            setTransferHandler(TableRowTransferHandler(this))
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