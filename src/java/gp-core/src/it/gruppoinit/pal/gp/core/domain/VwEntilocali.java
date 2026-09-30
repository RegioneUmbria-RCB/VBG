package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "VW_ENTILOCALI")
public class VwEntilocali implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2247138870849896970L;
    private String codicecomune;
    private String comune;
    private String descrizioneEstesa;
    private String transientDescrizioneComune;

    @Id
    @Column(name = "CODICECOMUNE", unique = true, length = 5)
    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    @Column(name = "COMUNE", length = 128)
    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public void setTransientDescrizioneComune(String transientDescrizioneComune) {

	this.transientDescrizioneComune = transientDescrizioneComune;
    }

    @Transient
    public String getTransientDescrizioneComune() {

	transientDescrizioneComune = comune;
	return transientDescrizioneComune;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {

	this.descrizioneEstesa = descrizioneEstesa;
    }

    @Transient
    public String getDescrizioneEstesa() {

	this.descrizioneEstesa = "";
	if (StringUtils.isNotBlank(getComune())) {
	    this.descrizioneEstesa = getComune() + " (" + getCodicecomune() + ")";
	}
	return this.descrizioneEstesa;
    }
}
