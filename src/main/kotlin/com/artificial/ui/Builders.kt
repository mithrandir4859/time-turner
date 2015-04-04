package com.artificial.ui

import javax.swing.JPanel
import javax.swing.JFrame
import java.awt.GridBagConstraints
import javax.swing.JList

/**
 * Created by Yurii on 4/4/2015.
 */

fun frame(title : String, init : JFrame.() -> Unit) : JFrame {
    val result = JFrame(title)
    result.init()
    return result
}

fun panel(init: JPanel.() -> Unit): JPanel {
    val p = JPanel()
    p.init()
    return p
}

fun <T> list(init: JList<T>.() -> Unit): JList<T> {
    val list = JList<T>()
    list.init()
    return list
}



fun gridBagConstraints(init : GridBagConstraints.() -> Unit): GridBagConstraints {
    val result = GridBagConstraints()
    result.init()
    return result
}