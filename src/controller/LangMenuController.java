/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.internationalization.Internationalizable;
import core.Controller;
import core.Model;

import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

/**
 *
 * @author Poisson Blob
 */
public class LangMenuController implements Controller
{
    private Controller mainController;
    private ChoiceBox langMenu;
    private Text langMenuLabel;
    private final String langMenuLabelBundle = "langMenuLabel";
    private List<Internationalizable> interElements;
    
    protected LangMenuController(VisualisationController mainController)
    {     
        this.mainController = mainController;
        this.langMenuLabel = mainController.langMenuLabel;
        this.langMenu = mainController.langMenu;
        registerForInter(new Internationalizable(langMenuLabelBundle, langMenuLabel), 
                getModel());
        this.interElements = mainController.getModel().getInterElements();
       
        ObservableList<String> langChoices = mainController.getModel().getLangChoices();
        langMenu.setItems(langChoices);
        langMenu.getSelectionModel().select(mainController.getModel().getCurrentLangLabel());

        setObservers();
        updateStrings(mainController.getModel().getCurrentLangLabel());
        
        addListener();
    }
    
    private void updateStrings(String langLabel) 
    {
        Runnable command = () -> {
            mainController.getModel().updateCurrentLang(langLabel);
        };
        
        if (Platform.isFxApplicationThread()) 
        {
        // Nous sommes déjà dans le thread graphique
         command.run();
        } 
        else 
        {
        // Nous ne sommes pas dans le thread graphique
        // on utilise runLater.
        Platform.runLater(command);
        }
    }
    
    private void setObservers()
    {
        interElements.forEach((interElement) -> {
            mainController.getModel().addObserver(interElement);
        });
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

    @Override
    public void registerForInter(Internationalizable inter, Model model) 
    {
        model.addInterElement(inter);
    }

    @Override
    public Model getModel() 
    {
        return mainController.getModel();
    }
}
