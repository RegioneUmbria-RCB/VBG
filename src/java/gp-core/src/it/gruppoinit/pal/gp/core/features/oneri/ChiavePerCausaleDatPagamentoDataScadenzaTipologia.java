package it.gruppoinit.pal.gp.core.features.oneri;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

public class ChiavePerCausaleDatPagamentoDataScadenzaTipologia {

    private String raggruppamento;
    private String causale;
    private Date dataPagamento;
    private Date dataScadenza;
    private EnumTipologiaOnereType tipologia;

    public static ChiavePerCausaleDatPagamentoDataScadenzaTipologia fromRaggruppamento(String raggruppamento) {

	return new ChiavePerCausaleDatPagamentoDataScadenzaTipologia(raggruppamento, null, null, null, null);
    }

    public static ChiavePerCausaleDatPagamentoDataScadenzaTipologia fromRaggruppamentoDataPagamentoEScadenza(String raggruppamento,
	    Date dataPagamento, Date dataScadenza) {

	return new ChiavePerCausaleDatPagamentoDataScadenzaTipologia(raggruppamento, null, dataPagamento, dataScadenza, null);
    }

    public static ChiavePerCausaleDatPagamentoDataScadenzaTipologia fromIstanzeOneri(Istanzeoneri onere) {

	if (onere == null) {
	    throw new IllegalArgumentException("Impossibile istanziare fromIstanzeOnere senza passare la riga di istanzeoneri valida");
	}
	String raggruppamento = (onere.getTipicausalioneri().getRaggruppamentocausalioneri() != null)
		? onere.getTipicausalioneri().getRaggruppamentocausalioneri().getRcoDescr()
		: null;
	Date dataPagamento = onere.getDatapagamento();
	Date dataScadenza = onere.getDatascadenza();
	return new ChiavePerCausaleDatPagamentoDataScadenzaTipologia(raggruppamento, null, dataPagamento, dataScadenza, null);
    }

    private ChiavePerCausaleDatPagamentoDataScadenzaTipologia(String raggruppamento, String causale, Date dataPagamento, Date dataScadenza,
	    EnumTipologiaOnereType tipologia) {

	super();
	this.raggruppamento = raggruppamento;
	this.causale = causale;
	this.dataPagamento = dataPagamento;
	this.dataScadenza = dataScadenza;
	this.tipologia = tipologia;
    }

    public String getRaggruppamento() {

	return raggruppamento;
    }

    public String getCausale() {

	return causale;
    }

    public Date getDataPagamento() {

	return dataPagamento;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public EnumTipologiaOnereType getTipologia() {

	return tipologia;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((causale == null) ? 0 : causale.hashCode());
	result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
	result = prime * result + ((dataScadenza == null) ? 0 : dataScadenza.hashCode());
	result = prime * result + ((raggruppamento == null) ? 0 : raggruppamento.hashCode());
	result = prime * result + ((tipologia == null) ? 0 : tipologia.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ChiavePerCausaleDatPagamentoDataScadenzaTipologia other = (ChiavePerCausaleDatPagamentoDataScadenzaTipologia) obj;
	if (causale == null) {
	    if (other.causale != null) {
		return false;
	    }
	} else if (!causale.equals(other.causale)) {
	    return false;
	}
	if (dataPagamento == null) {
	    if (other.dataPagamento != null) {
		return false;
	    }
	} else if (dataPagamento.compareTo(other.dataPagamento) != 0) {
	    return false;
	}
	if (dataScadenza == null) {
	    if (other.dataScadenza != null) {
		return false;
	    }
	} else if (dataScadenza.compareTo(other.dataScadenza) != 0) {
	    return false;
	}
	if (raggruppamento == null) {
	    if (other.raggruppamento != null) {
		return false;
	    }
	} else if (!raggruppamento.equals(other.raggruppamento)) {
	    return false;
	}
	return (tipologia == other.tipologia);
    }
}
