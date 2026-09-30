package it.gruppoinit.pal.gp.core.features.segnaposto.v2.registro;

public class ChiaveRegistro {

    private String nomeSegnaposto;
    private boolean supportaArgomenti;

    public ChiaveRegistro(String nomeSegnaposto, boolean supportaArgomenti) {

	this.nomeSegnaposto = nomeSegnaposto;
	this.supportaArgomenti = supportaArgomenti;
    }

    public String getNomeSegnaposto() {

	return nomeSegnaposto;
    }

    public boolean isSupportaArgomenti() {

	return supportaArgomenti;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((nomeSegnaposto == null) ? 0 : nomeSegnaposto.hashCode());
	result = prime * result + (supportaArgomenti ? 1231 : 1237);
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
	ChiaveRegistro other = (ChiaveRegistro) obj;
	if (nomeSegnaposto == null) {
	    if (other.nomeSegnaposto != null)
		return false;
	} else if (!nomeSegnaposto.equals(other.nomeSegnaposto))
	    return false;
	if (supportaArgomenti != other.supportaArgomenti)
	    return false;
	return true;
    }
}
