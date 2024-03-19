package robots.gui;

import robots.log.LogChangeListener;
import robots.log.LogEntry;
import robots.log.LogWindowSource;
import robots.serialize.Stateful;
import robots.serialize.WindowState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class LogWindow extends JInternalFrame implements LogChangeListener, Stateful
{
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    public LogWindow(LogWindowSource logSource)
    {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }


    @Override
    public void deformationState(WindowState windowState) {
        if (windowState == null) {
            System.err.printf("no saved entry for %s%n", getClass().getName());
            setBounds(10,10,
                    300, 650);
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
