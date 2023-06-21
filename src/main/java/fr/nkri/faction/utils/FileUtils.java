package fr.nkri.faction.utils;

import java.io.*;

public class FileUtils {

    //Permet de crée un fichier
    public static void createFile(File file) throws IOException{
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    //Permet de sauvegarder un fichier
    public static void save(File file, String text) {
        final FileWriter fileWriter;

        try {
            createFile(file);

            fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Permet de retourner le contenu d'un fichier sous forme de String.
    public static String loadContent(File file) {
        if(file.exists()) {
            try {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                final StringBuilder stringBuilder = new StringBuilder();

                String line;
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                return stringBuilder.toString();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "null";
    }
}