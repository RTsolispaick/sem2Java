package robots.serialize;


import java.util.Objects;


/**
 * Представляет состояние окна.
 */
public class WindowState {
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final String title;
    private final Boolean isIcon;

    /**
     * Конструктор класса WindowState.
     *
     * @param width   Ширина окна.
     * @param height  Высота окна.
     * @param x       Координата X окна.
     * @param y       Координата Y окна.
     * @param title   Заголовок окна.
     * @param isIcon  Показывает, является ли окно свернутым (иконизированным).
     */
    public WindowState(int width, int height, int x, int y, String title, Boolean isIcon) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.title = title;
        this.isIcon = isIcon;
    }

    /**
     * Возвращает ширину окна.
     *
     * @return Ширина окна.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Возвращает высоту окна.
     *
     * @return Высота окна.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Возвращает координату X окна.
     *
     * @return Координата X окна.
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает координату Y окна.
     *
     * @return Координата Y окна.
     */
    public int getY() {
        return y;
    }

    /**
     * Возвращает заголовок окна.
     *
     * @return Заголовок окна.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Показывает, является ли окно свернутым (иконизированным).
     *
     * @return true, если окно свернуто (иконизировано), в противном случае - false.
     */
    public Boolean getIcon() {
        return isIcon;
    }

    /**
     * Переопределение метода equals для сравнения объектов типа WindowState.
     *
     * @param o Объект для сравнения.
     * @return true, если объекты равны, в противном случае - false.
     */
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

    /**
     * Переопределение метода hashCode для вычисления хэш-кода объекта WindowState.
     *
     * @return Хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(width, height, x, y, title, isIcon);
    }
}
