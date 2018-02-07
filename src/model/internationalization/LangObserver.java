/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.internationalization;
import core.Observable;
import core.Observer;
import java.util.List;

/**
 *
 * @author Poisson Blob
 */
public interface LangObserver extends Observer
{
    public void update(Observable o);
}
