/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Consts;
import model.ImageModel;
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
    
    private ImageModel activeImageModel;
    
    /**
     *
     * @param mainController
     */
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
        
        initUpdateKeyWordsButton();
        initTitleField();
    }

    protected void update(ImageModel activeImageModel) 
    {
        this.activeImageModel = activeImageModel;
        this.managePane.setVisible(true);
        title.setText(activeImageModel.getTitle());
    }
    
    private void initUpdateKeyWordsButton()
    {
        updateKeyWords.setOnAction((ActionEvent event) -> 
        {
            updateKeyWords();
        });
    }
    
    private void initTitleField()
    {
        title.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if(oldPropertyValue)
                {
                    updateTitle();
                }
            }
        });
        
        title.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event) 
            {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    updateTitle();
                }
            }
        });
    }
    
    private void updateKeyWords()
    {
        String text = keyWords.getText();
        String[] separatedText = text.split(" ");
        activeImageModel.setKeyWords(new ArrayList<>(Arrays.asList(separatedText)));
    }
    
    private void updateTitle()
    {
        String oldTitle = activeImageModel.getTitle();
        String newTitle = title.getText();
        if(newTitle.matches("[_a-zA-Z0-9\\-\\.]+") && oldTitle.equals(newTitle) 
                == false && activeImageModel.isValidName(newTitle))
            activeImageModel.setTitle(newTitle);
        else
            title.setText(oldTitle);
    }
}
