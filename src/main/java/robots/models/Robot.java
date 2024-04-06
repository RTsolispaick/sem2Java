package robots.models;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Robot implements PropertyChangeListener {
    private final PropertyChangeSupport propertyChangeSupport;

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

    public Robot(PropertyChangeSupport pcs) {
        this.propertyChangeSupport = pcs;
        pcs.addPropertyChangeListener(this);
    }

    public synchronized void update() {
        double distanceToTarget = calculateDistance(targetX - posX, targetY - posY);

        if (distanceToTarget < 0.5) return;

        double angleToTarget = Math.atan2(targetY - posY, targetX - posX);
        determineRotationAngle(angleToTarget);

        moveRobot();
        notifyListeners();
    }

    private void moveRobot() {
        posX += SPEED * Math.cos(direction + rotationAngle);
        posY += SPEED * Math.sin(direction + rotationAngle);
        direction = normalizeRadians(direction + rotationAngle);
    }

    private void determineRotationAngle(double angleToTarget) {
        double angleDifference = normalizeRadians(angleToTarget - direction);

        if (Math.abs(angleDifference) < ANGLE) {
            rotationAngle = angleDifference;
        } else if (!rotationDirectionSet) {
            setRotationAngle(Math.signum(angleDifference));
            rotationDirectionSet = true;
        }
    }

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

    public void setTargetPosition(Point targetPosition) {
        targetX = targetPosition.x;
        targetY = targetPosition.y;
        rotationDirectionSet = false;
    }

    private double calculateDistance(double diffX, double diffY) {
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    // Приведение угла к диапазону от -pi до pi
    private double normalizeRadians(double angle) {
        if (angle <= -Math.PI) {
            angle += 2 * Math.PI;
        }
        else if (angle >= Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("target_update")) {
            Point point = (Point) evt.getNewValue();
            setTargetPosition(point);
        }
    }

    /**
     * Уведомляет слушателей об изменении состояния модели.
     */
    private void notifyListeners() {
        propertyChangeSupport.firePropertyChange("robot_update", null,
                new RobotState(posX, posY, direction, targetX, targetY));
    }
}
