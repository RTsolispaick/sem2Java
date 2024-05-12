package robots.gui;

import robots.locale.LanguageManager;
import robots.log.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Отвечает за создание и поведение MenuBar окна {@link #frame}
 */
public class PaneMenuBar extends JMenuBar {
    private final MainApplicationFrame frame;
    private final ResourceBundle resourceBundle = LanguageManager.getBundle();

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
        JMenu lookAndFeelMenu = createJMenu(resourceBundle.getString("bar.view.name"),
                resourceBundle.getString("bar.view.desc"), KeyEvent.VK_S);

        lookAndFeelMenu.add(getViewMenuItem(resourceBundle.getString("bar.view.system"),
                    UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(getViewMenuItem(resourceBundle.getString("bar.view.univ"),
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

    private JMenu createLocaleMenuBar() {
        JMenu localeMenu = createJMenu(resourceBundle.getString("bar.lang.name"),
                resourceBundle.getString("bar.lang.desc"),
                KeyEvent.VK_M);

        localeMenu.add(getLocaleMenuItem("en", resourceBundle.getString("bar.lang.en")));
        localeMenu.add(getLocaleMenuItem("ru", resourceBundle.getString("bar.lang.ru")));
        return localeMenu;
    }

    private JMenuItem getLocaleMenuItem(String local, String name) {
        JMenuItem changeLanguageItem = new JMenuItem(name, KeyEvent.VK_M);
        changeLanguageItem.addActionListener((event) -> {
            if (LanguageManager.setLocale(new Locale(local))) {
                frame.closeWindowForRestart();
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
        JMenu testMenu = createJMenu(resourceBundle.getString("bar.test.name"),
                resourceBundle.getString("bar.test.desc"), KeyEvent.VK_T);

        testMenu.add(getTestMenuItem());
        return testMenu;
    }

    /**
     * Отображение сообщения в окно класса {@link Logger}
     * @return объект {@link JMenuItem} с реализацией интрумента отладки
     */
    private JMenuItem getTestMenuItem() {
        JMenuItem addLogMessageItem = new JMenuItem(resourceBundle.getString("bar.test.mess"), KeyEvent.VK_T);
        addLogMessageItem.addActionListener((event) ->
                Logger.debug(LanguageManager.getBundle().getString("logger.test")));
        return addLogMessageItem;
    }

    /**
     * Cоздание опции меню с опциями приложения
     * @return объект {@link JMenu} c некоторыми опциями
     */
    private JMenu createOptionBar() {
        JMenu quitMenu = createJMenu(resourceBundle.getString("bar.option.name"),
                resourceBundle.getString("bar.option.desc"), KeyEvent.VK_O);

        quitMenu.add(getExitMentItem());
        quitMenu.add(getInfoAboutProgram());
        return quitMenu;
    }

    /**
     * Опция, которая реализуею кнопку выхода из приложения
     * @return объект {@link JMenuItem} генерируеющий событияе закрытия окна
     */
    private JMenuItem getExitMentItem() {
        JMenuItem addQuitItem = new JMenuItem(resourceBundle.getString("bar.option.exit"), KeyEvent.VK_Q);
        addQuitItem.addActionListener((event) -> {
            WindowEvent closeEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
        });
        return addQuitItem;
    }

    /**
     * Опция, предоставляющая информацию о программе
     * @return объект {@link JMenuItem}, выводящий информацию о приложении в отдельном окне
     */
    private JMenuItem getInfoAboutProgram() {
        JMenuItem addInfoItem = new JMenuItem(resourceBundle.getString("bar.option.info"), KeyEvent.VK_I);
        addInfoItem.addActionListener(e -> {
            String message = resourceBundle.getString("bar.option.info.mess");

            JDialog dialog = new JDialog(frame, resourceBundle.getString("main.title"), true);

            JLabel label = new JLabel();
            label.setBorder(new EmptyBorder(10, 20, 10, 20));
            label.setText(message);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 15));

            JButton button = new JButton(resourceBundle.getString("bar.option.info.button"));
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
