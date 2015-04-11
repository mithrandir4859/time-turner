package com.artificial.ui.task

import com.artificial.model.Task
import com.artificial.util.gridBagConstraints
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.time.Duration
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * Created by Yurii on 4/4/2015.
 */
public class TaskEditor(task: Task = Task()) : JPanel(GridBagLayout()) {
    private val jtfDuration = JTextField(task.duration.toMinutes().toString())
    private val jtfDescription = JTextField(task.description)

    init {
        add(JLabel("Duration: "), gridBagConstraints {
            gridx = 0
            gridy = 0
            weightx = .1
            anchor = GridBagConstraints.LINE_END
        })

        add(jtfDuration, gridBagConstraints {
            gridx = 1
            gridy = 0
            weightx = .9
            fill = GridBagConstraints.HORIZONTAL
        })

        add(JLabel("Description: "), gridBagConstraints {
            gridx = 0
            gridy = 1
            weightx = .1
            anchor = GridBagConstraints.LINE_END
        })

        add(jtfDescription, gridBagConstraints {
            gridx = 1
            gridy = 1
            weightx = .9
            fill = GridBagConstraints.HORIZONTAL
        })
        setBorder(BorderFactory.createTitledBorder("Task"))
    }

    public val task: Task
        get() {
            val task = Task()
            val durationAsLong = jtfDuration.getText().toLong()
            task.duration = Duration.ofMinutes(durationAsLong)
            task.description = jtfDescription.getText()
            return task
        }
}