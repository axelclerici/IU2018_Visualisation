package controller;

import java.io.File;
import java.io.IOException;
import model.VisualisationModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import visualisation.Consts;
import model.Observable;

//Classe qui sert de Main Controller
public class VisualisationController implements Initializable,DirectoryObserver,
        ActivationObserver
{
    protected VisualisationModel model;
    
    @FXML  protected ChoiceBox langMenu;
    @FXML  protected Text langMenuLabel;
    private LangMenuController langMenuController;
    @FXML protected Button diaporamaButton;
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
        //Initialisation du modèle
        try 
        {
            this.model = new VisualisationModel();
            model.addObserver(this);            
        } catch (IOException ex) {}
        initSubElements();
    }
    
    // Initialisation des différents boutons
    public void initSubElements()
    {
        this.galleryController = new GalleryController(this);
        galleryController.addObserver(this);
        try 
        {
            model.updateDirectoryPath(model.getDirectoryPath());
        } catch (IOException ex) {}
        this.manageController = new ManageController(this);
        // Il faut initialiser ce controller en dernier, car il faut que tous
        // les éléments existants soient enregistrés pour la traduction par
        // les autres controllers
        this.langMenuController = new LangMenuController(this);
        
        initDiaporamaButton();
        initChangeFolderButton();
    }
    

    public VisualisationModel getModel()
    {
        return this.model;
    }

    private void initDiaporamaButton()
    {
        model.registerForInter(Consts.DIAPORAMABUTTON, diaporamaButton);
        diaporamaButton.setOnAction((ActionEvent event) -> 
        {
            startDiaporama();
        });
    }
    
    // Non développé
    private void startDiaporama()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pas encore implémenté");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        String message = Consts.MESSAGE;
        alert.setContentText(message);
        alert.show();
    }
    
    public void initChangeFolderButton()
    {
        changeFolderButton.setOnAction((ActionEvent event) -> 
        {
            try 
            {
                selectFolder();
            } catch (IOException ex) {}
        });
    }
    
    // Fonction permettant à l'utilisateur de choisir le répertoire
    private void selectFolder() throws IOException
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        //on récupère ici le stage à partir d'un pane quelconque
        Stage stage = (Stage) galleryPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        String path;
        if(selectedDirectory != null) 
        {
            path = selectedDirectory.getAbsolutePath();
            model.updateDirectoryPath(path);
        }
    }
    

    //Met à jour le chemin indiqué par l'utilisateur. Si le chemin est bon,
    //et que le dossier contient des images, alors le galleryPane est activé 
    // et mis à jour.
    private void updateDirectoryPath(String directoryPath) throws IOException
    {
        Runnable command = () -> {
            // si le dossier passé est " ", alors un message d'information est affiché,
            // il faut qu'il puisse être traduit 
            if(directoryPath.equals(" ")) 
            {
                this.model.registerForInter(Consts.DIRECTORYPATH, folderPath);
            }
            else {
                // sinon le nom du dossier est affiché, et il ne faut pas qu'il
                // varie en fonction de la langue
                model.unregisterForInter(folderPath);
                folderPath.setText(directoryPath);
                if (model.folderContainsImage()) 
                {
                    galleryPane.setDisable(false);
                    managePane.setVisible(false);
                    try {
                        galleryController.update(model.getImages());
                    } catch (IOException ex) {
                        Logger.getLogger(VisualisationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    galleryController.clearGallery();
                    galleryPane.setDisable(true);
                }
            }
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

    /**
     *
     * @param o
     */
    @Override
    public void update(Observable o) 
    {
        if(o instanceof VisualisationModel)
        {
            try {
            updateDirectoryPath(model.getDirectoryPath());
        } catch (IOException ex) {
            Logger.getLogger(VisualisationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else if(o instanceof GalleryController)
        {
            manageController.update(((GalleryController)o).getActiveImageModel());
        }
    }
}
