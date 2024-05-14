package robots.locale;

import robots.serialize.LocaleIO;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Менеджер языков предоставляет доступ к ресурсам локализации и управляет выбором языка.
 */
public final class LanguageManager {
    private static final LocaleIO localeIO = new LocaleIO();
    private ResourceBundle resourceBundle;

    /**
     * Приватный конструктор. Инициализирует объект ResourceBundle на основе текущей локали, загруженной из сохраненных данных.
     */
    private LanguageManager() {
        resourceBundle = ResourceBundle.getBundle("locale", localeIO.loadLocaleFromFile());
    }

    private static class LanguageManagerHandler {
        private final static LanguageManager instance = new LanguageManager();
    }

    /**
     * Получает экземпляр класса LanguageManager.
     *
     * @return экземпляр класса LanguageManager
     */
    private static LanguageManager getInstance() {
        return LanguageManagerHandler.instance;
    }

    /**
     * Получает строковое значение из ресурсов локализации по указанному ключу.
     *
     * @param propName ключ ресурса
     * @return строковое значение из ресурсов локализации
     */
    public static String getStr(String propName) {
        return getInstance().resourceBundle.getString(propName);
    }

    /**
     * Устанавливает новую локаль и обновляет ресурсы локализации с учетом новой локали.
     * Если указанная локаль совпадает с текущей, изменения не производятся.
     * Новая локаль сохраняется для последующих вызовов.
     *
     * @param locale новая локаль
     * @return true, если локаль была изменена, иначе false
     */
    public static boolean setLocale(Locale locale) {
        if (locale.equals(getInstance().resourceBundle.getLocale()))
            return false;

        getInstance().resourceBundle = ResourceBundle.getBundle("locale", locale);
        localeIO.saveLocaleToFile(locale);

        return true;
    }
}
