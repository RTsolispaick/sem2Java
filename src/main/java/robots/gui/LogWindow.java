package robots.gui;

import robots.log.LogChangeListener;
import robots.log.LogEntry;
import robots.log.LogWindowSource;
import robots.log.Logger;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class LogWindow extends JInternalFrame implements LogChangeListener, Stateful
{
    private final LogWindowSource m_logSource = Logger.getDefaultLogSource();
    private final TextArea m_logContent;

    public LogWindow()
    {
        super("Протокол работы", true, true, true, true);
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
        setLocation(10,10);
        Logger.debug("Протокол работает");
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        Iterator<LogEntry> iterator = m_logSource.all();
        while (iterator.hasNext())
        {
            content.append(iterator.next().getMessage()).append("\n");
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
    public String getIDFrame() {
        return "LogWindow";
    }
}
