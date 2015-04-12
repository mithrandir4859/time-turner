package com.artificial.util

import org.apache.commons.lang3.text.WordUtils
import javax.swing.JPanel
import javax.swing.JFrame
import java.awt.GridBagConstraints
import java.time.Duration
import java.time.LocalDate
import java.util.Calendar
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

fun new<T>(aClass: Class<T>, init: T.() -> Unit): T {
    val instance = aClass.newInstance()
    instance.init()
    return instance
}

fun panel(init: JPanel.() -> Unit): JPanel {
    val p = JPanel()
    p.init()
    return p
}

public fun table(model: TableModel, columnModel: TableColumnModel, init: JTable.() -> Unit): JTable {
    val table = object: JTable(model, columnModel){
        override fun getScrollableTracksViewportHeight() = true
    }
    table.init()
    return table
}

fun <T> MutableList<T>.swap(x: Int, y: Int) {
    val tmp = this[x] // 'this' corresponds to the list
    this[x] = this[y]
    this[y] = tmp
}

public fun IntRange.size(): Int = this.end - this.start

public fun Calendar.toLocalDate(): LocalDate =
        LocalDate.of(
                this[Calendar.YEAR],
                1 + this[Calendar.MONTH],
                this[Calendar.DAY_OF_MONTH]
        )

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

/**
 * Transforms a string:
 * "SOME_ENUM_NAME" -> "Some Enum Name"
 * "PREFERRED_SIZE" -> "Preferred Size"
 */
public fun String.toNiceString(): String = WordUtils.capitalize(toLowerCase().replaceAll("_", " "))

public fun <E : Enum<E>> Enum<E>.toNiceString(): String = name().toNiceString()

