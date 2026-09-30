package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.EsitoDocumentoPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.InfoConnettoreType;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.BollettazioneLettereService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.LetteraGenerataPerComunicazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.RiferimentoPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ComunicazioneBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.DettaglioBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaDettagli;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaTestata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniToBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IParametriProtocolloHelperComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class ComunicazioniToBollettazioneServiceImpl implements IComunicazioniToBollettazioneService {

    private IBollettazioneComunicazioniMassiveDAO comunicazioniMassiveDAO;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private BollettazioneLettereService bollettazioneLettereService;
    private NodoPagamentiService nodoPagamentiService;
    private OggettiService oggettiService;
    private BollGestDettaglioDAO bollGestDettaglioDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private IParametriProtocolloHelperComunicazioniService parametriProtocolloHelperComunicazioniService;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;

    @Autowired
    public ComunicazioniToBollettazioneServiceImpl(IBollettazioneComunicazioniMassiveDAO comunicazioniMassiveDAO,
	    BollettazioneLettereService bollettazioneLettereService, NodoPagamentiService nodoPagamentiService, OggettiService oggettiService,
	    BollGestDettaglioDAO bollGestDettaglioDAO, VerticalizzazioniService verticalizzazioniService,
	    IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    IParametriProtocolloHelperComunicazioniService parametriProtocolloHelperComunicazioniService,
	    DettPosizioneDebitoriaService dettPosizioneDebitoriaService) {

	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
	this.bollettazioneLettereService = bollettazioneLettereService;
	this.nodoPagamentiService = nodoPagamentiService;
	this.oggettiService = oggettiService;
	this.bollGestDettaglioDAO = bollGestDettaglioDAO;
	this.verticalizzazioniService = verticalizzazioniService;
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.parametriProtocolloHelperComunicazioniService = parametriProtocolloHelperComunicazioniService;
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
    }

    @Override
    public void collegaBollettazioneAComunicazioni(int idTestata, int idBollettazione) {

	comunicazioniMassiveDAO.collegaBollettazioneAComunicazioni(idTestata, idBollettazione);
    }

    @Override
    public void collegaDettaglioBollettazioneADettaglioComunicazioni(int idDettaglioComunicazione, int idDettaglioBollettazione) {

	comunicazioniMassiveDAO.collegaDettaglioBollettazioneADettaglioComunicazioni(idDettaglioComunicazione, idDettaglioBollettazione);
    }

    @Override
    public List<DettaglioBollettazione> getDettagli(FiltriRicercaDettagli filtri) {

	return comunicazioniMassiveDAO.getDettagli(filtri);
    }

    @Override
    public int generaOggettoAccompagnamentoBollettazioneDettaglio(int codiceLettera, int idDettaglioMassiva, boolean trasformaInPdf) {

	return bollettazioneLettereService.generaOggettoPerDettaglioMassiva(idDettaglioMassiva, codiceLettera, trasformaInPdf);
    }

    @Override
    public List<Integer> recuperaAvvisoDiPagamento(Integer idDettaglioMassiva) throws FunzioneBusinessRemotaException {

	// questa deve recuperare la posizione debitoria dalle righe e non rilanciare l'errore
	// rilancia errore se trova più posizioni debitorie
	List<RiferimentoPosizioneDebitoria> dettPosizioneDebitoria = comunicazioniMassiveDettaglioDAO
		.recuperaDettPosizioneDebitoriaFromMassiva(idDettaglioMassiva);
	if (dettPosizioneDebitoria.isEmpty()) {
	    return null;
	}
	InfoConnettoreType infoConn = nodoPagamentiService.getInfoConnettoreByIdPosizioneDebitoria(
		dettPosizioneDebitoriaService.findById(new PkId(dettPosizioneDebitoria.get(0).getIdDettPosizioneDebitoria())));
	if (!infoConn.isSupportaInvioAvviso()) {
	    return null;
	}
	List<Integer> ret = new ArrayList<Integer>();
	for (RiferimentoPosizioneDebitoria riferimentoPosizioneDebitoria : dettPosizioneDebitoria) {
	    //se connettore supporta rataunica facciamo una sola chiamata
	    if (infoConn.isSupportaRataUnica()) {
		ElencoDocumentiEsitoType inviaAvviso = nodoPagamentiService.inviaAvviso(riferimentoPosizioneDebitoria.getIdDettPosizioneDebitoria());
		EsitoDocumentoPosizioneDebitoriaType esitoPosizione = inviaAvviso.getEsitoPosizione().get(0);
		inviaBollettino(ret, esitoPosizione);
		break;
	    } else {
		ElencoDocumentiEsitoType inviaAvviso = nodoPagamentiService.inviaAvviso(riferimentoPosizioneDebitoria.getIdDettPosizioneDebitoria());
		List<EsitoDocumentoPosizioneDebitoriaType> esitoPosizione = inviaAvviso.getEsitoPosizione();
		for (EsitoDocumentoPosizioneDebitoriaType esito : esitoPosizione) {
		    inviaBollettino(ret, esito);
		}
	    }
	}
	return ret;
    }

    private void inviaBollettino(List<Integer> ret, EsitoDocumentoPosizioneDebitoriaType esito) throws FunzioneBusinessRemotaException {

	if (esito.isEsito()) {
	    DataHandler documento = esito.getDocumento();
	    Oggetti o = Oggetti.fromNomeFileEContenuto(esito.getNomeDocumento(), documento);
	    oggettiService.insert(o);
	    ret.add(o.getId().getCodice());
	} else {
	    throw new FunzioneBusinessRemotaException(esito.getMessaggio());
	}
    }

    @Override
    public ComunicazioneBollettazione getComunicazioneBollettazione(FiltriRicercaTestata filtri) {

	return this.comunicazioniMassiveDAO.getComunicazioneBollettazione(filtri);
    }

    @Override
    public List<IParametriProtocolloPerEnteHelper> popolaParametriProtocollazione(Integer bollGestTestataId) {

	if (!verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    return new ArrayList<IParametriProtocolloPerEnteHelper>();
	}
	List<ISoftwareComuneData> softwareAndComune = bollGestDettaglioDAO.getSoftwareAndComuneForBollettazione(bollGestTestataId);
	return this.parametriProtocolloHelperComunicazioniService.popolaParametri(softwareAndComune);
    }

    @Override
    public List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(int idDettaglioComunicazione) {

	return bollGestDettaglioDAO.getSoftwareAndComunePerDettaglioComunicazione(idDettaglioComunicazione);
    }

    @Override
    public LetteraGenerataPerComunicazione generaLetteraAccompagnamentoBollettazioneDettaglio(int codiceLettera, int idDettaglioMassiva,
	    boolean trasformaInPdf) {

	return bollettazioneLettereService.generaLetteraPerDettaglioMassiva(idDettaglioMassiva, codiceLettera, trasformaInPdf);
    }
}
