/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Consts;
import model.ImageModel;
import model.VisualisationModel;

/**
 *
 * @author Poisson Blob
 */
public class GalleryController
{
    private VisualisationModel model;
    private TitledPane galleryPane;
    private TextField searchBar;
    private Button searchBarButton;
    private GridPane gridPane;
    private ArrayList<ImageView> imageViews;
    private ToggleGroup radioGroup;

    public GalleryController(VisualisationController mainController)
    {
        this.model = mainController.getModel();
        this.galleryPane = mainController.galleryPane;
        this.searchBar = mainController.searchBar;
        this.searchBarButton = mainController.searchBarButton;
        this.gridPane = mainController.gridPane;
        
        model.registerForInter(Consts.GALLERYPANE, galleryPane);
        model.registerForInter(Consts.SEARCHBAR, searchBar);
    }
    
    protected void update()
    {
        Runnable command = () -> 
        {
            clearGallery();
            imageViews = getGalleryViews(model.getImages());
            addImageViewsToGallery();
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
    
    protected void clearGallery()
    {
        gridPane.getChildren().clear();
    }
    
    private ArrayList<ImageView> getGalleryViews(ArrayList<ImageModel> imagesModel)
    {
        ArrayList<ImageView> galleryViews = new ArrayList<>();
        for(ImageModel imageModel : imagesModel)
        {
            String path = "file:" + imageModel.getPath();
            Image image = new Image(path);
            ImageView view = new ImageView();
            view.setId(imageModel.getStringId());
            view.setImage(image);
            view.setFitWidth(Consts.FITWIDTH);
            view.setFitHeight(Consts.FITWIDTH);
            view.setSmooth(true);
            view.setCache(true);
            galleryViews.add(view);
        }
        return galleryViews;
    }

    private void addImageViewsToGallery() 
    {
        this.radioGroup = new ToggleGroup();
        radioGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> 
                System.out.println(newVal + " was selected"));
        int row = 0;
        int col = 0;
        for(ImageView imageView : imageViews)
        {
            if(col == 4) {
                col = 0;
                row++;
            }
            gridPane.add(imageView, col, row);
            RadioButton button = new RadioButton();
            button.setId(imageView.getId());
            button.setToggleGroup(radioGroup);
            gridPane.add(button, col, row);
            col ++;
        }
    }
    
    public ImageModel getActiveImageModel(Toggle newVal) 
    {
        int activeIndex = Integer.parseInt(((RadioButton)newVal).getId());
        ImageModel activeImageModel = model.getImages().get(activeIndex);
        return activeImageModel;
    }
}