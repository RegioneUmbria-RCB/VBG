//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ListaTerminaliPOS" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Contesto" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stContestoPos" minOccurs="0"/>
 *                   &lt;element name="IdPos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DescrizionePos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listaTerminaliPOS"
})
@XmlRootElement(name = "RispostaListaTerminaliPOS")
public class RispostaListaTerminaliPOS {

    @XmlElement(name = "ListaTerminaliPOS")
    protected List<RispostaListaTerminaliPOS.ListaTerminaliPOS> listaTerminaliPOS;

    /**
     * Gets the value of the listaTerminaliPOS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaTerminaliPOS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaTerminaliPOS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RispostaListaTerminaliPOS.ListaTerminaliPOS }
     * 
     * 
     */
    public List<RispostaListaTerminaliPOS.ListaTerminaliPOS> getListaTerminaliPOS() {
        if (listaTerminaliPOS == null) {
            listaTerminaliPOS = new ArrayList<RispostaListaTerminaliPOS.ListaTerminaliPOS>();
        }
        return this.listaTerminaliPOS;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Contesto" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stContestoPos" minOccurs="0"/>
     *         &lt;element name="IdPos" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DescrizionePos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "contesto",
        "idPos",
        "descrizionePos"
    })
    public static class ListaTerminaliPOS {

        @XmlElement(name = "Contesto")
        @XmlSchemaType(name = "string")
        protected StContestoPos contesto;
        @XmlElement(name = "IdPos", required = true)
        protected String idPos;
        @XmlElement(name = "DescrizionePos")
        protected String descrizionePos;

        /**
         * Recupera il valore della proprietà contesto.
         * 
         * @return
         *     possible object is
         *     {@link StContestoPos }
         *     
         */
        public StContestoPos getContesto() {
            return contesto;
        }

        /**
         * Imposta il valore della proprietà contesto.
         * 
         * @param value
         *     allowed object is
         *     {@link StContestoPos }
         *     
         */
        public void setContesto(StContestoPos value) {
            this.contesto = value;
        }

        /**
         * Recupera il valore della proprietà idPos.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdPos() {
            return idPos;
        }

        /**
         * Imposta il valore della proprietà idPos.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdPos(String value) {
            this.idPos = value;
        }

        /**
         * Recupera il valore della proprietà descrizionePos.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizionePos() {
            return descrizionePos;
        }

        /**
         * Imposta il valore della proprietà descrizionePos.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizionePos(String value) {
            this.descrizionePos = value;
        }

    }

}
