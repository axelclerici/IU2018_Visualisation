/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Poisson Blob
 */
public class ImagesLoader 
{
    protected String directoryPath;
    private PreferencesLoader preferencesLoader;
    
    public ImagesLoader(PreferencesLoader preferencesLoader)
    {
        this.preferencesLoader = preferencesLoader;
        directoryPath = getLastDirectory();
        
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
                    // remplace un chemin erroné dans le fichier préférences
                    // par " ", plus standart. Sert notamment quand le dossier
                    // du fichier préférences
                    updateDirectoryPath(" ");
                } catch (IOException ex) {
                    Logger.getLogger(ImagesLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                return " ";
            }
        }
    }
}
