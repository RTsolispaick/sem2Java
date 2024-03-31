package robots.gui;

import robots.log.Logger;
import robots.serialize.Stateful;
import robots.serialize.WindowStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Главное окно приложения
 */
public class MainApplicationFrame extends JFrame implements Stateful {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final WindowStateManager windowStateManager = new WindowStateManager();

    /**
     * Конструктор для создания главного окна
     */
    public MainApplicationFrame() {
        setContentPane(desktopPane);
        setVisualMainFrame();

        setJMenuBar(new PaneMenuBar(this));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                launchExitDialog();
            }
        });

        addWindow(createGameWindow());
        addWindow(createLogWindow());

        restoreStatesWindows();
    }

    /**
     * Устанавливает визуальные отношения окна (размер, название и станадартную тему)
     */
    private void setVisualMainFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);
        setTitle("Старающийся комарик");
    }

    /**
     * Реализует логику закрытия окна
     */
    private void launchExitDialog() {
        String [] options = {"Да", "Нет"};
        int userChoice = JOptionPane.showOptionDialog(
                null,
                "Вы уверены?",
                "Выйти",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (userChoice == JOptionPane.YES_OPTION) {
            saveStatesWindows();
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }

    /**
     * Привязывает внутренние окна к главному окну
     * @param frame объект внутреннего окна
     */
    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    /**
     * Создаёт внутреннее окно класса {@link LogWindow} и настраивает его размер
     * @return готовый объект окна {@link LogWindow}
     */
    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        Logger.debug("Протокол работает");
        return logWindow;
    }

    /**
     * Создаёт внутреннее окно класса {@link GameWindow} и настраивает его размер
     * @return готовый объект окна {@link GameWindow}
     */
    private GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        Logger.debug("Окно игры работает");
        return gameWindow;
    }

    /**
     * Устанавливает внешний вид для приложения на основе указанного имени класса.
     * @param className имя класса, представляющего внешний вид
     */
    public void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            Logger.debug("Ошибка в setLookAndFeel");
        }
    }

    /**
     * Восстанавливает состояния окон приложения из сохраненных данных.
     */
    private void restoreStatesWindows() {
        windowStateManager.loadState(getListStateful());
    }

    /**
     * Сохраняет текущие состояния окон приложения.
     */
    private void saveStatesWindows() {
        windowStateManager.saveState(getListStateful());
    }

    /**
     * Создание списка, который содержит окна, реализующие интерфейс Stateful
     * @return список, содержащий окна с интерфейсом Stateful
     */
    private List<Stateful> getListStateful() {
        List<Stateful> statefuls = new ArrayList<>();
        statefuls.add(this);

        for (JInternalFrame jInternalFrame : desktopPane.getAllFrames())
            if (jInternalFrame instanceof Stateful statefulFrame)
                statefuls.add(statefulFrame);

        return statefuls;
    }


    @Override
    public String getIDFrame() {
        return "MainApplicationFrame";
    }
}
