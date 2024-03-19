package robots.serialize;


import java.io.Serializable;
import java.util.Objects;

/**
 * Представляет состояние окна, включая его размер, положение, заголовок и состояние минимизированности.
 */
public class WindowState implements Serializable {
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final String title;
    private final Boolean isIcon;

    public WindowState(int width, int height, int x, int y, String title, Boolean isIcon) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.title = title;
        this.isIcon = isIcon;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIcon() {
        return isIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WindowState that)) return false;
        return width == that.width                &&
                height == that.height             &&
                x == that.x                       &&
                y == that.y                       &&
                Objects.equals(title, that.title) &&
                Objects.equals(isIcon, that.isIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, x, y, title, isIcon);
    }
}
