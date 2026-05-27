package gui;

import controller.MainController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.geom.Point2D;
import java.io.File;
import javax.swing.*;
import repository.Edge;
import repository.ResultRepository;

public class MainFrame extends JFrame {

    private JButton readCoordinates;
    private JFileChooser fileChooser;
    private DrawGraph graph;

    public MainFrame(){
        setTitle("Graph layout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        MainController controller = new MainController(this, new ResultRepository());

        fileChooser = new JFileChooser();

        readCoordinates = new JButton("Wczytaj pliki");
        readCoordinates.setPreferredSize(new Dimension(120, 25));
        readCoordinates.addActionListener(e -> controller.loadCoordinates());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(readCoordinates);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public File getFile(){
        fileChooser.setCurrentDirectory(new File("."));

        int result = fileChooser.showOpenDialog(this);

        if(result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    public void showGraph(Point2D.Double[] points, int[] ids, Edge[] edges){

        if(graph != null){
            remove(graph);
        }

        graph = new DrawGraph(points, ids, edges);

        add(graph, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
