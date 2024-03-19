package robots.serialize;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс WindowStateManager предоставляет функциональность для сериализации и десериализации состояний окон.
 */
public class WindowStateManager {
    private final WindowIO windowIO = new WindowIO();
    private Map<String, WindowState> windowStateMap = new HashMap<>();

    /**
     * Сохраняет состояние главного окна и внутренних окон.
     *
     * @param frame            Главное окно JFrame.
     * @param jInternalFrames  Массив внутренних окон JInternalFrame.
     */
    public void saveState(JFrame frame, JInternalFrame[] jInternalFrames) {
        if (frame instanceof Stateful)
            windowStateMap.put(frame.getClass().getName(),((Stateful) frame).formationState());

        for (JInternalFrame jInternalFrame : jInternalFrames)
            if (jInternalFrame instanceof Stateful)
                windowStateMap.put(jInternalFrame.getClass().getName(), ((Stateful) jInternalFrame).formationState());

        windowIO.saveToJson(windowStateMap);
    }

    /**
     * Загружает состояние главного окна и внутренних окон.
     *
     * @param frame            Главное окно JFrame.
     * @param jInternalFrames  Массив внутренних окон JInternalFrame.
     */
    public void loadState(JFrame frame, JInternalFrame[] jInternalFrames) {
        try {
            windowStateMap = windowIO.loadFromJson();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (frame instanceof Stateful)
            ((Stateful) frame).deformationState(windowStateMap.get(frame.getClass().getName()));

        for (JInternalFrame jInternalFrame : jInternalFrames)
            if (jInternalFrame instanceof Stateful)
                ((Stateful) jInternalFrame).deformationState(windowStateMap.get(jInternalFrame.getClass().getName()));
    }
}
