import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {

    private Point2D.Double[] points;

    public GraphPanel(Point2D.Double[] pts) {
        this.points = pts;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(2));
      
        for (int i = 0; i < points.length - 1; i++) {

            int x1 = (int) points[i].x;
            int y1 = (int) points[i].y;

            int x2 = (int) points[i + 1].x;
            int y2 = (int) points[i + 1].y;

            g2.drawLine(x1, y1, x2, y2);
        }

        for (Point2D.Double p : points) {

            int x = (int) p.x;
            int y = (int) p.y;

            g2.fillOval(x - 1, y - 1, 2, 2);
        }
    }
}
