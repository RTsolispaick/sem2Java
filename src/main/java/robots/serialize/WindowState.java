package robots.serialize;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class WindowState implements Serializable {
    private final Dimension size;
    private final Point location;
    private final String title;
    private final Boolean isIcon;

    public WindowState(Dimension size, Point location, String title, Boolean isIcon) {
        this.size = size;
        this.location = location;
        this.title = title;
        this.isIcon = isIcon;
    }

    public Dimension getSize() {
        return size;
    }

    public Point getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIcon() {
        return isIcon;
    }

    public WindowState(WindowState orig) {
        this.size = new Dimension(orig.size);
        this.location = new Point(orig.location);
        this.title = orig.title;
        this.isIcon = orig.getIcon();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WindowState that)) return false;
        return Objects.equals(size, that.size)          &&
                Objects.equals(location, that.location) &&
                Objects.equals(title, that.title)       &&
                Objects.equals(isIcon, that.isIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, location, title, isIcon);
    }

    @Override
    public String toString() {
        return "WindowState{" +
                "size=" + size +
                ", location=" + location +
                ", title='" + title + '\'' +
                ", isIcon=" + isIcon +
                '}';
    }
}
