package wykres;

import javax.swing.*;
import java.awt.*;

public class WykresyDyfrakcji extends JPanel {
    private static final long serialVersionUID = 1L;
    private Color lineColor = Color.BLACK;
    double d;
    double lambda;
    private int n;
    int cc, c;
    int width,height;
    private double R;
    int centerX;
    int centerY;
    Color culor;
    private DiffractionGrating diffractionGrating;

    public WykresyDyfrakcji(double d, double lambda, int n, DiffractionGrating diffractionGrating) {
        this.d = d;
        this.lambda = lambda;
        this.n = n;
        this.diffractionGrating = diffractionGrating;
        setBackground(Color.BLACK);
    }
    public int setframe() {
    	c = (int)(0.5 * Math.min(centerX, centerY));
    	return c;
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
            lambda = cc * 0.0000001; // Update lambda based on slider value
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
        width = g.getClipBounds().width;
        height = g.getClipBounds().height;
        centerX = width / 2;
        centerY = height / 2;

        R = 0.7 * Math.min(centerX, centerY);

        for (int i = -n; i <= n; i++) {
            double theta = Math.asin(i * lambda / d);
            double x = R * Math.cos(theta);
            double y = R * Math.sin(theta);
            int startx = centerX;
            int starty = centerY;
            int vx = (int) (centerX + x);
            int vy = (int) (centerY + y);
            g.setColor(getLineColor());
            g.drawLine(startx, starty, vx, vy);
        }
    }
}
