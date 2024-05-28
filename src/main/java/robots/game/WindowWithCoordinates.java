package robots.game;

import robots.locale.LanguageManager;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
    public WindowWithCoordinates() {
        super(LanguageManager.getStr("WindowWithCoordinates.title"),
                true,
                true,
                true,
                true);

        xCord = new JLabel("x");
        yCord = new JLabel("y");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(xCord);
        panel.add(yCord);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        pack();

        setLocation(770, 10);
    }

    /**
     * Обновляет отображаемые координаты на основе состояния робота.
     */
    private void updateCoords(RobotModel robotModel) {
        xCord.setText("x: " + df.format(robotModel.getPosX()));
        yCord.setText("y: " + df.format(robotModel.getPosY()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("model_update")) {
            updateCoords((RobotModel) evt.getNewValue());
        }
    }

    @Override
    public String getIDFrame() {
        return "robot_location";
    }
}

