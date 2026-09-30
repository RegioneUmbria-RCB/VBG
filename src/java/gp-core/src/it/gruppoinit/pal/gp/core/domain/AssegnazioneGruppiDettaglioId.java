package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Embeddable
public class AssegnazioneGruppiDettaglioId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3761320262270806509L;
    private String idcomune;
    private Integer idTestata;
    private Integer codiceIstanza;

    @NotNull
    @Length(max = 6)
    @Column(name = "IDCOMUNE")
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "IDTESTATA", precision = 10, scale = 0)
    public Integer getIdTestata() {

	return idTestata;
    }

    public void setIdTestata(Integer idTestata) {

	this.idTestata = idTestata;
    }

    @Column(name = "CODICEISTANZA", precision = 6, scale = 0)
    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getIdTestata() == null ? 0 : this.getIdTestata().hashCode());
	result = 37 * result + (getCodiceIstanza() == null ? 0 : this.getCodiceIstanza().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AssegnazioneGruppiDettaglioId))
	    return false;
	AssegnazioneGruppiDettaglioId castOther = (AssegnazioneGruppiDettaglioId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getIdTestata().equals(castOther.getIdTestata()))
			|| (this.getIdTestata() != null && castOther.getIdTestata() != null && this.getIdTestata().equals(castOther.getIdTestata())))
		&& ((this.getCodiceIstanza().equals(castOther.getCodiceIstanza())) || (this.getCodiceIstanza() != null
			&& castOther.getCodiceIstanza() != null && this.getCodiceIstanza().equals(castOther.getCodiceIstanza())));
    }
}
