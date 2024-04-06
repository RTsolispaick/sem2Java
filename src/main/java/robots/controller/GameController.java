package robots.controller;

import robots.models.Robot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class GameController implements ActionListener {
    private final Robot robot;

    public GameController(PropertyChangeSupport pcs) {
        this.robot = new Robot(pcs);
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        robot.update();
    }
}
