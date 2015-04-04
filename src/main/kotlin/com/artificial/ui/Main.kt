package com.artificial.ui

import javax.swing.UIManager

/**
 * Created by Yurii on 4/4/2015.
 */
fun main(args: Array<String>) {
    // https://github.com/bulenkov/Darcula
    // http://blog.jetbrains.com/blog/2013/06/11/inside-darcula-look-and-feel-an-interview-with-konstantin-bulenkov/
    UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf")

    frame("Scheduler") {
        add(DayPanel())
        pack()
        setLocationRelativeTo(null)
        setDefaultCloseOperation(3)
        setVisible(true)
    }
}