package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generazioneavvisi;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per ElencoAvvisiType complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ElencoAvvisiType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AvvisoAnalogico" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElencoAvvisiType", propOrder = { "avvisoAnalogico" })
public class ElencoAvvisiType {

    @XmlElement(name = "AvvisoAnalogico", required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler avvisoAnalogico;

    /**
     * Recupera il valore della proprietà avvisoAnalogico.
     * 
     * @return possible object is {@link DataHandler }
     * 
     */
    public DataHandler getAvvisoAnalogico() {

	return avvisoAnalogico;
    }

    /**
     * Imposta il valore della proprietà avvisoAnalogico.
     * 
     * @param value
     *            allowed object is {@link DataHandler }
     * 
     */
    public void setAvvisoAnalogico(DataHandler value) {

	this.avvisoAnalogico = value;
    }
}
