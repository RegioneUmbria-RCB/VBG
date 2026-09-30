package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.NotEmpty;

@Entity
@Table(name = "ELENCOCASSAEDILEBASE")
public class Elencocassaedilebase implements Serializable {

    private String codice;
    private String descrizione;
    private String descrizioneEstesa;

    public Elencocassaedilebase() {

	super();
    }

    @Id
    @Column(name = "CODICE", unique = true, nullable = false, length = 4)
    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    @NotEmpty
    @Column(name = "DESCRIZIONE", length = 100)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Transient
    public String getDescrizioneEstesa() {

	String result = "";
	if (StringUtils.isNotBlank(getDescrizione())) {
	    result += getDescrizione();
	}
	if (StringUtils.isNotBlank(getCodice())) {
	    result += " (" + getCodice() + ")";
	}
	return result;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codice == null) ? 0 : codice.hashCode());
	return result;
    }
}
