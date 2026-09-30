
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.EsitoInserimentoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.ResponseType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.csi.it/epay/epaywso/types}ResponseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoInserimento" type="{http://www.csi.it/epay/epaywso/types}EsitoInserimentoType" minOccurs="0"/&gt;
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
    "esitoInserimento"
})
@XmlRootElement(name = "InserisciListaDiCaricoResponse")
public class InserisciListaDiCaricoResponse
    extends ResponseType
{

    @XmlElement(name = "EsitoInserimento")
    protected EsitoInserimentoType esitoInserimento;

    /**
     * Recupera il valore della proprietà esitoInserimento.
     * 
     * @return
     *     possible object is
     *     {@link EsitoInserimentoType }
     *     
     */
    public EsitoInserimentoType getEsitoInserimento() {
        return esitoInserimento;
    }

    /**
     * Imposta il valore della proprietà esitoInserimento.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoInserimentoType }
     *     
     */
    public void setEsitoInserimento(EsitoInserimentoType value) {
        this.esitoInserimento = value;
    }

}
