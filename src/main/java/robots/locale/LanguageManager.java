package robots.locale;

import robots.serialize.LocaleIO;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Утилитарный класс.
 * - хранит ресурсы выбранной локали
 * - загружает локаль пользователя из файла
 * - сохраняет локаль пользователя в файл
 */
public class LanguageManager {
    private static final LocaleIO localeIO = new LocaleIO();
    private static volatile ResourceBundle instanceBundle;

    private LanguageManager() {
    }

    public static ResourceBundle getBundle() {
        if (instanceBundle == null) {
            instanceBundle = ResourceBundle.getBundle("locale", localeIO.loadLocaleFromJson());
        }
        return instanceBundle;
    }

    public synchronized static boolean setLocale(Locale locale) {
        if (locale.equals(instanceBundle.getLocale()))
            return false;

        instanceBundle = ResourceBundle.getBundle("locale", locale);

        localeIO.saveLocaleToJson(locale);

        return true;
    }
}
