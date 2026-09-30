package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 */
@XmlType(name = "CircuitoAutorizzativo")
@XmlEnum
public enum CircuitoAutorizzativo {

    CCRED,
    HBANK,
    RID,
    MAV;

    public String value() {

	return name();
    }

    public static CircuitoAutorizzativo fromValue(String v) {

	return valueOf(v);
    }
}
