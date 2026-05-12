package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;


public class ResultRepository {
    private HashMap<Integer, Coordinates> position;
    private ArrayList<Adjacency> graph;

    public ResultRepository(){
        position = new HashMap<>();
        graph = new ArrayList<>();
    }
    public void readGraph(File adjacency){
        try(BufferedReader in = new BufferedReader(new FileReader(adjacency))){
            String line;
            while((line = in.readLine()) != null){
                String[] data = line.split(" ");
                int from = Integer.parseInt(data[0]);
                int to = Integer.parseInt(data[1]);
                double weight = Double.parseDouble(data[2]);
                graph.add(new Adjacency(from, to, weight));
            }
        }
        catch(IOException e){
            //wyswietlic blad w gui
        }



    }
    public void readCoordinates(File coord){
        try(BufferedReader in = new BufferedReader(new FileReader(coord))){
            String line;
            while((line = in.readLine()) != null){
                String[] data = line.split(" ");
                int id = Integer.parseInt(data[0]);
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);
                position.put(id, new Coordinates(x, y));
            }
        }
        catch(IOException e){
            //wyswietlic blad w gui
        }
    }
}

