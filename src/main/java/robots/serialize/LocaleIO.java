package robots.serialize;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Locale;

public class LocaleIO {
    private final File userConfLocaleFile;

    public LocaleIO() {
        String confFilePath = System.getProperty("user.home") +
                File.separator + "Robots" +
                File.separator + "locale.json";

        userConfLocaleFile = new File(confFilePath);

        try {
            FileUtils.forceMkdirParent(userConfLocaleFile);
        } catch (IOException e) {
            System.err.println("Не удалось создать структуру приложения");
        }
    }

    public void saveLocaleToJson(Locale locale) {
        try (Writer writer = new FileWriter(userConfLocaleFile)) {
            Gson gson = new Gson();
            gson.toJson(locale, writer);
        } catch (IOException e) {
            System.err.println("Ошибка в OutputStream");
        }
    }

    public Locale loadLocaleFromJson() {
        try (Reader reader = new FileReader(userConfLocaleFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Locale>() {}.getType();
            Locale temp = gson.fromJson(reader, type);
            return temp == null ? new Locale("ru") : temp;
        } catch (IOException e) {
            System.err.println("Файл не существует" + e.getMessage());
            return new Locale("ru");
        }
    }
}
