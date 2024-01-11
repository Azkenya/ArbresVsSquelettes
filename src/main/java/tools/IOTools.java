package tools;

import java.io.*;

public class IOTools {
    public static void writeToFile(String toWrite,String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources"+filename));
        writer.write(toWrite);
        writer.close();
    }

    public static String readFromFile(String filename) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources"+filename));
        String currentLine = reader.readLine();
        reader.close();
        return currentLine;
    }
}
