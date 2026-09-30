package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisi;
import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisiLogs;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.ElencoMetadati;

@Service
public class DocumentiCondivisiLogsServiceImpl implements IDocumentiCondivisiLogsService {

    private static final Logger log = LoggerFactory.getLogger(DocumentiCondivisiLogsServiceImpl.class);
    private static final String LOG_INSERIMENTO_GENERICO = "Documento inserito in condivisione";
    private static final String LOG_INSERIMENTO_PROTOCOLLAZIONE = "Documento inserito a fronte della protocollazione";
    private static final String LOG_CONDIVISIONE_OK = "Documento condiviso correttamente";
    private IDocumentiCondivisiLogsDAO documentiCondivisiLogsDAO;

    @Autowired
    public DocumentiCondivisiLogsServiceImpl(IDocumentiCondivisiLogsDAO documentiCondivisiLogsDAO) {

	this.documentiCondivisiLogsDAO = documentiCondivisiLogsDAO;
    }

    @Override
    public void addLogInserimentoDopoProtocollazione(Integer idTestata) {

	this.addSuccessLog(idTestata, LOG_INSERIMENTO_PROTOCOLLAZIONE);
    }

    @Override
    public void addLogInserimentoDopoProtocollazione(Set<DocumentiCondivisi> testate) {

	if (testate == null) {
	    return;
	}
	for (DocumentiCondivisi testata : testate) {
	    if (testata != null) {
		this.addLogInserimentoDopoProtocollazione(testata.getId().getCodice());
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addLogDocumentoCondiviso(DocumentiCondivisiHelper documento, List<DocumentiCondivisiMetadato> metadati) {

	if (documento == null) {
	    return;
	}
	Integer idTestata = documento.getId();
	String datiEstesi = null;
	if (metadati != null) {
	    ElencoMetadati elenco = new ElencoMetadati(metadati);
	    datiEstesi = elenco.toXmlString();
	}
	this.addSuccessLog(idTestata, LOG_CONDIVISIONE_OK, datiEstesi);
    }

    @SuppressWarnings("unchecked")
    private void addSuccessLog(Integer idTestata, String messaggio) {

	this.addSuccessLog(idTestata, messaggio, null);
    }

    @SuppressWarnings("unchecked")
    private void addSuccessLog(Integer idTestata, String messaggio, String datiEstesi) {

	if (StringUtils.isBlank(messaggio)) {
	    return;
	}
	DocumentiCondivisiLogs dcl = new DocumentiCondivisiLogs(idTestata, LivelloLogDocumentiCondivisiEnum.SUCCESS, new Date());
	dcl.setMessaggio(messaggio);
	dcl.setDatiEstesi(datiEstesi);
	this.documentiCondivisiLogsDAO.insert(dcl);
    }

    @Override
    public void addErrorLog(DocumentiCondivisiHelper documento, String messaggio) {

	this.addErrorLog(documento, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addErrorLog(DocumentiCondivisiHelper documento, String messaggio, List<DocumentiCondivisiMetadato> metadati) {

	if (documento == null) {
	    return;
	}
	log.info("Errore nella trasmissione dell'oggetto: " + documento.getCodiceOggetto() + " : " + messaggio);
	Integer idTEstata = documento.getId();
	String datiEstesi = null;
	if (metadati != null) {
	    ElencoMetadati elenco = new ElencoMetadati(metadati);
	    datiEstesi = elenco.toXmlString();
	}
	DocumentiCondivisiLogs dcl = new DocumentiCondivisiLogs(idTEstata, LivelloLogDocumentiCondivisiEnum.ERROR, new Date());
	dcl.setMessaggio(messaggio);
	dcl.setDatiEstesi(datiEstesi);
	this.documentiCondivisiLogsDAO.insert(dcl);
    }

    @Override
    public void addLogInserimentoDocumentoMancante(DocumentiCondivisi docCondiviso) {

	this.addSuccessLog(docCondiviso.getId().getCodice(), LOG_INSERIMENTO_GENERICO);
    }
}
