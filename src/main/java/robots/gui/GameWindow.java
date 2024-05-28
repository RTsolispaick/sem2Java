package robots.gui;

import robots.locale.LanguageManager;
import robots.log.Logger;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;

/**
 * Класс GameWindow представляет собой внутреннее окно Swing, содержащее игровое поле.
 */
public class GameWindow extends JInternalFrame implements Stateful {
    /**
     * Создает новое игровое окно с заданным объектом PropertyChangeSupport.
     */
    public GameWindow(JComponent jComponent) {
        super(LanguageManager.getStr("GameWindow.title"),
                true,
                true,
                true,
                true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(jComponent, BorderLayout.CENTER);
        getContentPane().add(panel);

        setBounds(230, 10,
                530, 530);

        Logger.debug("GameWindow.title");
    }

    @Override
    public String getIDFrame() {
        return "GameWindow";
    }
}
