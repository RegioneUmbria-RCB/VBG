package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Classe Java per PosizioneDebitoriaType complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoriaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroRata" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="importo" type="{http://www.paevolution.com/ws/pagamenti_types/}DettaglioImportoType"/&gt;
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="riferimentoClient" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoriaType", propOrder = { "descrizione", "numeroRata", "importo", "dataScadenza", "riferimentiClient" })
public class PosizioneDebitoriaType {

    @XmlElement(required = true)
    protected String descrizione;
    protected BigInteger numeroRata;
    @XmlElement(required = true)
    protected DettaglioImportoType importo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;
    @XmlAttribute(name = "riferimentiClient")
    protected List<String> riferimentiClient;

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizione() {

	return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizione(String value) {

	this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà numeroRata.
     * 
     * @return possible object is {@link BigInteger }
     * 
     */
    public BigInteger getNumeroRata() {

	return numeroRata;
    }

    /**
     * Imposta il valore della proprietà numeroRata.
     * 
     * @param value
     *            allowed object is {@link BigInteger }
     * 
     */
    public void setNumeroRata(BigInteger value) {

	this.numeroRata = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return possible object is {@link DettaglioImportoType }
     * 
     */
    public DettaglioImportoType getImporto() {

	return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *            allowed object is {@link DettaglioImportoType }
     * 
     */
    public void setImporto(DettaglioImportoType value) {

	this.importo = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataScadenza() {

	return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataScadenza(XMLGregorianCalendar value) {

	this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoClient.
     * 
     * @return possible object is {@link String }
     * 
     */
    public List<String> getRiferimentiClient() {

	if (this.riferimentiClient == null) {
	    this.riferimentiClient = new ArrayList<String>();
	}
	return riferimentiClient;
    }

    /**
     * Imposta il valore della proprietà riferimentoClient.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRiferimentoClient(List<String> value) {

	this.riferimentiClient = value;
    }
}
