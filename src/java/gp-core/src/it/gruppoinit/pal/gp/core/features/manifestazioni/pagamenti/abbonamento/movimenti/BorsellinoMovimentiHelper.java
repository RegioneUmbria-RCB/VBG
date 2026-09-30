package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;

public class BorsellinoMovimentiHelper {

    private MercatipresenzeD giornata;
    private Integer idBorsellino;
    private BigDecimal importo;
    private TipoEnum tipo;
    private List<RigaImporto> importi;

    public MercatipresenzeD getGiornata() {

	return giornata;
    }

    public void setGiornata(MercatipresenzeD giornata) {

	this.giornata = giornata;
    }

    public Integer getIdBorsellino() {

	return idBorsellino;
    }

    public void setIdBorsellino(Integer idBorsellino) {

	this.idBorsellino = idBorsellino;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public TipoEnum getTipo() {

	return tipo;
    }

    public void setTipo(TipoEnum tipo) {

	this.tipo = tipo;
    }

    public List<RigaImporto> getImporti() {

	return importi;
    }

    public void setImporti(List<RigaImporto> importi) {

	this.importi = importi;
    }
}
