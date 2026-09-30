package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.domain.SchedaHelper;
import it.init.sigepro.rte.types.DettaglioPraticaType;

public interface ModelliDinamiciFormuleARJService {

    public void updateSaveCampo(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo);

    public void updateLoadCampo(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo);

    public void updateSaveModello(DettaglioPraticaType pratica, SchedaHelper schedaH);

    public void updateLoadModello(DettaglioPraticaType pratica, SchedaHelper schedaH);

    public void updateChangeCampo(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo);

    public void aggiornaValoreCampo(SchedaHelper scheda, String codiceCampoDaAggiornare, String valoreCampoDaAggiornare);
}
