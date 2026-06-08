package repository;

public class Edge {

    public String id;
    public int from;
    public int to;
    public double length;

    public Edge(String id, int from, int to, double length){
        this.id = id;
        this.from = from;
        this.to = to;
        this.length = length;
    }
}
