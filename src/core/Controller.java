package core;

import model.internationalization.Internationalizable;
import javafx.fxml.Initializable;

/**
 *
 * @author Poisson Blob
 */
public interface Controller
{
   /* Cette fonction permet à tous les "sous-controller" de pouvoir enregistrer
    * les éléments graphiques de manière à ce qu'ils puissent être traduits
    */
    public void registerForInter(Internationalizable inter, Model model);

    public Model getModel();
}
