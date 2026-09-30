package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="IdentificativoDominio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "identificativoDominio", "identificativoUnivocoVersamento" })
@XmlRootElement(name = "GeneraAvvisoRequest")
public class GeneraAvvisoRequest {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected String identificativoUnivocoVersamento;

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
     * Recupera il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdentificativoUnivocoVersamento(String value) {

	this.identificativoUnivocoVersamento = value;
    }
}
