package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UpgrDaMigrareBean {

    private List<CdsDaMigrareBean> cdsDaMigrare;
    private Map<String, List<CdsTipologieInserite>> tipologieCreatePerIdcomune;
    private Map<String, List<CdsCaricheInserite>> tipologieCarichePerComune;

    public List<CdsDaMigrareBean> getCdsDaMigrare() {

	if (this.cdsDaMigrare == null) {
	    this.cdsDaMigrare = new ArrayList<CdsDaMigrareBean>();
	}
	return cdsDaMigrare;
    }

    public void setCdsDaMigrare(List<CdsDaMigrareBean> cdsDaMigrare) {

	this.cdsDaMigrare = risolviUnivocitaCDS(cdsDaMigrare);
    }

    /**
     * Le CDS potrebbero essere duplicate OTREBBERO TORNARE PIù RECORD DI CDS PER ISTANZA ES UNA CON MOVIMENTO NULLO E
     * UNA CON MOVIMENTO POPOLATO ES UMBRIA ASSISI PER ISTANZA 15276 / 190/2007/DIA
     * 
     * @param cdsDaMigrare
     * @return
     */
    private List<CdsDaMigrareBean> risolviUnivocitaCDS(List<CdsDaMigrareBean> cdsDaMigrare) {

	Map<String, List<CdsDaMigrareBean>> m = new HashMap<String, List<CdsDaMigrareBean>>();
	for (CdsDaMigrareBean cdsDaMigrareBean : cdsDaMigrare) {
	    String key = getKey(cdsDaMigrareBean);
	    List<CdsDaMigrareBean> cds = m.get(key);
	    if (cds == null) {
		cds = new ArrayList<CdsDaMigrareBean>();
	    }
	    cds.add(cdsDaMigrareBean);
	    m.put(key, cds);
	}
	List<CdsDaMigrareBean> finale = new ArrayList<CdsDaMigrareBean>();
	for (Entry<String, List<CdsDaMigrareBean>> c : m.entrySet()) {
	    List<CdsDaMigrareBean> cdss = c.getValue();
	    CdsDaMigrareBean cdsFinale = null;
	    for (CdsDaMigrareBean cdsDaMigrareBean : cdss) {
		cdsFinale = cdsDaMigrareBean;
		if (cdsFinale.getMovesistente() != null) {
		    break;
		}
	    }
	    finale.add(cdsFinale);
	}
	return finale;
    }

    private String getKey(CdsDaMigrareBean cds) {

	return cds.getIdcomune() + "-" + cds.getCodiceistanza();
    }

    public Map<String, List<CdsTipologieInserite>> getTipologieCreatePerIdcomune() {

	if (tipologieCreatePerIdcomune == null) {
	    this.tipologieCreatePerIdcomune = new HashMap<String, List<CdsTipologieInserite>>();
	}
	return tipologieCreatePerIdcomune;
    }

    public void setTipologieCreatePerIdcomune(Map<String, List<CdsTipologieInserite>> tipologieCreatePerIdcomune) {

	this.tipologieCreatePerIdcomune = tipologieCreatePerIdcomune;
    }

    public Map<String, List<CdsCaricheInserite>> getTipologieCarichePerComune() {

	if (this.tipologieCarichePerComune == null) {
	    this.tipologieCarichePerComune = new HashMap<String, List<CdsCaricheInserite>>();
	}
	return tipologieCarichePerComune;
    }

    public void setTipologieCarichePerComune(Map<String, List<CdsCaricheInserite>> tipologieCarichePerComune) {

	this.tipologieCarichePerComune = tipologieCarichePerComune;
    }
}
