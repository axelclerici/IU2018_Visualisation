/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualisation;

import java.io.File;

/**
 *
 * @author Poisson Blob
 */
public final class Consts 
{  
    private Consts()
    {
        throw new AssertionError();
    }
    
    public final static String MESSAGE = "L'implémentation de cette fonctionnalité n'est pas demandée dans le sujet, elle sera ajoutée si du temps reste avant la fin du projet";
    /* LangMenu */
    public final static String LANGMENULABEL = "langMenuLabel";
    
    /* Gallery */
    public final static int    FITWIDTH = 140;
    public final static int    COLUMNS = 4;
    public final static String GALLERYPANE = "galleryPane";
    public final static String SEARCHBAR = "searchBar";
    
    /* Manage */
    public final static String MANAGEPANE = "managePane";
    public final static String FULLSCREEN = "fullScreen";
    public final static String CUTIMAGE = "cutImage";
    public final static String UPDATEKEYWORDS = "updateKeyWords";
    public final static String KEYWORDS = "keyWords";
    public final static String TITLE = "title";
    public final static String METADATADIRECTORY = "src" + File.separator + 
            "model" + File.separator + "metadata";
    
    /* Visualisation */
    public final static String DIAPORAMABUTTON = "diaporamaButton";
    public final static String DIRECTORYPATH = "directoryPath";
}