package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TracciatoDebitoBassilichi {

    private List<ProprietaTracciatoDebitoBean> proprieta = new ArrayList<ProprietaTracciatoDebitoBean>();

    public TracciatoDebitoBassilichi() {

	buildProprieta();
    }

    public List<ProprietaTracciatoDebitoBean> getProprieta() {

	return proprieta;
    }

    private void buildProprieta() {

	int pos = 0;
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.tipo_operazione, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.tipo_codice_ente, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_ente, 8, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.tipologia_entrata, 20, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.anno_debito, 4, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.identificativo_debito, 20, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.data_emissione_debito, 10, "yyyy-MM-dd",
		ProprietaTracciatoDebitoBean.TYPE.DATE));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.numero_pratica_protocollo, 20, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.tipo_codice_intestatario, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_intestatario, 16, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.cognome_nome_intestatario, 60, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.tipo_codice_debitore, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_debitore, 16, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_fiscale_debitore, 16, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.prima_parte_denominazione_debitore, 30, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.seconda_parte_denominazione_debitore, 30, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.terza_parte_denominazione_debitore, 30, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.indirizzo_debitore, 30, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.completamento_indirizzo_debitore, 28, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.cap_debitore, 5, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.comune_provincia_debitore, 25, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_istat_debitore, 6, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.sigla_provincia_debitore, 2, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_paese_debitore, 2, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.importo_totale, 15, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.modalita_spedizione, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.flag_pagamento, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.flag_rateizzazione, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.numero_rate, 2, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.flag_ripartizione, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.flag_accorpamento, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.documenti_pagamento_da_generare, 5, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.descrizione, 2200, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_abi_conto_debitore, 5, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.codice_cab_conto_debitore, 5, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.conto_debitore, 12, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.nome_documento_debito, 33, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.numero_facciate_documento_debito, 3, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.flag_presenza_disposizione, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.flag_presenza_indirizzo, 1, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.nome_documento_allegato, 33, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.numero_facciate_documento_allegato, 3, null,
		ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.indirizzo_email_debitore, 80, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.identificativo_lotto, 20, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.causale_versamento, 100, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.filler, 1090, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
	proprieta.add(pos++, new ProprietaTracciatoDebitoBean(ProprietaTracciatoDebitoBean.CAMPI.versione_specifiche, 5, null,
		ProprietaTracciatoDebitoBean.TYPE.STRING));
    }

    public String writeRiga(boolean appendNewLine) {

	StringBuffer sb = new StringBuffer();
	for (ProprietaTracciatoDebitoBean ptb : proprieta) {
	    int len = ptb.getLength();
	    String valore = StringUtils.defaultString(ptb.getValore());
	    valore = StringUtils.left(valore, len);
	    if (ptb.getType().equals(ProprietaTracciatoDebitoBean.TYPE.NUMBER_PAD)) {
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
