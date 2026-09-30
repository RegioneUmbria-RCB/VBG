package it.gruppoinit.pal.gp.core.service.helper;

public class ProprietaTracciatoDebitoBean {

    public static enum CAMPI {
	tipo_operazione, tipo_codice_ente, codice_ente, tipologia_entrata, anno_debito, identificativo_debito, //  
	data_emissione_debito, numero_pratica_protocollo, tipo_codice_intestatario, codice_intestatario, cognome_nome_intestatario, //
	tipo_codice_debitore, codice_debitore, codice_fiscale_debitore, prima_parte_denominazione_debitore, seconda_parte_denominazione_debitore, //
	terza_parte_denominazione_debitore, indirizzo_debitore, completamento_indirizzo_debitore, cap_debitore, comune_provincia_debitore, codice_istat_debitore, //
	sigla_provincia_debitore, codice_paese_debitore, importo_totale, modalita_spedizione, flag_pagamento, flag_rateizzazione, numero_rate, flag_ripartizione, flag_accorpamento, //
	documenti_pagamento_da_generare, descrizione, codice_abi_conto_debitore, codice_cab_conto_debitore, conto_debitore, nome_documento_debito, numero_facciate_documento_debito, //
	flag_presenza_disposizione, flag_presenza_indirizzo, nome_documento_allegato, numero_facciate_documento_allegato, indirizzo_email_debitore, identificativo_lotto, //
	causale_versamento, filler, versione_specifiche,
    }

    public static enum TYPE {
	NUMBER_PAD, STRING, DATE
    }

    public ProprietaTracciatoDebitoBean(CAMPI name, int length, String format, TYPE type) {

	super();
	this.name = name;
	this.length = length;
	this.format = format;
	this.type = type;
    }

    private CAMPI name;
    private int length;
    private String format;
    private TYPE type;
    private String valore;

    public CAMPI getName() {

	return name;
    }

    public void setName(CAMPI name) {

	this.name = name;
    }

    public int getLength() {

	return length;
    }

    public void setLength(int length) {

	this.length = length;
    }

    public String getFormat() {

	return format;
    }

    public TYPE getType() {

	return type;
    }

    public void setType(TYPE type) {

	this.type = type;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
