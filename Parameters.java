package wykres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Parameters implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<WykresParameters> wykresParametersList;

    public Parameters(List<WykresyDyfrakcji> wykresyList) {
        wykresParametersList = new ArrayList<>();
        for (WykresyDyfrakcji wykres : wykresyList) {
            WykresParameters wykresParameters = new WykresParameters(wykres.getLineColor(), wykres.getD(), wykres.getLambda(), wykres.isSelected());
            wykresParametersList.add(wykresParameters);
        }
    }

    public void applyToWykresy(List<WykresyDyfrakcji> wykresyList) {
        for (int i = 0; i < wykresParametersList.size(); i++) {
            WykresParameters wykresParameters = wykresParametersList.get(i);
            WykresyDyfrakcji wykres = wykresyList.get(i);
            wykres.setLineColor(wykresParameters.getColor());
            wykres.updateD(wykresParameters.getD());
            wykres.updateLambda(wykresParameters.getLambda());
            wykres.setSelected(wykresParameters.isSelected());
        }
    }
}
