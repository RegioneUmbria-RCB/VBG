package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class DatiSingoloPagamento {

    @XmlElement(name = "parametriAggiuntiviSingoloVersamento", required = false)
    private String parametriAggiuntiviSingoloVersamento;
    @XmlElement(name = "indentificativoFlusso", required = false)
    private String indentificativoFlusso;
    @XmlElement(name = "dataOraFlusso", required = false)
    private Date dataOraFlusso;
    @XmlElement(name = "identificativoUnivocoRegolamento", required = false)
    private String identificativoUnivocoRegolamento;
    @XmlElement(name = "dataRegolamento", required = false)
    private String dataRegolamento;

    public String getParametriAggiuntiviSingoloVersamento() {

	return parametriAggiuntiviSingoloVersamento;
    }

    public void setParametriAggiuntiviSingoloVersamento(String parametriAggiuntiviSingoloVersamento) {

	this.parametriAggiuntiviSingoloVersamento = parametriAggiuntiviSingoloVersamento;
    }

    public String getIndentificativoFlusso() {

	return indentificativoFlusso;
    }

    public void setIndentificativoFlusso(String indentificativoFlusso) {

	this.indentificativoFlusso = indentificativoFlusso;
    }

    public Date getDataOraFlusso() {

	return dataOraFlusso;
    }

    public void setDataOraFlusso(Date dataOraFlusso) {

	this.dataOraFlusso = dataOraFlusso;
    }

    public String getIdentificativoUnivocoRegolamento() {

	return identificativoUnivocoRegolamento;
    }

    public void setIdentificativoUnivocoRegolamento(String identificativoUnivocoRegolamento) {

	this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
    }

    public String getDataRegolamento() {

	return dataRegolamento;
    }

    public void setDataRegolamento(String dataRegolamento) {

	this.dataRegolamento = dataRegolamento;
    }
}
