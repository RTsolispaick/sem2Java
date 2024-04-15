package robots.controller;

import robots.gui.GameVisualizer;
import robots.models.RobotModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс, представляющий контроллер игры.
 */
public class GameController {
    private final RobotModel robotModel;

    /**
     * Конструктор класса GameController.
     */
    public GameController(RobotModel robotModel, GameVisualizer gameVisualizer) {
        this.robotModel = robotModel;

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
}
