/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import model.internationalization.Internationalizable;
import model.internationalization.LangObserver;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Poisson Blob
 */
public interface Model extends Observable
{
    // Functions related to the language change
    public String getCurrentLangLabel();
    public ObservableList<String> getLangChoices();
    public void updateCurrentLang(String langLabel);
    public String getString(String string);
    public void notifyAllObservers(List<LangObserver> o);
    public List<Internationalizable> getInterElements();
    public void addInterElement(Internationalizable inter);
}
