package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NotificaPagamentoRequest {

	@XmlElement
	private ChiaveDebitoDto chiaveDebitoDto;
	@XmlElement
    private String codiceServizio;
	@XmlElement
    private String codIpaRichiedente;
	@XmlElement
    private String dataValuta; //prendiamola come stringa
	@XmlElement
    private List<InfoPagamentoTelematicoDto> listaInfoPagamentoTelematicoDto;
	@XmlElement
    private MultibeneficiarioDto multibeneficiarioDto;
    
	public ChiaveDebitoDto getChiaveDebitoDto() {
		return chiaveDebitoDto;
	}
	public void setChiaveDebitoDto(ChiaveDebitoDto chiaveDebitoDto) {
		this.chiaveDebitoDto = chiaveDebitoDto;
	}
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	public String getCodIpaRichiedente() {
		return codIpaRichiedente;
	}
	public void setCodIpaRichiedente(String codIpaRichiedente) {
		this.codIpaRichiedente = codIpaRichiedente;
	}
	public String getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(String dataValuta) {
		this.dataValuta = dataValuta;
	}
	public List<InfoPagamentoTelematicoDto> getListaInfoPagamentoTelematicoDto() {
		return listaInfoPagamentoTelematicoDto;
	}
	public void setListaInfoPagamentoTelematicoDto(List<InfoPagamentoTelematicoDto> listaInfoPagamentoTelematicoDto) {
		this.listaInfoPagamentoTelematicoDto = listaInfoPagamentoTelematicoDto;
	}
	public MultibeneficiarioDto getMultibeneficiarioDto() {
		return multibeneficiarioDto;
	}
	public void setMultibeneficiarioDto(MultibeneficiarioDto multibeneficiarioDto) {
		this.multibeneficiarioDto = multibeneficiarioDto;
	}
	
}
