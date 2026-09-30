package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class IstanzeListHelper extends BaseIstanzeListHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4320676443217572561L;
    private String idcomune;
    private BigDecimal codiceistanza;
    private String numeroistanza;
    private String software;
    private String codicesoftware;
    private BigDecimal countautorizzazioni;
    private Date data;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private BigDecimal fkidtipologiaistanza;
    private String tipologiaistanza;
    private BigDecimal codicerichiedente;
    private String richiedentecodicefiscale;
    private String richiedentepartitaiva;
    private BigDecimal codicetiposoggetto;
    private BigDecimal codiceazienda;
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
    private BigDecimal countstradari;
    private BigDecimal countendoprocedimenti;
    private BigDecimal codiceinterventoproc;
    private String interventoproc;
    private BigDecimal codiceprocedura;
    private String procedura;
    private String oggettoistanza;
    private BigDecimal tipoarchivio;
    private String archivio;
    private String posizionearchivio;
    private BigDecimal countsorteggiata;
    private String codicestatoistanza;
    private String statoistanza;
    private String colorestatoistanza;
    private String codicecomune;
    private String comune;
    private String codiceistat;
    private BigDecimal codicetecnico;
    private String tecnicocognome;
    private String tecniconome;
    private String azione;
    private Date datavalidita;
    private String domicilioelettronico;
    private String codicepraticatelematica;
    private String descrizionesportello;
    private String tecnicocf;
    private String tecnicopiva;
    private String note;
    private String denominazioneAttivita;
    private String tempisticaStato;
    private String operatoreincarico;
    private String uuid;

    public IstanzeListHelper() {

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

    public String getCodicesoftware() {

	return codicesoftware;
    }

    public void setCodicesoftware(String codicesoftware) {

	this.codicesoftware = codicesoftware;
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

    public BigDecimal getCodiceazienda() {

	return codiceazienda;
    }

    public void setCodiceazienda(BigDecimal codiceazienda) {

	this.codiceazienda = codiceazienda;
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

    public void setAzstoricocodicefiscale(String azstoricocodicefiscale) {

	this.azstoricocodicefiscale = azstoricocodicefiscale;
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

    public String getColorestatoistanza() {

	return colorestatoistanza;
    }

    public void setColorestatoistanza(String colorestatoistanza) {

	this.colorestatoistanza = colorestatoistanza;
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

    public String getCodiceistat() {

	return codiceistat;
    }

    public void setCodiceistat(String codiceistat) {

	this.codiceistat = codiceistat;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Override
    public int hashCode() {

	int result = 1;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodiceistanza() == null ? 0 : this.getCodiceistanza().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	IstanzeListHelper castOther = (IstanzeListHelper) obj;
	return ((this.getCodiceistanza() == castOther.getCodiceistanza()) || (this.getCodiceistanza() != null && castOther.getCodiceistanza() != null && this
		.getCodiceistanza().equals(castOther.getCodiceistanza())))
		&& ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
			.getIdcomune().equals(castOther.getIdcomune())));
    }

    public BigDecimal getCountautorizzazioni() {

	return countautorizzazioni;
    }

    public void setCountautorizzazioni(BigDecimal countautorizzazioni) {

	this.countautorizzazioni = countautorizzazioni;
    }

    public BigDecimal getCountstradari() {

	return countstradari;
    }

    public void setCountstradari(BigDecimal countstradari) {

	this.countstradari = countstradari;
    }

    public BigDecimal getCountendoprocedimenti() {

	return countendoprocedimenti;
    }

    public void setCountendoprocedimenti(BigDecimal countendoprocedimenti) {

	this.countendoprocedimenti = countendoprocedimenti;
    }

    public BigDecimal getCountsorteggiata() {

	return countsorteggiata;
    }

    public void setCountsorteggiata(BigDecimal countsorteggiata) {

	this.countsorteggiata = countsorteggiata;
    }

    public BigDecimal getCodicetecnico() {

	return codicetecnico;
    }

    public void setCodicetecnico(BigDecimal codicetecnico) {

	this.codicetecnico = codicetecnico;
    }

    public String getTecnicocognome() {

	return tecnicocognome;
    }

    public void setTecnicocognome(String tecnicocognome) {

	this.tecnicocognome = tecnicocognome;
    }

    public String getTecniconome() {

	return tecniconome;
    }

    public void setTecniconome(String tecniconome) {

	this.tecniconome = tecniconome;
    }

    public String getTransientDescrizioneTecnico() {

	String result = "";
	if (StringUtils.isNotBlank(getTecnicocognome())) {
	    result += getTecnicocognome();
	    if (StringUtils.isNotBlank(getTecniconome())) {
		result += " " + getTecniconome();
	    }
	}
	return StringUtils.defaultString(result).toUpperCase();
    }

    public String getAzione() {

	return azione;
    }

    public void setAzione(String azione) {

	this.azione = azione;
    }

    public Date getDatavalidita() {

	return datavalidita;
    }

    public void setDatavalidita(Date datavalidita) {

	this.datavalidita = datavalidita;
    }

    public String getDomicilioelettronico() {

	return domicilioelettronico;
    }

    public void setDomicilioelettronico(String domicilioelettronico) {

	this.domicilioelettronico = domicilioelettronico;
    }

    public String getCodicepraticatelematica() {

	return codicepraticatelematica;
    }

    public void setCodicepraticatelematica(String codicepraticatelematica) {

	this.codicepraticatelematica = codicepraticatelematica;
    }

    public String getDescrizionesportello() {

	return descrizionesportello;
    }

    public void setDescrizionesportello(String descrizionesportello) {

	this.descrizionesportello = descrizionesportello;
    }

    public String getTecnicocf() {

	return tecnicocf;
    }

    public void setTecnicocf(String tecnicocf) {

	this.tecnicocf = tecnicocf;
    }

    public String getTecnicopiva() {

	return tecnicopiva;
    }

    public void setTecnicopiva(String tecnicopiva) {

	this.tecnicopiva = tecnicopiva;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getDenominazioneAttivita() {

	return denominazioneAttivita;
    }

    public void setDenominazioneAttivita(String denominazioneAttivita) {

	this.denominazioneAttivita = denominazioneAttivita;
    }

    public String getTempisticaStato() {

	return tempisticaStato;
    }

    public void setTempisticaStato(String tempisticaStato) {

	this.tempisticaStato = tempisticaStato;
    }

    public String getOperatoreincarico() {

	return operatoreincarico;
    }

    public void setOperatoreincarico(String operatoreincarico) {

	this.operatoreincarico = operatoreincarico;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }
}
