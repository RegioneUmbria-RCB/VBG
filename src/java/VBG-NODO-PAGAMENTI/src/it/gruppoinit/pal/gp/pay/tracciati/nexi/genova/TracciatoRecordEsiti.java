package it.gruppoinit.pal.gp.pay.tracciati.nexi.genova;

import java.util.Date;

import it.gruppoinit.pal.gp.pay.tracciati.DateRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.IntegerRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.StringRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecord;

public class TracciatoRecordEsiti extends TracciatoRecord {

    public static enum CAMPI_ESITO {
	stato_rata,
	filler_0,
	tipo_codice_ente,
	codice_ente,
	tipologia_entrata,
	anno_debito,
	identificativo_debito,
	numero_rata,
	identificativo_rata,
	importo_nominale_rata,
	importo_spese_supplementari_rata,
	importo_arrotondato,
	importo_bollo,
	importo_commissioni,
	importo_spese_invio_quietanza,
	importo_spese_varie,
	importo_totale_pagato,
	canale_pagamento,
	metodo_pagamento,
	dettaglio_metodo_pagamento,
	codice_identificativo_documento_pagamento,
	filler_1,
	identificativo_operazione,
	data_ora_operazione,
	identificativo_transazione,
	data_ora_transazione,
	identificativo_autorizzazione,
	data_ora_autorizzazione,
	causale_banca,
	data_accredito,
	cf_pagante,
	cognome_pagante,
	nome_pagante,
	modalita_predisposizione_attestazione,
	cognome_recapito_attestazione,
	nome_recapito_attestazione,
	presso_recapito_attestazione,
	indirizzo_recapito_attestazione,
	civico_recapito_attestazione,
	sub_recapito_attestazione,
	cap_recapito_attestazione,
	comune_recapito_attestazione,
	sigla_provincia_recapito_attestazione,
	nazione_recapito_attestazione,
	email_recapito_attestazione,
	causale_pagamento, 
	note_pagamento,
	quantita,
	data_inizio_periodo,
	data_fine_periodo,
	codice_spedizione,
	data_spedizione,
	iuv,
	codice_avviso,
	filler_2,
	versione_specifiche
    }

    public TracciatoRecordEsiti() {

	this.addProperty(new StringRecordProperty(CAMPI_ESITO.stato_rata.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.filler_0.name(), 9));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.tipo_codice_ente.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.codice_ente.name(), 8));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.tipologia_entrata.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.anno_debito.name(), 4));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.identificativo_debito.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.numero_rata.name(), 2));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.identificativo_rata.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_nominale_rata.name(), 15));//start at index 85
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_spese_supplementari_rata.name(), 15));//start 100
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_arrotondato.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_bollo.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_commissioni.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_spese_invio_quietanza.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_spese_varie.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.importo_totale_pagato.name(), 15));//start 190
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.canale_pagamento.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.metodo_pagamento.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.dettaglio_metodo_pagamento.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.codice_identificativo_documento_pagamento.name(), 18));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.filler_1.name(), 21));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.identificativo_operazione.name(), 50));
	this.addProperty(new DateRecordProperty(CAMPI_ESITO.data_ora_operazione.name(), 19, "yyyy-MM-dd'T'HH:mm:ss"));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.identificativo_transazione.name(), 50));
	this.addProperty(new DateRecordProperty(CAMPI_ESITO.data_ora_transazione.name(), 19, "yyyy-MM-dd'T'HH:mm:ss"));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.identificativo_autorizzazione.name(), 50));
	this.addProperty(new DateRecordProperty(CAMPI_ESITO.data_ora_autorizzazione.name(), 19, "yyyy-MM-dd'T'HH:mm:ss"));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.causale_banca.name(), 5));
	this.addProperty(new DateRecordProperty(CAMPI_ESITO.data_accredito.name(), 10, "yyyy-MM-dd"));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.cf_pagante.name(), 16));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.cognome_pagante.name(), 40));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.nome_pagante.name(), 40));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.modalita_predisposizione_attestazione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.cognome_recapito_attestazione.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.nome_recapito_attestazione.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.presso_recapito_attestazione.name(), 40));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.indirizzo_recapito_attestazione.name(), 40));
	this.addProperty(new IntegerRecordProperty(CAMPI_ESITO.civico_recapito_attestazione.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.sub_recapito_attestazione.name(), 4));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.cap_recapito_attestazione.name(), 9));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.comune_recapito_attestazione.name(), 40));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.sigla_provincia_recapito_attestazione.name(), 2));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.nazione_recapito_attestazione.name(), 40));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.email_recapito_attestazione.name(), 64));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.causale_pagamento.name(), 255));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.note_pagamento.name(), 255));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.quantita.name(), 80));
	this.addProperty(new DateRecordProperty(CAMPI_ESITO.data_inizio_periodo.name(), 10, "yyyy-MM-dd"));
	this.addProperty(new DateRecordProperty(CAMPI_ESITO.data_fine_periodo.name(), 10, "yyyy-MM-dd"));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.codice_spedizione.name(), 15));
	this.addProperty(new DateRecordProperty(CAMPI_ESITO.data_spedizione.name(), 10, "yyyy-MM-dd"));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.iuv.name(), 35));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.codice_avviso.name(), 18));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.filler_2.name(), 425));
	this.addProperty(new StringRecordProperty(CAMPI_ESITO.versione_specifiche.name(), 5));
    }

    public static void main(String[] args) {

	TracciatoRecordEsiti trl = new TracciatoRecordEsiti();
	trl.setValue(CAMPI_ESITO.stato_rata.name(), "P");
	trl.setValue(CAMPI_ESITO.codice_ente.name(), "ProvaMIP");
	trl.setValue(CAMPI_ESITO.anno_debito.name(), 2020);
	trl.setValue(CAMPI_ESITO.numero_rata.name(), 0);
	trl.setValue(CAMPI_ESITO.importo_totale_pagato.name(), 133330);
	trl.setValue(CAMPI_ESITO.data_ora_operazione.name(), new Date());
	trl.setValue(CAMPI_ESITO.data_ora_transazione.name(), new Date());
	trl.setValue(CAMPI_ESITO.data_ora_autorizzazione.name(), new Date());
	trl.setValue(CAMPI_ESITO.data_accredito.name(), new Date());
	trl.setValue(CAMPI_ESITO.data_inizio_periodo.name(), new Date());
	trl.setValue(CAMPI_ESITO.cf_pagante.name(), "1234567890123456");
	trl.setValue(CAMPI_ESITO.nome_pagante.name(), "pippo");
	trl.setValue(CAMPI_ESITO.iuv.name(), "00054000023479");
	trl.setValue(CAMPI_ESITO.codice_avviso.name(), "100054000023479");
	trl.setValue(CAMPI_ESITO.versione_specifiche.name(), "04.05");
	System.out.println(trl.writeRecord());
    }
}
