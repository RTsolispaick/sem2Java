package robots.serialize;

/**
 * Интерфейс представляет объект, который может преобразовываться в {@link WindowState} и обратно.
 */
public interface Stateful {
    /**
     * Используется для восстановления состояния объекта из переданного {@link WindowState}.
     *
     * @param windowState cостояние окна, из которого необходимо восстановить состояние объекта, null - в случае отсутсвия сохранения
     */
    void deformationState(WindowState windowState);

    /**
     * Возвращает текущее состояние объекта в виде объекта {@link WindowState}.
     *
     * @return Объект WindowState, представляющий текущее состояние объекта.
     */
    WindowState formationState();
}
