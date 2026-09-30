package it.gruppoinit.pal.gp.pay.tracciati.nexi.genova;

import java.util.Date;

import it.gruppoinit.pal.gp.pay.tracciati.DateRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.IntegerRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.StringRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecord;


public class TracciatoRecordLotto extends TracciatoRecord {
    
    public static enum CAMPI_LOTTO {
	tipo_operazione,
	tipo_codice_ente,
	codice_ente,
	tipologia_entrata,
	identificativo_lotto, //
	data_creazione_lotto,
	numero_totale_debiti,
	numero_totale_rate,
	importo_totale_debiti,
	importo_totale_rate, //
	tipologia_documento_di_pag_da_emettere,
	flag_accorpamento_debiti,
	nome_documento_lotto,
	numero_facciate_documento_lotto, //
	flag_fronte_retro_lotto,
	flag_bianco_nero_colore_lotto,
	tipo_postalizzazione,
	identificativo_vettore,
	prima_parte_denominazione_creditore_mitt, //
	seconda_parte_denominazione_creditore_mitt,
	indirizzo_creditore_mittente,
	completamento_indirizzo_creditore_mittente,
	cap_creditore_mittente, //
	comune_e_provincia_creditore_mittente,
	sigla_provincia_creditore_mittente,
	prima_parte_denominazione_ente,
	seconda_parte_denominazione_ente, //
	iban_conto_corrente_postale,
	autorizzazione_bollettino_postale,
	iban_conto_corrente_bancario,
	indirizzo_ente,
	cap_localita_provincia_ente, //
	telefono_ente,
	municipio,
	filler,
	versione_specifiche
    }


    public TracciatoRecordLotto() {
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.tipo_operazione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.tipo_codice_ente.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.codice_ente.name(), 8));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.tipologia_entrata.name(), 20));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.identificativo_lotto.name(), 20));
	this.addProperty(new DateRecordProperty(CAMPI_LOTTO.data_creazione_lotto.name(), 10, "yyyy-MM-dd"));
	this.addProperty(new IntegerRecordProperty(CAMPI_LOTTO.numero_totale_debiti.name(), 10));
	this.addProperty(new IntegerRecordProperty(CAMPI_LOTTO.numero_totale_rate.name(), 10));
	this.addProperty(new IntegerRecordProperty(CAMPI_LOTTO.importo_totale_debiti.name(), 15));
	this.addProperty(new IntegerRecordProperty(CAMPI_LOTTO.importo_totale_rate.name(), 15));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.tipologia_documento_di_pag_da_emettere.name(), 20));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.flag_accorpamento_debiti.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.nome_documento_lotto.name(), 33));
	this.addProperty(new IntegerRecordProperty(CAMPI_LOTTO.numero_facciate_documento_lotto.name(), 3));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.flag_fronte_retro_lotto.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.flag_bianco_nero_colore_lotto.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.tipo_postalizzazione.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.identificativo_vettore.name(), 1));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.prima_parte_denominazione_creditore_mitt.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.seconda_parte_denominazione_creditore_mitt.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.indirizzo_creditore_mittente.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.completamento_indirizzo_creditore_mittente.name(), 28));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.cap_creditore_mittente.name(), 5));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.comune_e_provincia_creditore_mittente.name(), 25));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.sigla_provincia_creditore_mittente.name(), 2));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.prima_parte_denominazione_ente.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.seconda_parte_denominazione_ente.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.iban_conto_corrente_postale.name(), 27));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.autorizzazione_bollettino_postale.name(), 35));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.iban_conto_corrente_bancario.name(), 27));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.indirizzo_ente.name(), 30));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.cap_localita_provincia_ente.name(), 36));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.telefono_ente.name(), 20));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.municipio.name(), 3));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.filler.name(), 436));
	this.addProperty(new StringRecordProperty(CAMPI_LOTTO.versione_specifiche.name(), 5));
    }
    
    public static void main(String[] args) {
	
	TracciatoRecordLotto trl = new TracciatoRecordLotto();
	trl.setValue(CAMPI_LOTTO.tipo_operazione.name(), "I");
	trl.setValue(CAMPI_LOTTO.codice_ente.name(), "ProvaMIP");
	trl.setValue(CAMPI_LOTTO.data_creazione_lotto.name(), new Date());
	trl.setValue(CAMPI_LOTTO.importo_totale_debiti.name(), 12345);
	trl.setValue(CAMPI_LOTTO.versione_specifiche.name(), "04.05");
	System.out.println(trl.writeRecord());
    }
}
