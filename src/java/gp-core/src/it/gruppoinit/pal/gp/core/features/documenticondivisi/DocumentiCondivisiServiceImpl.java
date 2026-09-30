package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisi;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura.ILetturaMetadatiService;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.scrittura.IScritturaMetadatiService;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.scrittura.IScritturaOggettiService;

@Service
public class DocumentiCondivisiServiceImpl implements IDocumentiCondivisiService {

    private Logger logger = LoggerFactory.getLogger(DocumentiCondivisiServiceImpl.class);
    @Autowired
    private IDocumentiCondivisiLogsService documentiCondivisiLogsService;
    @Autowired
    private IDocumentiCondivisiDAO documentiCondivisiDAO;
    @Autowired
    private MovimentiDAO movimentiDAO;
    @Autowired
    private IScritturaMetadatiService scritturaMetadatiService;
    @Autowired
    private IScritturaOggettiService scritturaOggettiService;
    @Autowired
    private ILetturaMetadatiService letturaMetadatiService;
    private static final String ESTENSIONE_METADATI = ".meta";

    @Override
    public Boolean documentoPresente(Integer idDocumento) {

	if (idDocumento == null) {
	    throw new IllegalArgumentException("Impossibile richiamare documentoPresente(Integer idDocumento) con idDocumento null");
	}
	return this.documentiCondivisiDAO.documentoPresente(idDocumento);
    }

    @Override
    public void impostaStatoDocumentoDaCondividere(Integer idDocumento) {

	if (idDocumento == null) {
	    throw new IllegalArgumentException("Impossibile richiamare impostaStatoDocumentoDaCondividere(Integer idDocumento) con idDocumento null");
	}
	this.documentiCondivisiDAO.impostaStato(idDocumento, StatiDocumentiCondivisiEnum.DA_CONDIVIDERE);
    }

    @Override
    public void condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza) {

	if (idDocumenti == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza) con idDocumenti null");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza) con codiceIstanza null");
	}
	Set<DocumentiCondivisi> docs = this.documentiCondivisiDAO.condividiDocumentiIstanza(idDocumenti, codiceIstanza);
	this.documentiCondivisiLogsService.addLogInserimentoDopoProtocollazione(docs);
    }

    @Override
    public void condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceMovimento) {

	if (idDocumenti == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceMovimento) con idDocumenti null");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceMovimento) con codiceMovimento null");
	}
	Movimenti movimento = this.movimentiDAO.findById(new PkId(codiceMovimento));
	if (movimento == null) {
	    throw new InvalidArgumentException("Il codicemovimento " + codiceMovimento + " passato non esiste");
	}
	Integer codiceIStanza = movimento.getIstanza().getId().getCodice();
	Set<DocumentiCondivisi> docs = this.documentiCondivisiDAO.condividiDocumentiMovimento(idDocumenti, codiceIStanza, codiceMovimento);
	this.documentiCondivisiLogsService.addLogInserimentoDopoProtocollazione(docs);
    }

    @Override
    public void condividi(List<DocumentiCondivisiHelper> documenti) {

	if (documenti != null) {
	    for (DocumentiCondivisiHelper documento : documenti) {
		List<DocumentiCondivisiMetadato> metadati = new ArrayList<DocumentiCondivisiMetadato>();
		try {
		    metadati = this.letturaMetadatiService.read(documento);
		    String nomeFileMetadati = this.getNomeMetadatiDaNomeFile(documento.getNomeFile());
		    this.scritturaMetadatiService.scriviSuFTP(metadati, nomeFileMetadati, "");
		    this.scritturaOggettiService.scriviSuFTP(documento.getCodiceOggetto(), documento.getNomeFile(), "");
		    this.documentiCondivisiDAO.impostaStato(documento.getId(), StatiDocumentiCondivisiEnum.CONDIVISO);
		    this.documentiCondivisiLogsService.addLogDocumentoCondiviso(documento, metadati);
		    this.documentiCondivisiDAO.commit();
		    this.documentiCondivisiDAO.flush();
		} catch (Exception e) {
		    this.documentiCondivisiLogsService.addErrorLog(documento, e.getMessage(), metadati);
		    logger.error("Errore durante la condivisione del documento DOCUMENTI_CONDIVISI.ID = " + documento.getId(), e);
		    throw new RuntimeException(e);
		}
	    }
	}
    }

    private String getNomeMetadatiDaNomeFile(String nomeFile) {

	if (StringUtils.isBlank(nomeFile)) {
	    return null;
	}
	int index = nomeFile.lastIndexOf(".");
	return nomeFile.substring(0, index) + ESTENSIONE_METADATI;
    }

    @Override
    public void elaboraDocumenti(IDocumentiReader reader) {

	this.condividi(reader.getDocumentiDaInviare());
    }

    @Override
    public void inserisciDocumentiMancantiInCondivisioneDocumentale(IDocumentiReader reader) {

	List<DocumentoMancanteInCondivisioneDocumentale> docDaCondividere = reader.getDocumentiDaCondividere();
	DocumentiCondivisi docCondiviso = null;
	for (DocumentoMancanteInCondivisioneDocumentale doc : docDaCondividere) {
	    if (doc.getCodiceMovimento() != null) {
		docCondiviso = this.documentiCondivisiDAO.condividiDocumentoMovimento(doc.getIdDocumento(), doc.getCodiceIstanza(),
			doc.getCodiceMovimento());
	    } else {
		docCondiviso = this.documentiCondivisiDAO.condividiDocumentoIstanza(doc.getIdDocumento(), doc.getCodiceIstanza());
	    }
	    this.documentiCondivisiLogsService.addLogInserimentoDocumentoMancante(docCondiviso);
	    this.documentiCondivisiDAO.commit();
	    this.documentiCondivisiDAO.flush();
	}
    }

    @Override
    public void deleteByCodiceMovimento(Integer codiceMovimento) {

	this.documentiCondivisiDAO.deleteByCodiceMovimento(codiceMovimento);
    }
}
