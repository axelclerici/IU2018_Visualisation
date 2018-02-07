/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Poisson Blob
 */
public class ImagesLoader 
{
    private String directoryPath;
    
    public ImagesLoader()
    {

    }
    
    protected void updateDirectoryPath(String directoryPath)
    {
        this.directoryPath = directoryPath;
        System.out.println("Chemin mis à jour, je dois maintenant l'enregistrer dans les préférences");
    }
}
