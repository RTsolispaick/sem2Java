package robots.gui;

import robots.game.MVCControllerWithWindows;
import robots.game.MVCController;
import robots.game.ModuleController;
import robots.locale.LanguageManager;
import robots.log.Logger;
import robots.serialize.Stateful;
import robots.serialize.WindowStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

/**
 * Главное окно приложения
 */
public class MainApplicationFrame extends JFrame implements Stateful {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final WindowStateManager windowStateManager = new WindowStateManager();
    private GameWindow gameWindow;
    private MVCController mvcController;

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

        addWindow(new LogWindow());

        mvcController = new ModuleController();

        gameWindow = new GameWindow(mvcController.getPainter());
        addWindow(gameWindow);

        if (mvcController instanceof MVCControllerWithWindows mvcController)
            addDependedWindows(mvcController);

        restoreStatesWindows();
    }

    /**
     * Устанавливает визуальные отношения окна (размер, название и стандартную тему)
     */
    private void setVisualMainFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);
        setTitle(LanguageManager.getStr("MainApplicationFrame.title"));
    }

    /**
     * Реализует логику закрытия окна
     */
    private void launchExitDialog() {
        String [] options = {LanguageManager.getStr("Utils.optionYes"),
                LanguageManager.getStr("Utils.optionNo")};
        int userChoice = JOptionPane.showOptionDialog(
                null,
                LanguageManager.getStr("Utils.sure"),
                LanguageManager.getStr("Utils.exit"),
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
            Logger.error("Logger.setlookMess");
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

    /**
     * Метод для перезапуска игры на основе загруженного класса, реализующего интерфейс {@link MVCController}
     * @param newMvcController класс, реализующий интерфейс {@link MVCController}
     */
    public void restartGame(MVCController newMvcController) {
        saveStatesWindows();
        disposeGameWindowAndDependentInternal();

        mvcController = newMvcController;

        gameWindow = new GameWindow(mvcController.getPainter());
        addWindow(gameWindow);

        if (newMvcController instanceof MVCControllerWithWindows mvcControllerWithWindows)
            addDependedWindows(mvcControllerWithWindows);

        restoreStatesWindows();
    }

    /**
     * Метод для закрытия окон {@link GameWindow} и зависимых окон от текущего {@link MVCController},
     * если он реализует интерфейс {@link MVCControllerWithWindows}
     */
    private void disposeGameWindowAndDependentInternal() {
        if (gameWindow == null) return;

        if (mvcController instanceof MVCControllerWithWindows mvcControllerWithWindows) {
            for (JInternalFrame frame : mvcControllerWithWindows.getWindows()) {
                try {
                    frame.setClosed(true);
                } catch (PropertyVetoException e) {
                    System.err.println("Ошибка при закрытии зависимого внутреннего окна: " + e.getMessage());
                    e.printStackTrace();
                }
                desktopPane.remove(frame);
            }
        }

        try {
            gameWindow.setClosed(true);
        } catch (PropertyVetoException e) {
            System.err.println("Ошибка при закрытии окна GameWindow: " + e.getMessage());
            e.printStackTrace();
        }
        desktopPane.remove(gameWindow);
        desktopPane.repaint();
    }

    /**
     * Добавление зависимых окон от {@link MVCController}, если он реализует интерфейс {@link MVCControllerWithWindows}
     * @param mvcController класс реализующий интерфейс {@link MVCControllerWithWindows}
     */
    private void addDependedWindows(MVCControllerWithWindows mvcController) {
        JInternalFrame[] jInternalFrames = mvcController.getWindows();
        for (JInternalFrame jInternalFrame : jInternalFrames)
            addWindow(jInternalFrame);
    }

    @Override
    public void dispose() {
        saveStatesWindows();
        super.dispose();
    }

    @Override
    public String getIDFrame() {
        return "MainApplicationFrame";
    }
}
