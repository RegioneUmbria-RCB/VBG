
package it.alveo.ricalcoloaree.sigeprosecurity;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Enumerazione dei Contesti disponibili 
 * 				AMM: Amministrazioni 
 * 				APP: Applicazioni 
 * 				OPE: Utenti di Backoffice 
 * 				UTE: Utenti di Frontoffice
 * 				UTEG: Utenti di Frontoffice Persone giuridiche
 * 
 * <p>Classe Java per ContestoType.</p>
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.</p>
 * <pre>{@code
 * <simpleType name="ContestoType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="AMM"/>
 *     <enumeration value="APP"/>
 *     <enumeration value="OPE"/>
 *     <enumeration value="UTE"/>
 *     <enumeration value="UTEG"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "ContestoType")
@XmlEnum
public enum ContestoType {

    AMM,
    APP,
    OPE,
    UTE,
    UTEG;

    public String value() {
        return name();
    }

    public static ContestoType fromValue(String v) {
        return valueOf(v);
    }

}
