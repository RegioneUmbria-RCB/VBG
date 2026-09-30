package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.movimenti.Movimenti;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.AllegatoBaseType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti.MovimentiAllegatiInsertRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti.MovimentiAllegatiInsertResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti.MovimentiDownloadZipLogicoRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti.MovimentiDownloadZipLogicoResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.jws.WebService;
import javax.mail.util.ByteArrayDataSource;

import org.apache.kahadb.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(serviceName = "MovimentiService", portName = "MovimentiSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/movimenti", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.movimenti.Movimenti")
public class MovimentiWS extends BaseWS implements Movimenti {

    Logger log = LoggerFactory.getLogger(MovimentiWS.class);
    private MovimentiallegatiService movimentiallegatiService;
    private MovimentiService movimentiService;
    private MovimentiNoSecurityService movimentiNoSecurityService;

    @Override
    public MovimentiAllegatiInsertResponse movimentiAllegatiInsert(MovimentiAllegatiInsertRequest movimentiAllegatiInsertRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("movimentiAllegatiInsert# entro nel metodo WS movimentiAllegatiInsert");
	}
	MovimentiAllegatiInsertResponse response = new MovimentiAllegatiInsertResponse();
	setORMHelper(movimentiAllegatiInsertRequest.getSoftware(), movimentiAllegatiInsertRequest.getToken());
	if (log.isDebugEnabled()) {
	    log.debug("movimentiAllegatiInsert# popolo l'oggetto movimentiallegati dalla request");
	}
	Movimentiallegati movimentiallegati = populateMovimentiAllegati(movimentiAllegatiInsertRequest);
	OggettiBusinessRules rule = (OggettiBusinessRules) SigeproBusinessRules.getClassRules(OggettiBusinessRules.class);
	rule.setInsert(true);
	SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, rule);
	try {
	    if (log.isDebugEnabled()) {
		log.debug("movimentiAllegatiInsert# eseguo l'inserimento");
	    }
	    movimentiallegatiService.insert(movimentiallegati);
	    if (log.isDebugEnabled()) {
		log.debug("movimentiAllegatiInsert# inserito movimentiallegati con id" + String.valueOf(movimentiallegati.getId().getCodice()));
	    }
	    response.setId(BigInteger.valueOf(movimentiallegati.getId().getCodice()));
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error("movimentiAllegatiInsert: software={}, token={}, codicemovimento={}, ERRORE={}",
		    new Object[] { movimentiAllegatiInsertRequest.getSoftware(), movimentiAllegatiInsertRequest.getToken(),
			    movimentiAllegatiInsertRequest.getCodicemovimento(), err });
	    throw new RuntimeException(err);
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }

    private Movimentiallegati populateMovimentiAllegati(MovimentiAllegatiInsertRequest request) {

	it.gruppoinit.pal.gp.core.domain.Movimenti movimento = movimentiService.findById(new PkId(request.getCodicemovimento()));
	if (movimento == null) {
	    log.error("Movimento non trovato con questi riferimenti [idcomune={},codicemovimento{}]", ORMHelper.getIdcomune(),
		    request.getCodicemovimento());
	    throw new RuntimeException("Movimento non trovato con questi riferimenti [idcomune=" + ORMHelper.getIdcomune() + ",codicemovimento="
		    + request.getCodicemovimento() + "]");
	}
	Movimentiallegati ma = new Movimentiallegati();
	ma.setMovimento(movimento);
	if (request.getDataRegistrazione() != null) {
	    ma.setDataregistrazione(request.getDataRegistrazione().toGregorianCalendar().getTime());
	}
	ma.setDescrizione(request.getDescrizione());
	ma.setNote(request.getNote());
	ma.setStcIdallegato(request.getStcIdAllegato());
	ma.setStcIddocumento(request.getStcIdDocumento());
	ma.setFlagPubblica(request.isFlagPubblica());
	if (request.getAllegato() != null) {
	    AllegatoBaseType ab = request.getAllegato();
	    Oggetti oggetto = new Oggetti();
	    //	    if (ab.getRiferimento() != null) {
	    //		Integer codiceOggetto = ab.getRiferimento().getCodice();
	    //		if (codiceOggetto != null) {
	    //		    oggetto.getId().setCodice(codiceOggetto);
	    //		}
	    //	    }
	    if (ab.getDatiFile() != null) {
		if (ab.getDatiFile().getBinaryData() != null) {
		    oggetto.setOggetto(Utilities.dataHandlerToBytes(ab.getDatiFile().getBinaryData()));
		    oggetto.setNomefile(ab.getDatiFile().getFileName());
		    ma.setOggetto(oggetto);
		}
	    }
	}
	return ma;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Override
    public MovimentiDownloadZipLogicoResponse movimentiDownloadZipLogico(MovimentiDownloadZipLogicoRequest request) {

	setORMHelper(WebConstants.SOFTWARE_TT, request.getToken());
	log.debug("movimentiDownloadZipLogico  t:{}, m:{}, u:{}",
		new Object[] { request.getToken(), request.getCodicemovimento(), request.getUuidIstanza() });
	it.gruppoinit.pal.gp.core.domain.Movimenti movimento = movimentiNoSecurityService.findById(new PkId(request.getCodicemovimento()));
	if (movimento == null) {
	    log.error("Movimento non trovato con questi riferimenti [idcomune={},codicemovimento{}]", ORMHelper.getIdcomune(),
		    request.getCodicemovimento());
	    throw new RuntimeException("Movimento non trovato con questi riferimenti [idcomune=" + ORMHelper.getIdcomune() + ",codicemovimento="
		    + request.getCodicemovimento() + "]");
	}
	if (!movimentiNoSecurityService.validateDownloadZipLogico(request.getCodicemovimento(), request.getUuidIstanza())) {
	    log.error("Validazione non superata per t:{}, m:{}, u:{}",
		    new Object[] { request.getToken(), request.getCodicemovimento(), request.getUuidIstanza() });
	    throw new RuntimeException("Chiamata non valida");
	}
	ByteArrayOutputStream baos = movimentiNoSecurityService.downloadDocumentiZipLogico(request.getCodicemovimento());
	MovimentiDownloadZipLogicoResponse response = new MovimentiDownloadZipLogicoResponse();
	response.setFileName("documentazione.zip");
	response.setMimeType("application/zip");
	InputStream is = null;
	is = new ByteArrayInputStream(baos.toByteArray());
	try {
	    DataSource ds = new ByteArrayDataSource(is, "application/octet-stream");
	    response.setBinaryData(new DataHandler(ds));
	} catch (IOException e) {
	    log.error("Errore nel recupero del documento zip per t:{}, m:{}, u:{}, {}",
		    new Object[] { request.getToken(), request.getCodicemovimento(), request.getUuidIstanza(), e });
	    throw new RuntimeException("Chiamata non valida");
	}
	return response;
    }
}
