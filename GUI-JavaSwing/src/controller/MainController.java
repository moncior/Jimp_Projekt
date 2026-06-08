package controller;

import gui.MainFrame;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import repository.Edge;
import repository.ResultRepository;

import javax.swing.*;

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
                File coordFile = frame.getFile("Wybierz plik z koordynatami wierzchołków");

                if(coordFile == null){
                    return;
                }
                try{
                    repository.readCoordinates(coordFile);
                }
                catch(IOException io){
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(frame,
                                    "Nie udało się wczytać pliku",
                                    "Błąd wczytywania pliku",
                                    JOptionPane.ERROR_MESSAGE)
                    );
                    return;
                }
                catch(NumberFormatException n){
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(frame,
                                    "Plik zawiera niepoprawne dane",
                                    "Błąd wczytywania pliku",
                                    JOptionPane.ERROR_MESSAGE)
                    );
                    return;
                }
                // wybór pliku krawędzi
                File edgeFile = frame.getFile("Wybierz plik z krawędziami grafu");

                if(edgeFile == null){
                    return;
                }

                try{
                    repository.readEdges(edgeFile);
                }
                catch(IOException io){
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(frame,
                                    "Nie udało się wczytać pliku",
                                    "Błąd wczytywania pliku",
                                    JOptionPane.ERROR_MESSAGE)
                    );
                    return;
                }
                catch(NumberFormatException n){
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(frame,
                                    "Plik zawiera niepoprawne dane",
                                    "Błąd wczytywania pliku",
                                    JOptionPane.ERROR_MESSAGE)
                    );
                    return;
                }

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
