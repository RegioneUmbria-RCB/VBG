package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;

@Service
public class ConfigurazioneComunicazioneServiceImpl implements IConfigurazioneComunicazioneService {

    private IComunicazioniMassiveDAO massiveDAO;
    private IComunicazioniMassiveDettaglioDAO massiveDettaglioDAO;

    @Autowired
    public ConfigurazioneComunicazioneServiceImpl(IComunicazioniMassiveDAO massiveDAO, IComunicazioniMassiveDettaglioDAO massiveDettaglioDAO) {

	this.massiveDAO = massiveDAO;
	this.massiveDettaglioDAO = massiveDettaglioDAO;
    }

    @Override
    public void getById(int idTestata, IConfigurazioneComunicazione cfg) {

	MassiveTestata testata = massiveDAO.getTestataById(idTestata);
	this.getById(testata, cfg);
    }

    private void getById(MassiveTestata testata, IConfigurazioneComunicazione cfg) {

	int idTestata = testata.getId().getCodice();
	ConfigurazioneFlyweight cfw = new ConfigurazioneFlyweight();
	cfw.setDescrizione(testata.getDescrizione());
	if (testata.getSenderAccount() != null && testata.getFkidMailtipo() != null) {
	    ConfigurazioneMail cfmail = new ConfigurazioneMail(testata.getSenderAccount().getId().getCodice(),
		    testata.getFkidMailtipo().getId().getCodice());
	    cfw.setConfigurazioneMail(cfmail);
	}
	List<ParametroConfigurazioneComunicazione> parametri = massiveDAO.getParametriByIdTestata(idTestata);
	for (ParametroConfigurazioneComunicazione p : parametri) {
	    cfw.addParametro(p.getChiave(), p.getValore());
	}
	List<AllegatoComunicazione> allegatiFissi = massiveDAO.getAllegatiFissiByIdTestata(idTestata);
	for (AllegatoComunicazione allegatoComunicazione : allegatiFissi) {
	    cfw.getAllegatiFissi().add(allegatoComunicazione);
	}
	List<LetteraComunicazione> allegatiCompilabili = massiveDAO.getLettereComunicazioneByIdTestata(idTestata);
	for (LetteraComunicazione letteraComunicazione : allegatiCompilabili) {
	    cfw.getLettereComunicazione().add(letteraComunicazione);
	}
	List<Integer> firmatari = massiveDAO.getFirmatariByIdTestata(idTestata);
	for (Integer idFirmatario : firmatari) {
	    cfw.getSoggettiFirmatari().add(idFirmatario);
	}
	if (testata.getProtMailtipo() != null && testata.getProtMailtipo().getId() != null && testata.getProtMailtipo().getId().getCodice() != null) {
	    List<ParametriProtocolloPerEnte> pProtocollazione = massiveDAO.getParametriProtocollazione(idTestata);
	    ParametriProtocollazione param = new ParametriProtocollazione(testata.getProtMailtipo().getId().getCodice(), pProtocollazione);
	    cfw.setParametriProtocollazione(param);
	}
	cfg.inizializzaDaDatiDb(cfw);
    }

    @Override
    public void getByIdDettaglioComunicazione(int idDettaglio, IConfigurazioneComunicazione configurazione) {

	MassiveDettaglio dettaglio = massiveDettaglioDAO.getById(idDettaglio);
	MassiveTestata testata = dettaglio.getMassiveTestata();
	this.getById(testata, configurazione);
    }
}
