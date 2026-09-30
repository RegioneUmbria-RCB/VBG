package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.features.contabilita.AliquotaIVA;
import it.gruppoinit.pal.gp.core.features.contabilita.ImportoIvato;

public class ContoImportoTotaleHelper {

    private Integer idConto;
    private ImportoIvato importoIvato;

    //ImportoIvato importoIvato = new AliquotaIVA(conto.getIva()).applica(costiAttivi.get(idConto). );
    public ContoImportoTotaleHelper(Integer idConto, BigDecimal importoSenzaIVA, Integer iva) {

	this.idConto = idConto;
	this.importoIvato = new AliquotaIVA(iva).applica(importoSenzaIVA);
    }

    public Integer getIdConto() {

	return idConto;
    }

    public ImportoIvato getImportoIvato() {

	return importoIvato;
    }
}
