/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;

class StayOpenCheckBoxMenuItem extends JCheckBoxMenuItem {
    public StayOpenCheckBoxMenuItem(String text) {
        super(text);
        getModel().setArmed(true);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_RELEASED && contains(e.getPoint()) && getModel().isArmed()) {
            doClick();
            setArmed(true);
        } else {
            super.processMouseEvent(e);
        }
    }
}
*/
