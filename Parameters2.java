package wykres;

import java.io.Serializable;
import java.util.List;

public class Parameters2 implements Serializable {
    private static final long serialVersionUID = 1L;
    private double d;
    private double lambda;

    public Parameters2(List<WykresyPrazki> wykresy2List) {
        if (!wykresy2List.isEmpty()) {
            WykresyPrazki wykres = wykresy2List.get(0);
            this.d = wykres.d;
            this.lambda = wykres.lambda;
        }
    }

    public void applyToWykresy2(List<WykresyPrazki> wykresy2List) {
        for (WykresyPrazki wykres : wykresy2List) {
            wykres.updateD(d);
            wykres.updateLambda(lambda);
        }
    }
}
