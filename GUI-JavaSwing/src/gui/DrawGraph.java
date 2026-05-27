package gui;

import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import repository.Edge;

public class DrawGraph extends JPanel {

    private Point2D.Double[] points;
    private int[] ids;
    private Edge[] edges;

    public DrawGraph(Point2D.Double[] pts, int[] ids, Edge[] edges) {
        this.points = pts;
        this.ids = ids;
        this.edges = edges;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(2));
        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        double scale = 20;

        // AXIS
        g2.setColor(Color.BLACK);
        g2.drawLine(0, centerY, getWidth(), centerY);
        g2.drawLine(centerX, 0, centerX, getHeight());

        // KRAWEDZIE
        for (Edge edge : edges) {

            int fromIndex = -1;
            int toIndex = -1;

            // znalezienie indeksow punktow
            for (int i = 0; i < ids.length; i++) {

                if (ids[i] == edge.from) {
                    fromIndex = i;
                }

                if (ids[i] == edge.to) {
                    toIndex = i;
                }
            }

            if (fromIndex == -1 || toIndex == -1) {
                continue;
            }

            Point2D.Double p1 = points[fromIndex];
            Point2D.Double p2 = points[toIndex];

            int x1 = centerX + (int)(p1.x * scale);
            int y1 = centerY - (int)(p1.y * scale);

            int x2 = centerX + (int)(p2.x * scale);
            int y2 = centerY - (int)(p2.y * scale);

            // linia
            g2.setColor(Color.RED);
            g2.drawLine(x1, y1, x2, y2);

            // srodek linii
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;

            // tekst dlugosci
            String lengthText = String.format("%.2f", edge.length);

            double angle = Math.atan2(y2 - y1, x2 - x1);

            if (angle > Math.PI / 2 || angle < -Math.PI / 2) {
                angle += Math.PI;
            }

            g2.setColor(Color.BLACK);

            g2.translate(midX, midY);
            g2.rotate(angle);
            g2.drawString(lengthText, -10, -5);
            g2.rotate(-angle);
            g2.translate(-midX, -midY);
        }

        // PUNKTY
        g2.setColor(Color.BLUE);

        for (int i = 0; i < points.length; i++) {

            Point2D.Double p = points[i];

            int x = centerX + (int)(p.x * scale);
            int y = centerY - (int)(p.y * scale);

            g2.fillOval(x - 3, y - 3, 6, 6);

            String label = ids[i] + "(" + p.x + ", " + p.y + ")";
            g2.drawString(label, x + 6, y - 6);
        }
    }
}
