package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.util.List;

public class RichiestaCheckoutDto {

	private List<AvvisoPagamento> avvisiPagamento;
	private BackUrlDto backUrlDto;
	private String emailDiNotifica;
	private String idRichiestaPagamento;
	private String lang;
	
	public List<AvvisoPagamento> getAvvisiPagamento() {
		return avvisiPagamento;
	}
	public void setAvvisiPagamento(List<AvvisoPagamento> avvisiPagamento) {
		this.avvisiPagamento = avvisiPagamento;
	}
	public BackUrlDto getBackUrlDto() {
		return backUrlDto;
	}
	public void setBackUrlDto(BackUrlDto backUrlDto) {
		this.backUrlDto = backUrlDto;
	}
	public String getEmailDiNotifica() {
		return emailDiNotifica;
	}
	public void setEmailDiNotifica(String emailDiNotifica) {
		this.emailDiNotifica = emailDiNotifica;
	}
	public String getIdRichiestaPagamento() {
		return idRichiestaPagamento;
	}
	public void setIdRichiestaPagamento(String idRichiestaPagamento) {
		this.idRichiestaPagamento = idRichiestaPagamento;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
}
