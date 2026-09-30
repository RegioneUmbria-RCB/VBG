package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

@Service
public class BollettazioneLettereServiceImpl implements BollettazioneLettereService {

    private static final Logger log = LoggerFactory.getLogger(BollettazioneLettereServiceImpl.class);
    private DocumentMergeService documentMergeService;
    private BollettazioneDAO bollettazioneDAO;
    private OggettiService oggettiService;
    private LetteretipoService letteretipoService;

    @Autowired
    public BollettazioneLettereServiceImpl(DocumentMergeService documentMergeService, BollettazioneDAO bollettazioneDAO,
	    OggettiService oggettiService, LetteretipoService letteretipoService) {

	this.documentMergeService = documentMergeService;
	this.bollettazioneDAO = bollettazioneDAO;
	this.oggettiService = oggettiService;
	this.letteretipoService = letteretipoService;
    }

    @Override
    public InputStream generaLetteraAccompagnamento(String cf_ente_creditore, Integer rifIdPosizioneDebitoria, boolean convertiInPdf) {

	// RICHIAMARE IL SERVIZIO DI MERGE (INTERNO NON GMT) DELLA LETTERATIPO
	log.debug("generaLetteraAccompagnamento# {}-{} prima di recuperare il codice lettera", rifIdPosizioneDebitoria, convertiInPdf);
	Integer codiceLetteraAccompagnamento = bollettazioneDAO.recuperaCodiceLetteraAccompagnamento(cf_ente_creditore, rifIdPosizioneDebitoria);
	DocumentMergeHelper userData = new DocumentMergeHelper();
	userData.getParams().put("ID_POSIZIONE_DEBITORIA", String.valueOf(rifIdPosizioneDebitoria));
	userData.getParams().put("IDCOMUNE", ORMHelper.getIdcomune());
	log.debug("generaLetteraAccompagnamento# {}-{} prima di chiamare la sostituzione con query", rifIdPosizioneDebitoria, convertiInPdf);
	byte[] res = documentMergeService.eseguiSostituzioniBaseDocumento(codiceLetteraAccompagnamento, userData);
	if (convertiInPdf) {
	    res = convertiRTFinPDF(res);
	}
	InputStream result = new ByteArrayInputStream(res);
	return result;
    }

    private byte[] convertiRTFinPDF(byte[] documentBytes) {

	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	try {
	    return fileConverterWsClient.convertiRtfInPDF(documentBytes);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public int generaOggettoPerDettaglioMassiva(int idRigaDettaglioMassiva, int codiceLettera, boolean convertiInPdf) {

	// RICHIAMARE IL SERVIZIO DI MERGE (INTERNO NON GMT) DELLA LETTERATIPO
	LetteraGenerataPerComunicazione doc = this.generaLetteraPerDettaglioMassiva(idRigaDettaglioMassiva, codiceLettera, convertiInPdf);
	Oggetti o = new Oggetti();
	o.setNomefile(doc.getNomeFile());
	o.setOggetto(doc.getContenutoFile());
	log.debug("generaOggettoPerDettaglioMassiva# {}-{} prima di inserire in oggetti", idRigaDettaglioMassiva, convertiInPdf);
	oggettiService.insert(o);
	return o.getId().getCodice();
    }

    @Override
    public LetteraGenerataPerComunicazione generaLetteraPerDettaglioMassiva(int idRigaDettaglioMassiva, int codiceLettera, boolean convertiInPdf) {

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
	return new LetteraGenerataPerComunicazione(res, nomefile);
    }
}
