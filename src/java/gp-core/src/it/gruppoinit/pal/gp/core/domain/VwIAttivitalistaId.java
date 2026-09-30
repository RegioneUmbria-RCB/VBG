package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VwIAttivitalistaId implements Serializable {

    private static final long serialVersionUID = -8499993615131204192L;
    private Integer icodiceistanzaultima;
    private Integer iidattivita;
    private String idcomune;
    private Integer attiva;
    private Integer operante;
    private String software;
    private String denominazione;
    private String numeroistanza;
    private Integer codicerichiedente;
    private String richiedente;
    private String cfPivaRichiedente;
    private Integer fkcodicesoggetto;
    private String tiposoggetto;
    private String codicecomune;
    private Integer codicetitolarelegale;
    private String aziendarichiedente;
    private String cfPivaAziendarichiedente;
    private Integer codicestradario;
    private String localizzazione;
    private String civico;

    public VwIAttivitalistaId() {

	super();
    }

    @Column(name = "I_CODICEISTANZAULTIMA", precision = 6, scale = 0)
    public Integer getIcodiceistanzaultima() {

	return icodiceistanzaultima;
    }

    public void setIcodiceistanzaultima(Integer icodiceistanzaultima) {

	this.icodiceistanzaultima = icodiceistanzaultima;
    }

    @Column(name = "I_IDATTIVITA", nullable = false, precision = 6, scale = 0)
    public Integer getIidattivita() {

	return iidattivita;
    }

    public void setIidattivita(Integer iidattivita) {

	this.iidattivita = iidattivita;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "ATTIVA", precision = 1, scale = 0)
    public Integer getAttiva() {

	return attiva;
    }

    public void setAttiva(Integer attiva) {

	this.attiva = attiva;
    }

    @Column(name = "OPERANTE", precision = 1, scale = 0)
    public Integer getOperante() {

	return operante;
    }

    public void setOperante(Integer operante) {

	this.operante = operante;
    }

    @Column(name = "SOFTWARE", length = 2)
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    @Column(name = "DENOMINAZIONE", length = 150)
    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    @Column(name = "NUMEROISTANZA", length = 25)
    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    @Column(name = "CODICERICHIEDENTE", precision = 6, scale = 0)
    public Integer getCodicerichiedente() {

	return codicerichiedente;
    }

    public void setCodicerichiedente(Integer codicerichiedente) {

	this.codicerichiedente = codicerichiedente;
    }

    @Column(name = "RICHIEDENTE", length = 241)
    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    @Column(name = "CF_PIVA_RICHIEDENTE", length = 28)
    public String getCfPivaRichiedente() {

	return cfPivaRichiedente;
    }

    public void setCfPivaRichiedente(String cfPivaRichiedente) {

	this.cfPivaRichiedente = cfPivaRichiedente;
    }

    @Column(name = "FKCODICESOGGETTO", precision = 4, scale = 0)
    public Integer getFkcodicesoggetto() {

	return fkcodicesoggetto;
    }

    public void setFkcodicesoggetto(Integer fkcodicesoggetto) {

	this.fkcodicesoggetto = fkcodicesoggetto;
    }

    @Column(name = "TIPOSOGGETTO", length = 128)
    public String getTiposoggetto() {

	return tiposoggetto;
    }

    public void setTiposoggetto(String tiposoggetto) {

	this.tiposoggetto = tiposoggetto;
    }

    @Column(name = "CODICECOMUNE", length = 5)
    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    @Column(name = "CODICETITOLARELEGALE", precision = 6, scale = 0)
    public Integer getCodicetitolarelegale() {

	return codicetitolarelegale;
    }

    public void setCodicetitolarelegale(Integer codicetitolarelegale) {

	this.codicetitolarelegale = codicetitolarelegale;
    }

    @Column(name = "AZIENDARICHIEDENTE", length = 241)
    public String getAziendarichiedente() {

	return aziendarichiedente;
    }

    public void setAziendarichiedente(String aziendarichiedente) {

	this.aziendarichiedente = aziendarichiedente;
    }

    @Column(name = "CF_PIVA_AZIENDARICHIEDENTE", length = 28)
    public String getCfPivaAziendarichiedente() {

	return cfPivaAziendarichiedente;
    }

    public void setCfPivaAziendarichiedente(String cfPivaAziendarichiedente) {

	this.cfPivaAziendarichiedente = cfPivaAziendarichiedente;
    }

    @Column(name = "CODICESTRADARIO", nullable = false, precision = 6, scale = 0)
    public Integer getCodicestradario() {

	return codicestradario;
    }

    public void setCodicestradario(Integer codicestradario) {

	this.codicestradario = codicestradario;
    }

    @Column(name = "LOCALIZZAZIONE", length = 768)
    public String getLocalizzazione() {

	return localizzazione;
    }

    public void setLocalizzazione(String localizzazione) {

	this.localizzazione = localizzazione;
    }

    @Column(name = "CIVICO", length = 160)
    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((attiva == null) ? 0 : attiva.hashCode());
	result = prime * result + ((aziendarichiedente == null) ? 0 : aziendarichiedente.hashCode());
	result = prime * result + ((cfPivaAziendarichiedente == null) ? 0 : cfPivaAziendarichiedente.hashCode());
	result = prime * result + ((cfPivaRichiedente == null) ? 0 : cfPivaRichiedente.hashCode());
	result = prime * result + ((civico == null) ? 0 : civico.hashCode());
	result = prime * result + ((codicecomune == null) ? 0 : codicecomune.hashCode());
	result = prime * result + ((codicerichiedente == null) ? 0 : codicerichiedente.hashCode());
	result = prime * result + ((codicestradario == null) ? 0 : codicestradario.hashCode());
	result = prime * result + ((codicetitolarelegale == null) ? 0 : codicetitolarelegale.hashCode());
	result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
	result = prime * result + ((fkcodicesoggetto == null) ? 0 : fkcodicesoggetto.hashCode());
	result = prime * result + ((icodiceistanzaultima == null) ? 0 : icodiceistanzaultima.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((iidattivita == null) ? 0 : iidattivita.hashCode());
	result = prime * result + ((localizzazione == null) ? 0 : localizzazione.hashCode());
	result = prime * result + ((numeroistanza == null) ? 0 : numeroistanza.hashCode());
	result = prime * result + ((operante == null) ? 0 : operante.hashCode());
	result = prime * result + ((richiedente == null) ? 0 : richiedente.hashCode());
	result = prime * result + ((software == null) ? 0 : software.hashCode());
	result = prime * result + ((tiposoggetto == null) ? 0 : tiposoggetto.hashCode());
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
	VwIAttivitalistaId other = (VwIAttivitalistaId) obj;
	if (attiva == null) {
	    if (other.attiva != null) {
		return false;
	    }
	} else if (!attiva.equals(other.attiva)) {
	    return false;
	}
	if (aziendarichiedente == null) {
	    if (other.aziendarichiedente != null) {
		return false;
	    }
	} else if (!aziendarichiedente.equals(other.aziendarichiedente)) {
	    return false;
	}
	if (cfPivaAziendarichiedente == null) {
	    if (other.cfPivaAziendarichiedente != null) {
		return false;
	    }
	} else if (!cfPivaAziendarichiedente.equals(other.cfPivaAziendarichiedente)) {
	    return false;
	}
	if (cfPivaRichiedente == null) {
	    if (other.cfPivaRichiedente != null) {
		return false;
	    }
	} else if (!cfPivaRichiedente.equals(other.cfPivaRichiedente)) {
	    return false;
	}
	if (civico == null) {
	    if (other.civico != null) {
		return false;
	    }
	} else if (!civico.equals(other.civico)) {
	    return false;
	}
	if (codicecomune == null) {
	    if (other.codicecomune != null) {
		return false;
	    }
	} else if (!codicecomune.equals(other.codicecomune)) {
	    return false;
	}
	if (codicerichiedente == null) {
	    if (other.codicerichiedente != null) {
		return false;
	    }
	} else if (!codicerichiedente.equals(other.codicerichiedente)) {
	    return false;
	}
	if (codicestradario == null) {
	    if (other.codicestradario != null) {
		return false;
	    }
	} else if (!codicestradario.equals(other.codicestradario)) {
	    return false;
	}
	if (codicetitolarelegale == null) {
	    if (other.codicetitolarelegale != null) {
		return false;
	    }
	} else if (!codicetitolarelegale.equals(other.codicetitolarelegale)) {
	    return false;
	}
	if (denominazione == null) {
	    if (other.denominazione != null) {
		return false;
	    }
	} else if (!denominazione.equals(other.denominazione)) {
	    return false;
	}
	if (fkcodicesoggetto == null) {
	    if (other.fkcodicesoggetto != null) {
		return false;
	    }
	} else if (!fkcodicesoggetto.equals(other.fkcodicesoggetto)) {
	    return false;
	}
	if (icodiceistanzaultima == null) {
	    if (other.icodiceistanzaultima != null) {
		return false;
	    }
	} else if (!icodiceistanzaultima.equals(other.icodiceistanzaultima)) {
	    return false;
	}
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	if (iidattivita == null) {
	    if (other.iidattivita != null) {
		return false;
	    }
	} else if (!iidattivita.equals(other.iidattivita)) {
	    return false;
	}
	if (localizzazione == null) {
	    if (other.localizzazione != null) {
		return false;
	    }
	} else if (!localizzazione.equals(other.localizzazione)) {
	    return false;
	}
	if (numeroistanza == null) {
	    if (other.numeroistanza != null) {
		return false;
	    }
	} else if (!numeroistanza.equals(other.numeroistanza)) {
	    return false;
	}
	if (operante == null) {
	    if (other.operante != null) {
		return false;
	    }
	} else if (!operante.equals(other.operante)) {
	    return false;
	}
	if (richiedente == null) {
	    if (other.richiedente != null) {
		return false;
	    }
	} else if (!richiedente.equals(other.richiedente)) {
	    return false;
	}
	if (software == null) {
	    if (other.software != null) {
		return false;
	    }
	} else if (!software.equals(other.software)) {
	    return false;
	}
	if (tiposoggetto == null) {
	    if (other.tiposoggetto != null) {
		return false;
	    }
	} else if (!tiposoggetto.equals(other.tiposoggetto)) {
	    return false;
	}
	return true;
    }
}
