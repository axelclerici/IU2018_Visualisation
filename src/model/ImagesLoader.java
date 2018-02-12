package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;

public class ImagesLoader 
{
    protected String directoryPath;
    private PreferencesLoader preferencesLoader;
    private ArrayList<ImageModel> images;
    private MetaDataLoader metaDataLoader;
    
    public ImagesLoader(PreferencesLoader preferencesLoader)
    {
        this.preferencesLoader = preferencesLoader;
        directoryPath = getLastDirectory();
        this.images = new ArrayList<>();
    }
    
    protected void updateDirectoryPath(String directoryPath) throws IOException
    {
        this.directoryPath = directoryPath;
        this.preferencesLoader.updateDirectoryPath(directoryPath);
    }
    
    // Lis l'adresse du répertoire enregistré dans le fichier de préférence
    // renvoie l'adresse de ce répertoire s'il existe, null sinon
    private String getLastDirectory()
    {
        String lastDirectory = preferencesLoader.getLastDirectory();
        if(lastDirectory.equals(" ")) { 
            return " ";
        }
        else 
        {
            Path path = Paths.get(lastDirectory);
            if(Files.exists(path)) {
                return lastDirectory;
            }
            else {
                try {
                    updateDirectoryPath(" ");
                } catch (IOException ex) {
                    Logger.getLogger(ImagesLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                return " ";
            }
        }
    }
    
    protected void loadImages() throws IOException
    {
        images = new ArrayList<>();
        int id = 0;
        File folder = new File(directoryPath);
        for (File fileEntry : folder.listFiles()) 
        {
            String mimetype= new MimetypesFileTypeMap().getContentType(fileEntry);
            String type = mimetype.split("/")[0];
            // semble ignorer les png
            if(type.equals("image")) {
                images.add(new ImageModel(fileEntry, id));
                id++;
            }
        }
        metaDataLoader = new MetaDataLoader(directoryPath, images);
    }
    
    protected boolean folderContainsImage()
    {
        File folder = new File(directoryPath);
        for (File fileEntry : folder.listFiles()) 
        {
            String mimetype= new MimetypesFileTypeMap().getContentType(fileEntry);
            String type = mimetype.split("/")[0];
            if(type.equals("image")) {
                return true;
            }
        }
        return false;
    }
    
    protected ArrayList<ImageModel> getImages()
    {
        return images;
    }

    protected void setKeyWords(ImageModel activeImageModel, String keyWords) throws IOException 
    {
        int id = Integer.parseInt(activeImageModel.getStringId());
        activeImageModel.setKeyWords(keyWords);
        metaDataLoader.updateFile(keyWords, directoryPath, activeImageModel.getTitle());
    }

    void setTitle(ImageModel activeImageModel, String newTitle) throws IOException 
    {
        String oldTitle = activeImageModel.getTitle();
        activeImageModel.setTitle(newTitle);
        metaDataLoader.updateTitle(oldTitle, newTitle);
    }
}
