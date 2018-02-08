/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Observer;
import controller.DirectoryObserver;
import model.internationalization.*;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

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
    
    
    protected List<LangObserver> obs;
    protected DirectoryObserver directoryObserver;
    private List<Internationalizable> interElements;
    
    public VisualisationModel() throws IOException
    {
        this.interElements = new ArrayList<>();
        this.obs = new ArrayList<>();
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
        notifyAllObservers(obs);
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

    public void notifyAllObservers(List<LangObserver> o) 
    {
        System.out.println("Notify Observers");
        for(int i = 0; i < o.size(); i ++)
            notifyObserver(o.get(i));
    }

    public void notifyObserver(Observer o) 
    {
        ((LangObserver)o).update(this);
    }

    public void removeObserver(Observer o) 
    {
        obs.remove(o);
    }

    public void addObserver(Observer o) 
    {
        obs.add((LangObserver) o);
    } 
    
    public List<Internationalizable> getInterElements()
    {
        return interElements;
    }
    
    public void addInterElement(Internationalizable inter)
    {
        interElements.add(inter);
    }
    
    public void updateDirectoryPath(String directoryPath)
    {
        imagesLoader.updateDirectoryPath(directoryPath);
    }
    
    public PreferencesLoader getPreferenceLoader()
    {
        return this.preferencesLoader;
    }
}
