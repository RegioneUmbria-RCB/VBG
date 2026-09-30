package it.gruppoinit.pal.gp.backoffice.schemas.messages.regole;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ParametroRegolaResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParametroRegolaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="parametro" type="{http://gruppoinit.it/sigepro/schemas/messages/regole}ParametroType"/>
 *           &lt;element name="errore" type="{http://gruppoinit.it/sigepro/schemas/messages/base}ErroreBackofficeType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametroRegolaResponse", propOrder = { "parametro", "errore" })
public class ParametroRegolaResponse {

    protected ParametroType parametro;
    protected ErroreBackofficeType errore;

    /**
     * Gets the value of the parametro property.
     * 
     * @return possible object is {@link ParametroType }
     * 
     */
    public ParametroType getParametro() {

	return parametro;
    }

    /**
     * Sets the value of the parametro property.
     * 
     * @param value
     *            allowed object is {@link ParametroType }
     * 
     */
    public void setParametro(ParametroType value) {

	this.parametro = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return possible object is {@link ErroreBackofficeType }
     * 
     */
    public ErroreBackofficeType getErrore() {

	return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *            allowed object is {@link ErroreBackofficeType }
     * 
     */
    public void setErrore(ErroreBackofficeType value) {

	this.errore = value;
    }
}
