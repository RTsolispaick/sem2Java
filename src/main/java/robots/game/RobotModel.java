package robots.game;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Класс RobotModel представляет модель робота в игре.
 */
public class RobotModel {
    // Позиция робота
    private double posX = 100;
    private double posY = 100;
    private double direction = 0;
    private double rotationAngle = 0;
    private boolean rotationDirectionSet = false;

    // Константы для робота
    private final double SPEED = 1;
    private final double ANGLE = 0.01;

    // Целевая позиция
    private int targetX = 150;
    private int targetY = 100;

    private final PropertyChangeSupport pcs;

    /**
     * Конструктор класса Robot.
     */
    public RobotModel() {
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Обновляет состояние робота.
     */
    public void update() {
        double distanceToTarget = calculateDistance(targetX - posX, targetY - posY);

        if (distanceToTarget < 0.5) return;

        double angleToTarget = Math.atan2(targetY - posY, targetX - posX);
        determineRotationAngle(angleToTarget);

        moveRobot();

        notifyListeners();
    }

    /**
     * Устанавливает целевую позицию робота.
     *
     * @param targetPosition точка с координатами целевой позиции
     */
    public void setTargetPosition(Point targetPosition) {
        targetX = targetPosition.x;
        targetY = targetPosition.y;
        rotationDirectionSet = false;
    }

    /**
     * Выполняет перемещение робота в соответствии с текущим направлением и углом поворота.
     */
    private void moveRobot() {
        posX += SPEED * Math.cos(direction + rotationAngle);
        posY += SPEED * Math.sin(direction + rotationAngle);
        direction = normalizeRadians(direction + rotationAngle);
    }

    /**
     * Определяет угол поворота робота на основе угла до цели.
     *
     * @param angleToTarget угол до цели
     */
    private void determineRotationAngle(double angleToTarget) {
        double angleDifference = normalizeRadians(angleToTarget - direction);

        if (Math.abs(angleDifference) < ANGLE) {
            rotationAngle = angleDifference;
        } else if (!rotationDirectionSet) {
            setRotationAngle(Math.signum(angleDifference));
            rotationDirectionSet = true;
        }
    }

    /**
     * Устанавливает угол поворота робота в зависимости от направления поворота.
     *
     * @param rotationDirection направление поворота робота
     */
    private void setRotationAngle(double rotationDirection) {
        double R = (SPEED / 2) / Math.sin(ANGLE / 2);
        double xRComponent = posX + R * Math.cos(direction + rotationDirection * (ANGLE + Math.PI) / 2);
        double yRComponent = posY + R * Math.sin(direction + rotationDirection * (ANGLE + Math.PI) / 2);

        if (calculateDistance(targetX - xRComponent, targetY - yRComponent) > R) {
            rotationAngle = rotationDirection * ANGLE;
        } else {
            rotationAngle = -rotationDirection * ANGLE;
        }
    }

    /**
     * Вычисляет расстояние между двумя точками.
     *
     * @param diffX разница по оси X между точками
     * @param diffY разница по оси Y между точками
     * @return расстояние между точками
     */
    private double calculateDistance(double diffX, double diffY) {
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    /**
     * Нормализует угол в радианах, приводя его к диапазону от -pi до pi.
     *
     * @param angle угол для нормализации
     * @return нормализованный угол
     */
    private double normalizeRadians(double angle) {
        if (angle <= -Math.PI) {
            angle += 2 * Math.PI;
        } else if (angle >= Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    /**
     * Добавляет слушателя
     * @param pcl слушатель
     */
    public void addListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /**
     * Уведомляет слушателей об изменении состояния модели робота.
     */
    private void notifyListeners() {
        pcs.firePropertyChange("model_update", null, this);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getDirection() {
        return direction;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }
}
