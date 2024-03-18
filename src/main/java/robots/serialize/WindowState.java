package robots.serialize;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Представляет состояние окна, включая его размер, положение, заголовок и состояние минимизированности.
 */
public class WindowState implements Serializable {
    private final Dimension size;
    private final Point location;
    private final String title;
    private final Boolean isIcon;

    /**
     * Конструирует объект WindowState с указанным размером, положением, заголовком и состоянием минимизированности.
     *
     * @param size      размер окна
     * @param location  положение окна
     * @param title     заголовок окна
     * @param isIcon    true, если окно минимизировано (иконизировано), false в противном случае
     */
    public WindowState(Dimension size, Point location, String title, Boolean isIcon) {
        this.size = size;
        this.location = location;
        this.title = title;
        this.isIcon = isIcon;
    }

    /**
     * Получает размер окна.
     *
     * @return размер окна
     */
    public Dimension getSize() {
        return size;
    }

    /**
     * Получает положение окна.
     *
     * @return положение окна
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Получает заголовок окна.
     *
     * @return заголовок окна
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получает состояние минимизированности окна.
     *
     * @return true, если окно минимизировано, false в противном случае
     */
    public Boolean getIcon() {
        return isIcon;
    }

    /**
     * Конструирует копию заданного объекта WindowState.
     *
     * @param orig оригинальный объект WindowState для копирования
     */
    public WindowState(WindowState orig) {
        this.size = new Dimension(orig.size);
        this.location = new Point(orig.location);
        this.title = orig.title;
        this.isIcon = orig.getIcon();
    }

    /**
     * Показывает, равен ли этот объект другому объекту.
     *
     * @param o объект для сравнения
     * @return true, если этот объект равен переданному объекту; в противном случае - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WindowState that)) return false;
        return Objects.equals(size, that.size) &&
                Objects.equals(location, that.location) &&
                Objects.equals(title, that.title) &&
                Objects.equals(isIcon, that.isIcon);
    }

    /**
     * Возвращает хеш-код для объекта.
     *
     * @return хеш-код для этого объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(size, location, title, isIcon);
    }

    /**
     * Возвращает строковое представление объекта.
     *
     * @return строковое представление объекта
     */
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
