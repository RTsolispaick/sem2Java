package robots.utils;

import robots.game.MVCController;
import robots.game.MVCControllerWithWindows;
import robots.log.Logger;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Класс ModuleLoader предоставляет функциональность для загрузки и инициализации модулей
 * (классов, реализующих интерфейс {@link MVCController}) из JAR файлов.
 */
public class ModuleLoader {
    private final URLClassLoader urlClassLoader;

    /**
     * Конструктор инициализирует URLClassLoader для загрузки классов из указанного JAR файла.
     *
     * @param jarFile JAR файл, содержащий классы для загрузки.
     */
    public ModuleLoader(File jarFile) throws RuntimeException {
        try {
            URL[] urls = new URL[]{jarFile.toURI().toURL()};
            urlClassLoader = new URLClassLoader(urls);
        } catch (Exception e) {
            System.err.println("Указан неправильный адрес JAR файла: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Загружает и инициализирует класс, реализующий интерфейс {@link MVCController}, по его полному имени.
     *
     * @param className полное имя класса, включая пакет, для загрузки.
     * @return экземпляр загруженного класса, реализующего интерфейс {@link MVCController}, если произошла ошибка она передаётся выше.
     */
    public MVCController loadController(String className) throws RuntimeException {
        try {
            Class<?> clazz = urlClassLoader.loadClass(className);
            Object object = clazz.getConstructor().newInstance();
            Method getPainter = clazz.getMethod("getPainter");
            try {
                Method getWindows = clazz.getMethod("getWindows");
                return new MVCControllerWithWindows() {
                    @Override
                    public JInternalFrame[] getWindows() {
                        try {
                            return (JInternalFrame[]) getWindows.invoke(object);
                        } catch (IllegalAccessException e) {
                            Logger.error("moduleLoader.illegalAccess");
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            Logger.error("moduleLoader.invocationTarget");
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public JComponent getPainter() {
                        try {
                            return (JComponent) getPainter.invoke(object);
                        }catch (IllegalAccessException e) {
                            Logger.error("moduleLoader.illegalAccess");
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            Logger.error("moduleLoader.invocationTarget");
                            throw new RuntimeException(e);
                        }
                    }
                };
            } catch (NoSuchMethodException e) {
                return new MVCController() {
                    @Override
                    public JComponent getPainter() {
                        try {
                            return (JComponent) getPainter.invoke(object);
                        }catch (IllegalAccessException e) {
                            Logger.error("moduleLoader.illegalAccess");
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            Logger.error("moduleLoader.invocationTarget");
                            throw new RuntimeException(e);
                        }
                    }
                };
            } catch (SecurityException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            Logger.error("moduleLoader.classNotFound");
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            Logger.error("moduleLoader.noSuchMethod");
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            Logger.error("moduleLoader.invocationTarget");
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            Logger.error("moduleLoader.instantiation");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            Logger.error("moduleLoader.illegalAccess");
            throw new RuntimeException(e);
        }
    }
}
