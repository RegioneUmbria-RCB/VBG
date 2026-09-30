
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tipoDocumento" type="{http://www.paevolution.com/ws/pagamenti_types/}TipoDocumentoType" maxOccurs="3" minOccurs="0"/&gt;
 *         &lt;element name="erroreTipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tipoDocumento",
    "erroreTipoDocumento"
})
@XmlRootElement(name = "ElencoDocumentiType")
public class ElencoDocumentiType {

    @XmlSchemaType(name = "string")
    protected List<TipoDocumentoType> tipoDocumento;
    protected String erroreTipoDocumento;

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipoDocumento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipoDocumento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoDocumentoType }
     * 
     * 
     */
    public List<TipoDocumentoType> getTipoDocumento() {
        if (tipoDocumento == null) {
            tipoDocumento = new ArrayList<TipoDocumentoType>();
        }
        return this.tipoDocumento;
    }

    /**
     * Recupera il valore della proprietà erroreTipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErroreTipoDocumento() {
        return erroreTipoDocumento;
    }

    /**
     * Imposta il valore della proprietà erroreTipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErroreTipoDocumento(String value) {
        this.erroreTipoDocumento = value;
    }

}
