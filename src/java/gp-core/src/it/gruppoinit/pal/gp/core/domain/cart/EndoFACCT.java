package it.gruppoinit.pal.gp.core.domain.cart;

import java.io.Serializable;

public class EndoFACCT implements Serializable, Comparable<EndoFACCT> {

    /**
     * 
     */
    private static final long serialVersionUID = -7366030197940182708L;
    private int codiceInventario;
    private String descrizione;
    private String codiceEndo;
    private boolean obbligatorio;
    private boolean endoCART;

    /**
     * @return the codiceInventario
     */
    public int getCodiceInventario() {

	return codiceInventario;
    }

    /**
     * @param codiceInventario
     *            the codiceInventario to set
     */
    public void setCodiceInventario(int codiceInventario) {

	this.codiceInventario = codiceInventario;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {

	return descrizione;
    }

    /**
     * @param descrizione
     *            the descrizione to set
     */
    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    /**
     * @return the codiceBDR
     */
    public String getCodiceBDR() {

	return codiceEndo;
    }

    /**
     * @param codiceBDR
     *            the codiceBDR to set
     */
    public void setCodiceBDR(String codiceBDR) {

	this.codiceEndo = codiceBDR;
    }

    /**
     * @return the obbligatorio
     */
    public boolean isObbligatorio() {

	return obbligatorio;
    }

    /**
     * @param obbligatorio
     *            the obbligatorio to set
     */
    public void setObbligatorio(boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    public boolean isEndoCART() {

	return endoCART;
    }

    public void setEndoCART(boolean endoCART) {

	this.endoCART = endoCART;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + codiceInventario;
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
	EndoFACCT other = (EndoFACCT) obj;
	if (codiceInventario != other.codiceInventario)
	    return false;
	return true;
    }

    @Override
    public int compareTo(EndoFACCT o) {

	if(o != null){
	    int diff = this.getCodiceInventario() - o.getCodiceInventario();
	    if(diff  > 0){
		return 1;
	    }
	    else if(diff < 0){
		return -1;
	    }
	    else{
		return diff;
	    }
	}
	else{
	    return 1;
	}
    }
}
