package robots.gui;

import robots.serialize.WindowStateManager;
import robots.serialize.Stateful;
import robots.serialize.WindowState;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.util.NoSuchElementException;

import javax.swing.*;

public class GameWindow extends JInternalFrame implements Stateful {

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void restore() throws NoSuchElementException {
        WindowState ws = WindowStateManager.get().loadState("game_window");
        setLocation(ws.getLocation());
        setSize(ws.getSize());
        setMinimumSize(ws.getSize());
        setTitle(ws.getTitle());
        try {
            setIcon(ws.getIcon());
        } catch (PropertyVetoException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save() {
        WindowState ws = new WindowState(
                getSize(),
                getLocation(),
                getTitle(),
                isIcon()
        );
        WindowStateManager.get().saveState("game_window", ws);
    }
}
