package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.ListUtils;

import it.gruppoinit.pal.gp.core.dao.helper.AlberoprocSorteggiHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public class SorteggitestataFilter implements Serializable {

    private static final long serialVersionUID = -1724603341032439324L;
    private Comuni comune;
    private Date dataSorteggio;
    private Tipiarchivioistanze tipiarchivioistanza;
    private Alberoproc alberoproc;
    private Tipiprocedure procedura;
    // Se specificato il movimento le date si riferiscono al movimento altrimenti all'istanza
    private Date dataDal;
    private Date dataAl;
    private Tipimovimento tipoMovimento;
    // Utilizzo un campo Integer poiché sono valori fissi non mappati  
    private Integer tipoRicercaMovimento;
    // Utilizzo un Integer perché posso ricercare oltre che per esito positivo e negativo anche per entrambi
    private Integer esito;
    private Statiistanza chiusura;
    private Integer percentuale;
    private Integer gruppiIstanze;
    private Integer arrotondamento;
    private Boolean escludeIstanze;
    private Boolean salva;
    private String descrizione;
    private SorteggiCategorie categoria;
    private List<Integer> listaEstrazioni;
    private List<Integer> listaCategorieEstrazioni;
    private List<Integer> naturaendo;
    private List<IdentificativoDescrizioneBean> endoSelezionati;
    private String andOrSelezioneEndo;
    // Filtri Emilia Romagna
    private Date intervalloCampionamentoDal;
    private Date intervalloCampionamentoAl;
    private List<AlberoprocSorteggiHelper> alberoprocSorteggiHelpers;

    @SuppressWarnings("unchecked")
    public SorteggitestataFilter() {

	this.comune = new Comuni();
	this.tipiarchivioistanza = new Tipiarchivioistanze();
	this.alberoproc = new Alberoproc();
	this.procedura = new Tipiprocedure();
	this.tipoMovimento = new Tipimovimento();
	this.chiusura = new Statiistanza();
	this.categoria = new SorteggiCategorie();
	this.endoSelezionati = ListUtils.lazyList(new ArrayList<IdentificativoDescrizioneBean>(),
		FactoryUtils.instantiateFactory(IdentificativoDescrizioneBean.class));
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public Date getDataSorteggio() {

	return dataSorteggio;
    }

    public void setDataSorteggio(Date dataSorteggio) {

	this.dataSorteggio = dataSorteggio;
    }

    public Tipiarchivioistanze getTipiarchivioistanza() {

	return tipiarchivioistanza;
    }

    public void setTipiarchivioistanza(Tipiarchivioistanze tipiarchivioistanza) {

	this.tipiarchivioistanza = tipiarchivioistanza;
    }

    public Alberoproc getAlberoproc() {

	return alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    public Tipiprocedure getProcedura() {

	return procedura;
    }

    public void setProcedura(Tipiprocedure procedura) {

	this.procedura = procedura;
    }

    public Date getDataDal() {

	return dataDal;
    }

    public void setDataDal(Date dataDal) {

	this.dataDal = dataDal;
    }

    public Date getDataAl() {

	return dataAl;
    }

    public void setDataAl(Date dataAl) {

	this.dataAl = dataAl;
    }

    public Tipimovimento getTipoMovimento() {

	return tipoMovimento;
    }

    public void setTipoMovimento(Tipimovimento tipoMovimento) {

	this.tipoMovimento = tipoMovimento;
    }

    public Integer getTipoRicercaMovimento() {

	return tipoRicercaMovimento;
    }

    public void setTipoRicercaMovimento(Integer tipoRicercaMovimento) {

	this.tipoRicercaMovimento = tipoRicercaMovimento;
    }

    public Integer getEsito() {

	return esito;
    }

    public void setEsito(Integer esito) {

	this.esito = esito;
    }

    public Statiistanza getChiusura() {

	return chiusura;
    }

    public void setChiusura(Statiistanza chiusura) {

	this.chiusura = chiusura;
    }

    public Integer getPercentuale() {

	return percentuale;
    }

    public void setPercentuale(Integer percentuale) {

	this.percentuale = percentuale;
    }

    public Integer getGruppiIstanze() {

	return gruppiIstanze;
    }

    public void setGruppiIstanze(Integer gruppiIstanze) {

	this.gruppiIstanze = gruppiIstanze;
    }

    public SorteggiCategorie getCategoria() {

	return categoria;
    }

    public void setCategoria(SorteggiCategorie categoria) {

	this.categoria = categoria;
    }

    public Integer getArrotondamento() {

	return arrotondamento;
    }

    public void setArrotondamento(Integer arrotondamento) {

	this.arrotondamento = arrotondamento;
    }

    public Boolean getEscludeIstanze() {

	return escludeIstanze;
    }

    public void setEscludeIstanze(Boolean escludeIstanze) {

	this.escludeIstanze = escludeIstanze;
    }

    public Boolean getSalva() {

	return salva;
    }

    public void setSalva(Boolean salva) {

	this.salva = salva;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public List<Integer> getListaEstrazioni() {

	return listaEstrazioni;
    }

    public void setListaEstrazioni(List<Integer> listaEstrazioni) {

	this.listaEstrazioni = listaEstrazioni;
    }

    public List<Integer> getListaCategorieEstrazioni() {

	return listaCategorieEstrazioni;
    }

    public void setListaCategorieEstrazioni(List<Integer> listaCategorieEstrazioni) {

	this.listaCategorieEstrazioni = listaCategorieEstrazioni;
    }

    public Date getIntervalloCampionamentoDal() {

	return intervalloCampionamentoDal;
    }

    public void setIntervalloCampionamentoDal(Date intervalloCampionamentoDal) {

	this.intervalloCampionamentoDal = intervalloCampionamentoDal;
    }

    public Date getIntervalloCampionamentoAl() {

	return intervalloCampionamentoAl;
    }

    public void setIntervalloCampionamentoAl(Date intervalloCampionamentoAl) {

	this.intervalloCampionamentoAl = intervalloCampionamentoAl;
    }

    public List<AlberoprocSorteggiHelper> getAlberoprocSorteggiHelpers() {

	return alberoprocSorteggiHelpers;
    }

    public void setAlberoprocSorteggiHelpers(List<AlberoprocSorteggiHelper> alberoprocSorteggiHelpers) {

	this.alberoprocSorteggiHelpers = alberoprocSorteggiHelpers;
    }

    public List<Integer> getNaturaendo() {

	if (this.naturaendo == null) {
	    this.naturaendo = new ArrayList<Integer>();
	}
	return naturaendo;
    }

    public void setNaturaendo(List<Integer> naturaendo) {

	this.naturaendo = naturaendo;
    }

    public List<IdentificativoDescrizioneBean> getEndoSelezionati() {

	if (this.endoSelezionati == null) {
	    this.endoSelezionati = new ArrayList<IdentificativoDescrizioneBean>(20);
	}
	return endoSelezionati;
    }

    public void setEndoSelezionati(List<IdentificativoDescrizioneBean> endoSelezionati) {

	this.endoSelezionati = endoSelezionati;
    }

    public String getAndOrSelezioneEndo() {

	return andOrSelezioneEndo;
    }

    public void setAndOrSelezioneEndo(String andOrSelezioneEndo) {

	this.andOrSelezioneEndo = andOrSelezioneEndo;
    }
}
