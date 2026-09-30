package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.domain.Conti;

import java.math.BigDecimal;

public class ImportoHelperBean {

    private BigDecimal importo;
    private boolean valore;
    private Conti conto;
    private String contesto;
    private boolean valoreMensile;

    public ImportoHelperBean(Conti conto, BigDecimal importo, boolean valore, String contesto, boolean valoreMensile) {

	this.conto = conto;
	this.valore = valore;
	this.importo = importo;
	this.contesto = contesto;
	this.valoreMensile = valoreMensile;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public boolean isValore() {

	return valore;
    }

    public void setValore(boolean valore) {

	this.valore = valore;
    }

    public Conti getConto() {

	return conto;
    }

    public void setConto(Conti conto) {

	this.conto = conto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    public String getContesto() {

	return contesto;
    }

    public boolean isValoreMensile() {

	return valoreMensile;
    }

    public void setValoreMensile(boolean valoreMensile) {

	this.valoreMensile = valoreMensile;
    }

    @Override
    public String toString() {

	StringBuffer answer = new StringBuffer();
	answer.append("conto:").append(conto == null ? "" : conto.getDescrizione()).append("\nimporto:").append(importo == null ? 0 : importo)
		.append("\ncontoCanone:").append(valore).append("\ncontesto:").append(contesto == null ? "" : contesto).append("\nvaloreMensile:")
		.append(isValoreMensile());
	return answer.toString();
    }
}
