package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.math.BigDecimal;
import java.util.Date;

public class ImportoBean {

    private Integer numeroRata;
    private Date dataScadenza;
    private String raggruppamento;
    private String causale;
    private BigDecimal importo;

    public Integer getNumeroRata() {

	return numeroRata;
    }

    public void setNumeroRata(Integer numeroRata) {

	this.numeroRata = numeroRata;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getRaggruppamento() {

	return raggruppamento;
    }

    public void setRaggruppamento(String raggruppamento) {

	this.raggruppamento = raggruppamento;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }
}
