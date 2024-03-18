package robots.serialize;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class SerializationController {
    private static SerializationController serializationController;
    private final WindowIO windowIO = new WindowIO();
    private Map<String, WindowState> windowStates;

    public SerializationController() {
        if (windowIO.fileIsEmptyOrNotFound()) {
            windowStates = new HashMap<>();
            try {
                windowIO.save((Serializable) windowStates);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            try {
                windowStates = windowIO.load();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void saveState(String id, WindowState ws) {
        windowStates.put(id, ws);
    }

    public WindowState loadState(String id) throws NoSuchElementException {
        if (!windowStates.containsKey(id))
            throw new NoSuchElementException("no saved entry for %s".formatted(id));
        return new WindowState(windowStates.get(id));
    }

    public static SerializationController get() {
        if (serializationController == null)
            serializationController = new SerializationController();
        return serializationController;
    }

    public void flush() {
        try {
            windowIO.save((Serializable) windowStates);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
