package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RataBean {

    private Integer lunghezzaMaxDescrizione;
    private List<String> riferimentoClientOnereDaInserire;
    private List<ImportoBean> importi;
    private String descrizione;
    private int numerorata;
    private Date dataScadenza;

    public static RataBean fromImportoSingolo(List<String> riferimentoClientOnereDaInserire, ImportoBean importo, String descrizione, int numerorata,
	    Date dataScadenza) {

	List<ImportoBean> importi = new ArrayList<ImportoBean>();
	importi.add(importo);
	return new RataBean(riferimentoClientOnereDaInserire, importi, descrizione, numerorata, dataScadenza);
    }

    public RataBean(List<String> riferimentoClientOnereDaInserire, List<ImportoBean> importi, String descrizione, int numerorata, Date dataScadenza) {

	this.riferimentoClientOnereDaInserire = riferimentoClientOnereDaInserire;
	this.importi = importi;
	this.descrizione = descrizione;
	this.lunghezzaMaxDescrizione = 200;
	this.numerorata = numerorata;
	this.dataScadenza = dataScadenza;
    }

    public List<String> getRiferimentoClientOnereDaInserire() {

	return riferimentoClientOnereDaInserire;
    }

    public List<ImportoBean> getImporti() {

	return importi;
    }

    public String getDescrizione() {

	if (!StringUtils.isEmpty(this.descrizione) && this.descrizione.length() > this.lunghezzaMaxDescrizione)
	    return this.descrizione.substring(0, this.lunghezzaMaxDescrizione);
	return descrizione;
    }

    public Integer getLunghezzaMaxDescrizione() {

	return lunghezzaMaxDescrizione;
    }

    public void setLunghezzaMaxDescrizione(Integer lunghezzaMaxDescrizione) {

	this.lunghezzaMaxDescrizione = lunghezzaMaxDescrizione;
    }

    public int getNumerorata() {

	return numerorata;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public BigDecimal getImportoTotale() {

	if (this.importi == null || this.importi.isEmpty()) {
	    return BigDecimal.ZERO;
	}
	BigDecimal retVal = BigDecimal.ZERO;
	for (ImportoBean importo : this.importi) {
	    retVal = retVal.add(importo.getImporto());
	}
	return retVal;
    }
}
