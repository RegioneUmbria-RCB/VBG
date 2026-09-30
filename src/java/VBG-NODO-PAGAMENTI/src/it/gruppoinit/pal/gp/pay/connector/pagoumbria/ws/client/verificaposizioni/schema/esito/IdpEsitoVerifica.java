
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.IdpHeader;


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
 *         &lt;element name="IdpHeader" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpHeader"/&gt;
 *         &lt;element name="IdpBody"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="StatoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}StatoPagamentoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="InformazioniPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InformazioniPagamentoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="Esito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Esito" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Versione" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idpHeader",
    "idpBody"
})
@XmlRootElement(name = "IdpEsitoVerifica")
public class IdpEsitoVerifica {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpEsitoVerifica.IdpBody idpBody;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Recupera il valore della proprietà idpHeader.
     * 
     * @return
     *     possible object is
     *     {@link IdpHeader }
     *     
     */
    public IdpHeader getIdpHeader() {
        return idpHeader;
    }

    /**
     * Imposta il valore della proprietà idpHeader.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpHeader }
     *     
     */
    public void setIdpHeader(IdpHeader value) {
        this.idpHeader = value;
    }

    /**
     * Recupera il valore della proprietà idpBody.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoVerifica.IdpBody }
     *     
     */
    public IdpEsitoVerifica.IdpBody getIdpBody() {
        return idpBody;
    }

    /**
     * Imposta il valore della proprietà idpBody.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoVerifica.IdpBody }
     *     
     */
    public void setIdpBody(IdpEsitoVerifica.IdpBody value) {
        this.idpBody = value;
    }

    /**
     * Recupera il valore della proprietà versione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersione() {
        return versione;
    }

    /**
     * Imposta il valore della proprietà versione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersione(String value) {
        this.versione = value;
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
     *         &lt;element name="StatoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}StatoPagamentoType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="InformazioniPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InformazioniPagamentoType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="Esito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Esito" minOccurs="0"/&gt;
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
        "statoPagamento",
        "informazioniPagamento",
        "esito"
    })
    public static class IdpBody {

        @XmlElement(name = "StatoPagamento")
        protected List<StatoPagamentoType> statoPagamento;
        @XmlElement(name = "InformazioniPagamento")
        protected List<InformazioniPagamentoType> informazioniPagamento;
        @XmlElement(name = "Esito")
        protected Esito esito;

        /**
         * Gets the value of the statoPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the statoPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStatoPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StatoPagamentoType }
         * 
         * 
         */
        public List<StatoPagamentoType> getStatoPagamento() {
            if (statoPagamento == null) {
                statoPagamento = new ArrayList<StatoPagamentoType>();
            }
            return this.statoPagamento;
        }

        /**
         * Gets the value of the informazioniPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the informazioniPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInformazioniPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InformazioniPagamentoType }
         * 
         * 
         */
        public List<InformazioniPagamentoType> getInformazioniPagamento() {
            if (informazioniPagamento == null) {
                informazioniPagamento = new ArrayList<InformazioniPagamentoType>();
            }
            return this.informazioniPagamento;
        }

        /**
         * Recupera il valore della proprietà esito.
         * 
         * @return
         *     possible object is
         *     {@link Esito }
         *     
         */
        public Esito getEsito() {
            return esito;
        }

        /**
         * Imposta il valore della proprietà esito.
         * 
         * @param value
         *     allowed object is
         *     {@link Esito }
         *     
         */
        public void setEsito(Esito value) {
            this.esito = value;
        }

    }

}
