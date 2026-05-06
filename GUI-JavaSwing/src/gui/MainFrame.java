package gui;

import controller.MainController;
import repository.ResultRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {
    MainFrame(){
        this.setTitle("Graph layout");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
        JLabel label = new JLabel();
        label.setText("TEST");
        this.add(label);
        MainController controller = new MainController(new ResultRepository());
        JButton readGraph = new JButton();
        JButton readCoordinates = new JButton();
        readGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File graph = controller.getFile();
                controller.readGraph(graph);
            }
        });
        readCoordinates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File coordinates = controller.getFile();
                controller.readCoordinates(coordinates);
            }
        });
    }
    public static void main(String[] args){
        MainFrame frame = new MainFrame();
    }
}
