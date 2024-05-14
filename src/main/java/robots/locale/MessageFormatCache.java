package robots.locale;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Кэш для объектов MessageFormat, обеспечивающий повторное использование объектов для одинаковых шаблонов.
 */
public class MessageFormatCache {
    private final Map<String, MessageFormat> messageFormatCache = new HashMap<>();

    private MessageFormatCache() {}

    /**
     * Получает объект MessageFormat для указанного шаблона.
     * Если объект для указанного шаблона уже существует в кэше, возвращает его.
     * В противном случае создает новый объект MessageFormat для шаблона, помещает его в кэш и возвращает.
     *
     * @param pattern шаблон для форматирования сообщений
     * @return объект MessageFormat для указанного шаблона
     */
    public MessageFormat getMessageFormat(String pattern) {
        return messageFormatCache.computeIfAbsent(pattern, MessageFormat::new);
    }

    /**
     * Получает экземпляр класса MessageFormatCache.
     *
     * @return экземпляр класса MessageFormatCache
     */
    public static MessageFormatCache getInstance() {
        return MessageFormatCacheHolder.INSTANCE;
    }

    private static class MessageFormatCacheHolder {
        private static final MessageFormatCache INSTANCE = new MessageFormatCache();
    }
}
