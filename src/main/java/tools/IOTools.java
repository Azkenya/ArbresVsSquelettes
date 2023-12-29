package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class IOTools {
    public static void writeToFile(String toWrite,String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources"+filename));
        writer.write(toWrite);
        writer.close();
    }
}
