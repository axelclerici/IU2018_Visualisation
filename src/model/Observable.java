package model;

import visualisation.Observer;

public interface Observable 
{
    public void notifyObserver(Observer o);
    public void removeObserver(Observer o);
    public void addObserver(Observer o);
}
