package gui;

import controller.MainController;
import repository.ResultRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {
    private JButton readGraph;
    private JButton readCoordinates;
    private JFileChooser fileChooser;

    MainFrame(){
        setTitle("Graph layout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
        MainController controller = new MainController(new ResultRepository());
        readGraph = new JButton();
        readCoordinates = new JButton();
        readGraph.addActionListener(e -> controller.loadGraph());
        readCoordinates.addActionListener(e -> controller.loadCoordinates());
    }

    public File getFile(){
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(fileChooser);
        if(result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static void main(String[] args){
        MainFrame frame = new MainFrame();
    }
}
