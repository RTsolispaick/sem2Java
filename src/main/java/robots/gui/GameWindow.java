package robots.gui;

import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

public class GameWindow extends JInternalFrame implements Stateful {

    public GameWindow(PropertyChangeSupport pcs) {
        super("Игровое поле", true, true, true, true);
        GameVisualizer m_visualizer = new GameVisualizer(pcs);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        setBounds(320, 10,
                400, 400);
    }

    @Override
    public String getIDFrame() {
        return "GameWindow";
    }
}
