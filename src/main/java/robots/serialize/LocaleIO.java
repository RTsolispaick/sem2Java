package robots.serialize;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Locale;

/**
 * Класс для сериализации и десериализации локали в файл.
 */
public class LocaleIO {
    private final File userConfLocaleFile;

    /**
     * Конструктор класса LocaleIO.
     * Определяет путь к файлу локали и создает его, если не существует.
     */
    public LocaleIO() {
        String confFilePath = System.getProperty("user.home") +
                File.separator + "Robots" +
                File.separator + "locale.json";

        userConfLocaleFile = new File(confFilePath);

        try {
            FileUtils.forceMkdirParent(userConfLocaleFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Не удалось создать структуру приложения");
            e.printStackTrace();
        }
    }

    /**
     * Сохраняет локаль в файл.
     *
     * @param locale сохраняемая локаль
     */
    public void saveLocaleToFile(Locale locale) {
        try (Writer writer = new FileWriter(userConfLocaleFile)) {
            Gson gson = new Gson();
            gson.toJson(locale, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Ошибка в OutputStream");
            e.printStackTrace();
        }
    }

    /**
     * Загружает локаль из файла.
     *
     * @return загруженная локаль, либо локаль по умолчанию (ru), если файл не существует или произошла ошибка при чтении
     */
    public Locale loadLocaleFromFile() {
        try (Reader reader = new FileReader(userConfLocaleFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Locale>() {}.getType();
            Locale temp = gson.fromJson(reader, type);
            return temp == null ? new Locale("ru") : temp;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Файл не существует или была ошибка чтения из файла" + userConfLocaleFile.getPath());
            e.printStackTrace();
            return new Locale("ru");
        }
    }
}
