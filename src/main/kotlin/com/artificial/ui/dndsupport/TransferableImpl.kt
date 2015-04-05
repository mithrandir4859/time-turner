package com.artificial.ui.dndsupport

import java.awt.datatransfer.Transferable
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.UnsupportedFlavorException

/**
 * Created by Yurii on 4/5/2015.
 */
public class TransferableImpl<T>(val data: T) : Transferable{
    val dataFlavor = createDataFlavor(data.javaClass)

    override fun getTransferDataFlavors() = array(dataFlavor)

    override fun isDataFlavorSupported(flavor: DataFlavor) = dataFlavor equals flavor

    override fun getTransferData(flavor: DataFlavor): T {
        if (dataFlavor equals flavor){
            return data
        } else {
            throw UnsupportedFlavorException(flavor)
        }
    }
}