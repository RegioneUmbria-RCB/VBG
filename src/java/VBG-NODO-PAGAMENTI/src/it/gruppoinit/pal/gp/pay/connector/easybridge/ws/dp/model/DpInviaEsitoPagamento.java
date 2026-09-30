package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per anonymous complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificativoDominio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="param" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "identificativoDominio", "param" })
@XmlRootElement(name = "dpInviaEsitoPagamento", namespace = "http://easybridge.eu/bridge/")
public class DpInviaEsitoPagamento {

    protected String identificativoDominio;
    protected String param;

    /**
     * Recupera il valore della proprietà identificativoDominio.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdentificativoDominio() {

	return identificativoDominio;
    }

    /**
     * Imposta il valore della proprietà identificativoDominio.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdentificativoDominio(String value) {

	this.identificativoDominio = value;
    }

    /**
     * Recupera il valore della proprietà param.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getParam() {

	return param;
    }

    /**
     * Imposta il valore della proprietà param.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setParam(String value) {

	this.param = value;
    }
}
