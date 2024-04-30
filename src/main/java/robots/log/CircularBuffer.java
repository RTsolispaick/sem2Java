package robots.log;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Кольцевой буфер.
 *
 * @param <T> тип элементов буфера
 */
class CircularBuffer<T> implements Iterable<T> {
    private final T[] buffer;
    private final int capacity;

    private int size;
    private int head;
    private int tail;

    /**
     * Создает кольцевой буфер указанной вместимости.
     *
     * @param capacity вместимость буфера
     */
    public CircularBuffer(int capacity) {
        this.capacity = capacity;
        buffer = (T[]) new Object[capacity];
        size = 0;
        head = 0;
        tail = 0;
    }

    /**
     * Добавляет элемент в конец буфера.
     *
     * @param entry добавляемый элемент
     */
    public synchronized void append(T entry) {
        if (size == capacity) {
            head = (head + 1) % capacity; // Удаление старой записи
            size--;
        }
        buffer[tail] = entry;
        tail = (tail + 1) % capacity;
        size++;
    }

    /**
     * Возвращает размер буфера.
     *
     * @return размер буфера
     */
    public synchronized int size() {
        return size;
    }

    /**
     * Возвращает итератор по элементам буфера в указанном диапазоне.
     *
     * @param start начало диапазона
     * @param count количество элементов в диапазоне
     * @return итератор по элементам буфера
     * @throws IllegalArgumentException если значения start или count недопустимы
     */
    public synchronized Iterator<T> getIterator(int start, int count) {
        if (start + count > size || start < 0 || count < 0)
            throw new IllegalArgumentException("Недопустимые значения start или count");
        Iterator<T> iterator = new BufferIterator(start, count);
        return iterator;
    }

    @Override
    public Iterator<T> iterator() {
        return new BufferIterator(head, size());
    }

    /**
     * Итератор по элементам буфера.
     */
    private class BufferIterator implements Iterator<T> {
        private final int sizeIter;
        private final T[] snapshot;
        private int currentPosition;

        public BufferIterator(int start, int sizeIter) {
            this.sizeIter = sizeIter;
            this.snapshot = (T[]) new Object[sizeIter];
            this.currentPosition = 0;

            synchronized (buffer) {
                for (int i = 0; i < sizeIter; i++) {
                    snapshot[i] = buffer[(start + i) % capacity];
                }
            }
        }

        @Override
        public boolean hasNext() {
            return currentPosition < sizeIter;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Итератор не нашёл следующего значения");
            }
            return snapshot[currentPosition++];
        }
    }

}