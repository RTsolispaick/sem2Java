package robots.serialize;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс WindowIO отвечает за сохранение и загрузку состояния окон в файл.
 */
public class WindowIO {
    private final File confFile;

    /**
     * Конструктор класса WindowIO.
     * Создает файл конфигурации, если он не существует, иначе использует существующий.
     * Если создать необходимые директории не удалось, выводит сообщение об ошибке в консоль и завершает выполнение программы.
     */
    public WindowIO() {
        String confFilePath = System.getProperty("user.home") +
                File.separator + "Robots" +
                File.separator + "windowStates.json";

        confFile = new File(confFilePath);

        try {
            FileUtils.forceMkdirParent(confFile);
        } catch (IOException e) {
            System.err.println("Не удалось создать структуру приложения");
            System.exit(1);
        }
    }

    /**
     * Сохраняет состояния окон в файл.
     *
     * @param windowStates объект, содержащий состояния окон для сохранения
     */
    public void saveToJson(Map<String, WindowState> windowStates) {
        try (Writer writer = new FileWriter(confFile)) {
            Gson gson = new Gson();
            gson.toJson(windowStates, writer);
        } catch (IOException e) {
            System.err.println("Ошибка в OutputStream");
        }
    }

    /**
     * Загружает состояния окон из файла.
     *
     * @return словарь состояний окон, загруженных из файла
     * @throws IOException если возникают ошибки ввода-вывода при загрузке файла
     */
    public Map<String, WindowState> loadFromJson() throws IOException {
        try (Reader reader = new FileReader(confFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, WindowState>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл конфигурации не найден по пути " + confFile.getPath());
        } catch (IOException e) {
            throw new IOException("Ошибка чтения из файла " + confFile.getPath());
        }
    }
}
