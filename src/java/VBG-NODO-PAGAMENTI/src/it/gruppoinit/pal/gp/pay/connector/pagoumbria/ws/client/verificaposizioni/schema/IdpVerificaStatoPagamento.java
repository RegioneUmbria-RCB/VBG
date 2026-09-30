
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema;

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
 *                   &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}idPagamento" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="richiestaInformazioniPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
@XmlRootElement(name = "IdpVerificaStatoPagamento")
public class IdpVerificaStatoPagamento {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpVerificaStatoPagamento.IdpBody idpBody;
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
     *     {@link IdpVerificaStatoPagamento.IdpBody }
     *     
     */
    public IdpVerificaStatoPagamento.IdpBody getIdpBody() {
        return idpBody;
    }

    /**
     * Imposta il valore della proprietà idpBody.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpVerificaStatoPagamento.IdpBody }
     *     
     */
    public void setIdpBody(IdpVerificaStatoPagamento.IdpBody value) {
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
     *         &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}idPagamento" maxOccurs="unbounded"/&gt;
     *         &lt;element name="richiestaInformazioniPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
        "idPagamento",
        "richiestaInformazioniPagamento"
    })
    public static class IdpBody {

        @XmlElement(name = "IdPagamento", required = true)
        protected List<IdPagamento> idPagamento;
        protected Boolean richiestaInformazioniPagamento;

        /**
         * Gets the value of the idPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the idPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIdPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdPagamento }
         * 
         * 
         */
        public List<IdPagamento> getIdPagamento() {
            if (idPagamento == null) {
                idPagamento = new ArrayList<IdPagamento>();
            }
            return this.idPagamento;
        }

        /**
         * Recupera il valore della proprietà richiestaInformazioniPagamento.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isRichiestaInformazioniPagamento() {
            return richiestaInformazioniPagamento;
        }

        /**
         * Imposta il valore della proprietà richiestaInformazioniPagamento.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRichiestaInformazioniPagamento(Boolean value) {
            this.richiestaInformazioniPagamento = value;
        }

    }

}
