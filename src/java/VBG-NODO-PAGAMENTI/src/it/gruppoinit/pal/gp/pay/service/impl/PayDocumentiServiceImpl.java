package it.gruppoinit.pal.gp.pay.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.dao.PayDocumentiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayDocumenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayDocumentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.DettaglioFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RigaDettaglioFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

@Service
public class PayDocumentiServiceImpl extends BaseServiceImpl<PayDocumenti, PkId> implements PayDocumentiService {

    private static final Logger log = LoggerFactory.getLogger(PayDocumentiServiceImpl.class);
    @Autowired
    private PayDocumentiDAO payDocumentiDAO;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;

    @Override
    public void insert(PayDocumenti entity) {

	if (validateEntity(entity)) {
	    this.payDocumentiDAO.insert(entity);
	    manageDocumentPersistence(entity);
	}
    }

    @Override
    public void update(PayDocumenti entity) {

	if (validateEntity(entity)) {
	    this.payDocumentiDAO.update(entity);
	    manageDocumentPersistence(entity);
	}
    }

    @Override
    public void delete(PayDocumenti entity) {

	if (isDeleteAllowed(entity)) {
	    //la delete fa solo la cancellazione del record su DB 
	    //TODO i file saranno cancellati dal filesystem da una procedura schedulata
	    this.payDocumentiDAO.delete(entity);
	}
    }

    @Override
    public List<PayDocumenti> findAll(Integer firstResult, Integer maxResult) {

	return payDocumentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayDocumenti findById(PkId id) {

	return this.payDocumentiDAO.findById(id);
    }

    @Override
    protected Class<PayDocumenti> getEntityClass() {

	return PayDocumenti.class;
    }

    private void manageDocumentPersistence(PayDocumenti doc) {

	//contenuto binario del documento
	byte[] bytes = doc.getBytes();
	//persistenza documenti su filkesystem
	if (PayConfigurationHelper.isDocumentiSuFilesystem()) {
	    //try {
	    File docsRootPath = new File(PayConfigurationHelper.getDocumentiFilesystemPath());
	    if (doc.getId() == null || doc.getId().getCodice() == null) {
		throw new RuntimeException("id dell'oggetto non inizializzato, impossibile persistere il fìdocumento su filesystem");
	    }
	    String[] pathElements = buildStorageDirectoryFromDocumento(doc);
	    File docPath = FileUtils.getFile(docsRootPath, pathElements);
	    //sottodirectory per lo storage del file
	    String path = StringUtils.join(pathElements, File.separatorChar);
	    //nome del file basato sulla PK del documento
	    String fileName = buildFileNameFromDocumento(doc);
	    // scrittura di path e dimensione nell'oggetto di dominio
	    doc.setPercorso(path);
	    doc.setNomeFileSystem(fileName);
	    //scrittura del file su FS
	    File docFile = new File(docPath, fileName);
	    try {
		IOUtils.writeBytesToFile(bytes, docFile);
	    } catch (IOException e) {
		StringBuilder sbErr = new StringBuilder("errore nella scrittura su filesystem del documento ").append(doc.getNomeDocumento());
		sbErr.append(" con id: ").append(PkId.toStringId(doc.getId()));
		sbErr.append(", nome file: ").append(fileName);
		sbErr.append(", errore: ").append(e);
		log.error("manageDocumentPersistence - " + sbErr.toString());
		//rilancio RuntimeException per far rollbackare la transazione
		throw new RuntimeException(sbErr.toString(), e);
	    }
	}
	//persistenza su BLOB
	else {
	    doc.setDatiFileBlob(bytes);
	}
	doc.setDimensione(bytes == null ? 0 : bytes.length);
	this.payDocumentiDAO.update(doc);
    }

    public static String[] buildStorageDirectoryFromDocumento(PayDocumenti doc) {

	NumberFormat nf = NumberFormat.getIntegerInstance();
	nf.setGroupingUsed(false);
	String codiceoggetto = nf.format(doc.getId().getCodice());
	codiceoggetto = StringUtils.leftPad(codiceoggetto, 10, '0');
	String[] pathElements = new String[4];
	pathElements[0] = doc.getId().getIdcomune();
	pathElements[1] = codiceoggetto.substring(0, 4);
	pathElements[2] = codiceoggetto.substring(4, 6);
	pathElements[3] = codiceoggetto.substring(6, 8);
	return pathElements;
    }

    public static String buildFileNameFromDocumento(PayDocumenti doc) {

	StringBuilder sb = new StringBuilder(PkId.toStringId(doc.getId()));
	sb.append('.');
	sb.append(IOUtils.getFileExt(doc.getNomeDocumento()));
	return sb.toString();
    }

    @Override
    public PayDocumenti creaDocumentoPerPosizioneDebitoria(PayPosizioniDebitorie posDeb, TipoDocumentoType tipoDoc, String docName) {

	PayDocumenti doc = new PayDocumenti();
	if (StringUtils.isBlank(docName)) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(tipoDoc == null ? "DOCUMENTO" : tipoDoc.value());
	    if (posDeb != null && posDeb.getId() != null && posDeb.getId().getCodice() != null) {
		sb.append("_").append(PkId.toStringId(posDeb.getId()));
	    }
	    docName = sb.toString();
	}
	doc.setNomeDocumento(docName);
	this.insert(doc);
	if (posDeb != null && posDeb.getId() != null && posDeb.getId().getCodice() != null) {
	    if (tipoDoc != null) {
		switch (tipoDoc) {
		case AVVISO:
		    posDeb.setAvviso(doc);
		    break;
		case FATTURA:
		    posDeb.setFattura(doc);
		    break;
		case RICEVUTA:
		    posDeb.setRicevuta(doc);
		    break;
		case RICEVUTA_XML:
		    posDeb.setRicevutaXML(doc);
		}
	    }
	    this.payPosizioniDebitorieService.update(posDeb);
	}
	return doc;
    }

