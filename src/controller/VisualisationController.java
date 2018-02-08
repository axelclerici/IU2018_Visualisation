/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.internationalization.Internationalizable;


/**
 *
 * @author Poisson Blob
 */
public class VisualisationController implements Initializable,DirectoryObserver
{
    protected VisualisationModel model;
    
    /* Les éléments de la vue Internationalization */
    @FXML  protected ChoiceBox langMenu;
    @FXML  protected Text langMenuLabel;
    private LangMenuController langMenuController;
    
    /* Le bouton "diaporama" et la sélection du dossier */
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
    
    public VisualisationModel getModel()
    {
        return this.model;
    }

    public void registerForInter(Internationalizable inter, VisualisationModel model) 
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
        initChangeFolderButton();
        this.galleryController = new GalleryController(this);
        this.manageController = new ManageController(this);
        // Il faut initialiser ce controller en dernier, car il doit connaître tous
        // les éléments existants pour la traduction
        this.langMenuController = new LangMenuController(this);
    }
    
    // Ajoute l'action correspondante au bouton pour choisir un répertoire
    public void initChangeFolderButton()
    {
        changeFolderButton.setOnAction((ActionEvent event) -> 
        {
            selectFolder();
        });
    }
    
    // Fonction permettant à l'utilisateur de choisir le répertoire
    public void selectFolder()
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        //on récupère ici le stage à partir d'un pane quelconque
        Stage stage = (Stage) galleryPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        String path;
        if(selectedDirectory != null) {
            path = selectedDirectory.getAbsolutePath();
            folderPath.setText(path);
            if(getModel() instanceof VisualisationModel)
                ((VisualisationModel)getModel()).updateDirectoryPath(path);
        }
    }
}
