package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.scrittura;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.AccountFtp;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.VerticalizzazioneCondivisioneDocumentale;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.ElencoMetadati;
import it.gruppoinit.pal.gp.core.features.ftp.IAccountFTPService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.FTPHelper;

@Service
public class ScritturaMetadatiServiceImpl implements IScritturaMetadatiService {

    private VerticalizzazioniService verticalizzazioniService;
    private IAccountFTPService accountFTPService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIAccountFTPService(IAccountFTPService accountFTPService) {

	this.accountFTPService = accountFTPService;
    }

    @Override
    public void scriviSuFTP(List<DocumentiCondivisiMetadato> metadati, String nomeFile, String percorsoRelativo) {

	if (metadati == null || metadati.isEmpty()) {
	    return;
	}
	VerticalizzazioneCondivisioneDocumentale verticalizzazione = new VerticalizzazioneCondivisioneDocumentale(this.verticalizzazioniService);
	if (verticalizzazione.getIdAccountFTP() == null) {
	    String msg = String.format("Non è configurato il parametro {} della verticalizzazione{}",
		    WebConstants.VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE_ID_ACCOUNT_FTP, WebConstants.VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE);
	    throw new RuntimeException(msg);
	}
	AccountFtp accountFTP = this.accountFTPService.findById(verticalizzazione.getIdAccountFTP());
	if (accountFTP == null) {
	    String msg = String.format("Il parametro {} della verticalizzazione{} è configurato con il riferimento ad un account FTP non censito",
		    WebConstants.VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE_ID_ACCOUNT_FTP, WebConstants.VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE);
	    throw new RuntimeException(msg);
	}
	ElencoMetadati elenco = new ElencoMetadati(metadati);
	String xmlText = elenco.toXmlString();
	try {
	    FTPHelper ftpHelper = new FTPHelper(accountFTP);
	    InputStream inStream = new ByteArrayInputStream(xmlText.getBytes());
	    ftpHelper.put(inStream, nomeFile, percorsoRelativo);
	    inStream.close();
	} catch (Exception e) {
	    String msg = "Si è verificato un errore nella scrittura del file " + nomeFile + " contenente i metadati. Errore: " + e.getMessage();
	    throw new RuntimeException(msg, e);
	}
    }
}