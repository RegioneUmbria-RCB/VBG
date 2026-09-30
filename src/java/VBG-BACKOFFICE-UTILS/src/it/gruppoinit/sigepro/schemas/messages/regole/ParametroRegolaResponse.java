
package it.gruppoinit.sigepro.schemas.messages.regole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.sigepro.schemas.messages.base.ErroreBackofficeType;


/**
 * <p>Classe Java per ParametroRegolaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ParametroRegolaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="parametro" type="{http://gruppoinit.it/sigepro/schemas/messages/regole}ParametroType"/&gt;
 *           &lt;element name="errore" type="{http://gruppoinit.it/sigepro/schemas/messages/base}ErroreBackofficeType"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametroRegolaResponse", propOrder = {
    "parametro",
    "errore"
})
public class ParametroRegolaResponse {

    protected ParametroType parametro;
    protected ErroreBackofficeType errore;

    /**
     * Recupera il valore della proprietà parametro.
     * 
     * @return
     *     possible object is
     *     {@link ParametroType }
     *     
     */
    public ParametroType getParametro() {
        return parametro;
    }

    /**
     * Imposta il valore della proprietà parametro.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametroType }
     *     
     */
    public void setParametro(ParametroType value) {
        this.parametro = value;
    }

    /**
     * Recupera il valore della proprietà errore.
     * 
     * @return
     *     possible object is
     *     {@link ErroreBackofficeType }
     *     
     */
    public ErroreBackofficeType getErrore() {
        return errore;
    }

    /**
     * Imposta il valore della proprietà errore.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreBackofficeType }
     *     
     */
    public void setErrore(ErroreBackofficeType value) {
        this.errore = value;
    }

}
