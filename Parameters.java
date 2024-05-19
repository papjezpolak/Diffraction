package wykres;

import java.io.Serializable;
import java.util.List;

public class Parameters implements Serializable {
    private static final long serialVersionUID = 1L;
    private double d;
    private double lambda;

    public Parameters(List<WykresyDyfrakcji> wykresyList) {
        if (!wykresyList.isEmpty()) {
            WykresyDyfrakcji wykres = wykresyList.get(0);
            this.d = wykres.d;
            this.lambda = wykres.lambda;
        }
    }

    public void applyToWykresy(List<WykresyDyfrakcji> wykresyList) {
        for (WykresyDyfrakcji wykres : wykresyList) {
            wykres.updateD(d);
            wykres.updateLambda(lambda);
        }
    }
}
