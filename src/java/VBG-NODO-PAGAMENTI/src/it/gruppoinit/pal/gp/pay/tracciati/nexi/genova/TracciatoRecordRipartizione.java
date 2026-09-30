package it.gruppoinit.pal.gp.pay.tracciati.nexi.genova;

import java.util.Date;

import it.gruppoinit.pal.gp.pay.tracciati.IntegerRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.StringRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecord;

public class TracciatoRecordRipartizione extends TracciatoRecord {

    public static enum CAMPI_RIPARTIZIONE {
	tipo_operazione,
	tipo_codice_ente,
	codice_ente,
	tipologia_entrata,
	anno_debito,
	identificativo_debito,
	numero_rata,
	identificativo_rata,
	tipo_ripartizione,
	codice_ripartizione,
	anno_riferimento_ripartizione,
	numero_sub_accertamento,
	importo_per_ripartizione,
	flag_bollo,
	filler,
	versione_specifiche
    }

    public TracciatoRecordRipartizione() {

	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.tipo_operazione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.tipo_codice_ente.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.codice_ente.name(), 8));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.tipologia_entrata.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_RIPARTIZIONE.anno_debito.name(), 4));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.identificativo_debito.name(), 20));
	this.addProperty(new IntegerRecordProperty(CAMPI_RIPARTIZIONE.numero_rata.name(), 2));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.identificativo_rata.name(), 20));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.tipo_ripartizione.name(), 1));
	this.addProperty(new IntegerRecordProperty(CAMPI_RIPARTIZIONE.codice_ripartizione.name(), 9));
	this.addProperty(new IntegerRecordProperty(CAMPI_RIPARTIZIONE.anno_riferimento_ripartizione.name(), 4));
	this.addProperty(new IntegerRecordProperty(CAMPI_RIPARTIZIONE.numero_sub_accertamento.name(), 4));
	this.addProperty(new IntegerRecordProperty(CAMPI_RIPARTIZIONE.importo_per_ripartizione.name(), 15));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.flag_bollo.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.filler.name(), 85));
	this.addProperty(new StringRecordProperty(CAMPI_RIPARTIZIONE.versione_specifiche.name(), 5));
    }

    public static void main(String[] args) {

	TracciatoRecordRipartizione trl = new TracciatoRecordRipartizione();
	trl.setValue(CAMPI_RIPARTIZIONE.tipo_operazione.name(), "I");
	trl.setValue(CAMPI_RIPARTIZIONE.codice_ente.name(), "ProvaMIP");
	trl.setValue(CAMPI_RIPARTIZIONE.anno_debito.name(), 2020);
	trl.setValue(CAMPI_RIPARTIZIONE.numero_rata.name(), 0);
	trl.setValue(CAMPI_RIPARTIZIONE.importo_per_ripartizione.name(), 123450);
	trl.setValue(CAMPI_RIPARTIZIONE.versione_specifiche.name(), "04.05");
	System.out.println(trl.writeRecord());
    }
}
