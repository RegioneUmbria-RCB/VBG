package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoTabellaModel;

public class RimborsoBackofficeLogger extends AbstractAbbonamentoLogger {

    public RimborsoBackofficeLogger(AbbonamentoTabellaModel borsellino, String autore, String codiceComune, BigDecimal importo) {

	super(autore);
	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("RIMBORSO BACKOFFICE");
	sb.append("\nImporto: ").append(importo.doubleValue());
	sb.append("\nBorsellino id: ").append(borsellino.getId());
	sb.append("\nNominativo: ").append(borsellino.getNominativo());
	sb.append("\nDescrizione: ").append(borsellino.getDescrizione());
	sb.append("\nCodiceComune: ").append(codiceComune);
	sb.append("\n").append(DELIMITATORE_LOG);
	this.messaggio = sb.toString();
    }

    public static RimborsoBackofficeLogger fromModel(AbbonamentoTabellaModel borsellino, String autore, String codiceComune, BigDecimal importo) {

	return new RimborsoBackofficeLogger(borsellino, autore, codiceComune, importo);
    }
}
