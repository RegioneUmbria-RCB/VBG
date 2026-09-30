package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione.UpgrRiferimentiDettPosizione;

@Service
public class UpgrDettPosizioniComuneSoftwareServiceImpl implements IUpgrDettPosizioniComuneSoftwareService {

    private static Logger logger = LoggerFactory.getLogger(UpgrDettPosizioniComuneSoftwareServiceImpl.class);
    @Autowired
    private IUpgrPosizioniDebitorieDAO upgrPosizioniDebitorieDAO;
    @Autowired
    private BollGestDettaglioDAO bollGestDettaglioDAO;

    @Override
    public List<String> sistemaCodiceComuneESoftware() {

	logger.debug("sistemaCodiceComuneESoftware");
	String idComuneOrig = ORMHelper.getIdcomune();
	//1. Prendo l'elenco di DETT_POSIZIONE_DEBITORIA con CODICECOMUNE E SOFTWARE nulli
	List<UpgrRiferimentiDettPosizione> posizioni = this.upgrPosizioniDebitorieDAO.getElencoPosizioniSenzaCodiceComuneSoftware();
	//2. Prendo la lista dei parametri AR_COD_FISC_ENTE_CREDITORE
	//2. Verifico dalla verticalizzazione quale è il CF Ente Creditore previsto
	int i = 0;
	for (UpgrRiferimentiDettPosizione posizione : posizioni) {
	    logger.debug("sistemaCodiceComuneESoftware: processo la posizione {}", posizione.getIdcomune() + "-" + posizione.getId());
	    //3. Aggiorno ogni record
	    ORMHelper.setIdcomune(posizione.getIdcomune());
	    ISoftwareComuneData softwareAndcomune = getSoftwareAndcomuneFromDettPosizioneDebitoria(posizione.getIdcomune(), posizione.getId());
	    if (softwareAndcomune == null) {
		logger.error("Posizione {} non sistemata", posizione.getIdcomune() + "-" + posizione.getId());
		continue;
	    }
	    this.upgrPosizioniDebitorieDAO.aggiornaComuneESoftwarePosizioneDebitoria(posizione, softwareAndcomune);
	    this.upgrPosizioniDebitorieDAO.flush();
	    this.upgrPosizioniDebitorieDAO.commit();
	    i++;
	    if (i == 100) {
		this.upgrPosizioniDebitorieDAO.flush();
		this.upgrPosizioniDebitorieDAO.clear();
		i = 0;
	    }
	}
	this.upgrPosizioniDebitorieDAO.flush();
	this.upgrPosizioniDebitorieDAO.clear();
	ORMHelper.setIdcomune(idComuneOrig);
	return new ArrayList<String>();
    }

    public ISoftwareComuneData getSoftwareAndcomuneFromDettPosizioneDebitoria(String idcomune, Integer idDettPosizioneDebitoria) {

	ISoftwareComuneData io = upgrPosizioniDebitorieDAO.findInfoDettaglioPosizioneDebitoria(idcomune, idDettPosizioneDebitoria);
	if (io != null) {
	    return io;
	}
	List<ISoftwareComuneData> codiceComune = null;
	List<BollGestDettaglio> righeBollettazione = bollGestDettaglioDAO.findByIdDettPosizioneDebitoria(idDettPosizioneDebitoria);
	if (!righeBollettazione.isEmpty()) {
	    codiceComune = bollGestDettaglioDAO.getSoftwareAndComunePerRiga(righeBollettazione.get(0).getId().getCodice());
	}
	if (codiceComune != null && !codiceComune.isEmpty()) {
	    return codiceComune.get(0);
	}
	io = upgrPosizioniDebitorieDAO.findInfoByIdDettPosizioneDebitoriaManifestazioni(idcomune, idDettPosizioneDebitoria);
	if (io != null) {
	    return io;
	}
	return null;
    }
}
