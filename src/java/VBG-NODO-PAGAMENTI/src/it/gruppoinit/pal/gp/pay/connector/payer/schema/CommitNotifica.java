
package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlType(name = "CommitNotifica")
@XmlEnum
public enum CommitNotifica {

    S,
    N;

    public String value() {
        return name();
    }

    public static CommitNotifica fromValue(String v) {
        return valueOf(v);
    }

}
