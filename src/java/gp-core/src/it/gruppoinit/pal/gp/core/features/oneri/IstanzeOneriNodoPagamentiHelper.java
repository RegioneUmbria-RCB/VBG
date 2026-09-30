package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;
import java.util.Date;

public class IstanzeOneriNodoPagamentiHelper {

    private Integer idIstanzeOneri;
    private Date dataUltimoStato;
    private BigDecimal importoTotale;

    public Integer getIdIstanzeOneri() {

	return idIstanzeOneri;
    }

    public Date getDataUltimoStato() {

	return dataUltimoStato;
    }

    public BigDecimal getImportoTotale() {

	return importoTotale;
    }

    public void setIdIstanzeOneri(Integer idIstanzeOneri) {

	this.idIstanzeOneri = idIstanzeOneri;
    }

    public void setDataUltimoStato(Date dataUltimoStato) {

	this.dataUltimoStato = dataUltimoStato;
    }

    public void setImportoTotale(BigDecimal importoTotale) {

	this.importoTotale = importoTotale;
    }
}
