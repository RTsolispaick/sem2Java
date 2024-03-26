package robots.serialize;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Предоставляет функциональность для сериализации и десериализации состояний окон.
 */
public class WindowStateManager {
    private final WindowIO windowIO = new WindowIO();

    /**
     * Сохраняет состояние главного окна и внутренних окон.
     *
     * @param frames            Список сохраняемых окон.
     */
    public void saveState(List<Stateful> frames) {
        Map<String, WindowState> windowStateMap = new HashMap<>();

        for (Stateful frame: frames) {
            if (frame instanceof JFrame jFrame)
                windowStateMap.put(frame.getIDFrame(), getJFrameState(jFrame));
            else if (frame instanceof JInternalFrame jInternalFrame)
                windowStateMap.put(frame.getIDFrame(), getJInternalFrameState(jInternalFrame));
            else
                System.err.printf("Нет обработки случая в saveState для %s%n", frame.getClass().getName());
        }

        windowIO.saveToJson(windowStateMap);
    }

    /**
     * Возвращает объект WindowState, представляющий состояние JFrame.
     *
     * @param jFrame Объект JFrame, для которого необходимо получить состояние.
     * @return Объект WindowState, представляющий состояние JFrame.
     */
    private WindowState getJFrameState(JFrame jFrame) {
        return new WindowState(
                jFrame.getWidth(),
                jFrame.getHeight(),
                jFrame.getX(),
                jFrame.getY(),
                false);
    }

    /**
     * Возвращает объект WindowState, представляющий состояние JInternalFrame.
     *
     * @param jInternalFrame Объект JInternalFrame, для которого необходимо получить состояние.
     * @return Объект WindowState, представляющий состояние JInternalFrame.
     */
    private WindowState getJInternalFrameState(JInternalFrame jInternalFrame) {
        return new WindowState(
                jInternalFrame.getWidth(),
                jInternalFrame.getHeight(),
                jInternalFrame.getX(),
                jInternalFrame.getY(),
                jInternalFrame.isIcon());
    }

    /**
     * Загружает состояние главного окна и внутренних окон.
     *
     * @param frames            Список загружаемых окон.
     */
    public void loadState(List<Stateful> frames) {
        Map<String, WindowState> windowStateMap = new HashMap<>();
        try {
            windowStateMap = windowIO.loadFromJson();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        for (Stateful frame: frames) {
            if (frame instanceof JFrame jFrame)
                setJFrameState(jFrame, windowStateMap.get(frame.getIDFrame()));
            else if (frame instanceof JInternalFrame jInternalFrame)
                setJInternalFrameState(jInternalFrame, windowStateMap.get(frame.getIDFrame()));
            else
                System.err.printf("Нет обработки случая в loadState для %s%n", frame.getClass().getName());
        }
    }

    /**
     * Устанавливает состояние JFrame на основе объекта WindowState.
     *
     * @param jFrame       Объект JFrame, для которого необходимо установить состояние.
     * @param windowState  Объект WindowState, представляющий состояние JFrame.
     */
    private void setJFrameState(JFrame jFrame, WindowState windowState) {
        if (windowState == null) {
            System.err.printf("Нет записей о %s%n", jFrame.getClass().getName());
        } else {
            jFrame.setLocation(windowState.getX(), windowState.getY());
            jFrame.setSize(windowState.getWidth(), windowState.getHeight());
        }
    }

    /**
     * Устанавливает состояние JInternalFrame на основе объекта WindowState.
     *
     * @param jInternalFrame  Объект JInternalFrame, для которого необходимо установить состояние.
     * @param windowState     Объект WindowState, представляющий состояние JInternalFrame.
     */
    private void setJInternalFrameState(JInternalFrame jInternalFrame, WindowState windowState) {
        if (windowState == null) {
            System.err.printf("Нет записей о %s%n", jInternalFrame.getClass().getName());
        } else {
            jInternalFrame.setLocation(windowState.getX(), windowState.getY());
            jInternalFrame.setSize(windowState.getWidth(), windowState.getHeight());
            try {
                jInternalFrame.setIcon(windowState.getIcon());
            } catch (PropertyVetoException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
