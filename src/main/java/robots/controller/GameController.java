package robots.controller;

import robots.gui.GameVisualizer;
import robots.models.RobotModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс, представляющий контроллер игры.
 */
public class GameController {
    private final RobotModel robotModel;
    private final GameVisualizer gameVisualizer;

    /**
     * Конструктор класса GameController.
     *
     * @param propertyChangeSupport объект PropertyChangeSupport для поддержки событий изменения свойств
     */
    public GameController(PropertyChangeSupport propertyChangeSupport) {
        robotModel = new RobotModel(propertyChangeSupport);
        gameVisualizer = new GameVisualizer(propertyChangeSupport);

        gameVisualizer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robotModel.setTargetPosition(e.getPoint());
            }
        });
    }

    /**
     * Запускает игровой цикл.
     * Создает и запускает таймер для обновления состояния игры.
     */
    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                robotModel.update();
            }
        }, 0, 10);
    }

    /**
     * Получает объект GameVisualizer, представляющий визуализацию игры.
     *
     * @return объект GameVisualizer
     */
    public GameVisualizer getGameVisualizer() {
        return gameVisualizer;
    }
}
