package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ScadenzarioListHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1264489795607902150L;
    private BigDecimal id;
    private String idcomune;
    private String software;
    private String softwaredescrizione;
    private Integer codiceistanza;
    private String numeroistanza;
    private Integer codicerichiedente;
    private String richiedentenominativo;
    private String richiedentenome;
    private String richiedentecodicefiscale;
    private String richiedentepartitaiva;
    private Integer richstoricoid;
    private String richstoriconominativo;
    private String richstoriconome;
    private String richstoricocodicefiscale;
    private String richstoricopartitaiva;
    private String codicestato;
    private String stato;
    private String intervento;
    private Integer codicemovimento;
    private String descrmovimento;
    private String tipomovimentodafare;
    private String descrmovimentodafare;
    private Integer codiceinventario;
    private String endoprocedimento;
    private Integer codiceamministrazione;
    private String amministrazione;
    private Date datascadenza;
    private String tiposoggetto;
    private Integer codiceazienda;
    private String aziendanominativo;
    private String aziendanome;
    private String aziendacodicefiscale;
    private String aziendapartitaiva;
    private Integer azstoricoid;
    private String azstoriconominativo;
    private String azstoriconome;
    private String azstoricocodicefiscale;
    private String azstoricopartitaiva;

    public ScadenzarioListHelper() {

    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((amministrazione == null) ? 0 : amministrazione.hashCode());
	result = prime * result + ((codiceamministrazione == null) ? 0 : codiceamministrazione.hashCode());
	result = prime * result + ((codiceinventario == null) ? 0 : codiceinventario.hashCode());
	result = prime * result + ((codiceistanza == null) ? 0 : codiceistanza.hashCode());
	result = prime * result + ((codicemovimento == null) ? 0 : codicemovimento.hashCode());
	result = prime * result + ((codicerichiedente == null) ? 0 : codicerichiedente.hashCode());
	result = prime * result + ((codicestato == null) ? 0 : codicestato.hashCode());
	result = prime * result + ((datascadenza == null) ? 0 : datascadenza.hashCode());
	result = prime * result + ((descrmovimento == null) ? 0 : descrmovimento.hashCode());
	result = prime * result + ((descrmovimentodafare == null) ? 0 : descrmovimentodafare.hashCode());
	result = prime * result + ((endoprocedimento == null) ? 0 : endoprocedimento.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((intervento == null) ? 0 : intervento.hashCode());
	result = prime * result + ((numeroistanza == null) ? 0 : numeroistanza.hashCode());
	result = prime * result + ((richiedentecodicefiscale == null) ? 0 : richiedentecodicefiscale.hashCode());
	result = prime * result + ((richiedentenome == null) ? 0 : richiedentenome.hashCode());
	result = prime * result + ((richiedentenominativo == null) ? 0 : richiedentenominativo.hashCode());
	result = prime * result + ((richiedentepartitaiva == null) ? 0 : richiedentepartitaiva.hashCode());
	result = prime * result + ((richstoricocodicefiscale == null) ? 0 : richstoricocodicefiscale.hashCode());
	result = prime * result + ((richstoricoid == null) ? 0 : richstoricoid.hashCode());
	result = prime * result + ((richstoriconome == null) ? 0 : richstoriconome.hashCode());
	result = prime * result + ((richstoriconominativo == null) ? 0 : richstoriconominativo.hashCode());
	result = prime * result + ((richstoricopartitaiva == null) ? 0 : richstoricopartitaiva.hashCode());
	result = prime * result + ((software == null) ? 0 : software.hashCode());
	result = prime * result + ((softwaredescrizione == null) ? 0 : softwaredescrizione.hashCode());
	result = prime * result + ((stato == null) ? 0 : stato.hashCode());
	result = prime * result + ((tipomovimentodafare == null) ? 0 : tipomovimentodafare.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ScadenzarioListHelper other = (ScadenzarioListHelper) obj;
	if (amministrazione == null) {
	    if (other.amministrazione != null)
		return false;
	} else if (!amministrazione.equals(other.amministrazione))
	    return false;
	if (codiceamministrazione == null) {
	    if (other.codiceamministrazione != null)
		return false;
	} else if (!codiceamministrazione.equals(other.codiceamministrazione))
	    return false;
	if (codiceinventario == null) {
	    if (other.codiceinventario != null)
		return false;
	} else if (!codiceinventario.equals(other.codiceinventario))
	    return false;
	if (codiceistanza == null) {
	    if (other.codiceistanza != null)
		return false;
	} else if (!codiceistanza.equals(other.codiceistanza))
	    return false;
	if (codicemovimento == null) {
	    if (other.codicemovimento != null)
		return false;
	} else if (!codicemovimento.equals(other.codicemovimento))
	    return false;
	if (codicerichiedente == null) {
	    if (other.codicerichiedente != null)
		return false;
	} else if (!codicerichiedente.equals(other.codicerichiedente))
	    return false;
	if (codicestato == null) {
	    if (other.codicestato != null)
		return false;
	} else if (!codicestato.equals(other.codicestato))
	    return false;
	if (datascadenza == null) {
	    if (other.datascadenza != null)
		return false;
	} else if (!datascadenza.equals(other.datascadenza))
	    return false;
	if (descrmovimento == null) {
	    if (other.descrmovimento != null)
		return false;
	} else if (!descrmovimento.equals(other.descrmovimento))
	    return false;
	if (descrmovimentodafare == null) {
	    if (other.descrmovimentodafare != null)
		return false;
	} else if (!descrmovimentodafare.equals(other.descrmovimentodafare))
	    return false;
	if (endoprocedimento == null) {
	    if (other.endoprocedimento != null)
		return false;
	} else if (!endoprocedimento.equals(other.endoprocedimento))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	if (intervento == null) {
	    if (other.intervento != null)
		return false;
	} else if (!intervento.equals(other.intervento))
	    return false;
	if (numeroistanza == null) {
	    if (other.numeroistanza != null)
		return false;
	} else if (!numeroistanza.equals(other.numeroistanza))
	    return false;
	if (richiedentecodicefiscale == null) {
	    if (other.richiedentecodicefiscale != null)
		return false;
	} else if (!richiedentecodicefiscale.equals(other.richiedentecodicefiscale))
	    return false;
	if (richiedentenome == null) {
	    if (other.richiedentenome != null)
		return false;
	} else if (!richiedentenome.equals(other.richiedentenome))
	    return false;
	if (richiedentenominativo == null) {
	    if (other.richiedentenominativo != null)
		return false;
	} else if (!richiedentenominativo.equals(other.richiedentenominativo))
	    return false;
	if (richiedentepartitaiva == null) {
	    if (other.richiedentepartitaiva != null)
		return false;
	} else if (!richiedentepartitaiva.equals(other.richiedentepartitaiva))
	    return false;
	if (richstoricocodicefiscale == null) {
	    if (other.richstoricocodicefiscale != null)
		return false;
	} else if (!richstoricocodicefiscale.equals(other.richstoricocodicefiscale))
	    return false;
	if (richstoricoid == null) {
	    if (other.richstoricoid != null)
		return false;
	} else if (!richstoricoid.equals(other.richstoricoid))
	    return false;
	if (richstoriconome == null) {
	    if (other.richstoriconome != null)
		return false;
	} else if (!richstoriconome.equals(other.richstoriconome))
	    return false;
	if (richstoriconominativo == null) {
	    if (other.richstoriconominativo != null)
		return false;
	} else if (!richstoriconominativo.equals(other.richstoriconominativo))
	    return false;
	if (richstoricopartitaiva == null) {
	    if (other.richstoricopartitaiva != null)
		return false;
	} else if (!richstoricopartitaiva.equals(other.richstoricopartitaiva))
	    return false;
	if (software == null) {
	    if (other.software != null)
		return false;
	} else if (!software.equals(other.software))
	    return false;
	if (softwaredescrizione == null) {
	    if (other.softwaredescrizione != null)
		return false;
	} else if (!softwaredescrizione.equals(other.softwaredescrizione))
	    return false;
	if (stato == null) {
	    if (other.stato != null)
		return false;
	} else if (!stato.equals(other.stato))
	    return false;
	if (tipomovimentodafare == null) {
	    if (other.tipomovimentodafare != null)
		return false;
	} else if (!tipomovimentodafare.equals(other.tipomovimentodafare))
	    return false;
	return true;
    }

    public BigDecimal getId() {

	return id;
    }

    public void setId(BigDecimal id) {

	this.id = id;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getSoftwaredescrizione() {

	return softwaredescrizione;
    }

    public void setSoftwaredescrizione(String softwaredescrizione) {

	this.softwaredescrizione = softwaredescrizione;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public Integer getCodicerichiedente() {

	return codicerichiedente;
    }

    public void setCodicerichiedente(Integer codicerichiedente) {

	this.codicerichiedente = codicerichiedente;
    }

    public String getRichiedentenominativo() {

	return richiedentenominativo;
    }

    public void setRichiedentenominativo(String richiedentenominativo) {

	this.richiedentenominativo = richiedentenominativo;
    }

    public String getRichiedentenome() {

	return richiedentenome;
    }

    public void setRichiedentenome(String richiedentenome) {

	this.richiedentenome = richiedentenome;
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

    public Integer getRichstoricoid() {

	return richstoricoid;
    }

    public void setRichstoricoid(Integer richstoricoid) {

	this.richstoricoid = richstoricoid;
    }

    public String getRichstoriconominativo() {

	return richstoriconominativo;
    }

    public void setRichstoriconominativo(String richstoriconominativo) {

	this.richstoriconominativo = richstoriconominativo;
    }

    public String getRichstoriconome() {

	return richstoriconome;
    }

    public void setRichstoriconome(String richstoriconome) {

	this.richstoriconome = richstoriconome;
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

    public String getCodicestato() {

	return codicestato;
    }

    public void setCodicestato(String codicestato) {

	this.codicestato = codicestato;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public String getDescrmovimento() {

	return descrmovimento;
    }

    public void setDescrmovimento(String descrmovimento) {

	this.descrmovimento = descrmovimento;
    }

    public String getTipomovimentodafare() {

	return tipomovimentodafare;
    }

    public void setTipomovimentodafare(String tipomovimentodafare) {

	this.tipomovimentodafare = tipomovimentodafare;
    }

    public String getDescrmovimentodafare() {

	return descrmovimentodafare;
    }

    public void setDescrmovimentodafare(String descrmovimentodafare) {

	this.descrmovimentodafare = descrmovimentodafare;
    }

    public Integer getCodiceinventario() {

	return codiceinventario;
    }

    public void setCodiceinventario(Integer codiceinventario) {

	this.codiceinventario = codiceinventario;
    }

    public String getEndoprocedimento() {

	return endoprocedimento;
    }

    public void setEndoprocedimento(String endoprocedimento) {

	this.endoprocedimento = endoprocedimento;
    }

    public Integer getCodiceamministrazione() {

	return codiceamministrazione;
    }

    public void setCodiceamministrazione(Integer codiceamministrazione) {

	this.codiceamministrazione = codiceamministrazione;
    }

    public String getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(String amministrazione) {

	this.amministrazione = amministrazione;
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public String getTiposoggetto() {

	return tiposoggetto;
    }

    public void setTiposoggetto(String tiposoggetto) {

	this.tiposoggetto = tiposoggetto;
    }

    public Integer getCodiceazienda() {

	return codiceazienda;
    }

    public void setCodiceazienda(Integer codiceazienda) {

	this.codiceazienda = codiceazienda;
    }

    public String getAziendanominativo() {

	return aziendanominativo;
    }

    public void setAziendanominativo(String aziendanominativo) {

	this.aziendanominativo = aziendanominativo;
    }

    public String getAziendanome() {

	return aziendanome;
    }

    public void setAziendanome(String aziendanome) {

	this.aziendanome = aziendanome;
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

    public Integer getAzstoricoid() {

	return azstoricoid;
    }

    public void setAzstoricoid(Integer azstoricoid) {

	this.azstoricoid = azstoricoid;
    }

    public String getAzstoriconominativo() {

	return azstoriconominativo;
    }

    public void setAzstoriconominativo(String azstoriconominativo) {

	this.azstoriconominativo = azstoriconominativo;
    }

    public String getAzstoriconome() {

	return azstoriconome;
    }

    public void setAzstoriconome(String azstoriconome) {

	this.azstoriconome = azstoriconome;
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

    public String getDescrizioneRichiedente() {

	StringBuffer result = new StringBuffer();
	result.append(StringUtils.defaultIfEmpty(getRichiedentenominativo(), ""));
	if (StringUtils.isNotBlank(getRichiedentenome())) {
	    result.append(" ").append(getRichiedentenome());
	}
	if (StringUtils.isNotBlank(getRichiedentecodicefiscale())) {
	    result.append(" [CF: ").append(getRichiedentecodicefiscale()).append("]");
	}
	if (StringUtils.isNotBlank(getRichiedentepartitaiva())) {
	    result.append(" [PIVA: ").append(getRichiedentepartitaiva()).append("]");
	}
	return result.toString();
    }

    public String getTransientDescrizioneRichiedenteQualitaAzienda() {

	String result = "";
	result = getRichiedentenominativo();
	if (StringUtils.isNotBlank(getRichiedentenome())) {
	    result += " " + getRichiedentenome();
	}
	if (StringUtils.isNotBlank(getTiposoggetto())) {
	    result += " " + getTiposoggetto();
	}
	if (StringUtils.isNotBlank(getAziendanominativo())) {
	    result += " " + getAziendanominativo();
	}
	return result;
    }
}
