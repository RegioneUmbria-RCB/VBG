package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class InfoVersamentoSingoloDto {

	@XmlElement
	private ChiaveDebitoDto chiaveDebito;
	@XmlElement
    private List<DettaglioImportoDto> dettagliImporto;
	@XmlElement
    private String esitoVersamentoSingolo;
	@XmlElement
    private BigDecimal importoSingoloPagato;
	@XmlElement
    private BigDecimal importoSingoloRichiesta;
	@XmlElement
    private String progressivoVersamentoSingolo;
	@XmlElement
    private String testoAllegato;
	@XmlElement
    private String tipoAllegato;
	
	public ChiaveDebitoDto getChiaveDebito() {
		return chiaveDebito;
	}
	public void setChiaveDebito(ChiaveDebitoDto chiaveDebito) {
		this.chiaveDebito = chiaveDebito;
	}
	public List<DettaglioImportoDto> getDettagliImporto() {
		return dettagliImporto;
	}
	public void setDettagliImporto(List<DettaglioImportoDto> dettagliImporto) {
		this.dettagliImporto = dettagliImporto;
	}
	public String getEsitoVersamentoSingolo() {
		return esitoVersamentoSingolo;
	}
	public void setEsitoVersamentoSingolo(String esitoVersamentoSingolo) {
		this.esitoVersamentoSingolo = esitoVersamentoSingolo;
	}
	public BigDecimal getImportoSingoloPagato() {
		return importoSingoloPagato;
	}
	public void setImportoSingoloPagato(BigDecimal importoSingoloPagato) {
		this.importoSingoloPagato = importoSingoloPagato;
	}
	public BigDecimal getImportoSingoloRichiesta() {
		return importoSingoloRichiesta;
	}
	public void setImportoSingoloRichiesta(BigDecimal importoSingoloRichiesta) {
		this.importoSingoloRichiesta = importoSingoloRichiesta;
	}
	public String getProgressivoVersamentoSingolo() {
		return progressivoVersamentoSingolo;
	}
	public void setProgressivoVersamentoSingolo(String progressivoVersamentoSingolo) {
		this.progressivoVersamentoSingolo = progressivoVersamentoSingolo;
	}
	public String getTestoAllegato() {
		return testoAllegato;
	}
	public void setTestoAllegato(String testoAllegato) {
		this.testoAllegato = testoAllegato;
	}
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	public void setTipoAllegato(String tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
	}

}
