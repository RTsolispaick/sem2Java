package robots.gui;

import robots.models.RobotState;

import javax.swing.*;
import java.awt.*;

/**
 * Класс GameVisualizer представляет собой панель для визуализации состояния робота и его цели.
 */
public class GameVisualizer extends JPanel {
    private int posX;
    private int posY;
    private double direction;
    private int targetX;
    private int targetY;

    /**
     * Создает новый объект GameVisualizer.
     * Устанавливает параметры двойной буферизации для улучшения производительности отрисовки.
     */
    public GameVisualizer() {
        setDoubleBuffered(true);
    }

    /**
     * Метод отрисовки компонента.
     * Отображает робота и цель на игровом поле.
     *
     * @param g объект Graphics для рисования
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, posX, posY, direction);
        drawTarget(g2d, targetX, targetY);
    }

    /**
     * Отрисовывает робота на указанной позиции с заданным направлением.
     *
     * @param g         объект Graphics2D для рисования
     * @param x         координата X робота
     * @param y         координата Y робота
     * @param direction направление робота в радианах
     */
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

    /**
     * Отрисовывает цель на указанной позиции.
     *
     * @param g объект Graphics2D для рисования
     * @param x координата X цели
     * @param y координата Y цели
     */
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

    private void updateFields(RobotState robotState) {
        posX = round(robotState.getPosX());
        posY = round(robotState.getPosY());
        direction = robotState.getDirection();
        targetX = robotState.getTargetX();
        targetY = robotState.getTargetY();
    }

    /**
     * Обновляет состояние визуализатора на основе состояния робота.
     *
     * @param robotState объект RobotState, представляющий текущее состояние робота
     */
    public void update(RobotState robotState) {
        updateFields(robotState);
        onRedrawEvent();
    }
}
