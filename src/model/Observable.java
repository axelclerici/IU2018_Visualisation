package model;

import controller.Observer;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Poisson Blob
 */
public interface Observable 
{
    public void notifyObserver(Observer o);
    public void removeObserver(Observer o);
    public void addObserver(Observer o);
}
