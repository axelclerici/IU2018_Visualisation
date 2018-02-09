/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import visualisation.Observer;
import controller.DirectoryObserver;
import model.internationalization.*;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Poisson Blob
 */
public class VisualisationModel implements Observable
{
    private Internationalization inter;
    private ImagesLoader imagesLoader;
    
    private PreferencesLoader preferencesLoader;
    
    private static final String PREFERENCES = "preferences.txt";
    private static final String PREFERENCES_PATH = System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "model" + File.separator
            + PREFERENCES;
    
    
    protected List<Internationalizable> interElements;
    protected DirectoryObserver directoryObserver;
    
    public VisualisationModel() throws IOException
    {
        this.interElements = new ArrayList<>();
        this.preferencesLoader = new PreferencesLoader(PREFERENCES_PATH);
        this.inter = new Internationalization(preferencesLoader);
        this.imagesLoader = new ImagesLoader(preferencesLoader);
    }
    
    public void updateCurrentLang(String label)
    {
        String[] locale = null;
        try {
            locale = this.inter.getLocaleParams(label);
        } catch (IOException ex) {
            Logger.getLogger(VisualisationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.inter.update(locale[0], locale[1], label);
        } catch (IOException ex) {
            Logger.getLogger(VisualisationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyAllObservers();
    }
    
    public String getString(String string)
    {
        return inter.getString(string);
    }
    
    public ObservableList<String> getLangChoices()
    {
        return this.inter.getLangChoices();
    }
    
    public String getCurrentLangLabel()
    {
        return this.inter.getLangLabel();
    }

    public void notifyAllObservers() 
    {
        interElements.forEach((interElement) -> {
            notifyObserver(interElement);
        });
    }

    @Override
    public void notifyObserver(Observer o) 
    {
        if(o instanceof LangObserver) {
            ((LangObserver)o).update(this);
        }
        else if(o instanceof DirectoryObserver) {
            ((DirectoryObserver)o).update(this);  
        }
    }

    @Override
    public void removeObserver(Observer o) 
    {
        interElements.remove(o);
    }

    @Override
    public void addObserver(Observer o) 
    {
        if(o instanceof Internationalizable) {
            interElements.add((Internationalizable) o);
        }
        else if(o instanceof DirectoryObserver) {
            directoryObserver = (DirectoryObserver) o;
        }
    } 
    
    public List<Internationalizable> getInterElements()
    {
        return interElements;
    }
    
    public void updateDirectoryPath(String directoryPath) throws IOException
    {
        imagesLoader.updateDirectoryPath(directoryPath);
        notifyObserver(directoryObserver);
    }
    
    public PreferencesLoader getPreferencesLoader()
    {
        return this.preferencesLoader;
    }
    
    public String getDirectoryPath()
    {
        return imagesLoader.directoryPath;
    }
    
    public boolean folderContainsImage()
    {
        return imagesLoader.folderContainsImage();
    }
    
    //todo
    public ArrayList<ImageModel> getImages()
    {
        return imagesLoader.getImages();
    }
    
    public void registerForInter(String string, Object object)
    {
        addObserver(new Internationalizable(string, object));
    }
    
    public void unregisterForInter(Object object)
    {
        for(Internationalizable interElement : interElements)
        {
            if(interElement.getObject().equals(object)) {
                removeObserver(interElement);
            }
        }
    }
}