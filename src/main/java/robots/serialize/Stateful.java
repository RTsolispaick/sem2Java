package robots.serialize;

/**
 * Интерфейс представляет объект, который может быть сериализован и десериализован в {@link WindowState}.
 */
public interface Stateful {
    /**
     * Используется для восстановления состояния объекта из переданной Map состояний окон.
     *
     * @param windowState Cостояние окна, из которого необходимо восстановить состояние объекта, null - в случае отсутсвия сохранения
     */
    void deformationState(WindowState windowState);

    /**
     * Возвращает текущее состояние объекта в виде объекта WindowState.
     *
     * @return Объект WindowState, представляющий текущее состояние объекта.
     */
    WindowState formationState();
}
