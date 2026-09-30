package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TracciatoRataBassilichi {

    private List<ProprietaTracciatoRataBean> proprieta = new ArrayList<ProprietaTracciatoRataBean>();

    public TracciatoRataBassilichi() {

	buildProprieta();
    }

    public List<ProprietaTracciatoRataBean> getProprieta() {

	return proprieta;
    }

    private void buildProprieta() {

	int pos = 0;
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.tipo_operazione, 1, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.tipo_codice_ente, 1, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.codice_ente, 8, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.tipologia_entrata, 20, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.anno_debito, 4, null,
		ProprietaTracciatoRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.identificativo_debito, 20, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.numero_rata, 2, null,
		ProprietaTracciatoRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.identificativo_rata, 20, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.data_scadenza, 10, "yyyy-MM-dd",
		ProprietaTracciatoRataBean.TYPE.DATE));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.descrizione_rata, 80, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.importo_da_pagare, 15, null,
		ProprietaTracciatoRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.importo_nominale, 15, null,
		ProprietaTracciatoRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.importo_spese_supplementari, 15, null,
		ProprietaTracciatoRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.descrizione_spese_supplementari, 80, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.flag_pagabile, 1, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.codice_identificativo_mav, 12, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++,
		new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.filler, 91, null, ProprietaTracciatoRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRataBean(ProprietaTracciatoRataBean.CAMPI.versione_specifiche, 5, null,
		ProprietaTracciatoRataBean.TYPE.STRING));
    }

    public String writeRiga(boolean appendNewLine) {

	StringBuffer sb = new StringBuffer();
	for (ProprietaTracciatoRataBean ptb : proprieta) {
	    int len = ptb.getLength();
	    String valore = StringUtils.defaultString(ptb.getValore());
	    valore = StringUtils.left(valore, len);
	    if (ptb.getType().equals(ProprietaTracciatoRataBean.TYPE.NUMBER_PAD)) {
		sb.append(StringUtils.leftPad(valore, len, '0'));
	    } else {
		sb.append(StringUtils.rightPad(valore, len, ' '));
	    }
	}
	if (appendNewLine) {
	    sb.append("\n");
	}
	return sb.toString();
    }
}
