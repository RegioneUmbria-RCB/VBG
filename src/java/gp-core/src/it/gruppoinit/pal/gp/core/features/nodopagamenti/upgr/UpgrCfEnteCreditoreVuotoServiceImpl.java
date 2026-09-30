package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpgrCfEnteCreditoreVuotoServiceImpl implements IUpgrCfEnteCreditoreVuotoService {

    @Autowired
    private UpgrCfEnteCreditoreDAO cfEnteCreditoreDAO;

    @Override
    public void aggiornaCfEnteCreditoreVuoto() {

	//1. Prendo l'elenco di DETT_POSIZIONE_DEBITORIA con CF_ENTE_CREDITORE null
	List<PosizioneConCFNullBean> posizioni = this.cfEnteCreditoreDAO.getElencoPosizioniDaSanare();
	//2. Prendo la lista dei parametri AR_COD_FISC_ENTE_CREDITORE
	Map<CFDaVerticalizzazioneChiave, String> mappa = this.cfEnteCreditoreDAO.leggiConfigurazione();
	//2. Verifico dalla verticalizzazione quale è il CF Ente Creditore previsto
	for (PosizioneConCFNullBean posizione : posizioni) {
	    String cfEnteCreditore = mappa
		    .get(new CFDaVerticalizzazioneChiave(posizione.getIdComune(), posizione.getSoftware(), posizione.getCodiceComune()));
	    if (StringUtils.isEmpty(cfEnteCreditore)) {
		cfEnteCreditore = mappa.get(new CFDaVerticalizzazioneChiave(posizione.getIdComune(), "TT", posizione.getCodiceComune()));
	    }
	    if (StringUtils.isEmpty(cfEnteCreditore)) {
		cfEnteCreditore = mappa.get(new CFDaVerticalizzazioneChiave(posizione.getIdComune(), posizione.getSoftware(), "TUTTI"));
	    }
	    if (StringUtils.isEmpty(cfEnteCreditore)) {
		cfEnteCreditore = mappa.get(new CFDaVerticalizzazioneChiave(posizione.getIdComune(), "TT", "TUTTI"));
	    }
	    //3. Aggiorno ogni record
	    this.cfEnteCreditoreDAO.aggiornaPosizioneDebitoria(posizione.getIdComune(), posizione.getId(), cfEnteCreditore);
	    this.cfEnteCreditoreDAO.flush();
	    this.cfEnteCreditoreDAO.commit();
	}
    }
}
