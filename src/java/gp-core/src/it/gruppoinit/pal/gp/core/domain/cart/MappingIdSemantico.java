package it.gruppoinit.pal.gp.core.domain.cart;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="MappingIdSemantico",propOrder = {"idSemantico", "valore"})
public class MappingIdSemantico implements Comparable<MappingIdSemantico>{
    
    @XmlElement(name = "idSemantico", required = true)
    private String idSemantico;
    @XmlElement(name = "valore", required = true)
    private ValoreIdSemantico valore;
    
    public MappingIdSemantico(){};
    
    public MappingIdSemantico(String idSemantico, ValoreIdSemantico valore){
	
	this.idSemantico = idSemantico;
	this.valore = valore;
    }

    /**
     * @return the idSemantico
     */
    public String getIdSemantico() {
    
        return idSemantico;
    }
    
    /**
     * @param idSemantico the idSemantico to set
     */
    public void setIdSemantico(String idSemantico) {
    
        this.idSemantico = idSemantico;
    }
    
    /**
     * @return the valore
     */
    public ValoreIdSemantico getValore() {
    
        return valore;
    }
    
    /**
     * @param valore the valore to set
     */
    public void setValore(ValoreIdSemantico valore) {
    
        this.valore = valore;
    }

    @Override
    public int compareTo(MappingIdSemantico o) {

	return this.idSemantico.compareTo(o.getIdSemantico());
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idSemantico == null) ? 0 : idSemantico.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MappingIdSemantico other = (MappingIdSemantico) obj;
	if (idSemantico == null) {
	    if (other.idSemantico != null)
		return false;
	} else if (!idSemantico.equals(other.idSemantico))
	    return false;
	return true;
    }
}
