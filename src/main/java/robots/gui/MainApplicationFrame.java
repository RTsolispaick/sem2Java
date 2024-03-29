package robots.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import robots.log.Logger;

/**
 * Главное окно приложения
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();

    /**
     * Конструктор для создания главного окна
     */
    public MainApplicationFrame() {
        setContentPane(desktopPane);
        setJMenuBar(new PaneMenuBar(this));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                launchExitDialog();
            }
        });

        addWindow(createLogWindow());
        addWindow(createGameWindow());

        pack();
    }

    /**
     * Устанавливает визуальные отношения окна (размер, название и станадартную тему)
     */
    public void setVisualMainFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setTitle("Старающийся комарик");
        setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
        if (userChoice == JOptionPane.YES_OPTION)
            setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        logWindow.setBounds(10,10,
                300, 650);
        setMinimumSize(logWindow.getSize());
        Logger.debug("Протокол работает");
        return logWindow;
    }

    /**
     * Создаёт внутреннее окно класса {@link GameWindow} и настраивает его размер
     * @return готовый объект окна {@link GameWindow}
     */
    private GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setBounds(320, 10,
                400, 400);
        setMinimumSize(gameWindow.getSize());
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
            Logger.debug(e.getMessage());
        }
    }
}
