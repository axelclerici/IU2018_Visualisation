/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.internationalization.*;
import core.*;

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
public class VisualisationModel implements Model
{
    private Internationalization inter;
    private ImagesLoader imagesLoader;
    
    protected List<LangObserver> obs;
    private List<Internationalizable> interElements;
    
    public VisualisationModel() throws IOException
    {
        this.interElements = new ArrayList<>();
        this.inter = new Internationalization();
        this.obs = new ArrayList<>();
        this.imagesLoader = new ImagesLoader();
    }
    
    @Override
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
    
    @Override
    public String getString(String string)
    {
        return inter.getString(string);
    }
    
    @Override
    public ObservableList<String> getLangChoices()
    {
        return this.inter.getLangChoices();
    }
    
    @Override
    public String getCurrentLangLabel()
    {
        return this.inter.getLangLabel();
    }

    @Override
    public void notifyAllObservers(List<LangObserver> o) 
    {
        System.out.println("Notify Observers");
        for(int i = 0; i < o.size(); i ++)
            notifyObserver(o.get(i));
    }

    @Override
    public void notifyObserver(Observer o) 
    {
        ((LangObserver)o).update(this);
    }

    @Override
    public void removeObserver(Observer o) 
    {
        obs.remove(o);
    }

    @Override
    public void addObserver(Observer o) 
    {
        obs.add((LangObserver) o);
    } 
    
    @Override
    public List<Internationalizable> getInterElements()
    {
        return interElements;
    }
    
    @Override
    public void addInterElement(Internationalizable inter)
    {
        interElements.add(inter);
    }
    
    public void updateDirectoryPath(String directoryPath)
    {
        imagesLoader.updateDirectoryPath(directoryPath);
    }
}
