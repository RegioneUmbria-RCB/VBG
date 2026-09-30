package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TracciatoLottoBassilichi {

    private List<ProprietaTracciatoLottoBean> proprieta = new ArrayList<ProprietaTracciatoLottoBean>();

    public TracciatoLottoBassilichi() {

	buildProprieta();
    }

    public List<ProprietaTracciatoLottoBean> getProprieta() {

	return proprieta;
    }

    private void buildProprieta() {

	int pos = 0;
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.tipo_operazione, 1, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.tipo_codice_ente, 1, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.codice_ente, 8, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.tipologia_entrata, 20, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.identificativo_lotto, 20, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.data_creazione_lotto, 10, "yyyy-MM-dd",
		ProprietaTracciatoLottoBean.TYPE.DATE));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.numero_totale_debiti, 10, null,
		ProprietaTracciatoLottoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.numero_totale_rate, 10, null,
		ProprietaTracciatoLottoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.importo_totale_debiti, 15, null,
		ProprietaTracciatoLottoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.importo_totale_rate, 15, null,
		ProprietaTracciatoLottoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.tipologia_documento_di_pag_da_emettere, 20, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.flag_accorpamento_debiti, 1, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.nome_documento_lotto, 33, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.numero_facciate_documento_lotto, 3, null,
		ProprietaTracciatoLottoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.flag_fronte_retro_lotto, 1, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.flag_bianco_nero_colore_lotto, 1, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.tipo_postalizzazione, 1, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.identificativo_vettore, 1, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.prima_parte_denominazione_creditore_mitt, 30, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.seconda_parte_denominazione_creditore_mitt, 30, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.indirizzo_creditore_mittente, 30, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.completamento_indirizzo_creditore_mittente, 28, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.cap_creditore_mittente, 5, null,
		ProprietaTracciatoLottoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.comune_e_provincia_creditore_mittente, 25, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.sigla_provincia_creditore_mittente, 2, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.prima_parte_denominazione_ente, 30, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.seconda_parte_denominazione_ente, 30, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.iban_conto_corrente_postale, 27, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.autorizzazione_bollettino_postale, 35, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.iban_conto_corrente_bancario, 27, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.indirizzo_ente, 30, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.cap_localita_provincia_ente, 36, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.telefono_ente, 20, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.municipio, 3, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.filler, 436, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoLottoBean(ProprietaTracciatoLottoBean.CAMPI.versione_specifiche, 5, null,
		ProprietaTracciatoLottoBean.TYPE.STRING));
    }

    public String writeRiga(boolean appendNewLine) {

	StringBuffer sb = new StringBuffer();
	for (ProprietaTracciatoLottoBean ptb : proprieta) {
	    int len = ptb.getLength();
	    String valore = StringUtils.defaultString(ptb.getValore());
	    valore = StringUtils.left(valore, len);
	    if (ptb.getType().equals(ProprietaTracciatoLottoBean.TYPE.NUMBER_PAD)) {
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
