package robots.serialize;

import java.io.*;
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
                File.separator + "windowStates.conf";

        confFile = new File(confFilePath);

        if (!confFile.getParentFile().exists() && !confFile.getParentFile().mkdir()) {
            System.err.println("Не удалось создать структуру приложения");
            System.exit(1);
        }
    }

    /**
     * Загружает состояния окон из файла.
     *
     * @return словарь состояний окон, загруженных из файла
     * @throws IOException если возникают ошибки ввода-вывода при загрузке файла
     */
    public Map<String, WindowState> loadFromFile() throws IOException {
        try (InputStream is = new FileInputStream(confFile);
             ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {

            return (HashMap<String, WindowState>) ois.readObject();

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл конфигурации не найден по пути " + confFile.getPath());
        } catch (ClassNotFoundException e) {
            throw new IOException("Данные повреждены в файле " + confFile.getPath());
        } catch (IOException e) {
            throw new IOException("Непредвиденный тип объекта в файле конфигурации");
        }
    }

    /**
     * Сохраняет состояния окон в файл.
     *
     * @param windowStates объект, содержащий состояния окон для сохранения
     */
    public void saveToFile(Serializable windowStates) {
        try (OutputStream os = new FileOutputStream(confFile);
             ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {

            oos.writeObject(windowStates);

        } catch (IOException e) {
            System.err.println("Ошибка в OutputStream");
        }
    }
}
