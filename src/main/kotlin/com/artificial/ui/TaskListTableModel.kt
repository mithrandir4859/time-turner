package com.artificial.ui

import javax.swing.table.AbstractTableModel
import com.artificial.model.Task
import com.artificial.util.swap
import com.artificial.util.size
import com.artificial.util.shift
import java.time.Duration

/**
 * Created by Yurii on 4/5/2015.
 */
public class TaskListTableModel(val tasks: MutableList<Task>) : AbstractTableModel() {
    private val columns = Column.values()

    override fun getRowCount() = tasks.size()

    override fun getColumnCount() = columns.size()

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        val column = columns[columnIndex]
        val row = tasks[rowIndex]
        return column[row]
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return columns[columnIndex].clazz
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int) = columns[columnIndex].isEditable

    override fun setValueAt(aValue: Any, rowIndex: Int, columnIndex: Int) {
        val column = columns[columnIndex]
        val row = tasks[rowIndex]
        column[row] = aValue
    }

    fun move(rowsRange: IntRange, destinationIndex: Int): IntRange {
        if (destinationIndex in rowsRange) {
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