package robots.gui;

import robots.log.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class PaneMenuBar extends JMenuBar {
    private final MainApplicationFrame frame;

    public PaneMenuBar(MainApplicationFrame mainApplicationFrame) {
        this.frame = mainApplicationFrame;
        add(createViewModeBar());
        add(createTestMenuBar());
        add(createOptionBar());
    }

    private JMenu createJMenu(String name, String description) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(KeyEvent.VK_T);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }

    private JMenu createViewModeBar() {
        JMenu lookAndFeelMenu = createJMenu("Режим отображения",
                "Управление режимом отображения приложения");

        lookAndFeelMenu.add(getViewMenuItem("Системная схема",
                    UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(getViewMenuItem("Универсальная схема",
                    UIManager.getCrossPlatformLookAndFeelClassName()));
        lookAndFeelMenu.add(getViewMenuItem("Nimbis",
                "javax.swing.plaf.nimbus.NimbusLookAndFeel"));
        lookAndFeelMenu.add(getViewMenuItem("Metal",
                "javax.swing.plaf.metal.MetalLookAndFeel"));

        return lookAndFeelMenu;
    }

    private JMenuItem getViewMenuItem(String text, String className) {
        JMenuItem systemLookAndFeel = new JMenuItem(text, KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(className);
            frame.invalidate();
        });
        return systemLookAndFeel;
    }

    public void setDefaultTheme() {
        setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame.invalidate();
    }

    private JMenu createTestMenuBar() {
        JMenu testMenu = createJMenu("Тесты",
                "Тестовые команды");

        testMenu.add(getTestMenuItem());
        return testMenu;
    }

    private JMenuItem getTestMenuItem() {
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) ->
                Logger.debug("Всё нормально"));
        return addLogMessageItem;
    }

    private JMenu createOptionBar() {
        JMenu quitMenu = createJMenu("Опции",
                "Опции окна");

        quitMenu.add(getExitMentItem());
        quitMenu.add(getInfoAboutProgram());
        return quitMenu;
    }

    private JMenuItem getExitMentItem() {
        JMenuItem addQuitItem = new JMenuItem("Выход", KeyEvent.VK_S);
        addQuitItem.addActionListener((event) -> {
            WindowEvent closeEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
        });
        return addQuitItem;
    }

    private JMenuItem getInfoAboutProgram() {
        JMenuItem addInfoItem = new JMenuItem("О программе");
        addInfoItem.addActionListener(e -> {
            String message = "<html><div style='text-align: center;'>Старающийся комарик - это увлекательная игра, в которой игроку предстоит помочь маленькому комарику долететь до точки назначения.</div>" +
                    "<div style='text-align: center;'>Несмотря на свою маленькую и хрупкую природу, комарик мечтает достичь цели, находящейся на другом конце экрана.</div>" +
                    "<div style='text-align: center;'>Однако ему предстоит преодолеть множество препятствий и опасностей...</div>" +
                    "<div style='text-align: center;'>Хорошей игры!</div>" +
                    "<div style='text-align: right;'>prod. by Клепинин А.В</html>";

            JDialog dialog = new JDialog(frame, "Старающийся комарик", true);

            JLabel label = new JLabel();
            label.setBorder(new EmptyBorder(10, 20, 10, 20));
            label.setText(message);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 15));

            JButton button = new JButton("Спасибо!");
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



    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            Logger.debug("Всё очень плохо");
        }
    }
}
