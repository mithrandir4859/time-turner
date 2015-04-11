package com.artificial.ui

import javax.swing.UIManager
import javax.swing.TransferHandler
import java.awt.datatransfer.DataFlavor
import javax.activation.ActivationDataFlavor
import javax.swing.JTable
import java.awt.datatransfer.Transferable
import javax.swing.JComponent
import javax.activation.DataHandler
import java.awt.dnd.DragSource
import java.awt.Cursor
import com.artificial
import org.jdesktop.swingx.JXDatePicker
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by Yurii on 4/4/2015.
 */
fun main(args: Array<String>) {
    // https://github.com/bulenkov/Darcula
    // http://blog.jetbrains.com/blog/2013/06/11/inside-darcula-look-and-feel-an-interview-with-konstantin-bulenkov/
    UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf")
    val context = ClassPathXmlApplicationContext("SpringBeans.xml");
    context.getBeanDefinitionNames() forEach {
        println(it)
    }
    val dayPanel = (context getBean "dayPanel") as DayPanel

    artificial.util.frame("Scheduler") {
//        add(JXDatePicker())
        add(dayPanel)
        pack()
        setLocationRelativeTo(null)
        setDefaultCloseOperation(3)
        setVisible(true)
    }
}
