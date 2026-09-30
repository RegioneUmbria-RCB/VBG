package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.DettaglioRigaCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaDettagliCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniToCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;

@Service
public class CreazioneMassiveDettaglioServiceImpl implements ICreazioneMassiveDettaglioService {

    private IComunicazioniToBollettazioneService comunicazioniToBollettazioneService;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    private AnagrafeService anagrafeService;
    private AmministrazioniService amministrazioniService;
    private ResponsabiliService responsabiliService;
    private ICurrentDateService currentDateService;
    private IComunicazioniToCommissioniService comunicazioniToCommissioniService;

    @Autowired
    public CreazioneMassiveDettaglioServiceImpl(IComunicazioniToBollettazioneService comunicazioniToBollettazioneService,
	    IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, IComunicazioniMassiveDAO comunicazioniMassiveDAO,
	    AnagrafeService anagrafeService, ICurrentDateService currentDateService,
	    IComunicazioniToCommissioniService comunicazioniToCommissioniService, AmministrazioniService amministrazioniService,
	    ResponsabiliService responsabiliService) {

	super();
	this.comunicazioniToBollettazioneService = comunicazioniToBollettazioneService;
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
	this.anagrafeService = anagrafeService;
	this.currentDateService = currentDateService;
	this.comunicazioniToCommissioniService = comunicazioniToCommissioniService;
	this.amministrazioniService = amministrazioniService;
	this.responsabiliService = responsabiliService;
    }

    @Override
    public void collegaRigheBollettazioneAComunicazioni(int idTestata, ConfigurazioneComunicazioniBollettazione configurazioneComunicazione) {

	// trasforma la configurazione in filtri
	comunicazioniToBollettazioneService.collegaBollettazioneAComunicazioni(idTestata, configurazioneComunicazione.getIdBollettazione());
	FiltriRicercaDettagli filtri = recuperaFiltri(configurazioneComunicazione);
	// getDettagliFiltri
	MassiveTestata testata = comunicazioniMassiveDAO.getTestataById(idTestata);
	List<DettaglioBollettazione> dettagli = comunicazioniToBollettazioneService.getDettagli(filtri);
	Map<Integer, List<DettaglioBollettazione>> mappaComunicazioni = new HashMap<Integer, List<DettaglioBollettazione>>();
	for (DettaglioBollettazione dettaglioBollettazione : dettagli) {
	    Integer key = dettaglioBollettazione.getCodiceAnagrafe();
	    List<DettaglioBollettazione> list = mappaComunicazioni.get(key);
	    if (list == null) {
		list = new ArrayList<DettaglioBollettazione>();
	    }
	    list.add(dettaglioBollettazione);
	    mappaComunicazioni.put(key, list);
	}
	for (Entry<Integer, List<DettaglioBollettazione>> dettaglioBollettazione : mappaComunicazioni.entrySet()) {
	    List<DettaglioBollettazione> value = dettaglioBollettazione.getValue();
	    DettaglioBollettazione infoAnagrafe = value.get(0);
	    MassiveDettaglio m = popolaMassivaDettaglio(infoAnagrafe, testata);
	    for (DettaglioBollettazione db : value) {
		comunicazioniToBollettazioneService.collegaDettaglioBollettazioneADettaglioComunicazioni(m.getId().getCodice(),
			db.getIdRigaDettaglio());
	    }
	}
    }

    private MassiveDettaglio popolaMassivaDettaglio(DettaglioBollettazione dettaglioBollettazione, MassiveTestata testata) {

	MassiveDettaglio mds = new MassiveDettaglio();
	mds.setMassiveTestata(testata);
	mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	mds.setUltimoStatoData(currentDateService.getCurrentDate());
	comunicazioniMassiveDettaglioDAO.insert(mds);
	MassiveDettDestinatari dest = new MassiveDettDestinatari();
	dest.setMassiveDettaglio(mds);
	dest.setAnagrafe(anagrafeService.findById(new PkId(dettaglioBollettazione.getCodiceAnagrafe())));
	dest.setMailDestinatario(dettaglioBollettazione.getMail());
	comunicazioniMassiveDAO.saveEntity(dest);
	mds.setDestinatari(dest);
	return mds;
    }

    private FiltriRicercaDettagli recuperaFiltri(ConfigurazioneComunicazioniBollettazione configurazioneComunicazione) {

	return new FiltriRicercaDettagli(configurazioneComunicazione.getIdBollettazione(),
		configurazioneComunicazione.isEscludiDestinatariSenzaMail(), configurazioneComunicazione.isSoloPosizioniDebitorieNonPagate(),
		configurazioneComunicazione.getSceltaTipoMailAnagrafe());
    }

