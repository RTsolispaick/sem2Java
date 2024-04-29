package robots.log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Источник сообщений лога. Сообщения лога хранятся в кольцевом буфере
 * ограниченного размера, при добавлении нового сообщения самое старое удаляется.
 * Слушатели уведомляются об изменении лога при добавлении нового сообщения.
 */
public class LogWindowSource
{
    /**
     * Кольцевой буфер сообщений лога.
     */
    private final CircularBuffer<LogEntry> m_messages;

    /**
     * Список слабых ссылок на слушателей лога.
     */
    private final ArrayList<WeakReference<LogChangeListener>> m_listeners;

    /**
     * Массив активных слушателей лога.
     */
    private volatile List<WeakReference<LogChangeListener>> m_activeListeners;

    /**
     * Создает источник сообщений лога с указанным размером буфера.
     *
     * @param bufferLength размер буфера сообщений лога
     */
    public LogWindowSource(int bufferLength)
    {
        m_messages = new CircularBuffer<>(bufferLength);
        m_listeners = new ArrayList<>();
    }

    /**
     * Регистрирует слушателя лога.
     *
     * @param listener слушатель лога
     */
    public void registerListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            m_listeners.add(new WeakReference<>(listener));
            m_activeListeners = null;
        }
    }

    /**
     * Отменяет регистрацию слушателя лога.
     *
     * @param listener слушатель лога
     */
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            Iterator<WeakReference<LogChangeListener>> it = m_listeners.iterator();
            while (it.hasNext())
            {
                WeakReference<LogChangeListener> ref = it.next();
                if (ref.get() == listener)
                {
                    it.remove();
                    m_activeListeners = null;
                    break;
                }
            }
        }
    }

    /**
     * Добавляет новое сообщение в лог.
     *
     * @param logLevel уровень сообщения лога
     * @param strMessage текст сообщения лога
     */
    public void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        m_messages.append(entry);

        List<WeakReference<LogChangeListener>> activeListeners = m_activeListeners;
        if (activeListeners == null) {
            synchronized (m_listeners) {
                if (m_activeListeners == null) {
                    List<WeakReference<LogChangeListener>> temp = new ArrayList<>();
                    for (WeakReference<LogChangeListener> ref : m_listeners)
                    {
                        LogChangeListener listener = ref.get();
                        if (listener != null)
                        {
                            temp.add(ref);
                        }
                    }
                    activeListeners = temp;
                    m_activeListeners = activeListeners;
                }
            }
        }

        for (WeakReference<LogChangeListener> ref : activeListeners) {
            LogChangeListener listener = ref.get();
            if (listener != null) {
                listener.onLogChanged();
            }
        }
    }

    /**
     * Возвращает итератор по сообщениям лога в указанном диапазоне.
     *
     * @param start начало диапазона
     * @param count количество сообщений в диапазоне
     * @return итератор по сообщениям лога
     */
    public Iterator<LogEntry> range(int start, int count)
    {
        return m_messages.getIterator(start, count);
    }

    /**
     * Возвращает итератор по всем сообщениям лога.
     *
     * @return итератор по сообщениям лога
     */
    public Iterable<LogEntry> all()
    {
        return m_messages;
    }
}