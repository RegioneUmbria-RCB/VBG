package it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti;

public class StatoPagamentoNodoHelper {

    private boolean attivato;
    private Double importoRegistrato;
    private Double importoCalcolato;
    private StatoPagamentoPosDebHelper posizioneDebitoria;

    public boolean isAttivato() {

	return attivato;
    }

    public void setAttivato(boolean attivato) {

	this.attivato = attivato;
    }

    public Double getImportoRegistrato() {

	return importoRegistrato;
    }

    public void setImportoRegistrato(Double importoRegistrato) {

	this.importoRegistrato = importoRegistrato;
    }

    public Double getImportoCalcolato() {

	return importoCalcolato;
    }

    public void setImportoCalcolato(Double importoCalcolato) {

	this.importoCalcolato = importoCalcolato;
    }

    public StatoPagamentoPosDebHelper getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public void setPosizioneDebitoria(StatoPagamentoPosDebHelper posizioneDebitoria) {

	this.posizioneDebitoria = posizioneDebitoria;
    }
}
