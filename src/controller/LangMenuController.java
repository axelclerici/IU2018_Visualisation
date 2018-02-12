package controller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import visualisation.Consts;
import model.VisualisationModel;


public class LangMenuController
{
    private VisualisationModel model;
    private ChoiceBox langMenu;
    private Text langMenuLabel;
    
    protected LangMenuController(VisualisationController mainController)
    {     
        this.model = mainController.getModel();
        this.langMenuLabel = mainController.langMenuLabel;
        this.langMenu = mainController.langMenu;
        model.registerForInter(Consts.LANGMENULABEL, langMenuLabel);
       
        ObservableList<String> langChoices = model.getLangChoices();
        langMenu.setItems(langChoices);
        langMenu.getSelectionModel().select(model.getCurrentLangLabel());

        updateStrings(model.getCurrentLangLabel());
        
        addListener();
    }
    
    private void updateStrings(String langLabel) 
    {
        Runnable command = () -> {
            model.updateCurrentLang(langLabel);
        };
        
        if (Platform.isFxApplicationThread()) 
        {
         command.run();
        } 
        else 
        {
        Platform.runLater(command);
        }
    }
    
    public void addListener()
    {
        langMenu.getSelectionModel().selectedIndexProperty().addListener(
            (ObservableValue<? extends Number> observableValue, 
                Number number, Number number2) -> 
        {
            String langLabel = langMenu.getItems().get((Integer) number2)
                .toString();
            updateStrings(langLabel);
        });
    }
}
