
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioVociType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioVociType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DettaglioVoce"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Tipo" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TipoDettaglioVoce"/&gt;
 *                   &lt;element name="Descrizione" type="{http://www.csi.it/epay/epaywso/types}String100Type" minOccurs="0"/&gt;
 *                   &lt;element name="Importo" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioVociType", propOrder = {
    "dettaglioVoce"
})
public class DettaglioVociType {

    @XmlElement(name = "DettaglioVoce", required = true)
    protected DettaglioVociType.DettaglioVoce dettaglioVoce;

    /**
     * Recupera il valore della proprietà dettaglioVoce.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioVociType.DettaglioVoce }
     *     
     */
    public DettaglioVociType.DettaglioVoce getDettaglioVoce() {
        return dettaglioVoce;
    }

    /**
     * Imposta il valore della proprietà dettaglioVoce.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioVociType.DettaglioVoce }
     *     
     */
    public void setDettaglioVoce(DettaglioVociType.DettaglioVoce value) {
        this.dettaglioVoce = value;
    }


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
     *         &lt;element name="Tipo" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TipoDettaglioVoce"/&gt;
     *         &lt;element name="Descrizione" type="{http://www.csi.it/epay/epaywso/types}String100Type" minOccurs="0"/&gt;
     *         &lt;element name="Importo" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/&gt;
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
        "tipo",
        "descrizione",
        "importo"
    })
    public static class DettaglioVoce {

        @XmlElement(name = "Tipo", required = true)
        @XmlSchemaType(name = "string")
        protected TipoDettaglioVoce tipo;
        @XmlElement(name = "Descrizione")
        protected String descrizione;
        @XmlElement(name = "Importo", required = true)
        protected BigDecimal importo;

        /**
         * Recupera il valore della proprietà tipo.
         * 
         * @return
         *     possible object is
         *     {@link TipoDettaglioVoce }
         *     
         */
        public TipoDettaglioVoce getTipo() {
            return tipo;
        }

        /**
         * Imposta il valore della proprietà tipo.
         * 
         * @param value
         *     allowed object is
         *     {@link TipoDettaglioVoce }
         *     
         */
        public void setTipo(TipoDettaglioVoce value) {
            this.tipo = value;
        }

        /**
         * Recupera il valore della proprietà descrizione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizione() {
            return descrizione;
        }

        /**
         * Imposta il valore della proprietà descrizione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizione(String value) {
            this.descrizione = value;
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

    }

}
