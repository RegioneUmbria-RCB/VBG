package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio;

import java.io.Serializable;

public class ChiaveLivelloServizio implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6046543400565009143L;
    private Integer idPosteggio;
    private Integer idUso;

    public Integer getIdPosteggio() {

	return idPosteggio;
    }

    public void setIdPosteggio(Integer idPosteggio) {

	this.idPosteggio = idPosteggio;
    }

    public Integer getIdUso() {

	return idUso;
    }

    public void setIdUso(Integer idUso) {

	this.idUso = idUso;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ChiaveLivelloServizio other = (ChiaveLivelloServizio) obj;
	// idPosteggio
	if (idPosteggio == null) {
	    if (other.idPosteggio != null)
		return false;
	} else if (idPosteggio.compareTo(other.idPosteggio) != 0) {
	    return false;
	}
	// idUso
	if (idUso == null) {
	    if (other.idUso != null)
		return false;
	} else if (idUso.compareTo(other.idUso) != 0) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = (idPosteggio == null ? 0 : idPosteggio.hashCode());
	result = prime * result + (idUso == null ? 0 : idUso.hashCode());
	return result;
    }
}
