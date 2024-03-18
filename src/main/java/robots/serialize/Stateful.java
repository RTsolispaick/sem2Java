package robots.serialize;

import java.util.NoSuchElementException;

/**
 * Интерфейс, представляющий объект, который может сохранять и восстанавливать свое состояние.
 */
public interface Stateful {
    /**
     * Метод для восстановления состояния объекта.
     *
     * @throws NoSuchElementException если не удается восстановить состояние из-за отсутствия необходимых данных
     */
    void restore() throws NoSuchElementException;

    /**
     * Метод для сохранения текущего состояния объекта.
     */
    void save();
}
