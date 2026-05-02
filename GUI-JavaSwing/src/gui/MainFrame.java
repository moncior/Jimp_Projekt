package gui;

import javax.swing.*;

public class MainFrame extends JFrame {
    MainFrame(){
        this.setTitle("Graph layout");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
        JLabel label = new JLabel();
        label.setText("TEST");
        this.add(label);
    }
    public static void main(String[] args){
        MainFrame frame = new MainFrame();
    }
}