    @Override
    public PayDocumenti salvaDocumentoPerPosizioneDebitoria(PayPosizioniDebitorie posDeb, TipoDocumentoType tipoDoc, DataHandler docData,
	    String docName) {

	switch (tipoDoc) {
	case FATTURA:
	    posDeb.setDataGenerazioneFattura(new Date());
	    break;
	case AVVISO:
	    posDeb.setDataInvioAvviso(new Date());
	    break;
	default:
	    break;
	}
	PayDocumenti retDoc = this.creaDocumentoPerPosizioneDebitoria(posDeb, tipoDoc, docName);
	if (docData != null && docData.getDataSource() != null) {
	    retDoc.setDataHandler(docData);
	    this.update(retDoc);
	}
	return retDoc;
    }

    @Override
    public PayDocumenti salvaDocumentoPerPosizioneDebitoriaTrans(PayPosizioniDebitorie posDeb, TipoDocumentoType tipoDoc, DataHandler docData,
	    String docName) {

	return this.salvaDocumentoPerPosizioneDebitoria(posDeb, tipoDoc, docData, docName);
    }

    @Override
    public PayDocumenti salvaRicevutaXMLPerPosizioneDebitoria(PayPosizioniDebitorie posDeb, DataHandler docData) {

	return this.salvaDocumentoPerPosizioneDebitoria(posDeb, TipoDocumentoType.RICEVUTA_XML, docData, null);
    }

    @Override
    public void associaDocumentoAPosizioneDebitoria(PayPosizioniDebitorie posDeb, PayDocumenti doc, TipoDocumentoType tipoDoc) {

	switch (tipoDoc) {
	case FATTURA:
	    posDeb.setDataGenerazioneFattura(new Date());
	    posDeb.setFattura(doc);
	    break;
	case AVVISO:
	    posDeb.setDataInvioAvviso(new Date());
	    posDeb.setAvviso(doc);
	    break;
	case RICEVUTA:
	    posDeb.setRicevuta(doc);
	    break;
	case RICEVUTA_XML:
	    posDeb.setRicevutaXML(doc);
	    break;
	default:
	    break;
	}
	this.payPosizioniDebitorieService.update(posDeb);
    }

    @Override
    public void associaDocumentoAPosizioneDebitoriaTrans(PayPosizioniDebitorie pos, PayDocumenti doc, TipoDocumentoType tipoDoc) {

	this.associaDocumentoAPosizioneDebitoria(pos, doc, tipoDoc);
    }

    @Override
    public void populateDatiFattura(DatiFatturaType datiFattura) throws PayException {

	if (datiFattura.getDettaglio() == null || datiFattura.getDettaglio().getRigaDettaglio().isEmpty() || datiFattura.getDataScadenza() == null) {
	    PayPosizioniDebitorie pos = this.payPosizioniDebitorieService.findByRiferimentoPosizione(datiFattura);
	    if (pos == null) {
		throw new PayException("impossibile inizializzare i dati della fattura: riferimento posizione debitoria inesistente.");
	    }
	    if (datiFattura.getDataScadenza() == null && pos.getDataScadenza() != null) {
		datiFattura.setDataScadenza(Utilities.getXMLGregorianCalendar(pos.getDataScadenza()));
	    }
	    if (datiFattura.getDettaglio() == null || datiFattura.getDettaglio().getRigaDettaglio().isEmpty()) {
		Set<PayDettaglioImporti> posDetts = pos.getDettagliImporto();
		DettaglioFatturaType dettFatt = new DettaglioFatturaType();
		for (PayDettaglioImporti dettPos : posDetts) {
		    RigaDettaglioFatturaType rigaDett = new RigaDettaglioFatturaType();
		    rigaDett.setImportoTotale(dettPos.getImporto().divide(BigDecimal.valueOf(100)));
		    rigaDett.setDescrizione(dettPos.getDescCausale());
		    dettFatt.getRigaDettaglio().add(rigaDett);
		}
		datiFattura.setDettaglio(dettFatt);
	    }
	}
    }
}
