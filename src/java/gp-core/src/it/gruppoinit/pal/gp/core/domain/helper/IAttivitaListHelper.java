package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class IAttivitaListHelper extends BaseIstanzeListHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1109380145429165214L;
    private String idcomune;
    private BigDecimal id;
    private String denominazione;
    private BigDecimal codiceistanzaultima;
    private Boolean attiva;
    private Boolean operante;
    private BigDecimal codiceistanza;
    private String numeroistanza;
    private String software;
    private Date data;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private BigDecimal fkidtipologiaistanza;
    private String tipologiaistanza;
    private BigDecimal codicerichiedente;
    private String richiedentecodicefiscale;
    private String richiedentepartitaiva;
    private BigDecimal codicetiposoggetto;
    private String aziendacodicefiscale;
    private String aziendapartitaiva;
    private BigDecimal richstoricoid;
    private String richstoricocodicefiscale;
    private String richstoricopartitaiva;
    private BigDecimal azstoricoid;
    private String azstoricocodicefiscale;
    private String azstoricopartitaiva;
    private BigDecimal codiceoperatore;
    private String operatorenome;
    private BigDecimal codiceresponsabileproc;
    private String responsabileprocnome;
    private BigDecimal codiceistruttore;
    private String istruttorenome;
    private BigDecimal codicestradarioprimario;
    private String stradarioprefisso;
    private String stradariodescrizione;
    private String stradariocivico;
    private String stradariocap;
    private String stradarioesponente;
    private String stradariocolore;
    private String stradariolocalitafrazione;
    private String stradarioscala;
    private String stradariointerno;
    private String stradarioespinterno;
    private String stradariopiano;
    private String stradarioquartiere;
    private BigDecimal codiceinterventoproc;
    private String interventoproc;
    private BigDecimal codiceprocedura;
    private String procedura;
    private String oggettoistanza;
    private BigDecimal tipoarchivio;
    private String archivio;
    private String posizionearchivio;
    private String codicestatoistanza;
    private String statoistanza;
    private String codicecomune;
    private String comune;
    private String tipologiaattivita;
    private String aziendaIndirizzo;
    private String aziendaCitta;
    private String aziendaCap;
    private String aziendaComune;
    private String aziendaProvincia;
    private BigDecimal countstradari;
    private BigDecimal countschede;
    private String emailRichiedente;
    private String pecRichiedente;
    private String emailAziendaRichiedente;
    private String pecAziendaRichiedente;
    private String domicilioElettronico;

    //private String 
    public IAttivitaListHelper() {

    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public BigDecimal getId() {

	return id;
    }

    public void setId(BigDecimal id) {

	this.id = id;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    public BigDecimal getCodiceistanzaultima() {

	return codiceistanzaultima;
    }

    public void setCodiceistanzaultima(BigDecimal codiceistanzaultima) {

	this.codiceistanzaultima = codiceistanzaultima;
    }

    public Boolean getAttiva() {

	return attiva;
    }

    public void setAttiva(Boolean attiva) {

	this.attiva = attiva;
    }

    public Boolean getOperante() {

	return operante;
    }

    public void setOperante(Boolean operante) {

	this.operante = operante;
    }

    public BigDecimal getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(BigDecimal codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	this.numeroprotocollo = numeroprotocollo;
    }

    public Date getDataprotocollo() {

	return dataprotocollo;
    }

    public void setDataprotocollo(Date dataprotocollo) {

	this.dataprotocollo = dataprotocollo;
    }

    public BigDecimal getFkidtipologiaistanza() {

	return fkidtipologiaistanza;
    }

    public void setFkidtipologiaistanza(BigDecimal fkidtipologiaistanza) {

	this.fkidtipologiaistanza = fkidtipologiaistanza;
    }

    public String getTipologiaistanza() {

	return tipologiaistanza;
    }

    public void setTipologiaistanza(String tipologiaistanza) {

	this.tipologiaistanza = tipologiaistanza;
    }

    public BigDecimal getCodicerichiedente() {

	return codicerichiedente;
    }

    public void setCodicerichiedente(BigDecimal codicerichiedente) {

	this.codicerichiedente = codicerichiedente;
    }

    public String getRichiedentecodicefiscale() {

	return richiedentecodicefiscale;
    }

    public void setRichiedentecodicefiscale(String richiedentecodicefiscale) {

	this.richiedentecodicefiscale = richiedentecodicefiscale;
    }

    public String getRichiedentepartitaiva() {

	return richiedentepartitaiva;
    }

    public void setRichiedentepartitaiva(String richiedentepartitaiva) {

	this.richiedentepartitaiva = richiedentepartitaiva;
    }

    public BigDecimal getCodicetiposoggetto() {

	return codicetiposoggetto;
    }

    public void setCodicetiposoggetto(BigDecimal codicetiposoggetto) {

	this.codicetiposoggetto = codicetiposoggetto;
    }

    public String getAziendacodicefiscale() {

	return aziendacodicefiscale;
    }

    public void setAziendacodicefiscale(String aziendacodicefiscale) {

	this.aziendacodicefiscale = aziendacodicefiscale;
    }

    public String getAziendapartitaiva() {

	return aziendapartitaiva;
    }

    public void setAziendapartitaiva(String aziendapartitaiva) {

	this.aziendapartitaiva = aziendapartitaiva;
    }

    public BigDecimal getRichstoricoid() {

	return richstoricoid;
    }

    public void setRichstoricoid(BigDecimal richstoricoid) {

	this.richstoricoid = richstoricoid;
    }

    public String getRichstoricocodicefiscale() {

	return richstoricocodicefiscale;
    }

    public void setRichstoricocodicefiscale(String richstoricocodicefiscale) {

	this.richstoricocodicefiscale = richstoricocodicefiscale;
    }

    public String getRichstoricopartitaiva() {

	return richstoricopartitaiva;
    }

    public void setRichstoricopartitaiva(String richstoricopartitaiva) {

	this.richstoricopartitaiva = richstoricopartitaiva;
    }

    public BigDecimal getAzstoricoid() {

	return azstoricoid;
    }

    public void setAzstoricoid(BigDecimal azstoricoid) {

	this.azstoricoid = azstoricoid;
    }

    public String getAzstoricocodicefiscale() {

	return azstoricocodicefiscale;
    }

    public void setAzstoricocodicefiscale(String zstoricocodicefiscale) {

	this.azstoricocodicefiscale = zstoricocodicefiscale;
    }

    public String getAzstoricopartitaiva() {

	return azstoricopartitaiva;
    }

    public void setAzstoricopartitaiva(String azstoricopartitaiva) {

	this.azstoricopartitaiva = azstoricopartitaiva;
    }

    public BigDecimal getCodiceoperatore() {

	return codiceoperatore;
    }

    public void setCodiceoperatore(BigDecimal codiceoperatore) {

	this.codiceoperatore = codiceoperatore;
    }

    public String getOperatorenome() {

	return operatorenome;
    }

    public void setOperatorenome(String operatorenome) {

	this.operatorenome = operatorenome;
    }

    public BigDecimal getCodiceresponsabileproc() {

	return codiceresponsabileproc;
    }

    public void setCodiceresponsabileproc(BigDecimal codiceresponsabileproc) {

	this.codiceresponsabileproc = codiceresponsabileproc;
    }

    public String getResponsabileprocnome() {

	return responsabileprocnome;
    }

    public void setResponsabileprocnome(String responsabileprocnome) {

	this.responsabileprocnome = responsabileprocnome;
    }

    public BigDecimal getCodiceistruttore() {

	return codiceistruttore;
    }

    public void setCodiceistruttore(BigDecimal codiceistruttore) {

	this.codiceistruttore = codiceistruttore;
    }

    public String getIstruttorenome() {

	return istruttorenome;
    }

    public void setIstruttorenome(String istruttorenome) {

	this.istruttorenome = istruttorenome;
    }

    public BigDecimal getCodicestradarioprimario() {

	return codicestradarioprimario;
    }

    public void setCodicestradarioprimario(BigDecimal codicestradarioprimario) {

	this.codicestradarioprimario = codicestradarioprimario;
    }

    public String getStradarioprefisso() {

	return stradarioprefisso;
    }

    public void setStradarioprefisso(String stradarioprefisso) {

	this.stradarioprefisso = stradarioprefisso;
    }

    public String getStradariodescrizione() {

	return stradariodescrizione;
    }

    public void setStradariodescrizione(String stradariodescrizione) {

	this.stradariodescrizione = stradariodescrizione;
    }

    public String getStradariocivico() {

	return stradariocivico;
    }

    public void setStradariocivico(String stradariocivico) {

	this.stradariocivico = stradariocivico;
    }

    public String getStradariocap() {

	return stradariocap;
    }

    public void setStradariocap(String stradariocap) {

	this.stradariocap = stradariocap;
    }

    public String getStradarioesponente() {

	return stradarioesponente;
    }

    public void setStradarioesponente(String stradarioesponente) {

	this.stradarioesponente = stradarioesponente;
    }

    public String getStradariocolore() {

	return stradariocolore;
    }

    public void setStradariocolore(String stradariocolore) {

	this.stradariocolore = stradariocolore;
    }

    public String getStradariolocalitafrazione() {

	return stradariolocalitafrazione;
    }

    public void setStradariolocalitafrazione(String stradariolocalitafrazione) {

	this.stradariolocalitafrazione = stradariolocalitafrazione;
    }

    public String getStradarioscala() {

	return stradarioscala;
    }

    public void setStradarioscala(String stradarioscala) {

	this.stradarioscala = stradarioscala;
    }

    public String getStradariointerno() {

	return stradariointerno;
    }

    public void setStradariointerno(String stradariointerno) {

	this.stradariointerno = stradariointerno;
    }

    public String getStradarioespinterno() {

	return stradarioespinterno;
    }

    public void setStradarioespinterno(String stradarioespinterno) {

	this.stradarioespinterno = stradarioespinterno;
    }

    public String getStradariopiano() {

	return stradariopiano;
    }

    public void setStradariopiano(String stradariopiano) {

	this.stradariopiano = stradariopiano;
    }

    public String getStradarioquartiere() {

	return stradarioquartiere;
    }

    public void setStradarioquartiere(String stradarioquartiere) {

	this.stradarioquartiere = stradarioquartiere;
    }

    public BigDecimal getCodiceinterventoproc() {

	return codiceinterventoproc;
    }

    public void setCodiceinterventoproc(BigDecimal codiceinterventoproc) {

	this.codiceinterventoproc = codiceinterventoproc;
    }

    public String getInterventoproc() {

	return interventoproc;
    }

    public void setInterventoproc(String interventoproc) {

	this.interventoproc = interventoproc;
    }

    public BigDecimal getCodiceprocedura() {

	return codiceprocedura;
    }

    public void setCodiceprocedura(BigDecimal codiceprocedura) {

	this.codiceprocedura = codiceprocedura;
    }

    public String getProcedura() {

	return procedura;
    }

    public void setProcedura(String procedura) {

	this.procedura = procedura;
    }

    public String getOggettoistanza() {

	return oggettoistanza;
    }

    public void setOggettoistanza(String oggettoistanza) {

	this.oggettoistanza = oggettoistanza;
    }

    public BigDecimal getTipoarchivio() {

	return tipoarchivio;
    }

    public void setTipoarchivio(BigDecimal tipoarchivio) {

	this.tipoarchivio = tipoarchivio;
    }

    public String getArchivio() {

	return archivio;
    }

    public void setArchivio(String archivio) {

	this.archivio = archivio;
    }

    public String getPosizionearchivio() {

	return posizionearchivio;
    }

    public void setPosizionearchivio(String posizionearchivio) {

	this.posizionearchivio = posizionearchivio;
    }

    public String getCodicestatoistanza() {

	return codicestatoistanza;
    }

    public void setCodicestatoistanza(String codicestatoistanza) {

	this.codicestatoistanza = codicestatoistanza;
    }

    public String getStatoistanza() {

	return statoistanza;
    }

    public void setStatoistanza(String statoistanza) {

	this.statoistanza = statoistanza;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getTipologiaattivita() {

	return tipologiaattivita;
    }

    public void setTipologiaattivita(String tipologiaattivita) {

	this.tipologiaattivita = tipologiaattivita;
    }

    public String getAziendaIndirizzo() {

	return aziendaIndirizzo;
    }

    public void setAziendaIndirizzo(String aziendaIndirizzo) {

	this.aziendaIndirizzo = aziendaIndirizzo;
    }

    public String getAziendaCitta() {

	return aziendaCitta;
    }

    public void setAziendaCitta(String aziendaCitta) {

	this.aziendaCitta = aziendaCitta;
    }

    public String getAziendaCap() {

	return aziendaCap;
    }

    public void setAziendaCap(String aziendaCap) {

	this.aziendaCap = aziendaCap;
    }

    public String getAziendaComune() {

	return aziendaComune;
    }

    public void setAziendaComune(String aziendaComune) {

	this.aziendaComune = aziendaComune;
    }

    public String getAziendaProvincia() {

	return aziendaProvincia;
    }

    public void setAziendaProvincia(String aziendaProvincia) {

	this.aziendaProvincia = aziendaProvincia;
    }

    public BigDecimal getCountstradari() {

	return countstradari;
    }

    public void setCountstradari(BigDecimal countstradari) {

	this.countstradari = countstradari;
    }

    public BigDecimal getCountschede() {

	return countschede;
    }

    public void setCountschede(BigDecimal countschede) {

	this.countschede = countschede;
    }

    public String getEmailRichiedente() {

	return emailRichiedente;
    }

    public void setEmailRichiedente(String emailRichiedente) {

	this.emailRichiedente = emailRichiedente;
    }

    public String getPecRichiedente() {

	return pecRichiedente;
    }

    public void setPecRichiedente(String pecRichiedente) {

	this.pecRichiedente = pecRichiedente;
    }

    public String getEmailAziendaRichiedente() {

	return emailAziendaRichiedente;
    }

    public void setEmailAziendaRichiedente(String emailAziendaRichiedente) {

	this.emailAziendaRichiedente = emailAziendaRichiedente;
    }

    public String getPecAziendaRichiedente() {

	return pecAziendaRichiedente;
    }

    public void setPecAziendaRichiedente(String pecAziendaRichiedente) {

	this.pecAziendaRichiedente = pecAziendaRichiedente;
    }

    public String getDomicilioElettronico() {

	return domicilioElettronico;
    }

    public void setDomicilioElettronico(String domicilioElettronico) {

	this.domicilioElettronico = domicilioElettronico;
    }

    public static long getSerialversionuid() {

	return serialVersionUID;
    }

    public String getSedeLegaleAzienda() {

	String answer = "";
	if (StringUtils.isNotBlank(this.getAziendaIndirizzo())) {
	    answer = this.getAziendaIndirizzo();
	}
	if (StringUtils.isNotBlank(this.getAziendaCitta())) {
	    answer += ", " + this.getAziendaCitta();
	}
	try {
	    if (StringUtils.isNotBlank(this.getAziendaCap())
		    || (StringUtils.isNotBlank(this.getAziendaComune()) || StringUtils.isNotBlank(this.getAziendaProvincia()))) {
		answer += ",";
		if (StringUtils.isNotBlank(this.getAziendaCap())) {
		    answer += " " + this.getAziendaCap();
		}
		if (StringUtils.isNotBlank(this.getAziendaComune())) {
		    answer += " " + this.getAziendaComune();
		}
		if (StringUtils.isNotBlank(this.getAziendaProvincia())) {
		    answer += " " + this.getAziendaProvincia();
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return answer;
    }
}
