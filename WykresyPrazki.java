package wykres;

import javax.swing.*;
import java.awt.*;

public class WykresyPrazki extends JPanel {
    private static final long serialVersionUID = 1L;
    private Color lineColor = Color.BLACK;
    double d;
    double lambda;
    private int n;
    int cc;
    private double R;
    private int centerX;
    private int centerY;
    Color culor;
    private DiffractionGrating diffractionGrating;

    public WykresyPrazki(double d, double lambda, int n, DiffractionGrating diffractionGrating) {
        this.d = d;
        this.lambda = lambda;
        this.n = n;
        this.diffractionGrating = diffractionGrating;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(150, 200));
    }

    public double getD() {
        return d;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLineColor(Color color) {
        if (lineColor.equals(Color.WHITE)) {
            this.culor = color;
        } else {
            this.lineColor = color;
        }
        repaint();
    }

    public void updateD(double d) {
        this.d = d;
        repaint();
    }

    public void updateLambda(double lambda) {
        this.lambda = lambda;
        repaint();
    }

    public Color getLineColor() {
        if (lineColor.equals(Color.WHITE)) {
            cc = diffractionGrating.getSlider1Value();
            culor = DiffractionGrating.funk(cc);
            lambda = cc * 0.0000001;
            return culor;
        } else {
            return lineColor;
        }
    }

    public void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getLineColor());
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        centerX = width / 2;
        centerY = height / 2;

        R = 0.5 * centerX;

        for (int i = -n; i <= n; i++) {
            double theta = Math.asin(i * lambda / d);
            double x = R * Math.cos(theta);
            double y = R * Math.sin(theta);
            int vx = (int) (centerX);
            int vy = (int) (centerY + y);
            int vx2 = (int) (vx + R);
            g.setColor(getLineColor());
            g.drawLine(vx, vy, vx2, vy);
        }
    }
}
