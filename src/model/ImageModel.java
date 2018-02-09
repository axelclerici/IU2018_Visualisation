/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;

/**
 *
 * @author Poisson Blob
 */
public class ImageModel 
{
    private int id;
    private File file;
    
    public ImageModel(File file, int id)
    {
        this.id = id;
        this.file = file;
    }
    
    public String getPath()
    {
        return file.getAbsolutePath();
    }
    
    public String getStringId()
    {
        return String.valueOf(id);
    }
}
