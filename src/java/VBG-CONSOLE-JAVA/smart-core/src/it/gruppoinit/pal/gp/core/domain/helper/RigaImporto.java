package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Conti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RigaImporto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2965430147604981212L;
    private BigDecimal importo;
    private Conti conto;
    // Data per l'inizio del calcolo degli interessi legali
    private Date dataInizio;
    private Integer iva;
    private boolean valoreMensile;

    public Date getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    public RigaImporto() {

	this.importo = new BigDecimal(0);
	this.conto = new Conti();
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public Conti getConto() {

	return conto;
    }

    public void setConto(Conti conto) {

	this.conto = conto;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public Integer getIva() {

	return iva;
    }

    public boolean isValoreMensile() {

	return valoreMensile;
    }

    public void setValoreMensile(boolean valoreMensile) {

	this.valoreMensile = valoreMensile;
    }
}
