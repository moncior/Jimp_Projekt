package controller;

import gui.MainFrame;
import java.awt.geom.Point2D;
import java.io.File;
import repository.ResultRepository;

public class MainController {

    private ResultRepository repository;
    private MainFrame frame;

    public MainController(MainFrame frame, ResultRepository repository){
        this.frame = frame;
        this.repository = repository;
    }

    public void loadCoordinates(){
        new Thread(() -> {
            try{
                File f = frame.getFile();

                if(f != null){
                    repository.readCoordinates(f);

                    Point2D.Double[] points = repository.getPoints();
                    int[] ids = repository.getIds();

                    frame.showGraph(points, ids);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
