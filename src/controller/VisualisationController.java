/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.Controller;
import core.Model;
import java.io.IOException;
import model.VisualisationModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.internationalization.Internationalizable;


/**
 *
 * @author Poisson Blob
 */
public class VisualisationController implements Initializable,Controller
{
    protected Model model;
    
    /* Les éléments de la vue Internationalization */
    @FXML  protected ChoiceBox langMenu;
    @FXML  protected Text langMenuLabel;
    private LangMenuController langMenuController;
    
    @FXML protected Button diaporamaButton;
    private final String diaporamaButtonBundle = "diaporamaButton";
    
    @FXML protected TextField folderPath;
    @FXML protected Button changeFolderButton;
    
    /* Les éléments composant la galerie */
    private GalleryController galleryController;
    @FXML protected TitledPane galleryPane;
    @FXML protected TextField searchBar;
    @FXML protected Button searchBarButton;
    @FXML protected GridPane gridPane;
    
    /* Les éléments composant la partie gestion */
    private ManageController manageController;
    @FXML protected TitledPane managePane;
    @FXML protected Button rotateLeft;
    @FXML protected Button rotateRight;
    @FXML protected Button fullScreen;
    @FXML protected Button cutImage;
    @FXML protected Button updateKeyWords;
    @FXML protected TextArea keyWords;
    @FXML protected TextField title;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try {
            this.model = new VisualisationModel();
        } catch (IOException ex) {
            Logger.getLogger(VisualisationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initSubElements();
    }
    
    public Model getModel()
    {
        return this.model;
    }

    @Override
    public void registerForInter(Internationalizable inter, Model model) 
    {
        model.addInterElement(inter);
    }
    
    private void initDiaporamaButton()
    {
        registerForInter(new Internationalizable(diaporamaButtonBundle, 
            diaporamaButton), model);
        diaporamaButton.setOnAction((ActionEvent event) -> 
        {
            startDiaporama();
        });
    }
    
    private void startDiaporama()
    {
        System.out.println("L'implémentation de cette fonctionnalité n'est pas"
                + "demandée dans le sujet, elle sera ajoutée si du temps reste"
                + "avant la fin du projet");
    }
    
    public void initSubElements()
    {
        initDiaporamaButton();
        this.galleryController = new GalleryController(this);
        this.manageController = new ManageController(this);
        // Il faut initialiser ce controller en dernier, car il doit connaître tous
        // les éléments existants pour la traduction
        this.langMenuController = new LangMenuController(this);
    }
}
