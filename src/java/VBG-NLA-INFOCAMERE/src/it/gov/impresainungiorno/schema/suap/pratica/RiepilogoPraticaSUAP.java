package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per RiepilogoPraticaSUAP complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiepilogoPraticaSUAP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="info-schema" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}VersioneSchema"/&gt;
 *         &lt;element name="intestazione" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Intestazione"/&gt;
 *         &lt;element name="struttura" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Struttura"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "riepilogo-pratica-suap")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiepilogoPraticaSUAP", propOrder = { "infoSchema", "intestazione", "struttura" })
public class RiepilogoPraticaSUAP {

    @XmlElement(name = "info-schema", required = true)
    protected VersioneSchema infoSchema;
    @XmlElement(required = true)
    protected Intestazione intestazione;
    @XmlElement(required = true)
    protected Struttura struttura;

    /**
     * Recupera il valore della proprietà infoSchema.
     * 
     * @return possible object is {@link VersioneSchema }
     * 
     */
    public VersioneSchema getInfoSchema() {

	return infoSchema;
    }

    /**
     * Imposta il valore della proprietà infoSchema.
     * 
     * @param value
     *            allowed object is {@link VersioneSchema }
     * 
     */
    public void setInfoSchema(VersioneSchema value) {

	this.infoSchema = value;
    }

    /**
     * Recupera il valore della proprietà intestazione.
     * 
     * @return possible object is {@link Intestazione }
     * 
     */
    public Intestazione getIntestazione() {

	return intestazione;
    }

    /**
     * Imposta il valore della proprietà intestazione.
     * 
     * @param value
     *            allowed object is {@link Intestazione }
     * 
     */
    public void setIntestazione(Intestazione value) {

	this.intestazione = value;
    }

    /**
     * Recupera il valore della proprietà struttura.
     * 
     * @return possible object is {@link Struttura }
     * 
     */
    public Struttura getStruttura() {

	return struttura;
    }

    /**
     * Imposta il valore della proprietà struttura.
     * 
     * @param value
     *            allowed object is {@link Struttura }
     * 
     */
    public void setStruttura(Struttura value) {

	this.struttura = value;
    }
}
