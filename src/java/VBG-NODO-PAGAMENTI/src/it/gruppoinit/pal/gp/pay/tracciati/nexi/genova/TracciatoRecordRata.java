package it.gruppoinit.pal.gp.pay.tracciati.nexi.genova;

import java.util.Date;

import it.gruppoinit.pal.gp.pay.tracciati.DateRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.IntegerRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.StringRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecord;

public class TracciatoRecordRata extends TracciatoRecord {

    public static enum CAMPI_RATA {
	tipo_operazione,
	tipo_codice_ente,
	codice_ente,
	tipologia_entrata,
	anno_debito,
	identificativo_debito, //  
	numero_rata,
	identificativo_rata,
	data_scadenza,
	descrizione_rata,
	importo_da_pagare, //
	importo_nominale,
	importo_spese_supplementari,
	descrizione_spese_supplementari,
	flag_pagabile,
	codice_identificativo_mav, //
	filler,
	versione_specifiche
    }

    public TracciatoRecordRata() {

	this.addProperty(new StringRecordProperty(CAMPI_RATA.tipo_operazione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.tipo_codice_ente.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.codice_ente.name(), 8));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.tipologia_entrata.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_RATA.anno_debito.name(), 4));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.identificativo_debito.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_RATA.numero_rata.name(), 2));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.identificativo_rata.name(), 20));
	this.addProperty(new DateRecordProperty(CAMPI_RATA.data_scadenza.name(), 10, "yyyy-MM-dd"));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.descrizione_rata.name(), 80));
	this.addProperty(new IntegerRecordProperty(CAMPI_RATA.importo_da_pagare.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_RATA.importo_nominale.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_RATA.importo_spese_supplementari.name(), 15));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.descrizione_spese_supplementari.name(), 80));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.flag_pagabile.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.codice_identificativo_mav.name(), 12));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.filler.name(), 91));
	this.addProperty(new StringRecordProperty(CAMPI_RATA.versione_specifiche.name(), 5));
    }

    public static void main(String[] args) {

	TracciatoRecordRata trl = new TracciatoRecordRata();
	trl.setValue(CAMPI_RATA.tipo_operazione.name(), "I");
	trl.setValue(CAMPI_RATA.codice_ente.name(), "ProvaMIP");
	trl.setValue(CAMPI_RATA.data_scadenza.name(), new Date());
	trl.setValue(CAMPI_RATA.importo_da_pagare.name(), 123450);
	trl.setValue(CAMPI_RATA.versione_specifiche.name(), "04.05");
	System.out.println(trl.writeRecord());
    }
}
