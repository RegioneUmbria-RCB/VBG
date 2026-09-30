package it.gruppoinit.pal.gp.pay.dao.utils;

import java.math.BigDecimal;
import java.util.Date;

public class PosizioneDebitoriaFiltrata {

    private Integer idPosizioneDebitoria;
    private String uuid;
    private String iuv;
    private String qrcode;
    private String codiceAvviso;
    private Date dataRegistrazione;
    private String descrizione;
    private Integer idDettaglioImporti;
    private BigDecimal importo;
    private String descrizioneCausale;
    private String datiRiscossione;
    private Integer annoAccertamento;
    private String numeroAccertamento;
    private Integer idStatoPagamenti;
    private String stato;
    private String descrizioneStato;
    private Date dataEvento;
    private Boolean flagOtf;

    public Integer getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public void setIdPosizioneDebitoria(Integer idPosizioneDebitoria) {

	this.idPosizioneDebitoria = idPosizioneDebitoria;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getQrcode() {

	return qrcode;
    }

    public void setQrcode(String qrcode) {

	this.qrcode = qrcode;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public Date getDataRegistrazione() {

	return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {

	this.dataRegistrazione = dataRegistrazione;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getIdDettaglioImporti() {

	return idDettaglioImporti;
    }

    public void setIdDettaglioImporti(Integer idDettaglioImporti) {

	this.idDettaglioImporti = idDettaglioImporti;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    public void setDescrizioneCausale(String descrizioneCausale) {

	this.descrizioneCausale = descrizioneCausale;
    }

    public String getDatiRiscossione() {

	return datiRiscossione;
    }

    public void setDatiRiscossione(String datiRiscossione) {

	this.datiRiscossione = datiRiscossione;
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

    public Integer getIdStatoPagamenti() {

	return idStatoPagamenti;
    }

    public void setIdStatoPagamenti(Integer idStatoPagamenti) {

	this.idStatoPagamenti = idStatoPagamenti;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getDescrizioneStato() {

	return descrizioneStato;
    }

    public void setDescrizioneStato(String descrizioneStato) {

	this.descrizioneStato = descrizioneStato;
    }

    public Date getDataEvento() {

	return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {

	this.dataEvento = dataEvento;
    }

    public Boolean getFlagOtf() {

	return flagOtf;
    }

    public void setFlagOtf(Boolean flagOtf) {

	this.flagOtf = flagOtf;
    }
}
