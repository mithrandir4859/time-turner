package com.artificial.ui.task

import com.artificial.model.Task
import com.artificial.ui.task.Column
import com.artificial.util.shift
import com.artificial.util.size
import java.util.Arrays
import java.util.Collections
import java.util.Comparator
import javax.swing.table.AbstractTableModel

/**
 * Created by Yurii on 4/5/2015.
 */
public class TasksTableModel(val tasks: MutableList<Task>) : AbstractTableModel() {
    private val columns = Column.values()

    override fun getRowCount() = tasks.size()

    override fun getColumnCount() = columns.size()

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val column = columns[columnIndex]
        val row = tasks[rowIndex]
        return column[row]
    }

    override fun getColumnClass(columnIndex: Int)= columns[columnIndex].clazz

    override fun isCellEditable(rowIndex: Int, columnIndex: Int) = columns[columnIndex].isEditable

    override fun setValueAt(aValue: Any, rowIndex: Int, columnIndex: Int) {
        val column = columns[columnIndex]
        val row = tasks[rowIndex]
        column[row] = aValue
        fireTableDataChanged()
    }

    public fun remove(indices: IntArray){
        val comparator = Comparator.reverseOrder<Int>()
        val sortedIndices = indices.toArrayList()
        Collections.sort(sortedIndices, comparator)
        sortedIndices forEach {
            tasks remove it
        }
        fireTableDataChanged()
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