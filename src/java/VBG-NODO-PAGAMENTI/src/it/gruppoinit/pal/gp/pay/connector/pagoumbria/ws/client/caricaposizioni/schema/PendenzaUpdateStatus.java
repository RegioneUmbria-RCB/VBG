
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.Allegato;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.StatoPendenza;


/**
 * <p>Classe Java per Pendenza.UpdateStatus complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Pendenza.UpdateStatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Riscossore" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Riscossore" minOccurs="0"/&gt;
 *         &lt;element name="DataModificaEnte" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/&gt;
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}StatoPendenza" minOccurs="0"/&gt;
 *         &lt;element name="ImportoTotale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="InfoPagamento" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.UpdateStatus"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.UpdateStatus" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Allegato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Allegato" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pendenza.UpdateStatus", propOrder = {
    "riscossore",
    "dataModificaEnte",
    "stato",
    "importoTotale",
    "infoPagamento",
    "allegato"
})
public class PendenzaUpdateStatus {

    @XmlElement(name = "Riscossore")
    protected Riscossore riscossore;
    @XmlElement(name = "DataModificaEnte")
    @XmlSchemaType(name = "anySimpleType")
    protected Object dataModificaEnte;
    @XmlElement(name = "Stato")
    @XmlSchemaType(name = "string")
    protected StatoPendenza stato;
    @XmlElement(name = "ImportoTotale")
    protected BigDecimal importoTotale;
    @XmlElement(name = "InfoPagamento", required = true)
    protected List<PendenzaUpdateStatus.InfoPagamento> infoPagamento;
    @XmlElement(name = "Allegato")
    protected Allegato allegato;

    /**
     * Recupera il valore della proprietà riscossore.
     * 
     * @return
     *     possible object is
     *     {@link Riscossore }
     *     
     */
    public Riscossore getRiscossore() {
        return riscossore;
    }

    /**
     * Imposta il valore della proprietà riscossore.
     * 
     * @param value
     *     allowed object is
     *     {@link Riscossore }
     *     
     */
    public void setRiscossore(Riscossore value) {
        this.riscossore = value;
    }

    /**
     * Recupera il valore della proprietà dataModificaEnte.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDataModificaEnte() {
        return dataModificaEnte;
    }

    /**
     * Imposta il valore della proprietà dataModificaEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDataModificaEnte(Object value) {
        this.dataModificaEnte = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link StatoPendenza }
     *     
     */
    public StatoPendenza getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPendenza }
     *     
     */
    public void setStato(StatoPendenza value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della proprietà importoTotale.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    /**
     * Imposta il valore della proprietà importoTotale.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotale(BigDecimal value) {
        this.importoTotale = value;
    }

    /**
     * Gets the value of the infoPagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoPagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoPagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PendenzaUpdateStatus.InfoPagamento }
     * 
     * 
     */
    public List<PendenzaUpdateStatus.InfoPagamento> getInfoPagamento() {
        if (infoPagamento == null) {
            infoPagamento = new ArrayList<PendenzaUpdateStatus.InfoPagamento>();
        }
        return this.infoPagamento;
    }

    /**
     * Recupera il valore della proprietà allegato.
     * 
     * @return
     *     possible object is
     *     {@link Allegato }
     *     
     */
    public Allegato getAllegato() {
        return allegato;
    }

    /**
     * Imposta il valore della proprietà allegato.
     * 
     * @param value
     *     allowed object is
     *     {@link Allegato }
     *     
     */
    public void setAllegato(Allegato value) {
        this.allegato = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.UpdateStatus"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.UpdateStatus" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dettaglioPagamento"
    })
    public static class InfoPagamento
        extends InfoPagamentoUpdateStatus
    {

        @XmlElement(name = "DettaglioPagamento", required = true)
        protected List<DettaglioPagamentoUpdateStatus> dettaglioPagamento;

        /**
         * Gets the value of the dettaglioPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dettaglioPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDettaglioPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DettaglioPagamentoUpdateStatus }
         * 
         * 
         */
        public List<DettaglioPagamentoUpdateStatus> getDettaglioPagamento() {
            if (dettaglioPagamento == null) {
                dettaglioPagamento = new ArrayList<DettaglioPagamentoUpdateStatus>();
            }
            return this.dettaglioPagamento;
        }

    }

}
