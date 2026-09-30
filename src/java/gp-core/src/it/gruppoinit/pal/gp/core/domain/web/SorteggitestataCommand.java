package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.features.sorteggi.TipologiaStatoSorteggioIstanzaEnum;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioDTO;

public class SorteggitestataCommand extends BaseCommand {

    private Sorteggitestata entity;
    private SorteggitestataFilter sorteggitestataFilter;
    private List<Statiistanza> statiistanzaList;
    private List<SorteggiCategorie> sorteggiCategorieList;
    private List<Sorteggitestata> sorteggitestataList;
    private List<Sorteggidettaglio> sorteggidettaglioList;
    private List<SorteggioDettaglioDTO> sorteggidettaglioDTOList;
    private List<Naturaendo> naturaendoList;
    private Integer codiceAlgoritmo;
    private Movimenti movimento;
    private TipologiaStatoSorteggioIstanzaEnum tipologiaStatoSorteggioIstanza;

    public SorteggitestataCommand() {

	this.entity = new Sorteggitestata();
	this.sorteggitestataFilter = new SorteggitestataFilter();
	this.movimento = new Movimenti();
	sorteggidettaglioDTOList = new ArrayList<SorteggioDettaglioDTO>();
    }

    public Sorteggitestata getEntity() {

	return entity;
    }

    public void setEntity(Sorteggitestata entity) {

	this.entity = entity;
    }

    public SorteggitestataFilter getSorteggitestataFilter() {

	return sorteggitestataFilter;
    }

    public void setSorteggitestataFilter(SorteggitestataFilter sorteggitestataFilter) {

	this.sorteggitestataFilter = sorteggitestataFilter;
    }

    public List<Statiistanza> getStatiistanzaList() {

	return statiistanzaList;
    }

    public void setStatiistanzaList(List<Statiistanza> statiistanzaList) {

	this.statiistanzaList = statiistanzaList;
    }

    public List<SorteggiCategorie> getSorteggiCategorieList() {

	return sorteggiCategorieList;
    }

    public void setSorteggiCategorieList(List<SorteggiCategorie> sorteggiCategorieList) {

	this.sorteggiCategorieList = sorteggiCategorieList;
    }

    public List<Sorteggitestata> getSorteggitestataList() {

	return sorteggitestataList;
    }

    public void setSorteggitestataList(List<Sorteggitestata> sorteggitestataList) {

	this.sorteggitestataList = sorteggitestataList;
    }

    public List<Sorteggidettaglio> getSorteggidettaglioList() {

	return sorteggidettaglioList;
    }

    public void setSorteggidettaglioList(List<Sorteggidettaglio> sorteggidettaglioList) {

	this.sorteggidettaglioList = sorteggidettaglioList;
    }

    public List<SorteggioDettaglioDTO> getSorteggidettaglioDTOList() {

	return sorteggidettaglioDTOList;
    }

    public void setSorteggidettaglioDTOList(List<SorteggioDettaglioDTO> sorteggidettaglioDTOList) {

	this.sorteggidettaglioDTOList = sorteggidettaglioDTOList;
    }

    public Integer getCodiceAlgoritmo() {

	return codiceAlgoritmo;
    }

    public void setCodiceAlgoritmo(Integer codiceAlgoritmo) {

	this.codiceAlgoritmo = codiceAlgoritmo;
    }

    public it.gruppoinit.pal.gp.core.domain.Movimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(it.gruppoinit.pal.gp.core.domain.Movimenti movimento) {

	this.movimento = movimento;
    }

    public TipologiaStatoSorteggioIstanzaEnum getTipologiaStatoSorteggioIstanza() {

	return tipologiaStatoSorteggioIstanza;
    }

    public void setTipologiaStatoSorteggioIstanza(TipologiaStatoSorteggioIstanzaEnum tipologiaStatoSorteggioIstanza) {

	this.tipologiaStatoSorteggioIstanza = tipologiaStatoSorteggioIstanza;
    }

    public List<Naturaendo> getNaturaendoList() {

	return naturaendoList;
    }

    public void setNaturaendoList(List<Naturaendo> naturaendoList) {

	this.naturaendoList = naturaendoList;
    }
}
