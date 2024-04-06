package robots.gui;

import robots.models.RobotState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameVisualizer extends JPanel implements PropertyChangeListener {
    private final PropertyChangeSupport propertyChangeSupport;

    private int posX;
    private int posY;
    private double direction;
    private int targetX;
    private int targetY;

    public GameVisualizer(PropertyChangeSupport pcs) {
        this.propertyChangeSupport = pcs;
        pcs.addPropertyChangeListener(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifyListeners(e.getPoint());
            }
        });

        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, posX, posY, direction);
        drawTarget(g2d, targetX, targetY);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        g.rotate(direction, x, y);
        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x + 10, y, 5, 5);
        g.rotate(-direction, x, y);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private int round(double value) {
        return (int) (value + 0.5);
    }

    private void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private void updateViewFields(RobotState robotState) {
        posX = round(robotState.getPosX());
        posY = round(robotState.getPosY());
        direction = robotState.getDirection();
        targetX = robotState.getTargetX();
        targetY = robotState.getTargetY();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("robot_update")) {
            RobotState robotState = (RobotState) evt.getNewValue();
            updateViewFields(robotState);
            onRedrawEvent();
        }
    }

    private void notifyListeners(Point point) {
        propertyChangeSupport.firePropertyChange("target_update", null, point);
    }
}
