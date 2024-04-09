package robots.gui;

import robots.models.RobotModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Класс GameVisualizer представляет собой панель для визуализации состояния робота и его цели.
 */
public class GameVisualizer extends JPanel implements PropertyChangeListener {
    private int posX;
    private int posY;
    private double direction;
    private int targetX;
    private int targetY;

    /**
     * Создает новый объект GameVisualizer.
     * Устанавливает параметры двойной буферизации для улучшения производительности отрисовки и
     * подписывается на события изменения состояния робота через объект PropertyChangeSupport.
     *
     * @param propertyChangeSupport объект PropertyChangeSupport для подписки на события изменения состояния робота
     */
    public GameVisualizer(PropertyChangeSupport propertyChangeSupport) {
        propertyChangeSupport.addPropertyChangeListener(this);
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

    /**
     * Округляет переданное значение до ближайшего целого числа.
     *
     * @param value значение для округления
     * @return ближайшее целое число к переданному значению
     */
    private int round(double value) {
        return (int) (value + 0.5);
    }

    /**
     * Выполняет перерисовку панели, используя механизм событий AWT.
     * Этот метод вызывает repaint() события в потоке обработки событий AWT, чтобы обновить отображение панели.
     */
    private void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    /**
     * Обновляет поля объекта GameVisualizer с новыми значениями позиции робота, направления и координат цели.
     * Координаты позиции и цели округляются до ближайшего целого числа.
     *
     * @param posX     новая координата X позиции робота
     * @param posY     новая координата Y позиции робота
     * @param direction новое направление робота в радианах
     * @param targetX  новая координата X цели
     * @param targetY  новая координата Y цели
     */
    private void updateFields(double posX, double posY, double direction, int targetX, int targetY) {
        this.posX = round(posX);
        this.posY = round(posY);
        this.direction = direction;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("robot_update")) {
            RobotModel robotModel = (RobotModel) evt.getNewValue();
            updateFields(robotModel.getPosX(),
                    robotModel.getPosY(),
                    robotModel.getDirection(),
                    robotModel.getTargetX(),
                    robotModel.getTargetY());
            onRedrawEvent();
        }
    }
}
