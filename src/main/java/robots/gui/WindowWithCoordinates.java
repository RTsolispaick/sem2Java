package robots.gui;

import robots.models.RobotModel;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;

/**
 * Класс WindowWithCoordinates представляет внутреннее окно, отображающее координаты робота.
 */
public class WindowWithCoordinates extends JInternalFrame implements Stateful, PropertyChangeListener {
    private final JLabel xCord;
    private final JLabel yCord;
    private final DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Конструктор класса WindowWithCoordinates.
     */
    public WindowWithCoordinates(PropertyChangeSupport propertyChangeSupport) {
        super("Внутреннее окно", true, true, true, true);
        propertyChangeSupport.addPropertyChangeListener(this);

        xCord = new JLabel("x");
        yCord = new JLabel("y");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(xCord);
        panel.add(yCord);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        pack();
    }

    /**
     * Обновляет отображаемые координаты на основе состояния робота.
     *
     * @param x Координата x робота.
     * @param y Координата y робота.
     */
    private void updateCoords(double x, double y) {
        xCord.setText("x: " + df.format(x));
        yCord.setText("y: " + df.format(y));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("robot_update")) {
            RobotModel robotModel = (RobotModel) evt.getNewValue();
            updateCoords(robotModel.getPosX(), robotModel.getPosY());
        }
    }

    @Override
    public String getIDFrame() {
        return "robot_location";
    }
}

