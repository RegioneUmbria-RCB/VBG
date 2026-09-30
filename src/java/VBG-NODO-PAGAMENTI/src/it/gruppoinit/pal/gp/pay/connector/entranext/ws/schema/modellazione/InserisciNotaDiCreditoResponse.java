
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciNotaDiCreditoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciNotaDiCreditoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_NOTA_DI_CREDITO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Documento" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciNotaDiCreditoResponse", propOrder = {
    "idnotadicredito",
    "numero",
    "anno",
    "importo",
    "documento"
})
public class InserisciNotaDiCreditoResponse
    extends LinkNextResponse
{

    @XmlElement(name = "ID_NOTA_DI_CREDITO")
    protected int idnotadicredito;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "Anno")
    protected int anno;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "Documento")
    protected byte[] documento;

    /**
     * Recupera il valore della proprietà idnotadicredito.
     * 
     */
    public int getIDNOTADICREDITO() {
        return idnotadicredito;
    }

    /**
     * Imposta il valore della proprietà idnotadicredito.
     * 
     */
    public void setIDNOTADICREDITO(int value) {
        this.idnotadicredito = value;
    }

    /**
     * Recupera il valore della proprietà numero.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della proprietà numero.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Recupera il valore della proprietà anno.
     * 
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Imposta il valore della proprietà anno.
     * 
     */
    public void setAnno(int value) {
        this.anno = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà documento.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Imposta il valore della proprietà documento.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumento(byte[] value) {
        this.documento = value;
    }

}
