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

/**
 * Created by Yurii on 4/5/2015.
 */
public class TableRowTransferHandler(val table: JTable) : TransferHandler() {
    private val localObjectFlavor = ActivationDataFlavor(javaClass<Integer>(), DataFlavor.javaJVMLocalObjectMimeType, "Integer Row Index")

    override fun createTransferable(c: JComponent): Transferable {
        assert(c == table)
        val dataHandler = DataHandler(table.getSelectedRow(), localObjectFlavor.getMimeType())
        return dataHandler
    }

    override fun canImport(info: TransferHandler.TransferSupport): Boolean {
        val canImport =
                info.getComponent() == table
                        && info.isDrop()
//                        && info isDataFlavorSupported localObjectFlavor
        table.setCursor(if (canImport) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop)
        return canImport
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    override fun importData(info: TransferHandler.TransferSupport): Boolean {
        val target = info.getComponent() as JTable
        val dropLocation = info.getDropLocation() as JTable.DropLocation
        var index = dropLocation.getRow()
        val max = table.getModel().getRowCount()
        if (index < 0 || index > max)
            index = max
        target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
        try {
            val rowFrom = info.getTransferable().getTransferData(localObjectFlavor) as Int
            if (rowFrom != -1 && rowFrom != index) {
                (table.getModel() as Reorderable).reorder(rowFrom, index)
                if (index > rowFrom)
                    index--
                target.getSelectionModel().addSelectionInterval(index, index)
                return true
            }
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