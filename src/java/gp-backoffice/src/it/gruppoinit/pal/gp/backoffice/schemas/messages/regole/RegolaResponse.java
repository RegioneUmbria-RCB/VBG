package it.gruppoinit.pal.gp.backoffice.schemas.messages.regole;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RegolaResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegolaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attiva" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="listaParametri" type="{http://gruppoinit.it/sigepro/schemas/messages/regole}ParametroType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegolaResponse", propOrder = { "attiva", "listaParametri" })
public class RegolaResponse {

    protected boolean attiva;
    protected List<ParametroType> listaParametri;

    /**
     * Gets the value of the attiva property.
     * 
     */
    public boolean isAttiva() {

	return attiva;
    }

    /**
     * Sets the value of the attiva property.
     * 
     */
    public void setAttiva(boolean value) {

	this.attiva = value;
    }

    /**
     * Gets the value of the listaParametri property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the listaParametri property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getListaParametri().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ParametroType }
     * 
     * 
     */
    public List<ParametroType> getListaParametri() {

	if (listaParametri == null) {
	    listaParametri = new ArrayList<ParametroType>();
	}
	return this.listaParametri;
    }
}
