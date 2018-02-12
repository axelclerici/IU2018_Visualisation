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
import visualisation.Consts;

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
        else if (object instanceof TextField) {
            if(string.equals(Consts.TITLE) || string.equals(Consts.DIRECTORYPATH))
                ((TextField)object).setText(((VisualisationModel) o).getString(string));
            else
                ((TextField)object).setPromptText(((VisualisationModel) o).getString(string));
        }
        else if (object instanceof TitledPane)
            ((TitledPane)object).setText(((VisualisationModel) o).getString(string));
        else
            System.out.println("Cas non traité");
    }
    
    public Object getObject()
    {
        return object;
    }
}
