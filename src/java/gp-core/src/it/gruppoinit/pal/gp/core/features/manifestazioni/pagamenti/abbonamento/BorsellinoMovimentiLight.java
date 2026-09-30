package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.BorsellinoMovimentiToStringFactory;

public class BorsellinoMovimentiLight implements Comparable<BorsellinoMovimentiLight> {

    private Integer id;
    private Date datamovimento;
    private BorsellinoMovimentiToStringFactory dettaglio;
    private BigDecimal importo;
    private Integer posizioneDebitoria;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Date getDatamovimento() {

	return datamovimento;
    }

    public void setDatamovimento(Date datamovimento) {

	this.datamovimento = datamovimento;
    }

    public BorsellinoMovimentiToStringFactory getDettaglio() {

	return dettaglio;
    }

    public void setDettaglio(BorsellinoMovimentiToStringFactory dettaglio) {

	this.dettaglio = dettaglio;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public Integer getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public void setPosizioneDebitoria(Integer posizioneDebitoria) {

	this.posizioneDebitoria = posizioneDebitoria;
    }

    public static BorsellinoMovimentiLight fromBorsellinoMovimenti(BorsellinoMovimenti bm) {

	if (bm == null || bm.getId() == null) {
	    return null;
	}
	BorsellinoMovimentiLight model = new BorsellinoMovimentiLight();
	model.setId(bm.getId().getCodice());
	model.setDatamovimento(bm.getData());
	model.setDettaglio(BorsellinoMovimentiToStringFactory.fromBorsellinoMovimenti(bm));
	model.setImporto(bm.getImporto());
	if (bm.getDettPosizioneDebitoria() != null) {
	    model.setPosizioneDebitoria(bm.getDettPosizioneDebitoria().getId().getCodice());
	}
	return model;
    }

    @Override
    public int compareTo(BorsellinoMovimentiLight o) {

	if (o.datamovimento.compareTo(this.datamovimento) == 0) {
	    return o.getId().compareTo(this.id);
	}
	return o.datamovimento.compareTo(this.datamovimento);
    }    
    
}
