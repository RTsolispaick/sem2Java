package robots.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyVetoException;
import java.util.NoSuchElementException;

import javax.swing.*;

import robots.log.LogChangeListener;
import robots.log.LogEntry;
import robots.log.LogWindowSource;
import robots.serialize.SerializationController;
import robots.serialize.Stateful;
import robots.serialize.WindowState;

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
    public void restore() throws NoSuchElementException {
        WindowState ws = SerializationController.get().loadState("log_window");
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
        SerializationController.get().saveState("log_window", ws);
    }
}
