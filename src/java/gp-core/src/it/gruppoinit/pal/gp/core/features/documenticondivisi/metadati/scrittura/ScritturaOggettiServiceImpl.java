package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.scrittura;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.AccountFtp;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.VerticalizzazioneCondivisioneDocumentale;
import it.gruppoinit.pal.gp.core.features.ftp.IAccountFTPService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.FTPHelper;

@Service
public class ScritturaOggettiServiceImpl implements IScritturaOggettiService {

    private IAccountFTPService accountFTPService;
    private OggettiService oggettiService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setAccountFTPService(IAccountFTPService accountFTPService) {

	this.accountFTPService = accountFTPService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public void scriviSuFTP(Integer codiceOggetto, String nomeFile, String percorsoRelativo) {

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
	try {
	    FTPHelper ftpHelper = new FTPHelper(accountFTP);
	    InputStream inStream = this.oggettiService.getOggettoAsInputStream(codiceOggetto);
	    ftpHelper.put(inStream, nomeFile, percorsoRelativo);
	    inStream.close();
	} catch (Exception e) {
	    String msg = "Si è verificato un errore nella scrittura del file " + nomeFile + " contenente i metadati. Errore: " + e.getMessage();
	    throw new RuntimeException(msg, e);
	}
    }
}
