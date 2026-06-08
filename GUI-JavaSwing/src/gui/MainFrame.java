package gui;

import controller.MainController;
import java.awt.BorderLayout;
import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import repository.Edge;
import repository.ResultRepository;

public class MainFrame extends JFrame {

    private final JButton readCoordinates;
    private final JButton clearGraph;
    private final JButton hideInfo;
    private final JFileChooser fileChooser;
    private DrawGraph graph;
    private final JToolBar toolBar;

    public MainFrame(){
        setTitle("Graph layout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        MainController controller = new MainController(this, new ResultRepository());

        fileChooser = new JFileChooser();

        readCoordinates = new JButton("Wczytaj pliki");
        readCoordinates.setPreferredSize(new Dimension(120, 25));
        readCoordinates.addActionListener(e -> controller.loadCoordinates());

        clearGraph = new JButton("Usuń graf");
        clearGraph.setPreferredSize(new Dimension(120, 25));
        clearGraph.addActionListener(e -> graph.clear());

        hideInfo = new JButton("Przełącz etykiety");
        hideInfo.setPreferredSize(new Dimension(120, 25));
        hideInfo.addActionListener(e -> graph.toggleLabels());

        toolBar = new JToolBar();
        toolBar.add(readCoordinates);
        toolBar.add(clearGraph);
        toolBar.add(hideInfo);

        add(toolBar, BorderLayout.NORTH);

        graph = new DrawGraph(new Point2D.Double[0], new int[0], new Edge[0]);
        add(graph, BorderLayout.CENTER);
        setVisible(true);
    }

    public File getFile(String message){
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle(message);
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
