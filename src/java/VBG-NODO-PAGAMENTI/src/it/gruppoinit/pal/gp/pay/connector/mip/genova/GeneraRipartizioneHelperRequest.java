package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class GeneraRipartizioneHelperRequest {

    private BigDecimal importo;
    private Integer annoDebito;
    private String idDebito;
    private Integer numeroRata;
    private String idRata;
    private TipiRipartizione tipoRipartizione;
    private Integer annoAccertamento;
    private String numeroAccertamento;
    private String numeroSottoAccertamento;
    private PkId idPosizioneDebitoria;

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public Integer getAnnoDebito() {

	return annoDebito;
    }

    public void setAnnoDebito(Integer annoDebito) {

	this.annoDebito = annoDebito;
    }

    public String getIdDebito() {

	return idDebito;
    }

    public void setIdDebito(String idDebito) {

	this.idDebito = idDebito;
    }

    public Integer getNumeroRata() {

	return numeroRata;
    }

    public void setNumeroRata(Integer numeroRata) {

	this.numeroRata = numeroRata;
    }

    public String getIdRata() {

	return idRata;
    }

    public void setIdRata(String idRata) {

	this.idRata = idRata;
    }

    public TipiRipartizione getTipoRipartizione() {

	return tipoRipartizione;
    }

    public void setTipoRipartizione(TipiRipartizione tipoRipartizione) {

	this.tipoRipartizione = tipoRipartizione;
    }

    public Integer getAnnoAccertamento() {

	return annoAccertamento;
    }

    public void setAnnoAccertamento(Integer annoAccertamento) {

	this.annoAccertamento = annoAccertamento;
    }

    public String getNumeroAccertamento() {

	return numeroAccertamento;
    }

    public void setNumeroAccertamento(String numeroAccertamento) {

	this.numeroAccertamento = numeroAccertamento;
    }

    public String getNumeroSottoAccertamento() {

	return numeroSottoAccertamento;
    }

    public void setNumeroSottoAccertamento(String numeroSottoAccertamento) {

	this.numeroSottoAccertamento = numeroSottoAccertamento;
    }

    public PkId getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public void setIdPosizioneDebitoria(PkId idPosizioneDebitoria) {

	this.idPosizioneDebitoria = idPosizioneDebitoria;
    }
}
