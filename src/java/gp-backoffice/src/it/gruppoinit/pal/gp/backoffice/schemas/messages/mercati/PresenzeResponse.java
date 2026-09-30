
package it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="numeroPresenze" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "PresenzeResponse")
public class PresenzeResponse {

    protected int numeroPresenze;

    /**
     * Gets the value of the numeroPresenze property.
     * 
     */
    public int getNumeroPresenze() {
        return numeroPresenze;
    }

    /**
     * Sets the value of the numeroPresenze property.
     * 
     */
    public void setNumeroPresenze(int value) {
        this.numeroPresenze = value;
    }

}
