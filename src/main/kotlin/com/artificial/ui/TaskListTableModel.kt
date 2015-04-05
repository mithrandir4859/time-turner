package com.artificial.ui

import javax.swing.table.AbstractTableModel
import com.artificial.model.Task
import com.artificial.util.swap

/**
 * Created by Yurii on 4/5/2015.
 */
public class TaskListTableModel(val tasks: MutableList<Task>): AbstractTableModel(), Reorderable {

    override fun reorder(fromIndex: Int, toIndex: Int) {
        tasks.swap(fromIndex, toIndex)
    }

    override fun getRowCount() = tasks.size()

    override fun getColumnCount() = Column.values().size()

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        val column = Column.values()[columnIndex]
        val row = tasks[rowIndex]
        return column getValue row
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int) = true

    override fun setValueAt(aValue: Any, rowIndex: Int, columnIndex: Int) {
        val column = Column.values()[columnIndex]
        val row = tasks[rowIndex]
        column.setValue(row, aValue)
    }
}