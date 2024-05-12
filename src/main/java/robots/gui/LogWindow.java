package robots.gui;

import robots.locale.LanguageManager;
import robots.locale.MessageFormatCache;
import robots.log.LogChangeListener;
import robots.log.LogEntry;
import robots.log.LogWindowSource;
import robots.log.Logger;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;

public class LogWindow extends JInternalFrame implements LogChangeListener, Stateful
{
    private final LogWindowSource m_logSource = Logger.getDefaultLogSource();
    private final TextArea m_logContent;

    public LogWindow()
    {
        super(LanguageManager.getBundle().getString("log.title"),
                true,
                true,
                true,
                true);
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
        setLocation(10,10);

        String[] title = {LanguageManager.getBundle().getString("log.title")};
        Logger.debug(MessageFormatCache.getMessageFormat(
                        LanguageManager.getBundle().getString("logger.pattern")
                ).format(title)
        );
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry logEntry : m_logSource.all())
        {
            content.append(logEntry.getMessage()).append("\n");
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
