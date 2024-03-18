package robots.serialize;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Класс WindowStateManager предоставляет функциональность для сериализации и десериализации состояний окон.
 */
public class WindowStateManager {
    private static WindowStateManager windowStateManager;
    private final WindowIO windowIO = new WindowIO();
    private Map<String, WindowState> windowStates;

    /**
     * Получает экземпляр WindowStateManager.
     *
     * @return экземпляр WindowStateManager
     */
    public static synchronized WindowStateManager get() {
        if (windowStateManager == null)
            windowStateManager = new WindowStateManager();
        return windowStateManager;
    }

    /**
     * Приватный конструктор для создания экземпляра WindowStateManager.
     * Загружает сохраненные состояния окон из файла при инициализации.
     */
    private WindowStateManager() {
        try {
            windowStates = windowIO.loadFromFile();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            windowStates = new HashMap<>();
            windowIO.saveToFile((Serializable) windowStates);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Сохраняет состояние окна по заданному идентификатору.
     *
     * @param id идентификатор состояния окна
     * @param ws состояние окна для сохранения
     */
    public void saveState(String id, WindowState ws) {
        windowStates.put(id, ws);
    }

    /**
     * Загружает состояние окна по заданному идентификатору.
     *
     * @param id идентификатор состояния окна
     * @return состояние окна, сохраненное под заданным идентификатором
     * @throws NoSuchElementException если состояние окна с указанным идентификатором не найдено
     */
    public WindowState loadState(String id) throws NoSuchElementException {
        if (!windowStates.containsKey(id))
            throw new NoSuchElementException("Не найдено состояние окна %s".formatted(id));
        return new WindowState(windowStates.get(id));
    }

    /**
     * Выполняет сохранение текущих состояний окон в файл.
     */
    public void flush() {
        windowIO.saveToFile((Serializable) windowStates);
    }
}
