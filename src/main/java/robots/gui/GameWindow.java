package robots.gui;

import robots.serialize.Stateful;
import robots.serialize.WindowState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

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
    public void deformationState(WindowState windowState) {
        if (windowState == null) {
            System.err.printf("no saved entry for %s%n", getClass().getName());
            setBounds(320, 10,
                    400, 400);
        } else {
            setLocation(windowState.getX(), windowState.getY());
            setSize(windowState.getWidth(), windowState.getHeight());
            setTitle(windowState.getTitle());
            try {
                setIcon(windowState.getIcon());
            } catch (PropertyVetoException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public WindowState formationState() {
        return new WindowState(
                getWidth(),
                getHeight(),
                getX(),
                getY(),
                getTitle(),
                isIcon());
    }
}
