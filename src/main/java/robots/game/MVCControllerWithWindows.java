package robots.game;

import javax.swing.*;

/**
 * Интерфейс для модулей с дополнительными окнами
 */
public interface MVCControllerWithWindows extends MVCController {
    /**
     * Возвращает массив внутренних окон, которые управляются контроллером.
     *
     * @return массив объектов {@link JInternalFrame}, представляющих внутренние окна, управляемые контроллером.
     */
    JInternalFrame[] getWindows();
}
