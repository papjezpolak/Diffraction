package wykres;

import java.awt.Color;
import java.io.Serializable;

class WykresParameters implements Serializable {
    private static final long serialVersionUID = 1L;
    private Color color;
    private double d;
    private double lambda;
    private boolean isSelected;

    public WykresParameters(Color color, double d, double lambda, boolean isSelected) {
        this.color = color;
        this.d = d;
        this.lambda = lambda;
        this.isSelected = isSelected;
    }

    public Color getColor() {
        return color;
    }

    public double getD() {
        return d;
    }

    public double getLambda() {
        return lambda;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
