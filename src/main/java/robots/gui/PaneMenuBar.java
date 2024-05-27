package robots.gui;

import robots.game.MVCController;
import robots.locale.LanguageManager;
import robots.log.Logger;
import robots.utils.ModuleLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;

/**
 * Отвечает за создание и поведение MenuBar окна {@link #frame}
 */
public class PaneMenuBar extends JMenuBar {
    private final MainApplicationFrame frame;

    /**
     * Создаёт объект {@link PaneMenuBar} с объектом окна {@link MainApplicationFrame}
     * и добавляет к {@link PaneMenuBar} некоторые {@link JMenu}
     * @param mainApplicationFrame окно, которому требуется {@link PaneMenuBar}
     */
    public PaneMenuBar(MainApplicationFrame mainApplicationFrame) {
        this.frame = mainApplicationFrame;
        add(createViewModeBar());
        add(createTestMenuBar());
        add(createLocaleMenuBar());
        add(createOptionBar());
    }

    /**
     * Общий шаблон создания объектов {@link JMenu}
     * @param name имя меню
     * @param description информация, для людей с ограниченными возможностями
     * @param keyEvent код ключа, представляющий собой мнемонику
     * @return объект {@link JMenu} с указаной информацией
     */
    private JMenu createJMenu(String name, String description, int keyEvent) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(keyEvent);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }

    /**
     * Создание пункта меню для измненеие темы приложения
     * @return объект {@link JMenu} c набором тем для прилоежния
     */
    private JMenu createViewModeBar() {
        JMenu lookAndFeelMenu = createJMenu(LanguageManager.getStr("PaneMenuBar.viewMenu.menuName"),
                LanguageManager.getStr("PaneMenuBar.viewMenu.menuDesc"), KeyEvent.VK_S);

        lookAndFeelMenu.add(getViewMenuItem(LanguageManager.getStr("PaneMenuBar.viewMenu.system"),
                    UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(getViewMenuItem(LanguageManager.getStr("PaneMenuBar.viewMenu.univ"),
                    UIManager.getCrossPlatformLookAndFeelClassName()));
        lookAndFeelMenu.add(getViewMenuItem("Nimbis",
                "javax.swing.plaf.nimbus.NimbusLookAndFeel"));
        lookAndFeelMenu.add(getViewMenuItem("Metal",
                "javax.swing.plaf.metal.MetalLookAndFeel"));

        return lookAndFeelMenu;
    }

    /**
     * Изменяет тему приложения
     * @param text назавние темы, отображаемое пользователю
     * @param className имя класса, который реализует внешний вид приложения
     * @return объект {@link JMenuItem} с указаной темой
     */
    private JMenuItem getViewMenuItem(String text, String className) {
        JMenuItem systemLookAndFeel = new JMenuItem(text, KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(className);
            frame.invalidate();
        });
        return systemLookAndFeel;
    }

    /**
     * Создает меню выбора локали.
     *
     * @return объект JMenu для меню выбора локали
     */
    private JMenu createLocaleMenuBar() {
        JMenu localeMenu = createJMenu(LanguageManager.getStr("PaneMenuBar.langMenu.menuName"),
                LanguageManager.getStr("PaneMenuBar.langMenu.menuDesc"),
                KeyEvent.VK_M);

        localeMenu.add(getLocaleMenuItem("en", LanguageManager.getStr("PaneMenuBar.langMenu.enItemName")));
        localeMenu.add(getLocaleMenuItem("ru", LanguageManager.getStr("PaneMenuBar.langMenu.ruItemName")));
        return localeMenu;
    }

    /**
     * Возвращает элемент меню для выбора указанной локали.
     *
     * @param locale код локали
     * @param name   название локали
     * @return объект JMenuItem для выбора локали
     */
    private JMenuItem getLocaleMenuItem(String locale, String name) {
        JMenuItem changeLanguageItem = new JMenuItem(name, KeyEvent.VK_M);
        changeLanguageItem.addActionListener((event) -> {
            if (LanguageManager.setLocale(new Locale(locale))) {
                frame.dispose();
                SwingUtilities.invokeLater(() -> {
                    MainApplicationFrame frame = new MainApplicationFrame();
                    frame.setVisible(true);
                });
            }
        });
        return changeLanguageItem;
    }

    /**
     * Создание пункта меню для отладки приложения
     * @return объект {@link JMenu} c набором инструментов для отладки
     */
    private JMenu createTestMenuBar() {
        JMenu testMenu = createJMenu(LanguageManager.getStr("PaneMenuBar.testMenu.menuName"),
                LanguageManager.getStr("PaneMenuBar.testMenu.menuDesc"), KeyEvent.VK_T);

        testMenu.add(getTestMenuItem());
        return testMenu;
    }

    /**
     * Отображение сообщения в окно класса {@link Logger}
     * @return объект {@link JMenuItem} с реализацией интрумента отладки
     */
    private JMenuItem getTestMenuItem() {
        JMenuItem addLogMessageItem = new JMenuItem(LanguageManager.getStr("PaneMenuBar.testMenu.mess"), KeyEvent.VK_T);
        addLogMessageItem.addActionListener((event) ->
                Logger.info("Logger.testMess"));
        return addLogMessageItem;
    }

    /**
     * Cоздание опции меню с опциями приложения
     * @return объект {@link JMenu} c некоторыми опциями
     */
    private JMenu createOptionBar() {
        JMenu quitMenu = createJMenu(LanguageManager.getStr("PaneMenuBar.optionMenu.menuName"),
                LanguageManager.getStr("PaneMenuBar.optionMenu.menuDesc"), KeyEvent.VK_O);

        quitMenu.add(getExitMentItem());
        quitMenu.add(getCreateChangeGameMenuItem());
        quitMenu.add(getInfoAboutProgram());
        return quitMenu;
    }

    /**
     * Опция, которая реализуею кнопку выхода из приложения
     * @return объект {@link JMenuItem} генерируеющий событияе закрытия окна
     */
    private JMenuItem getExitMentItem() {
        JMenuItem addQuitItem = new JMenuItem(LanguageManager.getStr("PaneMenuBar.optionMenu.exitItemName"), KeyEvent.VK_Q);
        addQuitItem.addActionListener((event) -> {
            WindowEvent closeEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
        });
        return addQuitItem;
    }

    /**
     * Создает и возвращает JMenuItem для изменения игры.
     * При выборе этого элемента меню открывается диалог для выбора JAR файла и класса модуля,
     * затем загружает и инициализирует выбранный модуль и перезапускает игру с новым модулем.
     *
     * @return JMenuItem для изменения игры.
     */
    private JMenuItem getCreateChangeGameMenuItem() {
        JMenuItem changeGameMenuItem = new JMenuItem(LanguageManager.getStr("PaneMenuBar.optionMenu.changeGame"), KeyEvent.VK_I);
        changeGameMenuItem.addActionListener(e -> {
            ModuleLoaderDialog moduleLoaderDialog = new ModuleLoaderDialog(frame);
            File jarFile = moduleLoaderDialog.getSelectedJarFile();
            String className = moduleLoaderDialog.getModuleClassPath();

            if (jarFile == null || className == null || className.isEmpty()) {
                return;
            }

            try {
                ModuleLoader moduleLoader = new ModuleLoader(jarFile);
                MVCController mvcController = moduleLoader.loadController(className);
                frame.restartGame(mvcController);
            } catch (RuntimeException e1) {
                System.err.println(e1.getMessage());
                e1.printStackTrace();
            }
        });
        return changeGameMenuItem;
    }



    /**
     * Опция, предоставляющая информацию о программе
     * @return объект {@link JMenuItem}, выводящий информацию о приложении в отдельном окне
     */
    private JMenuItem getInfoAboutProgram() {
        JMenuItem addInfoItem = new JMenuItem(LanguageManager.getStr("PaneMenuBar.optionMenu.infoItemName"), KeyEvent.VK_I);
        addInfoItem.addActionListener(e -> {
            String message = LanguageManager.getStr("PaneMenuBar.optionMenu.infoItem.mess");

            JDialog dialog = new JDialog(frame, LanguageManager.getStr("MainApplicationFrame.title"), true);

            JLabel label = new JLabel();
            label.setBorder(new EmptyBorder(10, 20, 10, 20));
            label.setText(message);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 15));

            JButton button = new JButton(LanguageManager.getStr("PaneMenuBar.optionMenu.infoItem.buttonName"));
            button.addActionListener(e1 -> dialog.dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(button);

            dialog.getContentPane().setLayout(new BorderLayout());
            dialog.getContentPane().add(label, BorderLayout.CENTER);
            dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            dialog.pack();

            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        });
        return addInfoItem;
    }
}
