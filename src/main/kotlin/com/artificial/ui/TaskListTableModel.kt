package com.artificial.ui

import javax.swing.table.AbstractTableModel
import com.artificial.model.Task
import com.artificial.util.swap
import com.artificial.util.size
import com.artificial.util.shift

/**
 * Created by Yurii on 4/5/2015.
 */
public class TaskListTableModel(val tasks: MutableList<Task>) : AbstractTableModel(), Reorderable {

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

    fun move(rowsRange: IntRange, destinationIndex: Int): IntRange {
        if (rowsRange contains destinationIndex) {
            return IntRange.EMPTY;
        }
        val rows = tasks.subList(rowsRange.start, rowsRange.end + 1)
        tasks.addAll(destinationIndex, rows)
        val rangeLength = rowsRange.size()
        val removeRange = if (rowsRange.end < destinationIndex) {
            rowsRange
        } else {
            rowsRange.shift(rangeLength + 1)
        }
        0..rangeLength forEach {
            tasks remove removeRange.start
        }
        fireTableDataChanged()
        if (rowsRange.start > destinationIndex){
            return IntRange(destinationIndex, destinationIndex + rangeLength)
        } else {
            return IntRange(destinationIndex, destinationIndex + rangeLength).shift(-rangeLength-1)
        }
    }
}