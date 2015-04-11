package com.artificial.util

import javax.swing.JPanel
import javax.swing.JFrame
import java.awt.GridBagConstraints
import java.time.Duration
import javax.swing.JList
import javax.swing.table.TableModel
import javax.swing.JTable
import javax.swing.table.TableColumnModel

/**
 * Created by Yurii on 4/4/2015.
 */

fun frame(title : String, init : JFrame.() -> Unit) : JFrame {
    val result = JFrame(title)
    result.init()
    return result
}

fun panel(init: JPanel.() -> Unit): JPanel {
    val p = JPanel()
    p.init()
    return p
}

public fun table(model: TableModel, columnModel: TableColumnModel, init: JTable.() -> Unit): JTable {
    val table = JTable(model, columnModel)
    table.init()
    return table
}

fun <T> list(init: JList<T>.() -> Unit): JList<T> {
    val list = JList<T>()
    list.init()
    return list
}

fun <T> MutableList<T>.swap(x: Int, y: Int) {
    val tmp = this[x] // 'this' corresponds to the list
    this[x] = this[y]
    this[y] = tmp
}

public fun IntRange.size(): Int = this.end - this.start

public fun Duration.getHoursMinutes(): String {
    val minutesPerHour = 60
    val totalMinutes = this.toMinutes()
    val hours = totalMinutes / minutesPerHour
    val minutes = totalMinutes % minutesPerHour
    val minutesStr = if (minutes < 10) "0$minutes" else minutes
    return "$hours:$minutesStr"
}

public fun IntRange.shift(shift: Int): IntRange = IntRange(this.start + shift, this.end + shift)

public fun JTable.selectedRowsRange(): IntRange{
    val selectedRows = getSelectedRows()
    if (selectedRows.isEmpty()){
        return IntRange.EMPTY
    } else {
        selectedRows.sort()
        return IntRange(selectedRows.first(), selectedRows.last())
    }
}

fun gridBagConstraints(init : GridBagConstraints.() -> Unit): GridBagConstraints {
    val result = GridBagConstraints()
    result.init()
    return result
}
