package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentoHelper;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaOneriService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeOneriService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaOneriServiceImpl implements NuovaIstanzaOneriService {

    @Autowired
    private FoArjDomandeOneriService foArjDomandeOneriService;
    @Autowired
    private NuovaIstanzaService nuovaIstanzaService;
    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaOneriServiceImpl.class);

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setDomandaOneriHelper(null);
	foArjDomandeOneriService.deleteByDomanda(cmd.getId());
    }

    @Override
    public List<FoArjDomandeOneri> popolaOneri(NuovaIstanzaCommand command, Integer codiceDomanda) {

	// 1 verifico se sono già presenti gli oneri senza endo
	Integer codiceIntervento = Integer.valueOf(command.getIntervento().getCodice());
	log.debug("popolaOneri# oneri per l'intervento: {}", codiceIntervento);
	List<FoArjDomandeOneri> oneriintervento = foArjDomandeOneriService.findOneriInterventoByIdDomanda(codiceDomanda);
	// se non presenti verifico l'intervento e inserisco quelli dell'intervento
	if (oneriintervento.isEmpty()) {
	    foArjDomandeOneriService.insertOneriPerIntervento(codiceDomanda, codiceIntervento);
	}
	log.debug("popolaOneri# oneri dei procedimenti");
	// 2 verifico se presenti gli oneri per gli endo selezionati
	// lista degli endo caricati nella base dati
	log.debug("popolaOneri# rimuovo le righe di onere che non appartengono agli endoprocedimenti selezionati");
	// il set contiene tutti i codiciprocedimenti presenti sulla base dati (quelli da eliminare, quelli da mantenere)
	Set<Integer> codiceProcedimentoDB = new HashSet<Integer>();
	List<FoArjDomandeOneri> oneriDomanda = foArjDomandeOneriService.findByIdDomanda(codiceDomanda);
	for (FoArjDomandeOneri foArjDomandeOneri : oneriDomanda) {
	    if (foArjDomandeOneri.getInventarioprocedimenti() != null) {
		if (foArjDomandeOneri.getInventarioprocedimenti().getId() != null) {
		    if (foArjDomandeOneri.getInventarioprocedimenti().getId().getCodice() != null) {
			codiceProcedimentoDB.add(foArjDomandeOneri.getInventarioprocedimenti().getId().getCodice());
		    }
		}
	    }
	}
	// lista dei procedimenti selezionati da interfaccia
	List<ProcedimentoHelper> procSelezionati = command.getProcedimentiSelezionati();
	for (ProcedimentoHelper ph : procSelezionati) {
	    Integer codProcCommand = Integer.parseInt(ph.getProcedimento().getCodice());
	    if (codiceProcedimentoDB.contains(codProcCommand)) {
		// è tra quelli selezionati da interfaccia presente e lo rimuovo dal set che serve per eliminare quelli non presenti
		codiceProcedimentoDB.remove(codProcCommand);
	    }
	}
	// cancello quelli non usati
	for (Integer codProcDelete : codiceProcedimentoDB) {
	    log.debug("popolaOneri# esistono righe di oneri per il procedimento {} ma non è selezionato. Le cancello.", codProcDelete);
	    foArjDomandeOneriService.deleteByDomandaAndCodiceInventario(codiceDomanda, codProcDelete);
	}
	for (ProcedimentoHelper ph : procSelezionati) {
	    // GESTIONE DEGLI ONERI IN CASO DI ELIMINAZIONE DEGLI ENDO
	    // SE CI SONO ONERI PAGATI NON DEVO AZZERARLI
	    Integer codiceProcedimento = Integer.valueOf(ph.getProcedimento().getCodice());
	    List<FoArjDomandeOneri> oneris = foArjDomandeOneriService.findByIdDomandaAndCodiceInventario(command.getId(), codiceProcedimento);
	    if (oneris.isEmpty()) {
		log.debug("popolaOneri# inserisco gli oneri per l'endo {}", codiceProcedimento);
		foArjDomandeOneriService.insertOneriPerCodiceInventario(codiceDomanda, codiceProcedimento);
	    }
	}
	return foArjDomandeOneriService.findByIdDomanda(codiceDomanda);
    }
}
