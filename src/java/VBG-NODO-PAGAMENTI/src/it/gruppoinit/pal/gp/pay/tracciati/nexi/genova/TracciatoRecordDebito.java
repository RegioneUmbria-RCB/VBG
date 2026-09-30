package it.gruppoinit.pal.gp.pay.tracciati.nexi.genova;

import java.util.Date;

import it.gruppoinit.pal.gp.pay.tracciati.DateRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.IntegerRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.StringRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecord;

public class TracciatoRecordDebito extends TracciatoRecord {

    public static enum CAMPI_DEBITO {
	tipo_operazione,
	tipo_codice_ente,
	codice_ente,
	tipologia_entrata,
	anno_debito,
	identificativo_debito, //  
	data_emissione_debito,
	numero_pratica_protocollo,
	tipo_codice_intestatario,
	codice_intestatario,
	cognome_nome_intestatario, //
	tipo_codice_debitore,
	codice_debitore,
	codice_fiscale_debitore,
	prima_parte_denominazione_debitore,
	seconda_parte_denominazione_debitore, //
	terza_parte_denominazione_debitore,
	indirizzo_debitore,
	completamento_indirizzo_debitore,
	cap_debitore,
	comune_provincia_debitore,
	codice_istat_debitore, //
	sigla_provincia_debitore,
	codice_paese_debitore,
	importo_totale,
	modalita_spedizione,
	flag_pagamento,
	flag_rateizzazione,
	numero_rate,
	flag_ripartizione,
	flag_accorpamento, //
	documenti_pagamento_da_generare,
	descrizione,
	codice_abi_conto_debitore,
	codice_cab_conto_debitore,
	conto_debitore,
	nome_documento_debito,
	numero_facciate_documento_debito, //
	flag_presenza_disposizione,
	flag_presenza_indirizzo,
	nome_documento_allegato,
	numero_facciate_documento_allegato,
	indirizzo_email_debitore,
	identificativo_lotto, //
	causale_versamento,
	filler,
	versione_specifiche,
    }

    public TracciatoRecordDebito() {

	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.tipo_operazione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.tipo_codice_ente.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.codice_ente.name(), 8));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.tipologia_entrata.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_DEBITO.anno_debito.name(), 4));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.identificativo_debito.name(), 20));
	this.addProperty(new DateRecordProperty(CAMPI_DEBITO.data_emissione_debito.name(), 10, "yyyy-MM-dd"));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.numero_pratica_protocollo.name(), 20));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.tipo_codice_intestatario.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.codice_intestatario.name(), 16));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.cognome_nome_intestatario.name(), 60));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.tipo_codice_debitore.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.codice_debitore.name(), 16));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.codice_fiscale_debitore.name(), 16));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.prima_parte_denominazione_debitore.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.seconda_parte_denominazione_debitore.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.terza_parte_denominazione_debitore.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.indirizzo_debitore.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.completamento_indirizzo_debitore.name(), 28));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.cap_debitore.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.comune_provincia_debitore.name(), 25));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.codice_istat_debitore.name(), 6));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.sigla_provincia_debitore.name(), 2));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.codice_paese_debitore.name(), 2));
	this.addProperty(new IntegerRecordProperty(CAMPI_DEBITO.importo_totale.name(), 15));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.modalita_spedizione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.flag_pagamento.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.flag_rateizzazione.name(), 1));
	this.addProperty(new IntegerRecordProperty(CAMPI_DEBITO.numero_rate.name(), 2));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.flag_ripartizione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.flag_accorpamento.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.documenti_pagamento_da_generare.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.descrizione.name(), 2200));
	this.addProperty(new IntegerRecordProperty(CAMPI_DEBITO.codice_abi_conto_debitore.name(), 5));
	this.addProperty(new IntegerRecordProperty(CAMPI_DEBITO.codice_cab_conto_debitore.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.conto_debitore.name(), 12));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.nome_documento_debito.name(), 33));
	this.addProperty(new IntegerRecordProperty(CAMPI_DEBITO.numero_facciate_documento_debito.name(), 3));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.flag_presenza_disposizione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.flag_presenza_indirizzo.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.nome_documento_allegato.name(), 33));
	this.addProperty(new IntegerRecordProperty(CAMPI_DEBITO.numero_facciate_documento_allegato.name(), 3));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.indirizzo_email_debitore.name(), 80));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.identificativo_lotto.name(), 20));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.causale_versamento.name(), 100));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.filler.name(), 1090));
	this.addProperty(new StringRecordProperty(CAMPI_DEBITO.versione_specifiche.name(), 5));
    }

    public static void main(String[] args) {

	TracciatoRecordDebito trl = new TracciatoRecordDebito();
	trl.setValue(CAMPI_DEBITO.tipo_operazione.name(), "I");
	trl.setValue(CAMPI_DEBITO.codice_ente.name(), "ProvaMIP");
	trl.setValue(CAMPI_DEBITO.data_emissione_debito.name(), new Date());
	trl.setValue(CAMPI_DEBITO.importo_totale.name(), 435675);
	trl.setValue(CAMPI_DEBITO.numero_rate.name(), 0);
	trl.setValue(CAMPI_DEBITO.indirizzo_email_debitore.name(), "debitore@senza.euro");
	trl.setValue(CAMPI_DEBITO.versione_specifiche.name(), "04.05");
	System.out.println(trl.writeRecord());
    }
}
