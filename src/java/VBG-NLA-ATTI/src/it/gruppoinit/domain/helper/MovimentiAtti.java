package it.gruppoinit.domain.helper;

import java.util.Date;

public class MovimentiAtti {

    private String idcomune;
    private Integer id;
    private Integer movimenti;
    private Date dataRichiestaAtto;
    private Date dataRicezioneAtto;
    private Integer idDocumento;
    private Integer numero;
    private Integer anno;
    private String tipoDocumento;
    private Integer stato;

    public MovimentiAtti() {

    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getMovimenti() {

	return this.movimenti;
    }

    public void setMovimenti(Integer movimenti) {

	this.movimenti = movimenti;
    }

    public Date getDataRichiestaAtto() {

	return this.dataRichiestaAtto;
    }

    public void setDataRichiestaAtto(Date dataRichiestaAtto) {

	this.dataRichiestaAtto = dataRichiestaAtto;
    }

    public Date getDataRicezioneAtto() {

	return this.dataRicezioneAtto;
    }

    public void setDataRicezioneAtto(Date dataRicezioneAtto) {

	this.dataRicezioneAtto = dataRicezioneAtto;
    }

    public Integer getIdDocumento() {

	return this.idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {

	this.idDocumento = idDocumento;
    }

    public Integer getNumero() {

	return this.numero;
    }

    public void setNumero(Integer numero) {

	this.numero = numero;
    }

    public Integer getAnno() {

	return this.anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public String getTipoDocumento() {

	return this.tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    public Integer getStato() {

	return this.stato;
    }

    public void setStato(Integer stato) {

	this.stato = stato;
    }
}
