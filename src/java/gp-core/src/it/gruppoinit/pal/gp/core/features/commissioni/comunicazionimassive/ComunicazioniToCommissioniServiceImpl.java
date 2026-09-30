package it.gruppoinit.pal.gp.core.features.commissioni.comunicazionimassive;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ComunicazioneCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.DettaglioRigaCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaDettagliCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaTestataCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniToCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IParametriProtocolloHelperComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

@Service
public class ComunicazioniToCommissioniServiceImpl implements IComunicazioniToCommissioniService {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniToCommissioniServiceImpl.class);
    private ICommissioniComunicazioniMassiveDAO commissioniComunicazioniMassiveDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private DocumentMergeService documentMergeService;
    private OggettiService oggettiService;
    private LetteretipoService letteretipoService;
    private IParametriProtocolloHelperComunicazioniService parametriProtocolloHelperComunicazioniService;

    @Autowired
    public ComunicazioniToCommissioniServiceImpl(ICommissioniComunicazioniMassiveDAO commissioniComunicazioniMassiveDAO,
	    VerticalizzazioniService verticalizzazioniService, DocumentMergeService documentMergeServic, OggettiService oggettiService,
	    LetteretipoService letteretipoService, IParametriProtocolloHelperComunicazioniService parametriProtocolloHelperComunicazioniService) {

	this.commissioniComunicazioniMassiveDAO = commissioniComunicazioniMassiveDAO;
	this.verticalizzazioniService = verticalizzazioniService;
	this.documentMergeService = documentMergeServic;
	this.oggettiService = oggettiService;
	this.letteretipoService = letteretipoService;
	this.parametriProtocolloHelperComunicazioniService = parametriProtocolloHelperComunicazioniService;
    }

    @Override
    public void collegaCommissioneAComunicazioni(int idTestata, int idCommissioni) {

	this.commissioniComunicazioniMassiveDAO.collegaCommissioniAComunicazioni(idTestata, idCommissioni);
    }

    @Override
    public void collegaDettaglioCommissioneADettaglioComunicazioni(int idDettaglioComunicazione, int idAppelloCommissioni) {

	this.commissioniComunicazioniMassiveDAO.collegaDettaglioCommissioniADettaglioComunicazioni(idDettaglioComunicazione, idAppelloCommissioni);
    }

    @Override
    public ComunicazioneCommissione getComunicazioneCommissione(FiltriRicercaTestataCommissioni filtri) {

	return this.commissioniComunicazioniMassiveDAO.getComunicazioneCommissioni(filtri);
    }

    @Override
    public List<IParametriProtocolloPerEnteHelper> popolaParametriProtocollazione(Integer idCommissione) {

	if (!verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    return new ArrayList<IParametriProtocolloPerEnteHelper>();
	}
	List<ISoftwareComuneData> softwareAndComune = commissioniComunicazioniMassiveDAO.getSoftwareAndComunePerDettaglioCommissione(idCommissione);
	return this.parametriProtocolloHelperComunicazioniService.popolaParametri(softwareAndComune);
    }

    @Override
    public List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(int idDettaglioComunicazione) {

	return commissioniComunicazioniMassiveDAO.getSoftwareAndComunePerDettaglioComunicazione(idDettaglioComunicazione);
    }

    @Override
    public List<DettaglioRigaCommissione> getDettagli(FiltriRicercaDettagliCommissioni filtri) {

	return this.commissioniComunicazioniMassiveDAO.getDettagli(filtri);
    }

    @Override
    public int generaLetteraAccompagnamentoCommissioniDettaglio(int codiceLettera, int idRigaDettaglioMassiva, boolean convertiInPdf) {

	// RICHIAMARE IL SERVIZIO DI MERGE (INTERNO NON GMT) DELLA LETTERATIPO
	log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di recuperare il codice lettera", idRigaDettaglioMassiva, convertiInPdf);
	DocumentMergeHelper userData = new DocumentMergeHelper();
	Letteretipo lettera = letteretipoService.findById(new PkId(codiceLettera));
	userData.getParams().put("ID_DETTAGLIO_MASSIVA", String.valueOf(idRigaDettaglioMassiva));
	userData.getParams().put("IDCOMUNE", ORMHelper.getIdcomune());
	log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di chiamare la sostituzione con query", idRigaDettaglioMassiva, convertiInPdf);
	byte[] res = documentMergeService.eseguiSostituzioniBaseDocumento(codiceLettera, userData);
	String nomefile = lettera.getFile().getNomefile();
	if (convertiInPdf) {
	    log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di convertire in pdf", idRigaDettaglioMassiva, convertiInPdf);
	    res = convertiRTFinPDF(res);
	    nomefile = nomefile + ".pdf";
	}
	Oggetti o = new Oggetti();
	o.setNomefile(nomefile);
	o.setOggetto(res);
	log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di inserire in oggetti", idRigaDettaglioMassiva, convertiInPdf);
	oggettiService.insert(o);
	return o.getId().getCodice();
    }

    private byte[] convertiRTFinPDF(byte[] documentBytes) {

	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	try {
	    return fileConverterWsClient.convertiRtfInPDF(documentBytes);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }
}
