package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

public class PayConnectorWsEndpointId implements Serializable {

    private static final long serialVersionUID = 6702314640538995529L;
    private String codiceConnettore;
    private Integer id;

    public String getCodiceConnettore() {

	return codiceConnettore;
    }

    public void setCodiceConnettore(String codiceConnettore) {

	this.codiceConnettore = codiceConnettore;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceConnettore == null) ? 0 : codiceConnettore.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	PayConnectorWsEndpointId other = (PayConnectorWsEndpointId) obj;
	if (codiceConnettore == null) {
	    if (other.codiceConnettore != null) {
		return false;
	    }
	} else if (!codiceConnettore.equals(other.codiceConnettore)) {
	    return false;
	}
	if (id == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!id.equals(other.id)) {
	    return false;
	}
	return true;
    }
}
