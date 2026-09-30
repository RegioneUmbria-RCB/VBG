package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ArchiviazioneMetadatiIstanza {

    private Integer codiceIstanza;
    private String idcomune;
    private String numeroProtocollo;
    private Date dataProtocollo;
    //private Date dataDocumento;
    //SOFTWARE
    private String tipologiaPratica;
    private String sottotipo;
    private String richiedenteNome;
    private String richiedenteCognome;
    private String richiedenteCF;
    private String richiedentePIVA;
    private String professionistaNome;
    private String professionistaCognome;
    private String professionistaCF;
    private String professionistaPIVA;
    private String indirizzo;
    //NUMERO PRATICA
    private String collegamentoPratiche;
    private String titolario;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public String getTipologiaPratica() {

	return tipologiaPratica;
    }

    public void setTipologiaPratica(String tipologiaPratica) {

	this.tipologiaPratica = tipologiaPratica;
    }

    public String getSottotipo() {

	return sottotipo;
    }

    public void setSottotipo(String sottotipo) {

	this.sottotipo = sottotipo;
    }

    public String getRichiedenteNome() {

	return richiedenteNome;
    }

    public void setRichiedenteNome(String richiedenteNome) {

	this.richiedenteNome = richiedenteNome;
    }

    public String getRichiedenteCognome() {

	return richiedenteCognome;
    }

    public void setRichiedenteCognome(String richiedenteCognome) {

	this.richiedenteCognome = richiedenteCognome;
    }

    public String getRichiedenteCF() {

	return richiedenteCF;
    }

    public void setRichiedenteCF(String richiedenteCF) {

	this.richiedenteCF = richiedenteCF;
    }

    public String getRichiedentePIVA() {

	return richiedentePIVA;
    }

    public void setRichiedentePIVA(String richiedentePIVA) {

	this.richiedentePIVA = richiedentePIVA;
    }

    public String getProfessionistaNome() {

	return professionistaNome;
    }

    public void setProfessionistaNome(String professionistaNome) {

	this.professionistaNome = professionistaNome;
    }

    public String getProfessionistaCognome() {

	return professionistaCognome;
    }

    public void setProfessionistaCognome(String professionistaCognome) {

	this.professionistaCognome = professionistaCognome;
    }

    public String getProfessionistaCF() {

	return professionistaCF;
    }

    public void setProfessionistaCF(String professionistaCF) {

	this.professionistaCF = professionistaCF;
    }

    public String getProfessionistaPIVA() {

	return professionistaPIVA;
    }

    public void setProfessionistaPIVA(String professionistaPIVA) {

	this.professionistaPIVA = professionistaPIVA;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getCollegamentoPratiche() {

	return collegamentoPratiche;
    }

    public void setCollegamentoPratiche(String collegamentoPratiche) {

	this.collegamentoPratiche = collegamentoPratiche;
    }

    public String getTitolario() {

	return titolario;
    }

    public void setTitolario(String titolario) {

	this.titolario = titolario;
    }

    /**
     * lista metadati obbligatori: codf_piva, data_documento (questo lo recupero da ArchiviazioneMetadatiOggetto),
     * data_prot, indirizzo, num_prot, procedimento, soggetto_rich, sottotipo.
     * 
     * @return
     */
    public String getMetadatiObbligatoriMancanti() {

	String errorString = "";
	StringBuffer metadatiMancanti = new StringBuffer();
	//questo lo recupero da ArchiviazioneMetadatiOggetto.getMetadatiObbligatoriMancanti
	//if (dataDocumento == null) {
	//    metadatiMancanti.append("__data_documento_dt,");
	//}
	if (StringUtils.isBlank(richiedenteCF) && StringUtils.isBlank(richiedentePIVA)) {
	    metadatiMancanti.append("codf_piva_s,");
	}
	if (dataProtocollo == null) {
	    metadatiMancanti.append("data_prot_dt,");
	}
	if (StringUtils.isBlank(indirizzo)) {
	    metadatiMancanti.append("indirizzo_s,");
	}
	if (StringUtils.isBlank(numeroProtocollo)) {
	    metadatiMancanti.append("num_prot_s,");
	}
	if (StringUtils.isBlank(tipologiaPratica)) {
	    metadatiMancanti.append("procedimento_s,");
	}
	if (StringUtils.isBlank(richiedenteNome) && StringUtils.isBlank(richiedenteCognome)) {
	    metadatiMancanti.append("soggetto_rich_s,");
	}
	if (StringUtils.isBlank(sottotipo)) {
	    metadatiMancanti.append("sottotipo_s");
	}
	if (metadatiMancanti.length() > 0) {
	    //TODO recuperare il numero istanza ed il software per il messaggio di errore
	    errorString = "I documenti dell'istanza " + collegamentoPratiche + " del modulo " + tipologiaPratica
		    + " non sono archiviabili perchè mancano i seguenti metadati: " + metadatiMancanti.toString() + "<br />";
	}
	return errorString;
    }
}
