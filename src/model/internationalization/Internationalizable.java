/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.internationalization;

import model.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import model.VisualisationModel;

/**
 *
 * @author Poisson Blob
 */

// Wrap all GUI elements with text susceptible to be modifies if language changes in
// Internationalizable object, then bind them to the model in the LangMenuController
// setObservers() method.
// Just a reminder that "string" has to be registered in the different 
//StringsBundle properties file.
public class Internationalizable implements LangObserver
{
    private String string;
    private Object object;
    
    public Internationalizable(String string, Object object)
    {
        this.string = string;
        this.object = object;
    }

    @Override
    public void update(Observable o) 
    {
        if (object instanceof Label)
            ((Label) object).setText(((VisualisationModel) o).getString(string));
        else if (object instanceof CheckBox)
            ((CheckBox)object).setText(((VisualisationModel) o).getString(string));
        else if (object instanceof Tab)
            ((Tab)object).setText(((VisualisationModel) o).getString(string));
        else if (object instanceof Button)
            ((Button)object).setText(((VisualisationModel) o).getString(string));
        else if (object instanceof TextArea)
            ((TextArea)object).setPromptText(((VisualisationModel) o).getString(string));
        else if (object instanceof Label) {
            ((Label)object).setText(((VisualisationModel) o).getString(string));
        }
        else if (object instanceof Text)
            ((Text)object).setText(((VisualisationModel) o).getString(string));
        else if (object instanceof TextField)
            ((TextField)object).setText(((VisualisationModel) o).getString(string));
        else if (object instanceof TitledPane)
            ((TitledPane)object).setText(((VisualisationModel) o).getString(string));
        else
            System.out.println("Cas non traité");
    }
}
