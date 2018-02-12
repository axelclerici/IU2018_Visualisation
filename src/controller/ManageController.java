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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import visualisation.Consts;
import model.image.ImageModel;
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
    private Button rotateLeft;
    private Button rotateRight;
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
        this.rotateLeft = mainController.rotateLeft;
        this.rotateRight = mainController.rotateRight;
        this.managePane = mainController.managePane;
        this.fullScreen = mainController.fullScreen;
        this.cutImage = mainController.cutImage;
        this.keyWords = mainController.keyWords;
        this.updateKeyWords = mainController.updateKeyWords;
        this.title = mainController.title;
        
        registerAllForInter();
        initAllElements();
    }

    protected void update(ImageModel activeImageModel) 
    {
        this.activeImageModel = activeImageModel;
        this.managePane.setVisible(true);
        title.setText(activeImageModel.getTitle());
        setKeyWords();
    }
    
    private void initUpdateKeyWordsButton()
    {
        updateKeyWords.setOnAction((ActionEvent event) -> 
        {
            try {
                updateKeyWords();
            } catch (IOException ex) {
                Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                    try {
                        updateTitle();
                    } catch (IOException ex) {
                        Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        title.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event) 
            {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        updateTitle();
                    } catch (IOException ex) {
                        Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    private void updateKeyWords() throws IOException
    {
        String text = keyWords.getText();
        model.setKeyWords(activeImageModel, text);
    }
    
    private void updateTitle() throws IOException
    {
        String oldTitle = activeImageModel.getTitle();
        String newTitle = title.getText();
        if(newTitle.matches("[_a-zA-Z0-9\\-\\.]+") && oldTitle.equals(newTitle) 
                == false && activeImageModel.isValidName(newTitle))
            //activeImageModel.setTitle(newTitle);
            model.setTitle(activeImageModel, newTitle);
        else
            title.setText(oldTitle);
    }
    
    private void setKeyWords()
    {
        String modelKeyWords = activeImageModel.getKeyWords();
        this.keyWords.setText(modelKeyWords);
    }
    
    private void initRotateButtons()
    {
        rotateLeft.setOnAction((ActionEvent event) -> 
        {
            rotateLeft();
        });
        
        rotateRight.setOnAction((ActionEvent event) -> 
        {
            rotateRight();
        });
    }
    
    private void rotateLeft()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Pas encore implémenté");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        String message = Consts.MESSAGE;
        alert.setContentText(message);
        alert.show();
    }
    
    private void rotateRight()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Pas encore implémenté");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        String message = Consts.MESSAGE;
        alert.setContentText(message);
        alert.show();
    }
    
    private void registerAllForInter()
    {
        model.registerForInter(Consts.MANAGEPANE, managePane);
        model.registerForInter(Consts.FULLSCREEN, fullScreen);
        model.registerForInter(Consts.CUTIMAGE, cutImage);
        model.registerForInter(Consts.UPDATEKEYWORDS, updateKeyWords);
        model.registerForInter(Consts.KEYWORDS, keyWords);
        model.registerForInter(Consts.TITLE, title);
    }
    
    private void initAllElements ()
    {
        initUpdateKeyWordsButton();
        initTitleField();
        initRotateButtons();
        initCutImageButton();
        initFullScreenButton();
    }
    
    private void initCutImageButton()
    {
        cutImage.setOnAction((ActionEvent event) -> 
        {
            cutImage();
        });
    }
    
    private void cutImage()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Pas encore implémenté");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        String message = Consts.MESSAGE;
        alert.setContentText(message);
        alert.show();
    }
    
    private void initFullScreenButton()
    {
        fullScreen.setOnAction((ActionEvent event) -> 
        {
            fullScreen();
        });
    }
    
    public void fullScreen()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Pas encore implémenté");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        String message = Consts.MESSAGE;
        alert.setContentText(message);
        alert.show();
    }
}
