package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public enum SceltaTipoMailAnagrafeEnum {

    SOLO_PEC("Usa solo caselle PEC"),
    PEC_O_MAIL("Usa PEC se disponibile altrimenti mail"),
    SOLO_MAIL("Usa solo mail");

    private final String descrizione;

    private SceltaTipoMailAnagrafeEnum(String s) {

	descrizione = s;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public static List<CodiceDescrizioneBean> asList() {

	List<CodiceDescrizioneBean> ret = new ArrayList<CodiceDescrizioneBean>(3);
	ret.add(0, new CodiceDescrizioneBean(SOLO_PEC.name(), SOLO_PEC.descrizione));
	ret.add(1, new CodiceDescrizioneBean(PEC_O_MAIL.name(), PEC_O_MAIL.descrizione));
	ret.add(2, new CodiceDescrizioneBean(SOLO_MAIL.name(), SOLO_MAIL.descrizione));
	return ret;
    }

    public static SceltaTipoMailAnagrafeEnum fromDefault() {

	return SceltaTipoMailAnagrafeEnum.PEC_O_MAIL;
    }

    public static SceltaTipoMailAnagrafeEnum fromName(String name) {

	if (StringUtils.isBlank(name)) {
	    return SceltaTipoMailAnagrafeEnum.fromDefault();
	}
	for (SceltaTipoMailAnagrafeEnum c : SceltaTipoMailAnagrafeEnum.values()) {
	    if (c.name().equals(name)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Nessun enumeratore trovato con il nome " + name);
    }
}
