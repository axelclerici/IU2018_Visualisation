package model;

import java.io.File;
import java.util.regex.Pattern;

public class ImageModel 
{
    private int id;
    private File file;
    private String title;
    private String keyWords;
    
    public ImageModel(File file, int id)
    {
        this.id = id;
        this.file = file;
        this.title = getTitleFromPath();
        this.keyWords = "";
    }
    
    public String getPath()
    {
        return file.getAbsolutePath();
    }
    
    public String getStringId()
    {
        return String.valueOf(id);
    }
    
    private String getTitleFromPath()
    {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] parts = file.getAbsolutePath().split(pattern);
        String name = parts[parts.length-1];
        parts = name.split("\\.");
        return parts[0];
    }
    
    public String getTitle()
    {
        return title;
    }
    
    protected void setKeyWords(String keyWords)
    {
        this.keyWords = keyWords;
    }
    
    protected void setTitle(String newTitle)
    {
        File file2 = getNewFile(newTitle);
        file.renameTo(file2);
        file = file2;
        this.title = newTitle;

    }
    
    private File getNewFile(String newTitle)
    {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] parts = file.getAbsolutePath().split(pattern);
        String name = parts[parts.length-1];
        String[] parts2 = name.split("\\.");
        parts2[0] = newTitle;
        parts[parts.length-1] = parts2[0] + "." + parts2[1];
        String newPath = "";
        for(int i = 0; i < parts.length; i ++)
        {
            newPath += parts[i];
            newPath += File.separator;
        }
        newPath = newPath.substring(0, newPath.length()-1);
        System.out.println(getPath());
        System.out.println(newPath);

        return new File(newPath);
    }
    
    public boolean isValidName(String newTitle)
    {
        File file2 = getNewFile(newTitle);
        if (file2.exists())
            return false;
        else
            return true;
    }
    
    public String getKeyWords()
    {
        return this.keyWords;
    }
}
