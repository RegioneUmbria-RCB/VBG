package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;

public class AvvisoPagamento {

	private BigDecimal importo;
	private String numeroAvviso;
	private String codiceIpaEnte;
	private String codiceServizio;
	
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getNumeroAvviso() {
		return numeroAvviso;
	}
	public void setNumeroAvviso(String numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}
	public String getCodiceIpaEnte() {
		return codiceIpaEnte;
	}
	public void setCodiceIpaEnte(String codiceIpaEnte) {
		this.codiceIpaEnte = codiceIpaEnte;
	}
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
		
}
