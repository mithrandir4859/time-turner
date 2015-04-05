package com.artificial.ui.dndsupport

import com.artificial.util.selectedRowsRange
import javax.swing.JTable
import javax.swing.TransferHandler
import com.artificial.ui.TaskListTableModel
import javax.swing.JComponent
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.DataFlavor
import javax.activation.ActivationDataFlavor
import javax.activation.DataHandler
import java.awt.dnd.DragSource
import java.awt.Cursor
import com.artificial.model.Task

/**
 * Created by Yurii on 4/5/2015.
 */
public class TableRowTransferHandler(val table: JTable) : TransferHandler() {
    private val intRangeFlavor = ActivationDataFlavor(javaClass<IntRange>(), DataFlavor.javaJVMLocalObjectMimeType, "Index Range")
    private val taskDataFlavor = createDataFlavor(javaClass<Task>())
    private val tableModel = table.getModel() as TaskListTableModel

    private var currentDataFlavor = taskDataFlavor
    private var canImport = false
    private var dragRange = IntRange.EMPTY
    private var dropIndex = -1

    override fun createTransferable(c: JComponent): Transferable {
        assert(c == table)
        val dataHandler = DataHandler(table.selectedRowsRange(), intRangeFlavor.getMimeType())
        return dataHandler
    }

    override fun canImport(info: TransferHandler.TransferSupport): Boolean {
        canImport = info.getComponent() == table && info.isDrop()
        val dropLocation = info.getDropLocation() as JTable.DropLocation
        dropIndex = dropLocation.getRow()
        if (info isDataFlavorSupported intRangeFlavor) {
            currentDataFlavor = intRangeFlavor
            val transferData = info.getTransferable() getTransferData intRangeFlavor
            dragRange = transferData as IntRange
            if (dragRange.isEmpty() || dragRange.contains(dropIndex)) {
                canImport = false
            }
        } else if (info isDataFlavorSupported taskDataFlavor) {
            currentDataFlavor = taskDataFlavor
        } else {
            canImport = false
        }
        table.setCursor(if (canImport) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop)
        return canImport
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    override fun importData(info: TransferHandler.TransferSupport): Boolean {
        if (!canImport) {
            return false
        }
        table setCursor Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
        when (currentDataFlavor) {
            taskDataFlavor -> importTaskFromTaskCreator(info)
            intRangeFlavor -> handleTableRowRearrangement()
            else -> assert(false)
        }
        tableModel.fireTableDataChanged()
        return true
    }

    fun importTaskFromTaskCreator(info: TransferHandler.TransferSupport) {
        val transferable = info.getTransferable()
        val task = transferable getTransferData taskDataFlavor
        tableModel.tasks.add(dropIndex, task as Task)
    }

    fun handleTableRowRearrangement() {
        val selectionRange = tableModel.move(dragRange, dropIndex)
        table.getSelectionModel().addSelectionInterval(selectionRange.start, selectionRange.end)
        dragRange = IntRange.EMPTY
        dropIndex = -1
    }

    override fun exportDone(c: JComponent?, t: Transferable?, act: Int) {
        if (act == TransferHandler.MOVE) {
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
        }
    }

}