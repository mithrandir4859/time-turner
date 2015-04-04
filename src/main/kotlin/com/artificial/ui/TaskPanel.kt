package com.artificial.ui

import javax.swing.JPanel
import java.awt.GridBagLayout
import javax.swing.JTextField
import java.awt.GridBagConstraints
import javax.swing.JLabel
import com.artificial.model.Task
import java.time.Duration
import javax.swing.BorderFactory

/**
 * Created by Yurii on 4/4/2015.
 */
public class TaskPanel(task: Task = Task()) : JPanel(GridBagLayout()) {
    private val jtfDuration = JTextField(task.duration.toMinutes().toString())
    private val jtfDescription = JTextField(task.description);

    {
        add(JLabel("Duration: "), gridBagConstraints {
            gridx = 0
            gridy = 0
        })

        add(jtfDuration, gridBagConstraints {
            gridx = 1
            gridy = 0
        })

        add(JLabel("Description: "), gridBagConstraints {
            gridx = 0
            gridy = 1
        })

        add(jtfDuration, gridBagConstraints {
            gridx = 1
            gridy = 1
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