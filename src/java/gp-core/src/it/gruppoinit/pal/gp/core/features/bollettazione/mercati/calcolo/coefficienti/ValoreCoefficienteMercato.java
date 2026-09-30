package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti;

import java.math.BigDecimal;
import java.util.Date;

public class ValoreCoefficienteMercato {

    private Integer idMercato;
    private Integer idCategoriaMercato;
    private Integer idSettorePosteggio;
    private String codiceIstat;
    private Integer idConcessioneUso;
    private Date inizioValidita;
    private Date fineValidita;
    private BigDecimal importo;

    public Integer getIdMercato() {

	return idMercato;
    }

    public void setIdMercato(Integer idMercato) {

	this.idMercato = idMercato;
    }

    public Integer getIdCategoriaMercato() {

	return idCategoriaMercato;
    }

    public void setIdCategoriaMercato(Integer idCategoriaMercato) {

	this.idCategoriaMercato = idCategoriaMercato;
    }

    public Integer getIdSettorePosteggio() {

	return idSettorePosteggio;
    }

    public void setIdSettorePosteggio(Integer idSettorePosteggio) {

	this.idSettorePosteggio = idSettorePosteggio;
    }

    public String getCodiceIstat() {

	return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {

	this.codiceIstat = codiceIstat;
    }

    public Integer getIdConcessioneUso() {

	return idConcessioneUso;
    }

    public void setIdConcessioneUso(Integer idConcessioneUso) {

	this.idConcessioneUso = idConcessioneUso;
    }

    public Date getInizioValidita() {

	return inizioValidita;
    }

    public void setInizioValidita(Date inizioValidita) {

	this.inizioValidita = inizioValidita;
    }

    public Date getFineValidita() {

	return fineValidita;
    }

    public void setFineValidita(Date fineValidita) {

	this.fineValidita = fineValidita;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }
}
