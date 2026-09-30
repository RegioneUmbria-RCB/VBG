package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiDeleteRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiDeleteResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindNomeRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindNomeResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiInsertRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiInsertResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiUpdateRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiUpdateResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.math.BigInteger;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(serviceName = "OggettiService", portName = "OggettiSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/oggetti", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.oggetti.Oggetti")
public class OggettiWS extends BaseWS implements it.gruppoinit.pal.gp.backoffice.definitions.oggetti.Oggetti {

    private static final Logger log = LoggerFactory.getLogger(OggettiWS.class);
    private OggettiService oggettiService;
    private ContenttypesService contenttypesService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }

    public OggettiFindResponse oggettiFind(OggettiFindRequest oggettiFindRequest) {

	log.debug("oggettiFind(id={})", oggettiFindRequest.getId());
	OggettiFindResponse oggettiFindResponse = new OggettiFindResponse();
	setORMHelper(WebConstants.SOFTWARE_TT, oggettiFindRequest.getToken());
	try {
	    Oggetti oggetti = oggettiService.findById(new PkId(oggettiFindRequest.getId().intValue()));
	    if (oggetti == null) {
		log.error("oggettiFind: file con id={} non trovato", oggettiFindRequest.getId());
		throw new RuntimeException("File non trovato");
	    }
	    oggettiFindResponse.setFileName(oggetti.getNomefile());
	    String cType = contenttypesService.findMimeTypeByFileName(oggetti.getNomefile());
	    oggettiFindResponse.setBinaryData(Utilities.bytesToDataHandler(oggetti.getOggetto()));
	    oggettiFindResponse.setMimeType(cType);
	} catch (Exception e) {
	    log.error("oggettiFind(id={}): {}", oggettiFindRequest.getId(), e.getMessage());
	    throw new RuntimeException("Errore durante il recupero del file (id=" + oggettiFindRequest.getId() + "): " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiFind(id={}) return file={}", oggettiFindRequest.getId(), oggettiFindResponse.getFileName());
	return oggettiFindResponse;
    }

    public OggettiFindNomeResponse oggettiFindNome(OggettiFindNomeRequest oggettiFindNomeRequest) {

	log.debug("oggettiFindNome(id={})", oggettiFindNomeRequest.getId());
	OggettiFindNomeResponse oggettiFindNomeResponse = new OggettiFindNomeResponse();
	setORMHelper(WebConstants.SOFTWARE_TT, oggettiFindNomeRequest.getToken());
	try {
	    String nome = oggettiService.findNomeById(new PkId(oggettiFindNomeRequest.getId().intValue()));
	    if (nome == null) {
		throw new RuntimeException("File non trovato");
	    }
	    oggettiFindNomeResponse.setFileName(nome);
	} catch (Exception e) {
	    log.error("oggettiFindNome(id={}): {}", oggettiFindNomeRequest.getId(), e.getMessage());
	    throw new RuntimeException("Errore durante il recupero del del nome del file (id=" + oggettiFindNomeRequest.getId() + "): "
		    + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiFindNome(id={}) return file={}", oggettiFindNomeRequest.getId(), oggettiFindNomeResponse.getFileName());
	return oggettiFindNomeResponse;
    }

    public OggettiInsertResponse oggettiInsert(OggettiInsertRequest oggettiInsertRequest) {

	log.debug("oggettiInsert(nome={})", oggettiInsertRequest.getFileName());
	OggettiInsertResponse oggettiInsertResponse = new OggettiInsertResponse();
	setORMHelper(WebConstants.SOFTWARE_TT, oggettiInsertRequest.getToken());
	try {
	    Oggetti oggetti = new Oggetti();
	    oggetti.setNomefile(oggettiInsertRequest.getFileName());
	    oggetti.setOggetto(Utilities.dataHandlerToBytes(oggettiInsertRequest.getBinaryData()));
	    oggettiService.insert(oggetti);
	    oggettiInsertResponse.setId(BigInteger.valueOf(oggetti.getId().getCodice()));
	} catch (Exception e) {
	    log.error("oggettiInsert(nome={}): {}", oggettiInsertRequest.getFileName(), e.getMessage());
	    throw new RuntimeException("Errore durante l'inserimento del file (nome=" + oggettiInsertRequest.getFileName() + "): " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiInsert(nome={}) return id={}", oggettiInsertRequest.getFileName(), oggettiInsertResponse.getId());
	return oggettiInsertResponse;
    }

    public OggettiUpdateResponse oggettiUpdate(OggettiUpdateRequest oggettiUpdateRequest) {

	log.debug("oggettiUpdate(id={})", oggettiUpdateRequest.getId());
	OggettiUpdateResponse oggettiUpdateResponse = new OggettiUpdateResponse();
	oggettiUpdateResponse.setResult(false);
	setORMHelper(WebConstants.SOFTWARE_TT, oggettiUpdateRequest.getToken());
	try {
	    Oggetti oggetti = oggettiService.findById(new PkId(oggettiUpdateRequest.getId().intValue()));
	    if (oggetti == null) {
		throw new RuntimeException("File non trovato");
	    }
	    if (StringUtils.isNotBlank(oggettiUpdateRequest.getFileName())) {
		oggetti.setNomefile(oggettiUpdateRequest.getFileName());
	    }
	    if (oggettiUpdateRequest.getBinaryData() != null) {
		byte[] data = Utilities.dataHandlerToBytes(oggettiUpdateRequest.getBinaryData());
		if (data != null && data.length > 0) {
		    oggetti.setOggetto(data);
		}
	    }
	    oggettiService.update(oggetti);
	    oggettiUpdateResponse.setResult(true);
	} catch (Exception e) {
	    log.error("oggettiUpdate(id={}): {}", oggettiUpdateRequest.getId(), e.getMessage());
	    throw new RuntimeException("Errore durante l'aggiornamento del file (id=" + oggettiUpdateRequest.getId() + "): " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiUpdate(id={}) return success={}", oggettiUpdateRequest.getId(), oggettiUpdateResponse.isResult());
	return oggettiUpdateResponse;
    }

    public OggettiDeleteResponse oggettiDelete(OggettiDeleteRequest oggettiDeleteRequest) {

	log.debug("oggettiDelete(id={})", oggettiDeleteRequest.getId());
	OggettiDeleteResponse oggettiDeleteResponse = new OggettiDeleteResponse();
	oggettiDeleteResponse.setResult(false);
	setORMHelper(WebConstants.SOFTWARE_TT, oggettiDeleteRequest.getToken());
	try {
	    Oggetti oggetti = oggettiService.findById(new PkId(oggettiDeleteRequest.getId().intValue()));
	    if (oggetti == null) {
		throw new RuntimeException("File non trovato");
	    }
	    oggettiService.delete(oggetti);
	    oggettiDeleteResponse.setResult(true);
	} catch (Exception e) {
	    log.error("oggettiDelete(id={}): {}", oggettiDeleteRequest.getId(), e.getMessage());
	    throw new RuntimeException("Errore durante l'eliminazione del file (id=" + oggettiDeleteRequest.getId() + "): " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiDelete(id={}) return success={}", oggettiDeleteRequest.getId(), oggettiDeleteResponse.isResult());
	return oggettiDeleteResponse;
    }
}
