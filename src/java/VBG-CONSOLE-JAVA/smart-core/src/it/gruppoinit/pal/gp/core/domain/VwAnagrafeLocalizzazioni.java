package it.gruppoinit.pal.gp.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VW_ANAGRAFE_LOCALIZZAZIONI")
public class VwAnagrafeLocalizzazioni implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6855115738207823857L;
    private PkId id;
    private String nominativo;
    private String indirizzoResidenza;
    private String localizzazioneResidenza;
    private String indirizzoCorrispondenza;
    private String localizzazioneCorrispondenza;
    private Boolean isCorrispondenza;
    private Set<Anagrafe> anagrafes = new HashSet<Anagrafe>();

    public VwAnagrafeLocalizzazioni() {

    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "CODICEANAGRAFE", nullable = false, precision = 2, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "NOMINATIVO")
    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    @Column(name = "INDIRIZZO_RESIDENZA")
    public String getIndirizzoResidenza() {

	return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {

	this.indirizzoResidenza = indirizzoResidenza;
    }

    @Column(name = "LOCALIZZAZIONE_RESIDENZA")
    public String getLocalizzazioneResidenza() {

	return localizzazioneResidenza;
    }

    public void setLocalizzazioneResidenza(String localizzazioneResidenza) {

	this.localizzazioneResidenza = localizzazioneResidenza;
    }

    @Column(name = "INDIRIZZO_CORRISPONDENZA")
    public String getIndirizzoCorrispondenza() {

	return indirizzoCorrispondenza;
    }

    public void setIndirizzoCorrispondenza(String indirizzoCorrispondenza) {

	this.indirizzoCorrispondenza = indirizzoCorrispondenza;
    }

    @Column(name = "LOCALIZZAZIONE_CORRISPONDENZA")
    public String getLocalizzazioneCorrispondenza() {

	return localizzazioneCorrispondenza;
    }

    public void setLocalizzazioneCorrispondenza(String localizzazioneCorrispondenza) {

	this.localizzazioneCorrispondenza = localizzazioneCorrispondenza;
    }

    @Column(name = "IS_CORRISPONDENZA")
    public Boolean getIsCorrispondenza() {

	return isCorrispondenza;
    }

    public void setIsCorrispondenza(Boolean isCorrispondenza) {

	this.isCorrispondenza = isCorrispondenza;
    }

    public void setAnagrafes(Set<Anagrafe> anagrafes) {

	this.anagrafes = anagrafes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vwAnagrafeLocalizzazioni")
    public Set<Anagrafe> getAnagrafes() {

	return anagrafes;
    }
}
