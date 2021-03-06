package model;

import java.io.BufferedReader;
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

public class PreferencesLoader 
{
    private String preferencesPath;
    private ArrayList<String> content;
    
    public PreferencesLoader(String preferencesPath) throws FileNotFoundException, IOException
    {
        this.preferencesPath = preferencesPath;
        loadContent();
    }
    
    private void loadContent() throws FileNotFoundException, IOException
    {
        this.content = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(preferencesPath));
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
        for(int i = 0; i < content.size()-1; i++)
        {
            if(content.get(i).charAt(0) == '1')
                line = content.get(i).split(" ");
        }
        return line;
    }
    
    public void setActiveLang(String langLabel) throws IOException
    {
        String[] line = null;
        StringBuilder builder = null;
        for (int i = 0; i < content.size()-1; i++)
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
        saveContent();
    }
    
    private void saveContent() throws IOException
    {
        Path file = Paths.get(this.preferencesPath);
        Files.write(file, this.content, Charset.forName("UTF-8"));
    }
    
    public ObservableList<String> getAllLangLabels()
    {
        ArrayList<String> choices = new ArrayList<>();
        String [] line = null;
        for(int i = 0; i < content.size()-1; i ++)
        {
            line = content.get(i).split(" ");
            choices.add(line[1]);
        }
        List<String> langLabels = choices.subList(0,3);
        ObservableList<String> availableChoices = 
                FXCollections.observableArrayList(choices);
        return availableChoices;
    }
    
    public String[] getLocaleParams(String langLabel)
    {
        String[] line = null;
        String[] params = null;
        for(int i = 0; i < content.size()-1; i ++)
        {
            line = content.get(i).split(" ");
            if(line[1].equals(langLabel))
                params = new String[]{line[2], line[3]};
        }
        return params;
    }

    protected String getLastDirectory() 
    {
        return content.get(3);
    }
    
    public void updateDirectoryPath(String directoryPath) throws IOException
    {
        content.set(3, directoryPath);
        saveContent();
    }
}
