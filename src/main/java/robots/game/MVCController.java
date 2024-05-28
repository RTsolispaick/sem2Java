package robots.game;

import javax.swing.*;

/**
 * Интерфейс MVCController определяет методы, которые должен реализовать контроллер
 * для управления графическим интерфейсом пользователя в рамках архитектуры MVC.
 */
public interface MVCController {
    /**
     * Возвращает компонент, ответственный за рисование или отображение данных.
     *
     * @return компонент, реализующий интерфейс {@link JComponent}, который используется для рисования или отображения данных.
     */
    JComponent getPainter();
}
