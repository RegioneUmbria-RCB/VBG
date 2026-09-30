package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TracciatoRipartizioneRataBassilichi {

    private List<ProprietaTracciatoRipartizioneRataBean> proprieta = new ArrayList<ProprietaTracciatoRipartizioneRataBean>();

    public TracciatoRipartizioneRataBassilichi() {

	buildProprieta();
    }

    public List<ProprietaTracciatoRipartizioneRataBean> getProprieta() {

	return proprieta;
    }

    private void buildProprieta() {

	int pos = 0;
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.tipo_operazione, 1, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.tipo_codice_ente, 1, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.codice_ente, 8, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.tipologia_entrata, 20, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.anno_debito, 4, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.identificativo_debito, 20, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.numero_rata, 2, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.identificativo_rata, 20, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.tipo_ripartizione, 1, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.codice_ripartizione, 9, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.anno_riferimento_ripartizione,
		4, null, ProprietaTracciatoRipartizioneRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.numero_sub_accertamento, 4,
		null, ProprietaTracciatoRipartizioneRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.importo_per_ripartizione, 15,
		null, ProprietaTracciatoRipartizioneRataBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.flag_bollo, 1, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.filler, 85, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoRipartizioneRataBean(ProprietaTracciatoRipartizioneRataBean.CAMPI.versione_specifiche, 5, null,
		ProprietaTracciatoRipartizioneRataBean.TYPE.STRING));
    }

    public String writeRiga(boolean appendNewLine) {

	StringBuffer sb = new StringBuffer();
	for (ProprietaTracciatoRipartizioneRataBean ptb : proprieta) {
	    int len = ptb.getLength();
	    String valore = StringUtils.defaultString(ptb.getValore());
	    valore = StringUtils.left(valore, len);
	    if (ptb.getType().equals(ProprietaTracciatoRipartizioneRataBean.TYPE.NUMBER_PAD)) {
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
