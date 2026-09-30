package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import java.util.List;
import java.util.Map;

public interface IVerticalizzazioniOverrideService {

    public Map<String, IVerticalizzazioneConOverride> findVerticalizzazioniConOverride();

    public List<OverrideDelParametro> findOverride(String modulo, String comune, String parametro);

    public boolean parametroConOvverride(String modulo, String comune, String parametro);
}
