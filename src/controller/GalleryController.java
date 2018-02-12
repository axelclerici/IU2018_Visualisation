package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import visualisation.Consts;
import model.ImageModel;
import model.Observable;
import model.VisualisationModel;
import visualisation.Observer;

public class GalleryController implements Observable
{
    private VisualisationModel model;
    private TitledPane galleryPane;
    private TitledPane managePane;
    private TextField searchBar;
    private Button searchBarButton;
    private GridPane gridPane;
    private ArrayList<ImageView> imageViews;
    private ToggleGroup radioGroup;
    private ActivationObserver obs;
    
    private ImageModel activeImageModel;

    /**
     *
     * @param mainController
     */
    public GalleryController(VisualisationController mainController)
    {
        this.model = mainController.getModel();
        this.managePane = mainController.managePane;
        this.galleryPane = mainController.galleryPane;
        this.searchBar = mainController.searchBar;
        this.searchBarButton = mainController.searchBarButton;
        this.gridPane = mainController.gridPane;
        
        model.registerForInter(Consts.GALLERYPANE, galleryPane);
        model.registerForInter(Consts.SEARCHBAR, searchBar);
        
        initSearchBarButton();
    }
    
    /**
     *
     */
    protected void update(ArrayList<ImageModel> images)
    {
        Runnable command = () -> 
        {
            clearGallery();
            imageViews = getGalleryViews(images);
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
        {
            if(newVal != null) {
                try {
                    setActiveImageModel(newVal);
                } catch (IOException ex) {
                    Logger.getLogger(GalleryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                managePane.setVisible(false);
        });
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
    
    private void setActiveImageModel(Toggle newVal) throws IOException 
    {
        int activeIndex = Integer.parseInt(((RadioButton)newVal).getId());
        activeImageModel = model.getImages().get(activeIndex);
        notifyObserver(obs);
    }

    /**
     *
     * @param o
     */
    @Override
    public void notifyObserver(Observer o) 
    {
        obs.update(this);
    }

    /**
     *
     * @param o
     */
    @Override
    public void removeObserver(Observer o) 
    {
        this.obs = null;
    }

    /**
     *
     * @param o
     */
    @Override
    public void addObserver(Observer o) 
    {
        this.obs = (ActivationObserver) o;
    }
    
    /**
     *
     * @return
     */
    public ImageModel getActiveImageModel()
    {
        return activeImageModel;
    }
    
    private void initSearchBarButton()
    {
        searchBarButton.setOnAction((ActionEvent event) -> 
        {
            try {
                search(searchBar.getText());
            } catch (IOException ex) {
                Logger.getLogger(GalleryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void search(String text) throws IOException
    {
        ArrayList<ImageModel> results = new ArrayList<>();
        if(text.equals("")) 
            results = model.getImages();
        else
        {
            for(ImageModel image : model.getImages())
            {
                if (image.getKeyWords().contains(text))
                    results.add(image);
            }
        }
        radioGroup.selectToggle(null);
        update(results);
    }
}