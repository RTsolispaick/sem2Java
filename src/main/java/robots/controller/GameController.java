package robots.controller;

import robots.gui.GameVisualizer;
import robots.models.Robot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;

/**
 * Класс, представляющий контроллер игры.
 */
public class GameController implements ActionListener {
    private final Robot robot;
    private final GameVisualizer gameVisualizer;

    /**
     * Конструктор класса GameController.
     *
     * @param propertyChangeSupport объект PropertyChangeSupport для поддержки событий изменения свойств
     */
    public GameController(PropertyChangeSupport propertyChangeSupport) {
        this.robot = new Robot(propertyChangeSupport);
        this.gameVisualizer = new GameVisualizer();

        gameVisualizer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robot.setTargetPosition(e.getPoint());
            }
        });
    }

    /**
     * Обработчик действия, вызываемый таймером.
     * Обновляет состояние робота и визуализатора игры.
     *
     * @param e объект ActionEvent, представляющий событие
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        robot.update();
        gameVisualizer.update(robot.getRobotState());
    }

    /**
     * Получает объект GameVisualizer, представляющий визуализацию игры.
     *
     * @return объект GameVisualizer
     */
    public GameVisualizer getGameVisualizer() {
        return gameVisualizer;
    }

    /**
     * Запускает игровой цикл.
     * Создает и запускает таймер для обновления состояния игры.
     */
    public void start() {
        Timer timer = new Timer(10, this);
        timer.start();
    }
}
