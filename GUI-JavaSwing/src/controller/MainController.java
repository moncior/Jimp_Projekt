package controller;

import repository.ResultRepository;

import javax.swing.*;
import java.io.File;

public class MainController{
    private ResultRepository repository;

    public MainController(ResultRepository repository){
        this.repository = repository;
    }

    public File getFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        int result = chooser.showOpenDialog(chooser);
        if(result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    public void loadGraph(){
        new Thread(() -> {
            try{
                File f = getFile();
                if(f != null)
                    repository.readGraph(f);
                //wyswietl graf w frame
            }
            catch (Exception e){

            }
        }).start();
    }

    public void loadCoordinates(){
        new Thread(() -> {
            try{
                File f = getFile();
                if(f != null)
                    repository.readCoordinates(f);
                //wyswietl graf w frame
            }
            catch (Exception e){

            }
        }).start();
    }
}
