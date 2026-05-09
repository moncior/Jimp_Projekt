package repository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultRepository {
    private HashMap<Integer, Coordinates> position;
    private ArrayList<Adjacency> graph;

    public ResultRepository(){
        position = new HashMap<>();
        graph = new ArrayList<>();

    }
    public void readGraph(File graph){

    }
    public void readCoordinates(File path){

    }
}

