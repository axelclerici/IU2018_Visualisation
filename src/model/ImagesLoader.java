/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Poisson Blob
 */
public class ImagesLoader 
{
    private String directoryPath;
    private PreferencesLoader preferencesLoader;
    
    public ImagesLoader(PreferencesLoader preferencesLoader)
    {
        this.preferencesLoader = preferencesLoader;
        directoryPath = getLastDirectory();
        
    }
    
    protected void updateDirectoryPath(String directoryPath)
    {
        this.directoryPath = directoryPath;
        this.preferencesLoader.updateDirectoryPath(directoryPath);
        System.out.println("Chemin mis à jour, je dois maintenant l'enregistrer dans les préférences");
    }
    
    // Lis l'adresse du répertoire enregistré dans le fichier de préférence
    // renvoie l'adresse de ce répertoire s'il existe, null sinon
    private String getLastDirectory()
    {
        String lastDirectory = preferencesLoader.getLastDirectory();
        if(lastDirectory.equals("")) { 
            return null;
        }
        else 
        {
            Path path = Paths.get(lastDirectory);
            if(Files.exists(path)) {
                return lastDirectory;
            }
            else {
                return null;
            }
        }
    }
}
