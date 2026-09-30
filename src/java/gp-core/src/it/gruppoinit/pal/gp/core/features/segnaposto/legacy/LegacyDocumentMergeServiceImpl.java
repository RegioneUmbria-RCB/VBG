package it.gruppoinit.pal.gp.core.features.segnaposto.legacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacementFactory;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.odt.ISostituzioniSegnapostoODTLegacyService;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.rtf.ISostituzioniSegnapostoRTFLegacyService;
import it.gruppoinit.pal.gp.core.features.sistema.IUrlGeneraAllegatoMicrosoftService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

@Service
public class LegacyDocumentMergeServiceImpl implements DocumentMergeService {

    private final Logger log = LoggerFactory.getLogger(LegacyDocumentMergeServiceImpl.class);
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private LetteretipoService lettereTipoService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private IUsefulDataForPlaceholderReplacementFactory usefulDataForPlaceholderReplacementFactory;
    @Autowired
    private ISostituzioniSegnapostoODTLegacyService sostituzioniSegnapostoODTLegacyService;
    @Autowired
    private ISostituzioniSegnapostoRTFLegacyService sostituzioniSegnapostoRTFLegacyService;
    @Autowired
    private IUrlGeneraAllegatoMicrosoftService urlGeneraAllegatoMicrosoftService;

    @Override
    public byte[] eseguiSostituzioniBaseDocumento(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento,
	    DocumentMergeHelper userData) {

	Istanze istanza = null;
	if (codiceIstanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	}
	Movimenti movimento = null;
	if (codiceMovimento != null) {
	    movimento = movimentiService.findById(new PkId(codiceMovimento));
	    if (istanza == null && movimento != null) {
		istanza = movimento.getIstanza();
	    }
	}
	DatiOggettoLettera oggettoLettera = this.getModelloByIdLetteraTipo(codicelettera);
	log.debug("eseguiSostituzioniBaseDocumento Codiceistanza {}, codice movimento {} DatiOggettoLettera {} recuperato ",
		new Object[] { codiceIstanza, codiceMovimento, oggettoLettera.getTipoFile() });
	IUsefulDataForPlaceholderReplacement documentMergeHelper = this.usefulDataForPlaceholderReplacementFactory.create(istanza, movimento);
	if (oggettoLettera.getTipoFile() == TipoFileEnum.ODT) {
	    return this.sostituzioniSegnapostoODTLegacyService.effettuaSostituzioniBaseOdt(userData, oggettoLettera, documentMergeHelper);
	}
	return this.sostituzioniSegnapostoRTFLegacyService.effettuaSostituzioniBaseRtf(userData, oggettoLettera, documentMergeHelper);
    }

    @Override
    public byte[] eseguiSostituzioniBaseDocumento(Integer codicelettera, DocumentMergeHelper userData) {

	return eseguiSostituzioniBaseDocumento(codicelettera, null, null, userData);
    }

    private DatiOggettoLettera getModelloByIdLetteraTipo(int codiceLettera) {

	Letteretipo lt = lettereTipoService.findById(new PkId(codiceLettera));
	if (lt == null) {
	    throw new BusinessValidationException("Selezionare il documento tipo da compilare.");
	}
	TipoFileEnum tipoFile = lt.getFile().getNomefile().toLowerCase().endsWith(".odt") ? //
		TipoFileEnum.ODT : //
		TipoFileEnum.RTF;
	Oggetti obj = lt.getFile();
	obj = oggettiService.findById(new PkId(obj.getId().getCodice()));
	byte[] contenutoFile = obj.getOggetto();
	if (contenutoFile == null || contenutoFile.length == 0) {
	    throw new BusinessValidationException("Impossibile recuperare il contenuto del documento selezionato.");
	}
	return new DatiOggettoLettera(contenutoFile, tipoFile);
    }

    @Override
    public Oggetti insertAllegatoDaDocumentoTipo(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento,
	    DocumentMergeHelper userData) {

	Letteretipo lt = lettereTipoService.findById(new PkId(codicelettera));
	Oggetti oggettoTemplate = oggettiService.findById(new PkId(lt.getFile().getId().getCodice()));
	if (lt == null) {
	    //TODO generare BusinessException ????
	}
	byte[] documentBytes = eseguiSostituzioniBaseDocumento(codicelettera, codiceIstanza, codiceMovimento, userData);
	Oggetti replacedDocument = new Oggetti();
	replacedDocument.setOggetto(documentBytes);
	replacedDocument.setDimensioneFile(documentBytes.length);
	StringBuilder sbNomeFile = new StringBuilder();
	String nomeFile = oggettoTemplate.getNomefile();
	sbNomeFile.append(FormatUtils.stringFormat(System.currentTimeMillis() + "_" + nomeFile));
	replacedDocument.setNomefile(sbNomeFile.toString());
	oggettiService.insert(replacedDocument);
	return replacedDocument;
    }

