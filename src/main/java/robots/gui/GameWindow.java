package robots.gui;

import robots.controller.GameController;
import robots.locale.LanguageManager;
import robots.locale.MessageFormatCache;
import robots.log.Logger;
import robots.models.RobotModel;
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
        super(LanguageManager.getBundle().getString("game.title"),
                true,
                true,
                true,
                true);

        RobotModel robotModel = new RobotModel(propertyChangeSupport);
        GameVisualizer gameVisualizer = new GameVisualizer(propertyChangeSupport);
        GameController gameController = new GameController(robotModel, gameVisualizer);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        gameController.start();

        setBounds(230, 10,
                530, 530);

        String[] title = {LanguageManager.getBundle().getString("game.title")};
        Logger.debug(MessageFormatCache.getMessageFormat(
                        LanguageManager.getBundle().getString("logger.pattern")
                ).format(title)
        );
    }

    @Override
    public String getIDFrame() {
        return "GameWindow";
    }
}
