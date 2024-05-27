package Attempt2;

import java.io.FileReader;
import java.io.FileWriter;

public interface IOManager {
    public void loadFromFile(FileReader reader);
    public void saveToFile(FileWriter writer);
}
