package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import repository.Edge;

public class DrawGraph extends JPanel {

    private Point2D.Double[] points;
    private int[] ids;
    private Edge[] edges;

    private double scale = 4.0;
    private double offsetX = 0.0;
    private double offsetY = 0.0;
    private int selectedIndex = -1;
    private final int RADIUS = 6;
    private boolean draggingCamera = false;
    private Point lastMouse;
    private boolean showLabels = true;

    public DrawGraph(Point2D.Double[] pts, int[] ids, Edge[] edges) {
        this.points = pts;
        this.ids = ids;
        this.edges = edges;


        addMouseWheelListener(e -> {

            double oldScale = scale;
            double zoomFactor = 1.1;

            if (e.getPreciseWheelRotation() < 0 && scale < 80) {
                scale *= zoomFactor;
            } else if (e.getPreciseWheelRotation() > 0 && scale > 0.2){
                scale /= zoomFactor;
            }


            Point p = e.getPoint();

            double scaleChange = scale / oldScale;

            offsetX = p.x - scaleChange * (p.x - offsetX);
            offsetY = p.y - scaleChange * (p.y - offsetY);
            repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                Point2D.Double world = screenToWorld(e.getPoint());
                selectedIndex = -1;
                for(int i=0; i < points.length; i++){
                    double dx = points[i].x - world.x;
                    double dy = points[i].y - world.y;

                    if(dx * dx + dy * dy < 10){
                        selectedIndex = i;
                        break;
                    }
                }
                if(selectedIndex == -1){
                    draggingCamera = true;
                    lastMouse = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                selectedIndex = -1;
                draggingCamera = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(selectedIndex != -1) {
                    Point2D.Double world = screenToWorld(e.getPoint());
                    points[selectedIndex].x = world.x;
                    points[selectedIndex].y = world.y;
                }
                else if (draggingCamera) {
                    offsetX += e.getX() - lastMouse.x;
                    offsetY += e.getY() - lastMouse.y;
                    lastMouse = e.getPoint();
                }
                repaint();
            }
        });
    }

    private Point2D.Double screenToWorld(Point p) {
        return new Point2D.Double(
                (p.x - offsetX) / scale,
                (p.y - offsetY) / scale
        );
    }

    @Override
    public void addNotify(){
        super.addNotify();

        offsetX = getWidth() / 2.0;
        offsetY = getHeight() / 2.0;
    }

    private void drawAxes(Graphics2D g2) {

        g2.setColor(Color.BLACK);

        double left = -offsetX / scale;
        double right = (getWidth() - offsetX) / scale;

        double top = -offsetY / scale;
        double bottom = (getHeight() - offsetY) / scale;

        // oś X
        g2.drawLine((int) left, 0, (int) right, 0);

        // oś Y
        g2.drawLine(0, (int) top, 0, (int) bottom);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(0.7f));
        g2.setFont(new Font("Arial", Font.PLAIN, 16));

        AffineTransform original = g2.getTransform();

        g2.translate(offsetX, offsetY);
        g2.scale(scale, scale);


        drawAxes(g2);

        g2.setColor(Color.RED);

        for (Edge edge : edges) {

            int fromIndex = -1;
            int toIndex = -1;

            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == edge.from) fromIndex = i;
                if (ids[i] == edge.to) toIndex = i;
            }

            if (fromIndex == -1 || toIndex == -1) continue;

            Point2D.Double p1 = points[fromIndex];
            Point2D.Double p2 = points[toIndex];

            g2.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
        }

        g2.setColor(Color.BLUE);

        for (int i = 0; i < points.length; i++) {

            Point2D.Double p = points[i];

            g2.fillOval((int)p.x - 2, (int)p.y - 2, 4, 4);
        }

        g2.setTransform(original);

        drawLabels(g2);


    }
    public void clear() {
        this.points = new Point2D.Double[0];
        this.ids = new int[0];
        this.edges = new Edge[0];
        repaint();
    }

    private void drawLabels(Graphics2D g2) {

        if (!showLabels) {
            return;
        }

        g2.setColor(Color.BLACK);

        for (int i = 0; i < points.length; i++) {

            Point2D.Double p = points[i];

            int screenX = (int) (p.x * scale + offsetX);
            int screenY = (int) (p.y * scale + offsetY);

            String label = String.format("%d (%.2f, %.2f)", ids[i], p.x, p.y);

            g2.drawString(label, screenX + 6, screenY - 6);
        }

        g2.setColor(Color.BLACK);

        for (int i = 0; i < edges.length; i++) {

            Edge edge = edges[i];

            int fromIndex = -1;
            int toIndex = -1;

            for (int j = 0; j < ids.length; j++) {
                if (ids[j] == edge.from) fromIndex = j;
                if (ids[j] == edge.to) toIndex = j;
            }

            if (fromIndex == -1 || toIndex == -1) continue;

            Point2D.Double p1 = points[fromIndex];
            Point2D.Double p2 = points[toIndex];

            int midX = (int)(((p1.x + p2.x) / 2) * scale + offsetX);
            int midY = (int)(((p1.y + p2.y) / 2) * scale + offsetY);

            String lengthText = String.format("%.2f", edge.length);

            g2.drawString(lengthText, midX, midY);
        }
    }

    public void toggleLabels(){
        showLabels = !showLabels;
        repaint();
    }
}