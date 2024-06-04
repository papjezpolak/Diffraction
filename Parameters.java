package wykres;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.Color;

public class Parameters implements Serializable {
    private static final long serialVersionUID = 1L;
    private double d;
    private double lambda;
    private int slider1Value;
    private int slider2Value;
    private Map<Color, Boolean> wavelengthSet;

    public Parameters(List<WykresyDyfrakcji> wykresyList, int slider1Value, int slider2Value, Map<Color, Boolean> wavelengthSet) {
        if (!wykresyList.isEmpty()) {
            WykresyDyfrakcji wykres = wykresyList.get(0);
            this.d = wykres.d;
            this.lambda = wykres.lambda;
        }
        this.slider1Value = slider1Value;
        this.slider2Value = slider2Value;
        this.wavelengthSet = new HashMap<>(wavelengthSet);
    }

    public void applyToWykresy(List<WykresyDyfrakcji> wykresyList) {
        for (WykresyDyfrakcji wykres : wykresyList) {
            wykres.updateD(d);
            wykres.updateLambda(lambda);
        }
    }

    public int getSlider1Value() {
        return slider1Value;
    }

    public int getSlider2Value() {
        return slider2Value;
    }

    public Map<Color, Boolean> getWavelengthSet() {
        return wavelengthSet;
    }
}
