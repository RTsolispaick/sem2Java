package robots.gui;

import robots.controller.GameController;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

/**
 * Класс GameWindow представляет собой внутреннее окно Swing, содержащее игровое поле.
 */
public class GameWindow extends JInternalFrame implements Stateful {
    /**
     * Создает новое игровое окно с заданным объектом PropertyChangeSupport.
     *
     * @param propertyChangeSupport объект PropertyChangeSupport для обмена данными с контроллером игры
     */
    public GameWindow(PropertyChangeSupport propertyChangeSupport) {
        super("Игровое поле", true, true, true, true);

        GameController gameController = new GameController(propertyChangeSupport);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameController.getGameVisualizer(), BorderLayout.CENTER);
        getContentPane().add(panel);

        gameController.start();
    }

    @Override
    public String getIDFrame() {
        return "GameWindow";
    }
}
