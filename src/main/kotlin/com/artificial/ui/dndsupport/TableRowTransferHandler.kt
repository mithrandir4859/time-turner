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

    override fun createTransferable(c: JComponent): Transferable {
        assert(c == table)
        val dataHandler = DataHandler(table.selectedRowsRange(), intRangeFlavor.getMimeType())
        return dataHandler
    }

    override fun canImport(info: TransferHandler.TransferSupport): Boolean {
        val canImport =
                info.getComponent() == table
                && info.isDrop()
                && (info isDataFlavorSupported intRangeFlavor || info isDataFlavorSupported taskDataFlavor)
        table.setCursor(if (canImport) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop)
        return canImport
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    override fun importData(info: TransferHandler.TransferSupport): Boolean {
        val target = info.getComponent() as JTable
        val dropLocation = info.getDropLocation() as JTable.DropLocation
        val dropIndex = dropLocation.getRow()
        target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
        try {
            val transferData = info.getTransferable() getTransferData intRangeFlavor
            val dragRange = transferData as IntRange
            if (dragRange.isEmpty() || dragRange.contains(dropIndex)) {
                return false
            }
            val selectionRange = tableModel.move(dragRange, dropIndex)
            target.getSelectionModel().addSelectionInterval(selectionRange.start, selectionRange.end)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun exportDone(c: JComponent?, t: Transferable?, act: Int) {
        if (act == TransferHandler.MOVE) {
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
        }
    }

}