package controller;

import repository.ResultRepository;

import javax.swing.*;
import java.io.File;

public class MainController{
    private ResultRepository data;

    public MainController(ResultRepository data){
        this.data = data;
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

    public void readGraph(File f){
        if(f != null)
            data.getGraph(f);
    }

    public void readCoordinates(File f){
        if(f != null)
            data.getCoordinates(f);
    }
}
