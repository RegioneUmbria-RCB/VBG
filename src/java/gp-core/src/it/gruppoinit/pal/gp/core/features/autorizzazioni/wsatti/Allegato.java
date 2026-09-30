package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;

public class Allegato {

    private Integer idDocumento;
    private Integer numeroDocumento;
    private Integer annoDocumento;
    private String nomeFile;
    private String allegatoBase64;
    private boolean principale;
    private String serial;

    public Integer getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {

	this.idDocumento = idDocumento;
    }

    public Integer getNumeroDocumento() {

	return numeroDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {

	this.numeroDocumento = numeroDocumento;
    }

    public Integer getAnnoDocumento() {

	return annoDocumento;
    }

    public void setAnnoDocumento(Integer annoDocumento) {

	this.annoDocumento = annoDocumento;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getAllegatoBase64() {

	return allegatoBase64;
    }

    public void setAllegatoBase64(String allegatoBase64) {

	this.allegatoBase64 = allegatoBase64;
    }

    public boolean isPrincipale() {

	return principale;
    }

    public void setPrincipale(boolean principale) {

	this.principale = principale;
    }

    public String getEstensioneFile() {

	if (StringUtils.isBlank(this.nomeFile)) {
	    return null;
	}
	return FilenameUtils.getExtension(this.nomeFile);
    }

    public String getSerial() {

	return serial;
    }

    public void setSerial(String serial) {

	this.serial = serial;
    }

    public static Allegato fromDocumentiAutorizzazione(DocumentiAutorizzazione documento) {

	if (documento == null) {
	    return null;
	}
	Allegato allegato = new Allegato();
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(documento.getAutorizzazioni().getAutorizdata());
	allegato.setAnnoDocumento(calendar.get(Calendar.YEAR));
	allegato.setIdDocumento(Integer.parseInt(documento.getAutorizzazioni().getFkidprotocollo()));
	allegato.setNomeFile(documento.getOggetti().getNomefile());
	String numeroAtto = documento.getAutorizzazioni().getAutoriznumero().contains("/")
		? documento.getAutorizzazioni().getAutoriznumero().substring(0, documento.getAutorizzazioni().getAutoriznumero().indexOf("/"))
		: documento.getAutorizzazioni().getAutoriznumero();
	allegato.setNumeroDocumento(Integer.parseInt(numeroAtto));
	allegato.setPrincipale(documento.isPrincipale());
	allegato.setSerial(documento.getId().getCodiceoggetto().toString());
	return allegato;
    }
}
