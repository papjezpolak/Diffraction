package wykres;

import javax.swing.*;
import java.awt.*;

public class WykresyDyfrakcji extends JPanel {
    private static final long serialVersionUID = 1L;
    private Color lineColor = Color.BLACK;
    private double d;
    private double lambda;
    private int n;
    private int R = 150;
    private int centerX;
    private int centerY;

    public WykresyDyfrakcji(double d, double lambda, int n) {
        this.d = d;
        this.lambda = lambda;
        this.n = n;
    }
    public double getD() {
        return d;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLineColor(Color color) {
        this.lineColor = color;
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
        return lineColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        centerX = getWidth() /2;
        centerY = getHeight() /2;

        for (int i = -n; i <= n; i++) {
            double theta = Math.asin(i * lambda / d);
            double x = R * Math.cos(theta);
            double y = R * Math.sin(theta);
            int startx = centerX;
            int starty = centerY;
            int vx = (int) (centerX + x);
            int vy = (int) (centerY + y);
            g.setColor(lineColor);
            g.drawLine(startx, starty, vx, vy);
        }
    }
}
