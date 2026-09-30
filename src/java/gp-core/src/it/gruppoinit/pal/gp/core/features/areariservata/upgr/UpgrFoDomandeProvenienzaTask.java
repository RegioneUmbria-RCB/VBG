package it.gruppoinit.pal.gp.core.features.areariservata.upgr;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrFoDomandeProvenienzaTask")
public class UpgrFoDomandeProvenienzaTask extends BaseJavaTask {

    @Autowired
    private IUpgrFoDomandeProvenienzaDAO upgrFoDomandeProvenienzaDAO;

    @Override
    public void initialize() throws SetupRunException {

	// non serve nessuna inizializzazione
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	//1. Verifico la presenza di record alberoproc con SC_PUBBLICA = 4 (Solo domanda online)
	List<InterventoSoloDomandaOnlineBean> interventiDOL = this.upgrFoDomandeProvenienzaDAO.recuperaInterventiSoloDomandaOnline();
	//1.2 Se non sono presenti nell'intera installazione
	//1.2.1 Update fo_domande set PROVENIENZA = AreaRiservata
	//1.2.2 Fine
	if (interventiDOL.isEmpty()) {
	    this.upgrFoDomandeProvenienzaDAO.aggiornaProvenienza("AreaRiservata");
	    return 0;
	}
	List<InterventoSoloDomandaOnlineBean> interventi = new ArrayList<InterventoSoloDomandaOnlineBean>();
	//1.3 Se sono presenti nell'intera installazione
	//1.3.1 Recupero idcomune, software, sc_id, sc_codice, sc_padre
	for (InterventoSoloDomandaOnlineBean intervento : interventiDOL) {
	    //1.3.2 Se sc_padre = 0
	    //1.3.2.1 Aggiungo idcomune, sc_id a mappa
	    if (!intervento.isPadre()) {
		interventi.add(intervento);
		continue;
	    }
	    //1.3.3 Se sc_padre = 1 analizzo i figli per aggiungerli eventualmente alla mappa
	    List<InterventoSoloDomandaOnlineBean> figliDOL = this.analizzaFigli(intervento);
	    if (!figliDOL.isEmpty()) {
		interventi.addAll(figliDOL);
	    }
	}
	//1.3.4 Update fo_domande set PROVENIENZA = DomandaOnLine a parità di idcomune e codiceintervento di quelli della mappa
	this.upgrFoDomandeProvenienzaDAO.aggiornaProvenienza("DomandaOnLine", interventi);
	//1.3.5 Update fo_domande set PROVENIENZA = AreaRiservata dove PROVENIENZA = null
	this.upgrFoDomandeProvenienzaDAO.aggiornaProvenienza("AreaRiservata");
	return 0;
    }

    private List<InterventoSoloDomandaOnlineBean> analizzaFigli(InterventoSoloDomandaOnlineBean interventoPadre) {

	if (!interventoPadre.isPadre()) {
	    return new ArrayList<InterventoSoloDomandaOnlineBean>();
	}
	List<InterventoSoloDomandaOnlineBean> retVal = new ArrayList<InterventoSoloDomandaOnlineBean>();
	List<InterventoSoloDomandaOnlineBean> figli = this.upgrFoDomandeProvenienzaDAO.recuperaFigli(interventoPadre);
	for (InterventoSoloDomandaOnlineBean figlio : figli) {
	    //1.3.3.1 Le foglie figlie con  SC_PUBBLICA != null -> IGNORATI
	    //1.3.3.2 Le sottocartelle figlie con SC_PUBBLICA != null -> IGNORATI
	    if (figlio.getPubblica() != null) {
		continue;
	    }
	    //1.3.3.3 Le foglie figlie con  SC_PUBBLICA == null -> Aggiungo idcomune, sc_id a mappa
	    if (!figlio.isPadre()) {
		retVal.add(figlio);
	    }
	    //1.3.3.5 Le sottocartelle figlie con SC_PUBBLICA == null -> Ripeto i passaggi
	    List<InterventoSoloDomandaOnlineBean> trovati = this.analizzaFigli(figlio);
	    if (!trovati.isEmpty()) {
		retVal.addAll(trovati);
	    }
	}
	return retVal;
    }
}
