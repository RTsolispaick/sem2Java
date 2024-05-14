package robots.gui;

import robots.locale.LanguageManager;
import robots.locale.MessageFormatCache;
import robots.log.*;
import robots.serialize.Stateful;

import javax.swing.*;
import java.awt.*;

public class LogWindow extends JInternalFrame implements LogChangeListener, Stateful
{
    private final LogWindowSource m_logSource = Logger.getDefaultLogSource();
    private final TextArea m_logContent;

    public LogWindow()
    {
        super(LanguageManager.getStr("LogWindow.title"),
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

        Logger.debug("LogWindow.title");
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        MessageFormatCache messageFormatCache = MessageFormatCache.getInstance();
        for (LogEntry logEntry : m_logSource.all())
        {
            String logLevel = switch (logEntry.getLevel()) {
                case Debug -> "Logger.debug";
                case Info -> "Logger.info";
                case Error -> "Logger.error";
                default -> "Logger.pass";
            };
            content.append(messageFormatCache.getMessageFormat(LanguageManager.getStr(logLevel))
                            .format(new Object[]{LanguageManager.getStr(logEntry.getMessage())})
                    ).append("\n");
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
