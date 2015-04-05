package com.artificial.ui.dndsupport

import java.awt.datatransfer.DataFlavor

/**
 * Created by Yurii on 4/5/2015.
 */
public fun <T> createDataFlavor(clazz: Class<T>): DataFlavor = DataFlavor(clazz, clazz.getSimpleName())