/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.internationalization.Internationalizable;

/**
 *
 * @author Poisson Blob
 */
public class MetaDataLoader 
{
    private ArrayList<String> content;
    private String fileName;
    private String directoryPath;
    private ArrayList<ImageModel> images;
    
    public MetaDataLoader(String directoryPath, ArrayList<ImageModel> images) throws IOException
    {
        this.content = new ArrayList<>();
        this.images = images;
        this.directoryPath = directoryPath;
        this.fileName = transformDirectoryPath(directoryPath);
        if(!directoryMetaDataExist()) {
            createNewFile();
        }
        loadContent();
        cleanContent();
        setKeyWords();
    }
    
    private void createNewFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for(int i = 0; i < images.size(); i ++) 
            writer.println(images.get(i).getTitle());
        writer.close();
    }
    
    // Vérifie si de nouvelles images ont été ajoutées au dossier
    // pour les ajoutées au content et au fichier
    private void addNewLines()
    {
        for(ImageModel image : images)
        {
            int index = getIndexOf(image.getTitle());
            if (index == -1)
                content.add(image.getTitle());
        }
    }
      
    protected boolean directoryMetaDataExist()
    {
        File f = new File(fileName);
        if(f.exists() && !f.isDirectory()) { 
           return true;
        }
        return false;
    }

    protected void setKeyWords() throws IOException 
    {
        int newImages = 0;
        for(ImageModel image : images)
        {
            String title = image.getTitle();
            String line = content.get(getIndexOf(title));
            image.setKeyWords(cutLine(line));
        }
    }
    
    protected void updateFile(String keyWords, String directoryPath, String title) 
            throws FileNotFoundException, IOException
    {
        String result = title + " " + keyWords;
        content.set(getIndexOf(title), result);
        saveContent();
    }
    
    private void loadContent() throws FileNotFoundException, IOException
    {
        //ajout de la suppression des lignes inutiles
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while((line = br.readLine()) != null)
        {
            content.add(line);
        }
        br.close();
    }
    
    private void saveContent() throws IOException
    {
        Path file = Paths.get(fileName);
        Files.write(file, content, Charset.forName("UTF-8"));
    }

    protected static void deleteWrongFolder(String lastDirectory) 
    {
        String fileName = transformDirectoryPath(lastDirectory);
        File file = new File(fileName);
        file.delete();
    }
    
    private static String transformDirectoryPath(String directory)
    {
        String fileName = "";
        fileName += Consts.METADATADIRECTORY + File.separator;

        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String transformed = directory.replaceAll(pattern, "-");
        transformed = transformed.replaceAll(":", "");
        fileName += transformed + ".txt";

        return fileName;
    }
    
    private static String reverseTransform(String fileName)
    {
        String directoryPath = "";
        directoryPath = fileName.substring(0, fileName.length()-4);
        directoryPath = directoryPath.replaceAll("-", Matcher.quoteReplacement(File.separator));
        directoryPath = directoryPath.substring(0, 1) + ":" + 
                directoryPath.substring(1, directoryPath.length());
        return directoryPath;
    }
    
    private int getIndexOf(String title)
    {
        int index = 0;
        for(String line : content) {

            if(cutTitle(line).equals(title)) { 
                return index;
            }
            index ++;
        }
        return -1;
    }
    
    private String cutLine(String line)
    {
        int lineLength = line.length();
        int titleLength = line.split(" ")[0].length();
        String result = "";
        result += line.substring(titleLength, lineLength);
        return result;
    }
    
    private String cutTitle(String line)
    {
        int titleLength = line.split(" ")[0].length();
        String result = "";
        result += line.substring(0, titleLength);
        return result;
    }

    void updateTitle(String oldTitle, String newTitle) throws IOException 
    {
        String oldLine = content.get(getIndexOf(oldTitle));
        String newLine = newTitle + " " + cutLine(oldLine);
        content.set(getIndexOf(oldTitle), newLine);
        saveContent();
    }
    
    // On vérfie dans que tous les fichiers correspondent à des dossiers
    // encore existants. On supprime les fichiers qui ne servent plus
    public static void deleteAllWrongFiles()
    {
        File folder = new File(Consts.METADATADIRECTORY);
        for(File fileEntry : folder.listFiles())
        {
            String fileName = reverseTransform(fileEntry.getName());
            File target = new File(fileName);
            if(!target.exists())
                fileEntry.delete();
        }
    }
    
    //On vérifie que les informations de content correspondent à des informations
    //qui existent toujours, sinon on les supprime et on met à jour le content
    private void cleanContent() throws IOException
    {
        for(Iterator<String> iterator = content.iterator();
            iterator.hasNext();)
        {
            String line = iterator.next();
            boolean keep = false;
            File folder = new File(directoryPath);
            String title = cutTitle(line);
            for(File fileEntry : folder.listFiles())
            {
                String fileName = fileEntry.getName().split("\\.")[0];
                if(fileName.equals(title)) {
                    keep = true;
                    break;
                }
            }
            if(keep == false)
                iterator.remove();
            else
                keep = false;
        }
        addNewLines();
        saveContent();
    }

}
