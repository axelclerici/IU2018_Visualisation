/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import model.VisualisationModel;
import model.internationalization.Internationalizable;

/**
 *
 * @author Poisson Blob
 */
public class ManageController
{
    private VisualisationModel model;
    
    private TitledPane managePane;
    private final String managePaneBundle = "managePane";
    
    private Button fullScreen;
    private final String fullScreenBundle = "fullScreen";
    
    private Button cutImage;
    private final String cutImageBundle = "cutImage";
    
    private Button updateKeyWords;
    private final String updateKeyWordsBundle = "updateKeyWords";
    
    private TextArea keyWords;
    private final String keyWordsBundle = "keyWords";
        
    private TextField title;
    private final String titleBundle = "title";
    
    public ManageController(VisualisationController mainController)
    {
        this.model = mainController.model;
        this.managePane = mainController.managePane;
        this.fullScreen = mainController.fullScreen;
        this.cutImage = mainController.cutImage;
        this.keyWords = mainController.keyWords;
        this.updateKeyWords = mainController.updateKeyWords;
        this.title = mainController.title;
        
        model.addObserver(new Internationalizable(managePaneBundle, 
            managePane));
        model.addObserver(new Internationalizable(fullScreenBundle, 
            fullScreen));
        model.addObserver(new Internationalizable(cutImageBundle, 
            cutImage));
        model.addObserver(new Internationalizable(updateKeyWordsBundle, 
            updateKeyWords));
        model.addObserver(new Internationalizable(keyWordsBundle, 
            keyWords));
        model.addObserver(new Internationalizable(titleBundle, 
            title));
    }
}
