/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.Controller;
import core.Model;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import model.internationalization.Internationalizable;

/**
 *
 * @author Poisson Blob
 */
public class GalleryController implements Controller
{
    private Controller mainController;
    
    private TitledPane galleryPane;
    private final String galleryPaneBundle = "galleryPane";
    
    private TextField searchBar;
    private final String searchBarBundle = "searchBar";
    
    private Button searchBarButton;
    private GridPane gridPane;

    
    public GalleryController(VisualisationController mainController)
    {
        this.mainController = mainController;
        this.galleryPane = mainController.galleryPane;
        this.searchBar = mainController.searchBar;
        this.searchBarButton = mainController.searchBarButton;
        this.gridPane = mainController.gridPane;
        
        registerForInter(new Internationalizable(galleryPaneBundle, 
            galleryPane), getModel());
        registerForInter(new Internationalizable(searchBarBundle, 
            searchBar), getModel());
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