    @Override
    public Oggetti createAllegatoDaDocumentoTipo(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento,
	    DocumentMergeHelper userData) {

	Oggetti replacedDocument = new Oggetti();
	Letteretipo lt = lettereTipoService.findById(new PkId(codicelettera));
	log.debug("Codice istanza {}, codicemovimento {}, codice lettera {}, lettera {}",
		new Object[] { codiceIstanza, codiceMovimento, codicelettera, lt });
	Oggetti oggettoTemplate = oggettiService.findById(new PkId(lt.getFile().getId().getCodice()));
	log.debug("Codice istanza {}, codicemovimento {}, codice lettera {}, oggettoTemplate {} - eseguo le sostituzioni ",
		new Object[] { codiceIstanza, codiceMovimento, codicelettera, lt.getFile().getId().getCodice() });
	byte[] documentBytes = eseguiSostituzioniBaseDocumento(codicelettera, codiceIstanza, codiceMovimento, userData);
	log.debug("Codice istanza {}, codicemovimento {}, codice lettera {} - sostituzioni effettuate ",
		new Object[] { codiceIstanza, codiceMovimento, codicelettera });
	replacedDocument.setOggetto(documentBytes);
	replacedDocument.setDimensioneFile(documentBytes.length);
	StringBuilder sbNomeFile = new StringBuilder();
	String nomeFile = oggettoTemplate.getNomefile();
	sbNomeFile.append(FormatUtils.stringFormat(nomeFile));
	replacedDocument.setNomefile(sbNomeFile.toString());
	if (odtCheckConversioneODT(replacedDocument.getNomefile())) {
	    log.debug("Codice istanza {}, codicemovimento {}, codice lettera {} replacedDocument.getNomefile() {} - odtCheckConversioneODT ",
		    new Object[] { codiceIstanza, codiceMovimento, codicelettera, replacedDocument.getNomefile() });
	    odtConvertiRTFinODT(documentBytes, replacedDocument);
	}
	oggettiService.insert(replacedDocument);
	return replacedDocument;
    }

    @Override
    public String getUrlGeneraAllegato() {

	return this.urlGeneraAllegatoMicrosoftService.getUrl();
	/*
	String urlGeneraAllegato = "";
	
	if( this.verticalizzazioneParametriSistemaService.isAttiva() ) {
	    String overrideUrlGeneraAllegato = this.verticalizzazioneParametriSistemaService.overrideUrlGeneraAllegato();
	    if (StringUtils.isNotBlank(overrideUrlGeneraAllegato)) {
		urlGeneraAllegato = overrideUrlGeneraAllegato.trim();
	    }
	}
	
	if( urlGeneraAllegato.toLowerCase().startsWith("http") ) {
	    return urlGeneraAllegato;
	}
	
	String baseUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.BASE_URL);
	if( !StringUtils.isBlank(baseUrl) ) {
	    return baseUrl + '/' + urlGeneraAllegato;
	}
	
	return this.getBaseUrlFromAspnetBaseURL() +  urlGeneraAllegato;
	*/
    }

    @Override
    public void verificaConvertiRtfInOdt(Integer codiceOggetto, boolean saveOggetto) {

	Oggetti o = oggettiService.findByIdLazy(new PkId(codiceOggetto));
	if (o != null && odtCheckConversioneODT(o.getNomefile())) {
	    o = oggettiService.findById(new PkId(codiceOggetto));
	    odtConvertiRTFinODT(o.getOggetto(), o);
	    if (saveOggetto) {
		oggettiService.update(o);
	    }
	}
    }

    private boolean odtCheckConversioneODT(String sbNomeFile) {

	return sbNomeFile.toLowerCase().endsWith(".rtf") && sbNomeFile.toUpperCase().indexOf("_TO_ODT_") >= 0;
    }

    private void odtConvertiRTFinODT(byte[] documentBytes, Oggetti replacedDocument) {

	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	try {
	    byte[] convertiRtfInODT = fileConverterWsClient.convertiRtfInODT(documentBytes);
	    replacedDocument.setOggetto(convertiRtfInODT);
	    replacedDocument.setNomefile((replacedDocument.getNomefile() + ".odt").replace("_TO_ODT_", ""));
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("Errore nella conversione del File RTF in ODT", e);
	    throw new RuntimeException(e);
	}
    }
}