    private FiltriRicercaDettagliCommissioni recuperaFiltri(ConfigurazioneComunicazioniCommissioni configurazioneComunicazione) {

	return new FiltriRicercaDettagliCommissioni(configurazioneComunicazione.getIdCommissione(),
		configurazioneComunicazione.isEscludiDestinatariSenzaMail(), configurazioneComunicazione.getSceltaTipoMailAnagrafe());
    }

    @Override
    public void collegaRigheCommissioniAComunicazioni(int idTestata, ConfigurazioneComunicazioniCommissioni configurazioneComunicazione) {

	comunicazioniToCommissioniService.collegaCommissioneAComunicazioni(idTestata, configurazioneComunicazione.getIdCommissione());
	FiltriRicercaDettagliCommissioni filtri = recuperaFiltri(configurazioneComunicazione);
	MassiveTestata testata = comunicazioniMassiveDAO.getTestataById(idTestata);
	List<DettaglioRigaCommissione> dettaglioCommissioni = this.comunicazioniToCommissioniService.getDettagli(filtri);
	Map<String, List<DettaglioRigaCommissione>> mappaComunicazioni = new HashMap<String, List<DettaglioRigaCommissione>>();
	for (DettaglioRigaCommissione dettaglioCommissione : dettaglioCommissioni) {
	    String key = "";
	    if (dettaglioCommissione.getCodiceAmministrazione() != null) {
		key = "AMM_" + dettaglioCommissione.getCodiceAmministrazione();
	    } else if (dettaglioCommissione.getCodiceResponsabile() != null) {
		key = "RESP_" + dettaglioCommissione.getCodiceResponsabile();
	    } else if (dettaglioCommissione.getCodiceAnagrafe() != null) {
		key = "ANAG_" + dettaglioCommissione.getCodiceAnagrafe();
	    }
	    List<DettaglioRigaCommissione> list = mappaComunicazioni.get(key);
	    if (list == null) {
		list = new ArrayList<DettaglioRigaCommissione>();
	    }
	    list.add(dettaglioCommissione);
	    mappaComunicazioni.put(key, list);
	}
	for (Entry<String, List<DettaglioRigaCommissione>> dettaglioBollettazione : mappaComunicazioni.entrySet()) {
	    List<DettaglioRigaCommissione> value = dettaglioBollettazione.getValue();
	    DettaglioRigaCommissione infoAnagrafe = value.get(0);
	    MassiveDettaglio m = popolaMassivaDettaglioCommissione(infoAnagrafe, testata);
	    for (DettaglioRigaCommissione db : value) {
		comunicazioniToCommissioniService.collegaDettaglioCommissioneADettaglioComunicazioni(m.getId().getCodice(), db.getIdRigaAppello());
	    }
	}
    }

    private MassiveDettaglio popolaMassivaDettaglioCommissione(DettaglioRigaCommissione dettaglioRigaCommissione, MassiveTestata testata) {

	MassiveDettaglio mds = new MassiveDettaglio();
	mds.setMassiveTestata(testata);
	mds.setUltimoStatoCompletato(StatoComunicazioniCommissioniEnum.PRONTA_PER_ELABORAZIONE.name());
	mds.setUltimoStatoData(currentDateService.getCurrentDate());
	comunicazioniMassiveDettaglioDAO.insert(mds);
	MassiveDettDestinatari dest = new MassiveDettDestinatari();
	dest.setMassiveDettaglio(mds);
	if (dettaglioRigaCommissione.getCodiceAmministrazione() != null) {
	    dest.setAmministrazioni(amministrazioniService.findById(new PkId(dettaglioRigaCommissione.getCodiceAmministrazione())));
	} else if (dettaglioRigaCommissione.getCodiceAnagrafe() != null) {
	    dest.setAnagrafe(anagrafeService.findById(new PkId(dettaglioRigaCommissione.getCodiceAnagrafe())));
	} else if (dettaglioRigaCommissione.getCodiceResponsabile() != null) {
	    dest.setResponsabili(responsabiliService.findById(new PkId(dettaglioRigaCommissione.getCodiceResponsabile())));
	} else {
	    throw new IllegalArgumentException("Dettaglio riga non corretto. Non è stato passato ne anagrafe ne amministrazione ne resposnabili");
	}
	dest.setMailDestinatario(dettaglioRigaCommissione.getMail());
	comunicazioniMassiveDAO.saveEntity(dest);
	mds.setDestinatari(dest);
	return mds;
    }
}
