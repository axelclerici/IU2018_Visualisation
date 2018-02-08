/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import model.VisualisationModel;
import model.internationalization.Internationalizable;

/**
 *
 * @author Poisson Blob
 */
public class GalleryController
{
    private VisualisationModel model;
    
    private TitledPane galleryPane;
    private final String galleryPaneBundle = "galleryPane";
    
    private TextField searchBar;
    private final String searchBarBundle = "searchBar";
    
    private Button searchBarButton;
    private GridPane gridPane;

    
    public GalleryController(VisualisationController mainController)
    {
        this.model = mainController.getModel();
        this.galleryPane = mainController.galleryPane;
        this.searchBar = mainController.searchBar;
        this.searchBarButton = mainController.searchBarButton;
        this.gridPane = mainController.gridPane;
        
        registerForInter(new Internationalizable(galleryPaneBundle, 
            galleryPane));
        registerForInter(new Internationalizable(searchBarBundle, 
            searchBar));
    }
    
    public void registerForInter(Internationalizable inter) 
    {
        model.addInterElement(inter);
    }   
}
