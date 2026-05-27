package gui;

import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

public class DrawGraph extends JPanel {

    private Point2D.Double[] points;
    private int[] ids;

    public DrawGraph(Point2D.Double[] pts, int[] ids) {
        this.points = pts;
        this.ids = ids;
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

        // OSIE - czarne
        g2.setColor(Color.BLACK);
        g2.drawLine(0, centerY, getWidth(), centerY);
        g2.drawLine(centerX, 0, centerX, getHeight());

        // LINIE - czerwone
        g2.setColor(Color.RED);

        for (int i = 0; i < points.length - 1; i++) {

            int x1 = centerX + (int)(points[i].x * scale);
            int y1 = centerY - (int)(points[i].y * scale);

            int x2 = centerX + (int)(points[i + 1].x * scale);
            int y2 = centerY - (int)(points[i + 1].y * scale);

            g2.drawLine(x1, y1, x2, y2);

            double length = Math.sqrt(
                    Math.pow(points[i + 1].x - points[i].x, 2) +
                    Math.pow(points[i + 1].y - points[i].y, 2)
            );

            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;

            String lengthText = String.format("%.2f", length);

            double angle = Math.atan2(y2 - y1, x2 - x1);

            if (angle > Math.PI / 2 || angle < -Math.PI / 2) {
                angle += Math.PI;
            }

            g2.setColor(Color.BLACK);

            g2.translate(midX, midY);
            g2.rotate(angle);
            g2.drawString(lengthText, -10, -6);
            g2.rotate(-angle);
            g2.translate(-midX, -midY);
            g2.setColor(Color.RED);
        }

        // PUNKTY - niebieskie
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
