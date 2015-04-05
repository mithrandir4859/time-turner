package com.artificial.ui.dndsupport

import java.awt.dnd.DropTargetAdapter
import javax.swing.JPanel
import java.awt.dnd.DropTargetDropEvent
import java.awt.datatransfer.Transferable
import java.awt.dnd.DnDConstants
import com.artificial.model.Task
import com.artificial.ui.DayTasksPanel

/**
 * Created by Yurii on 4/5/2015.
 */
class DropTargetAdapterImpl(private val panel: DayTasksPanel) : DropTargetAdapter() {

    override fun drop(event: DropTargetDropEvent) {
        try {
            val transferable = event.getTransferable() as TransferableImpl<Task>
            val dataFlavor = transferable.dataFlavor
            val task = transferable getTransferData dataFlavor

            if (event isDataFlavorSupported dataFlavor) {
                event acceptDrop DnDConstants.ACTION_COPY
//                panel.add(JLabelAnimal(an))
                event dropComplete true
                panel.validate()
                return
            }
            event.rejectDrop()
        } catch (e: Exception) {
            e.printStackTrace()
            event.rejectDrop()
        }

    }
}