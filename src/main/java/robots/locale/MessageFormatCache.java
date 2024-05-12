package robots.locale;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageFormatCache {
    private static final Map<String, MessageFormat> messageFormatCache = new ConcurrentHashMap<>();

    private MessageFormatCache(){}

    public static MessageFormat getMessageFormat(String pattern) {
        return messageFormatCache.computeIfAbsent(pattern, MessageFormat::new);
    }
}