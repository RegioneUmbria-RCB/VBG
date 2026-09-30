
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Causali_CBI.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Causali_CBI"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ComunicazioneRicevutaStornoNonAccettabile"/&gt;
 *     &lt;enumeration value="AutorizzazioneRicevutaBancaDomiciliatariaCliente"/&gt;
 *     &lt;enumeration value="AutorizzazioneAttiva"/&gt;
 *     &lt;enumeration value="RevocaAutorizzazioneAddebitoIntestatario"/&gt;
 *     &lt;enumeration value="RevocaAutorizzazioneAddebitoBanca"/&gt;
 *     &lt;enumeration value="VariazioneCoordinateBancarieSottoscrittore"/&gt;
 *     &lt;enumeration value="VariazioneCoordinateBancarieTrasferibilita"/&gt;
 *     &lt;enumeration value="StornoRevocaAutorizzazioneAddebito"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Causali_CBI")
@XmlEnum
public enum CausaliCBI {

    @XmlEnumValue("ComunicazioneRicevutaStornoNonAccettabile")
    COMUNICAZIONE_RICEVUTA_STORNO_NON_ACCETTABILE("ComunicazioneRicevutaStornoNonAccettabile"),
    @XmlEnumValue("AutorizzazioneRicevutaBancaDomiciliatariaCliente")
    AUTORIZZAZIONE_RICEVUTA_BANCA_DOMICILIATARIA_CLIENTE("AutorizzazioneRicevutaBancaDomiciliatariaCliente"),
    @XmlEnumValue("AutorizzazioneAttiva")
    AUTORIZZAZIONE_ATTIVA("AutorizzazioneAttiva"),
    @XmlEnumValue("RevocaAutorizzazioneAddebitoIntestatario")
    REVOCA_AUTORIZZAZIONE_ADDEBITO_INTESTATARIO("RevocaAutorizzazioneAddebitoIntestatario"),
    @XmlEnumValue("RevocaAutorizzazioneAddebitoBanca")
    REVOCA_AUTORIZZAZIONE_ADDEBITO_BANCA("RevocaAutorizzazioneAddebitoBanca"),
    @XmlEnumValue("VariazioneCoordinateBancarieSottoscrittore")
    VARIAZIONE_COORDINATE_BANCARIE_SOTTOSCRITTORE("VariazioneCoordinateBancarieSottoscrittore"),
    @XmlEnumValue("VariazioneCoordinateBancarieTrasferibilita")
    VARIAZIONE_COORDINATE_BANCARIE_TRASFERIBILITA("VariazioneCoordinateBancarieTrasferibilita"),
    @XmlEnumValue("StornoRevocaAutorizzazioneAddebito")
    STORNO_REVOCA_AUTORIZZAZIONE_ADDEBITO("StornoRevocaAutorizzazioneAddebito");
    private final String value;

    CausaliCBI(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CausaliCBI fromValue(String v) {
        for (CausaliCBI c: CausaliCBI.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
