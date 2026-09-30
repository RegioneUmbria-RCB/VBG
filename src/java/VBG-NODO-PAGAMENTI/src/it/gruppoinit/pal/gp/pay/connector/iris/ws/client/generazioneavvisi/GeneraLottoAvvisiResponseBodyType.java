package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generazioneavvisi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per GeneraLottoAvvisiResponseBodyType complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GeneraLottoAvvisiResponseBodyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentificativoDominio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DimensioneLotto" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ElencoAvvisi" type="{http://idp.tasgroup.it/GenerazioneAvvisi/}ElencoAvvisiType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraLottoAvvisiResponseBodyType", propOrder = { "identificativoDominio", "dimensioneLotto", "elencoAvvisi" })
public class GeneraLottoAvvisiResponseBodyType {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "DimensioneLotto")
    protected int dimensioneLotto;
    @XmlElement(name = "ElencoAvvisi", required = true)
    protected List<ElencoAvvisiType> elencoAvvisi;

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
     * Recupera il valore della proprietà dimensioneLotto.
     * 
     */
    public int getDimensioneLotto() {

	return dimensioneLotto;
    }

    /**
     * Imposta il valore della proprietà dimensioneLotto.
     * 
     */
    public void setDimensioneLotto(int value) {

	this.dimensioneLotto = value;
    }

    /**
     * Gets the value of the elencoAvvisi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the elencoAvvisi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getElencoAvvisi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ElencoAvvisiType }
     * 
     * 
     */
    public List<ElencoAvvisiType> getElencoAvvisi() {

	if (elencoAvvisi == null) {
	    elencoAvvisi = new ArrayList<ElencoAvvisiType>();
	}
	return this.elencoAvvisi;
    }
}
