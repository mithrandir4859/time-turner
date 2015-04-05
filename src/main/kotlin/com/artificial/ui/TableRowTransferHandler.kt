package com.artificial.ui

import javax.activation.ActivationDataFlavor
import javax.activation.DataHandler
import javax.swing.JComponent
import javax.swing.JTable
import javax.swing.TransferHandler
import java.awt.Cursor
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.dnd.DragSource
import com.artificial.util.selectedRowsRange

/**
 * Created by Yurii on 4/5/2015.
 */
public class TableRowTransferHandler(val table: JTable) : TransferHandler() {
    private val localObjectFlavor = ActivationDataFlavor(javaClass<IntRange>(), DataFlavor.javaJVMLocalObjectMimeType, "Integer Row Index")
    private val tableModel = table.getModel() as TaskListTableModel

    override fun createTransferable(c: JComponent): Transferable {
        assert(c == table)
        val dataHandler = DataHandler(table.selectedRowsRange(), localObjectFlavor.getMimeType())
        return dataHandler
    }

    override fun canImport(info: TransferHandler.TransferSupport): Boolean {
        val canImport =
                info.getComponent() == table
                && info.isDrop()
                && info isDataFlavorSupported localObjectFlavor
        table.setCursor(if (canImport) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop)
        return canImport
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    override fun importData(info: TransferHandler.TransferSupport): Boolean {
        val target = info.getComponent() as JTable
        val dropLocation = info.getDropLocation() as JTable.DropLocation
        var dropIndex = dropLocation.getRow()
        val max = table.getModel().getRowCount()
        if (dropIndex < 0 || dropIndex > max) {
            dropIndex = max
        }
        target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
        try {
            val dragRange = info.getTransferable().getTransferData(localObjectFlavor) as IntRange
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