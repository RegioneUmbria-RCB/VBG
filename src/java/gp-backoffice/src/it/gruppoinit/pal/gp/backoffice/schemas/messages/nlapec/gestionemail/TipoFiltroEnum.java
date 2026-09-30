package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

import javax.xml.bind.annotation.XmlEnum;


@XmlEnum
public enum TipoFiltroEnum {
    
    DATA_DA, DATA_A, LETTI, NON_LETTI;
    
    public String value() {

	return name();
    }

    public static TipoFiltroEnum fromValue(String v) {

	return valueOf(v);
    }
}
