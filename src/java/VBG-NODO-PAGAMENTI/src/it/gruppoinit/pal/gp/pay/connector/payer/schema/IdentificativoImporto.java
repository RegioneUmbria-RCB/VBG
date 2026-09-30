package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 */
@XmlType(name = "IdentificativoImporto")
@XmlEnum
public enum IdentificativoImporto {

    IC1,
    IC2,
    IC3,
    IC4,
    IC5;

    public String value() {

	return name();
    }

    public static IdentificativoImporto fromValue(String v) {

	return valueOf(v);
    }
}
