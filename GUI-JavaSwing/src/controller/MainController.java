package controller;

import gui.MainFrame;
import java.awt.geom.Point2D;
import java.io.File;

import repository.Edge;
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

                // wybór pliku punktów
                File coordFile = frame.getFile();

                if(coordFile == null){
                    return;
                }

                // wybór pliku krawędzi
                File edgeFile = frame.getFile();

                if(edgeFile == null){
                    return;
                }

                // wczytanie danych
                repository.readCoordinates(coordFile);
                repository.readEdges(edgeFile);

                // pobranie danych
                Point2D.Double[] points = repository.getPoints();
                int[] ids = repository.getIds();
                Edge[] edges = repository.getEdges();

                // rysowanie grafu
                frame.showGraph(points, ids, edges);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }
}
