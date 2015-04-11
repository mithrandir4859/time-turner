package com.artificial.ui.dndsupport

import java.awt.dnd.DragGestureListener
import java.awt.dnd.DragGestureEvent
import java.awt.dnd.DnDConstants
import java.awt.dnd.DragSource
import com.artificial.ui.task.TaskEditor

/**
 * Created by Yurii on 4/5/2015.
 */
public class DragGestureImpl : DragGestureListener {

    override fun dragGestureRecognized(event: DragGestureEvent) {
        val createTaskPanel = event.getComponent() as TaskEditor
        val cursor = if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            DragSource.DefaultCopyDrop
        } else {
            DragSource.DefaultMoveDrop
        }
        event.startDrag(cursor, TransferableImpl(createTaskPanel.task))
    }
}