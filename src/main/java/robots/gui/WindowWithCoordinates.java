package robots.gui;

import robots.models.RobotState;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;

public class WindowWithCoordinates extends JInternalFrame implements Stateful, PropertyChangeListener {
    JLabel xCord;
    JLabel yCord;

    /**
     * Формат вывода координат робота
     */
    private final DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Создает окно, подписывается на изменения переданной модели.
     */
    public WindowWithCoordinates(PropertyChangeSupport pcs) {
        super("Internal Frame", true, true, true, true);
        pcs.addPropertyChangeListener(this);

        xCord = new JLabel("x");
        yCord = new JLabel("y");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(xCord);
        panel.add(yCord);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setBounds(730, 10,
                150, 100);
    }

    @Override
    public String getIDFrame() {
        return "robot_location";
    }

    /**
     * Обновляет текстовые поля gui новыми координатами робота
     */
    private void updateCoords(double x, double y) {
        xCord.setText("x: " + df.format(x));
        yCord.setText("y: " + df.format(y));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("robot_update")) {
            RobotState gameState = (RobotState) evt.getNewValue();
            updateCoords(gameState.getPosX(), gameState.getPosY());
        }
    }
}
