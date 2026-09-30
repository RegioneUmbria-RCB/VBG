package it.gruppoinit.pal.gp.backoffice.ws;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.MetadatoType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiDeleteRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiDeleteResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindByUidRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindMetadatiRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindMetadatiResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindNomeRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindNomeResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiFindResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiInsertRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiInsertResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiInsertV2Request;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiUpdateRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti.OggettiUpdateResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

@WebService(serviceName = "OggettiService", portName = "OggettiSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/oggetti", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.oggetti.Oggetti")
public class OggettiWS extends BaseWS implements it.gruppoinit.pal.gp.backoffice.definitions.oggetti.Oggetti {

    private static final Logger log = LoggerFactory.getLogger(OggettiWS.class);
    private OggettiService oggettiService;
    private ContenttypesService contenttypesService;
    private IstanzeService istanzeService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private DocumentiHelperService documentiHelperService;

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

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
	    throw new RuntimeException(
		    "Errore durante il recupero del del nome del file (id=" + oggettiFindNomeRequest.getId() + "): " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiFindNome(id={}) return file={}", oggettiFindNomeRequest.getId(), oggettiFindNomeResponse.getFileName());
	return oggettiFindNomeResponse;
    }

    public OggettiInsertResponse oggettiInsertV2(OggettiInsertV2Request oggettiInsertRequest) {

	log.debug("oggettiInsertV2(nome={})", oggettiInsertRequest.getFileName());
	OggettiInsertResponse oggettiInsertResponse = new OggettiInsertResponse();
	setORMHelper(WebConstants.SOFTWARE_TT, oggettiInsertRequest.getToken());
	try {
	    Oggetti oggetti = new Oggetti();
	    oggetti.setNomefile(oggettiInsertRequest.getFileName());
	    oggetti.setOggetto(Utilities.dataHandlerToBytes(oggettiInsertRequest.getBinaryData()));
	    List<MetadatiBean> metadati = new ArrayList<MetadatiBean>();
	    for (MetadatoType metadato : oggettiInsertRequest.getMetadati()) {
		metadati.add(new MetadatiBean(metadato.getChiave(), metadato.getValore()));
	    }
	    oggetti.setMetadatiTransient(metadati);
	    oggettiService.insert(oggetti);
	    oggettiInsertResponse.setId(BigInteger.valueOf(oggetti.getId().getCodice()));
	} catch (Exception e) {
	    log.error("oggettiInsertV2(nome={}): \n{}", new Object[] { oggettiInsertRequest.getFileName(), e });
	    throw new RuntimeException("Errore durante l'inserimento del file (nome=" + oggettiInsertRequest.getFileName() + "): " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiInsertV2(nome={}) return id={}", oggettiInsertRequest.getFileName(), oggettiInsertResponse.getId());
	return oggettiInsertResponse;
    }

    public OggettiInsertResponse oggettiInsert(OggettiInsertRequest oggettiInsertRequest) {

	return this.oggettiInsertV2(new OggettiInsertV2Request(oggettiInsertRequest));
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
	    oggettiService.aggiornaSenzaStoricizzare(oggetti);
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

    @Override
    public OggettiFindMetadatiResponse oggettiFindMetadati(OggettiFindMetadatiRequest oggettiFindMetadatiRequest) {

	log.debug("oggettiFindMetadati(id={})", oggettiFindMetadatiRequest.getCodiceIstanza());
	OggettiFindMetadatiResponse oggettiFindNomeResponse = new OggettiFindMetadatiResponse();
	setORMHelper(WebConstants.SOFTWARE_TT, oggettiFindMetadatiRequest.getToken());
	try {
	    Integer codiceistanza = oggettiFindMetadatiRequest.getCodiceIstanza().intValue();
	    List<MetadatoType> metadati = oggettiFindMetadatiRequest.getMetadati();
	    List<CodiceDescrizioneBean> mds = new ArrayList<CodiceDescrizioneBean>();
	    for (MetadatoType metadatoType : metadati) {
		CodiceDescrizioneBean hlp = new CodiceDescrizioneBean();
		hlp.setCodice(metadatoType.getChiave());
		String valore = metadatoType.getValore();
		hlp.setDescrizione(valore);
		mds.add(hlp);
	    }
	    Set<Integer> codiciOggetto = istanzeService.findDocumentiIstanzaByMetadati(codiceistanza, mds);
	    List<BigInteger> cs = new ArrayList<BigInteger>();
	    for (Integer co : codiciOggetto) {
		cs.add(BigInteger.valueOf(co));
	    }
	    oggettiFindNomeResponse.getCodiciOggetto().addAll(cs);
	} catch (Exception e) {
	    log.error("oggettiFindMetadati(id={}): {}", oggettiFindMetadatiRequest.getCodiceIstanza(), e.getMessage());
	    throw new RuntimeException(
		    "Errore durante il recupero del del nome del file (id=" + oggettiFindMetadatiRequest.getCodiceIstanza() + "): " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("oggettiFindMetadati(id={}) uscita Ok", oggettiFindMetadatiRequest.getCodiceIstanza());
	return oggettiFindNomeResponse;
    }

    @Override
    public OggettiFindResponse oggettiFindByUid(OggettiFindByUidRequest oggettiFindByUiidRequest) {

	log.debug("oggettiFindByUid(token={}, software={}, Uid={}) ",
		new Object[] { oggettiFindByUiidRequest.getToken(), oggettiFindByUiidRequest.getSoftware(), oggettiFindByUiidRequest.getUid() });
	OggettiFindResponse oggettiFindResponse = new OggettiFindResponse();
	setORMHelper(oggettiFindByUiidRequest.getSoftware(), oggettiFindByUiidRequest.getToken());
	Integer codiceOggetto = oggettiMetadatiService.findByChiaveEValore(WebConstants.OGGETTI_FILE_UID, oggettiFindByUiidRequest.getUid());
	if (codiceOggetto == null) {
	    throw new RuntimeException("Nessun oggetto trovato");
	}
	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (r == null) {
	    throw new SecurityException("Autenticazione non valida");
	}
	Oggetti o = oggettiService.findByIdLazy(new PkId(codiceOggetto));
	if (o == null) {
	    throw new RuntimeException("Nessun oggetto trovato");
	}
	Oggetti oggetti = documentiHelperService.checkDocumentoPerResponsabile(r.getId().getCodice(), oggettiFindByUiidRequest.getSoftware(),
		codiceOggetto);
	oggettiFindResponse.setFileName(oggetti.getNomefile());
	String cType = contenttypesService.findMimeTypeByFileName(oggetti.getNomefile());
	oggettiFindResponse.setBinaryData(Utilities.bytesToDataHandler(oggetti.getOggetto()));
	oggettiFindResponse.setMimeType(cType);
	return oggettiFindResponse;
    }
}
