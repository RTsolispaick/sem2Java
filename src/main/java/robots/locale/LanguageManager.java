package robots.locale;

import robots.serialize.LocaleIO;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Менеджер языков предоставляет доступ к ресурсам локализации и управляет выбором языка.
 */
public class LanguageManager {
    private static final LocaleIO localeIO = new LocaleIO();
    private static volatile ResourceBundle instanceBundle;

    private LanguageManager() {
    }

    /**
     * Получает объект ResourceBundle, представляющий ресурсы локализации.
     * Если объект еще не создан, он инициализируется на основе текущей локали, загруженной из сохраненных данных.
     *
     * @return объект ResourceBundle с ресурсами локализации
     */
    public static ResourceBundle getBundle() {
        if (instanceBundle == null) {
            instanceBundle = ResourceBundle.getBundle("locale", localeIO.loadLocaleFromJson());
        }
        return instanceBundle;
    }

    /**
     * Устанавливает новую локаль и обновляет объект ResourceBundle с учетом новой локали.
     * Если указанная локаль совпадает с текущей, изменения не производятся.
     * Новая локаль сохраняется для последующих вызовов.
     *
     * @param locale новая локаль
     * @return true, если локаль была изменена, иначе false
     */
    public synchronized static boolean setLocale(Locale locale) {
        if (locale.equals(instanceBundle.getLocale()))
            return false;

        instanceBundle = ResourceBundle.getBundle("locale", locale);

        localeIO.saveLocaleToJson(locale);

        return true;
    }
}
