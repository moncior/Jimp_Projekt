package repository;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResultRepository {

    private Point2D.Double[] points;
    private int[] ids;
    private Edge[] edges;

    public void readCoordinates(File coord){

        List<Point2D.Double> pointsList = new ArrayList<>();
        List<Integer> idsList = new ArrayList<>();

        try(BufferedReader in = new BufferedReader(new FileReader(coord))){

            String line;

            while((line = in.readLine()) != null){

                String[] data = line.split(" ");

                int id = Integer.parseInt(data[0]);
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);

                idsList.add(id);
                pointsList.add(new Point2D.Double(x, y));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        points = pointsList.toArray(new Point2D.Double[0]);

        ids = new int[idsList.size()];

        for(int i = 0; i < idsList.size(); i++){
            ids[i] = idsList.get(i);
        }
    }

    public void readEdges(File edgeFile){

        List<Edge> edgesList = new ArrayList<>();

        try(BufferedReader in = new BufferedReader(new FileReader(edgeFile))){

            String line;

            while((line = in.readLine()) != null){

                String[] data = line.split(" ");

                int id = Integer.parseInt(data[0]);
                int from = Integer.parseInt(data[1]);
                int to = Integer.parseInt(data[2]);
                double length = Double.parseDouble(data[3]);

                edgesList.add(new Edge(id, from, to, length));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        edges = edgesList.toArray(new Edge[0]);
    }

    public Point2D.Double[] getPoints(){
        return points;
    }

    public int[] getIds(){
        return ids;
    }

    public Edge[] getEdges(){
        return edges;
    }
}
