package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement()
public class RigaComunicazione {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "codiceanagrafe")
    private Integer codiceAnagrafe;
    @XmlElement(name = "codiceamministrazione")
    private Integer codiceAmministrazione;
    @XmlElement(name = "codiceresponsabile")
    private Integer codiceResponsabile;
    @XmlElement(name = "destinatario")
    private String destinatario;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "pec")
    private String pec;
    @XmlElement(name = "modificamail")
    private boolean modificaMail;
    @XmlElement(name = "descrizionestato")
    private String descrizioneStato;
    @XmlElement(name = "errore")
    private String errore;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public Integer getCodiceAmministrazione() {

	return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(Integer codiceAmministrazione) {

	this.codiceAmministrazione = codiceAmministrazione;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public void setCodiceResponsabile(Integer codiceResponsabile) {

	this.codiceResponsabile = codiceResponsabile;
    }

    public String getDestinatario() {

	return destinatario;
    }

    public void setDestinatario(String destinatario) {

	this.destinatario = destinatario;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    public boolean isModificaMail() {

	return modificaMail;
    }

    public void setModificaMail(boolean modificaMail) {

	this.modificaMail = modificaMail;
    }

    public String getDescrizioneStato() {

	return descrizioneStato;
    }

    public void setDescrizioneStato(String descrizioneStato) {

	this.descrizioneStato = descrizioneStato;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public static RigaComunicazione FromMassiveDettaglio(MassiveDettaglio riga) {

	if (riga == null || riga.getId() == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo RigaComunicazione senza passsare il parametro riga valido");
	}
	RigaComunicazione retval = new RigaComunicazione();
	retval.setId(riga.getId().getCodice());
	MassiveDettDestinatari destinatari = riga.getDestinatari();
	String dest = "";
	String mailDestinatario = destinatari.getMailDestinatario();
	String mail = "";
	String pec = "";
	if (destinatari.getAmministrazioni() != null && destinatari.getAmministrazioni().getId() != null) {
	    retval.setCodiceAmministrazione(destinatari.getAmministrazioni().getId().getCodice());
	    dest = destinatari.getAmministrazioni().getAmministrazione();
	    mail = destinatari.getAmministrazioni().getEmail();
	    pec = destinatari.getAmministrazioni().getPec();
	} else if (destinatari.getAnagrafe() != null && destinatari.getAnagrafe().getId() != null) {
	    retval.setCodiceAnagrafe(destinatari.getAnagrafe().getId().getCodice());
	    dest = destinatari.getAnagrafe().getDescrizioneRichiedenteBreve();
	    mail = destinatari.getAnagrafe().getEmail();
	    pec = destinatari.getAnagrafe().getPec();
	} else if (destinatari.getResponsabili() != null && destinatari.getResponsabili().getId() != null) {
	    retval.setCodiceResponsabile(destinatari.getResponsabili().getId().getCodice());
	    dest = destinatari.getResponsabili().getResponsabile();
	    mail = destinatari.getResponsabili().getEmail();	    
	}
	StringBuilder destinatario = new StringBuilder();
	destinatario.append(dest);
	if (StringUtils.isNotBlank(mailDestinatario)) {
	    destinatario.append(" (");
	    destinatario.append(mailDestinatario);
	    destinatario.append(")");
	}
	retval.setDestinatario(destinatario.toString());
	retval.setEmail(mail);
	retval.setPec(pec);
	retval.setModificaMail(StringUtils.isBlank(mailDestinatario));
	retval.setErrore(riga.getErrore());
	StringBuilder ultimoStato = new StringBuilder();
	ultimoStato.append(riga.getUltimoStatoCompletato());
	ultimoStato.append(" (");
	ultimoStato.append(Utilities.formatDate(riga.getUltimoStatoData(), false));
	ultimoStato.append(")");
	retval.setDescrizioneStato(ultimoStato.toString());
	return retval;
    }
}
