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
import model.Consts;
import model.VisualisationModel;

/**
 *
 * @author Poisson Blob
 */
public class ManageController
{
    private VisualisationModel model;
    
    private TitledPane managePane;
    private Button fullScreen;
    private Button cutImage;
    private Button updateKeyWords;
    private TextArea keyWords;   
    private TextField title;
    
    public ManageController(VisualisationController mainController)
    {
        this.model = mainController.model;
        this.managePane = mainController.managePane;
        this.fullScreen = mainController.fullScreen;
        this.cutImage = mainController.cutImage;
        this.keyWords = mainController.keyWords;
        this.updateKeyWords = mainController.updateKeyWords;
        this.title = mainController.title;
        
        model.registerForInter(Consts.MANAGEPANE, managePane);
        model.registerForInter(Consts.FULLSCREEN, fullScreen);
        model.registerForInter(Consts.CUTIMAGE, cutImage);
        model.registerForInter(Consts.UPDATEKEYWORDS, updateKeyWords);
        model.registerForInter(Consts.KEYWORDS, keyWords);
        model.registerForInter(Consts.TITLE, title);
    }
}
