package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;

@SuppressWarnings("rawtypes")
public interface UpgrCfEnteCreditoreDAO extends BaseDAO {

    List<PosizioneConCFNullBean> getElencoPosizioniDaSanare();

    Map<CFDaVerticalizzazioneChiave, String> leggiConfigurazione();

    void aggiornaPosizioneDebitoria(String idComune, int id, String cfEnteCreditore);
}
