package controller;

import gui.MainFrame;
import repository.ResultRepository;

import javax.swing.*;
import java.io.File;

public class MainController{
    private ResultRepository repository;
    private MainFrame frame;

    public MainController(MainFrame frame, ResultRepository repository){
        this.frame = frame;
        this.repository = repository;
    }

    public void loadGraph(){
        new Thread(() -> {
            try{
                File f = frame.getFile();
                if(f != null)
                    repository.readGraph(f);
                //wyswietl graf w frame
            }
            catch (Exception e){
                //dodac wyswietlanie bledu w gui
            }
        }).start();
    }

    public void loadCoordinates(){
        new Thread(() -> {
            try{
                File f = frame.getFile();
                if(f != null)
                    repository.readCoordinates(f);
                //wyswietl graf w frame
            }
            catch (Exception e){
                //dodac wyswietlanie bledu w gui
            }
        }).start();
    }
}
