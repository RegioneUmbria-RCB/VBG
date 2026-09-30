package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.StatoPagamentoNodoHelper;

public class StatoPagamentoSpuntistaRestHelper {

    private PosteggioInfoRestBean posteggioInfo;
    private Boolean pagato;
    private StatoPagamentoNodoHelper statoPagamento;
    private String label;
    private boolean nodoPagamentiAttivo;

    public boolean getNodoPagamentiAttivo() {

	return nodoPagamentiAttivo;
    }

    public void setNodoPagamentiAttivo(boolean nodoPagamentiAttivo) {

	this.nodoPagamentiAttivo = nodoPagamentiAttivo;
    }

    public String getLabel() {

	return label;
    }

    public void setLabel(String label) {

	this.label = label;
    }

    public PosteggioInfoRestBean getPosteggioInfo() {

	return posteggioInfo;
    }

    public void setPosteggioInfo(PosteggioInfoRestBean posteggioInfo) {

	this.posteggioInfo = posteggioInfo;
    }

    public Boolean getPagato() {

	return pagato;
    }

    public void setPagato(Boolean pagato) {

	this.pagato = pagato;
    }

    public StatoPagamentoNodoHelper getStatoPagamento() {

	return statoPagamento;
    }

    public void setStatoPagamento(StatoPagamentoNodoHelper statoPagamento) {

	this.statoPagamento = statoPagamento;
    }
}
