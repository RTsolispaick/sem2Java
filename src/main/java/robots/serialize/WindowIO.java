
package robots.serialize;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс(singlton), отвечающий за сохранение состояний об окнах.
 */
public class WindowIO {
    private final File confFile;

    public WindowIO() {
        String confFilePath = System.getProperty("user.home") +
                File.separator + "Robots" +
                File.separator + "windowStates.conf";

        confFile = new File(confFilePath);

        if (!confFile.getParentFile().exists() && !confFile.getParentFile().mkdir()) {
            System.err.println("Failed to create app structure");
            System.exit(1);
        }
    }

    public boolean fileIsEmptyOrNotFound() {
        return !confFile.exists() || confFile.length() == 0;
    }

    public Map<String, WindowState> load() throws IOException{
        try (InputStream is = new FileInputStream(confFile);
             ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {

            return (HashMap<String, WindowState>) ois.readObject();

        } catch (FileNotFoundException e) {
            throw new IOException("Configuration file not found at " + confFile.getPath());
        } catch (ClassNotFoundException e) {
            throw new IOException("Data is corrupted in " + confFile.getPath());
        } catch (IOException e) {
            throw new IOException("Unexpected object type in configuration file");
        }
    }

    public void save(Serializable windowStates) throws IOException {
        try (OutputStream os = new FileOutputStream(confFile);
             ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {

            oos.writeObject(windowStates);

        } catch (FileNotFoundException e) {
            throw new IOException("Can't find file structure.", e);
        }
    }
}
