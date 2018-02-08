/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.internationalization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import model.PreferencesLoader;

/**
 *
 * @author Poisson Blob
 */
public class Internationalization 
{
    protected String langLabel;
    protected String language;
    protected String country;
    protected Locale currentLocale;
    protected ResourceBundle strings;
    protected PreferencesLoader preferencesLoader;
    
    public void update(String language, String country, String langLabel) throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        this.language = language;
        this.country = country;
        this.langLabel = langLabel;
        this.currentLocale = new Locale(language, country);
        this.strings = ResourceBundle.getBundle("model.internationalization.StringsBundle", currentLocale);
        writeCurrentLang();

    }
    
    public Internationalization(PreferencesLoader preferencesLoader) throws IOException
    {
        this.preferencesLoader = preferencesLoader;
        loadCurrentLang();
        this.currentLocale = new Locale(language, country);
        this.strings = ResourceBundle.getBundle("model.internationalization.StringsBundle", currentLocale);
    }
       
    public String getString(String string)
    {
        return strings.getString(string);
    }
    
    private void loadCurrentLang() throws FileNotFoundException, IOException
    {
        String[] current;
        current = preferencesLoader.getActiveLine();
        this.langLabel = current[1];
        this.language = current[2];
        this.country = current[3];
    }
    
    private void writeCurrentLang() throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        this.preferencesLoader.setActiveLang(langLabel);
        this.preferencesLoader.saveContent();
    }
    
    public ObservableList<String> getLangChoices()
    {
        return this.preferencesLoader.getAllLangLabels();
    }
    
    public String getLabel()
    {
        return this.langLabel;
    }
    
    public String[] getLocaleParams(String label) throws FileNotFoundException, IOException
    {
        return this.preferencesLoader.getLocaleParams(label);
    }

    public String getLangLabel() 
    {
        return this.langLabel;
    }
}
