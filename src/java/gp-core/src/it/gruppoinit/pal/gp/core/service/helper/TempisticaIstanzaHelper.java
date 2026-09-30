package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MovimentiTempistica;
import it.gruppoinit.pal.gp.core.service.MovimentiTempisticaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class TempisticaIstanzaHelper {

    private Date datainizio;
    private Date datafine;
    private Date datafineeffettiva;
    private String stato;
    private Integer giorniprocedura;
    private int ggaggiuntivi;
    private int ggProroghe;
    private Integer transientDurataProcedimento;
    private Integer transientDurataStimataProcedimento;
    private Istanze istanza;

    public TempisticaIstanzaHelper(Istanze i, List<MovimentiTempistica> movtempisticas) {

	this.istanza = i;
	if (i.getIstanzeTempistica() != null) {
	    this.datainizio = i.getIstanzeTempistica().getDatainizio();
	    this.datafine = i.getIstanzeTempistica().getDatafine();
	    this.datafineeffettiva = i.getIstanzeTempistica().getDatafineeffettiva();
	    this.stato = i.getIstanzeTempistica().getStato();
	    this.giorniprocedura = i.getIstanzeTempistica().getGiorniprocedura();
	    this.transientDurataProcedimento = i.getIstanzeTempistica().getTransientDurataProcedimento();
	    this.transientDurataStimataProcedimento = i.getIstanzeTempistica().getTransientDurataStimataProcedimento();
	    calcolaGiorniAggiuntivi(i.getIstanzeTempistica().getGgaggiuntivi(), movtempisticas);
	}
    }

    private void calcolaGiorniAggiuntivi(Integer ggAggiuntivitempistica, List<MovimentiTempistica> movtempisticas) {

	ggaggiuntivi = ggAggiuntivitempistica == null ? 0 : ggAggiuntivitempistica.intValue();
	ggProroghe = 0;
	for (MovimentiTempistica mt : movtempisticas) {
	    if (mt.getEvento().equals(MovimentiTempisticaService.TIPO_EVENTO.P.name()) && mt.getDurata() != null) {
		ggaggiuntivi = ggaggiuntivi - mt.getDurata();
		if (Utilities.compareDates(mt.getMovimentoByFkApertura().getData(), datainizio) >= 0) {
		    ggProroghe += mt.getDurata().intValue();
		}
	    }
	}
    }

    public Date getDatainizio() {

	return datainizio;
    }

    public Date getDatafine() {

	return datafine;
    }

    public Date getDatafineeffettiva() {

	return datafineeffettiva;
    }

    public String getStato() {

	return stato;
    }

    public Integer getGiorniprocedura() {

	return giorniprocedura;
    }

    public int getGgaggiuntivi() {

	return ggaggiuntivi;
    }

    public int getGgProroghe() {

	return ggProroghe;
    }

    public Integer getTransientDurataProcedimento() {

	return transientDurataProcedimento;
    }

    public Integer getTransientDurataStimataProcedimento() {

	return transientDurataStimataProcedimento;
    }

    public Istanze getIstanza() {

	return istanza;
    }
}
