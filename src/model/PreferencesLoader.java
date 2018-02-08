/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Poisson Blob
 */
public class PreferencesLoader 
{
    private String path;
    private ArrayList<String> content;
    
    public PreferencesLoader(String path) throws FileNotFoundException, IOException
    {
        this.path = path;
        loadContent();
    }
    
    private void loadContent() throws FileNotFoundException, IOException
    {
        this.content = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while((line = br.readLine()) != null)
        {
            this.content.add(line);
        }
        br.close();
    }
    
    protected List<String> getContent()
    {
        return content;
    }
    
    public String[] getActiveLine()
    {
        String[] line = null;
        for(int i = 0; i < content.size(); i++)
        {
            if(content.get(i).charAt(0) == '1')
                line = content.get(i).split(" ");
        }
        return line;
    }
    
    public void setActiveLang(String langLabel)
    {
        String[] line = null;
        StringBuilder builder = null;
        for (int i = 0; i < content.size(); i++)
        {
            line = content.get(i).split(" ");
            if(line[0].equals("1") && line[1].equals(langLabel))
            {
                return;
            }
            // set the right label as active
            else if(line[0].equals("0") && line[1].equals(langLabel))  
            {
                builder = new StringBuilder(content.get(i));
                builder.setCharAt(0, '1');
                content.set(i, "" + builder);
            }
            // set all the inactive labels as 0
            else
            {
                builder = new StringBuilder(content.get(i));
                builder.setCharAt(0, '0');
                content.set(i, "" + builder);
            }
        }
    }
    
    public void saveContent() throws IOException
    {
        Path file = Paths.get(this.path);
        Files.write(file, this.content, Charset.forName("UTF-8"));
    }
    
    public ObservableList<String> getAllLangLabels()
    {
        ArrayList<String> choices = new ArrayList<>();
        String [] line = null;
        for(int i = 0; i < content.size(); i ++)
        {
            line = content.get(i).split(" ");
            choices.add(line[1]);
        }
        ObservableList<String> availableChoices = 
                FXCollections.observableArrayList(choices);
        return availableChoices;
    }
    
    public String[] getLocaleParams(String langLabel)
    {
        String[] line = null;
        String[] params = null;
        for(int i = 0; i < content.size(); i ++)
        {
            line = content.get(i).split(" ");
            if(line[1].equals(langLabel))
                params = new String[]{line[2], line[3]};
        }
        return params;
    }

    protected String getLastDirectory() 
    {
        String temp = "";
        String temp2 = "B:" + File.separator + "Users" + File.separator + "Blob"
            + File.separator + "Documents" + File.separator + "cool stuff";
        String temp3 = "B:" + File.separator + "Users" + File.separator + "Blob"
            + File.separator + "Documents" + File.separator + "cool stuff" 
            + File.separator + "missingno";
        return temp3;
    }
    
    public void updateDirectoryPath(String directoryPath)
    {
        System.out.println("Je dois écrire " + directoryPath + " dans le fichier préférences");
    }
}
