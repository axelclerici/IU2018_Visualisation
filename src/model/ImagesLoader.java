/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Poisson Blob
 */
public class ImagesLoader 
{
    protected String directoryPath;
    private PreferencesLoader preferencesLoader;
    private ArrayList<ImageModel> images;
    
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
        loadImages();
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
                    // remplace un chemin erroné dans le fichier préférences
                    // par " ", plus standart. Sert notamment quand le dossier
                    // a été supprimé
                    // !!!!!!!!!!!!!!!!!! il faut également supprimé le fichier de métadata correspondant
                    updateDirectoryPath(" ");
                } catch (IOException ex) {
                    Logger.getLogger(ImagesLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                return " ";
            }
        }
    }
    
    protected void loadImages()
    {
        images = new ArrayList<>();
        int id = 0;
        File folder = new File(directoryPath);
        for (File fileEntry : folder.listFiles()) 
        {
            String mimetype= new MimetypesFileTypeMap().getContentType(fileEntry);
            String type = mimetype.split("/")[0];
            if(type.equals("image")) {
                // check si directoryPath.txt existe, et si oui s'il contient les meta data de fileEntry (étape réalisée par le MetaDataLoader)
                // si existe alors construction par le MetaDataLoader : il faut toujours l'image ET les méta Data pour charger
                // prevoir le cas où il faut supprimer des méta data car l'image n'est plus présente
                //sinon :
                images.add(new ImageModel(fileEntry, id));
                System.out.println("je charge : " + fileEntry.getAbsolutePath() + " " + id);
                id++;
            }
        }
        // à ce moment, si directoryPath.txt n'existait pas, on le créé et on enregistre les méta data dedans
        // ou s'il a changé, on le met à jour
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
}
