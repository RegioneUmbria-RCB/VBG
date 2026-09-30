package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti;

import java.io.Serializable;

public class ChiaveCoefficienteMercato implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6435878516733443218L;
    private Integer idConto;

    public Integer getIdConto() {

	return idConto;
    }

    public void setIdConto(Integer idConto) {

	this.idConto = idConto;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ChiaveCoefficienteMercato other = (ChiaveCoefficienteMercato) obj;
	//idconto
	if (idConto == null) {
	    if (other.idConto != null)
		return false;
	} else if (idConto.compareTo(other.idConto) != 0) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + (idConto == null ? 0 : idConto.hashCode());
	return result;
    }
}
