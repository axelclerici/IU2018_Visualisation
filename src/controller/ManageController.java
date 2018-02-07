/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.Controller;
import core.Model;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import model.internationalization.Internationalizable;

/**
 *
 * @author Poisson Blob
 */
public class ManageController implements Controller
{
    private Controller mainController;
    
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
        this.mainController = mainController;
        this.managePane = mainController.managePane;
        this.fullScreen = mainController.fullScreen;
        this.cutImage = mainController.cutImage;
        this.keyWords = mainController.keyWords;
        this.updateKeyWords = mainController.updateKeyWords;
        this.title = mainController.title;
        
        registerForInter(new Internationalizable(managePaneBundle, 
            managePane), getModel());
        registerForInter(new Internationalizable(fullScreenBundle, 
            fullScreen), getModel());
        registerForInter(new Internationalizable(cutImageBundle, 
            cutImage), getModel());
        registerForInter(new Internationalizable(updateKeyWordsBundle, 
            updateKeyWords), getModel());
        registerForInter(new Internationalizable(keyWordsBundle, 
            keyWords), getModel());
        registerForInter(new Internationalizable(titleBundle, 
            title), getModel());
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
