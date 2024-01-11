package tools;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

    public static Clip getClipAssociatedToMusic(String location){
        Clip clip = null;
        try{
            File musicPath = new File(location);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

        }catch (Exception e){
            e.printStackTrace();
        }
        return clip;

    }
}
