
package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlType(name = "Commit")
@XmlEnum
public enum Commit {

    OK,
    NOK;

    public String value() {
        return name();
    }

    public static Commit fromValue(String v) {
        return valueOf(v);
    }

}
