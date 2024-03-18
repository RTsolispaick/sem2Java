package robots.serialize;

import java.util.NoSuchElementException;

public interface Stateful {

    void restore() throws NoSuchElementException;

    void save();
}
