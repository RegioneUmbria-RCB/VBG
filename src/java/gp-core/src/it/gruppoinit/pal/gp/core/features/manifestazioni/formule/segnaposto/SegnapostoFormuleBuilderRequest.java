package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcoloMercati;

public class SegnapostoFormuleBuilderRequest {

    private MercatiFormuleCalcolo formula;
    private Integer idGiornata;
    private Integer idPosteggio;
    private PROVENIENZA provenienza;

    public enum PROVENIENZA {
	PRESENZE,
	ASSENZE,
	ALTRO,
	PROIEZIONE,
	PROIEZIONE_SUBENTRI,
    }

    public SegnapostoFormuleBuilderRequest(MercatiFormuleCalcolo formula, Integer idGiornata, Integer idPosteggio, String provenienzaValore) {

	super();
	this.formula = formula;
	this.idGiornata = idGiornata;
	this.idPosteggio = idPosteggio;
	this.provenienza = toProvenienzaEnum(provenienzaValore);
    }

    public SegnapostoFormuleBuilderRequest(MercatiFormuleCalcolo formula, RigaDettaglioCalcoloMercati rigaDettaglioCalcoloMercati) {

	this(formula, rigaDettaglioCalcoloMercati.getIdGiornata(), rigaDettaglioCalcoloMercati.getIdPosteggio(),
		rigaDettaglioCalcoloMercati.getProvenienza());
    }

    private PROVENIENZA toProvenienzaEnum(String provenienzaValore) {

	if (StringUtils.isBlank(provenienzaValore)) {
	    return null;
	}
	PROVENIENZA[] values = PROVENIENZA.values();
	for (PROVENIENZA p : values) {
	    if (p.name().equalsIgnoreCase(provenienzaValore)) {
		return p;
	    }
	}
	return null;
    }

    public MercatiFormuleCalcolo getFormula() {

	return formula;
    }

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public Integer getIdPosteggio() {

	return idPosteggio;
    }

    public String getProvenienza() {

	if (this.provenienza != null) {
	    return this.provenienza.name();
	}
	return null;
    }
}
