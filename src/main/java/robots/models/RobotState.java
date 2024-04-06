package robots.models;

public class RobotState {
    private final double posX;
    private final double posY;
    private final double direction;
    private final int targetX;
    private final int targetY;

    public RobotState(double posX, double posY, double direction, int targetX, int targetY) {
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public double getDirection() {
        return direction;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }
}
