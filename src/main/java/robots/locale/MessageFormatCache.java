package robots.locale;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Кэш для объектов MessageFormat, обеспечивающий повторное использование объектов для одинаковых шаблонов.
 */
public class MessageFormatCache {
    private static final Map<String, MessageFormat> messageFormatCache = new ConcurrentHashMap<>();

    private MessageFormatCache(){}

    /**
     * Получает объект MessageFormat для указанного шаблона.
     * Если объект для указанного шаблона уже существует в кэше, возвращает его.
     * В противном случае создает новый объект MessageFormat для шаблона, помещает его в кэш и возвращает.
     *
     * @param pattern шаблон для форматирования сообщений
     * @return объект MessageFormat для указанного шаблона
     */
    public static MessageFormat getMessageFormat(String pattern) {
        return messageFormatCache.computeIfAbsent(pattern, MessageFormat::new);
    }
}
