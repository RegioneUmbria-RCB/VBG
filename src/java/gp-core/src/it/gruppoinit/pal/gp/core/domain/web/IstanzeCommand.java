package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;

import java.util.ArrayList;
import java.util.List;

public class IstanzeCommand extends BaseCommand {

    private IstanzeFilter istanzeFilter;
    private List<Istanze> istanzeList = new ArrayList<Istanze>(0);
    private String soggettoMovimento;
    private Letteretipo letteretipo;
    private Istanze entity;
    private Integer tipoInserimento;
    private Integer tipoRicerca;
    private Istanze istanzaDaConfigurare;
    private String codiceIstanzaOnline;
    private Boolean isCollegamentoPrecedente;
    private Integer codiceRicerca;
    private Istanzearee istanzearee;
    private Integer codiceIstanzaAccessoAtti;
    private Integer codiceGruppoAnagrafetributaria;

    public IstanzeCommand() {

	this.istanzeFilter = new IstanzeFilter();
	this.letteretipo = new Letteretipo();
	this.entity = new Istanze();
	this.tipoInserimento = TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE.value();
	this.tipoRicerca = WebConstants.SEARCH_ISTANZE;
	this.istanzaDaConfigurare = new Istanze();
	this.isCollegamentoPrecedente = Boolean.FALSE;
	this.istanzearee = new Istanzearee();
    }

    public void setIstanzeFilter(IstanzeFilter istanzeFilter) {

	this.istanzeFilter = istanzeFilter;
    }

    public IstanzeFilter getIstanzeFilter() {

	return istanzeFilter;
    }

    public List<Istanze> getIstanzeList() {

	return istanzeList;
    }

    public void setIstanzeList(List<Istanze> istanzeList) {

	this.istanzeList = istanzeList;
    }

    public void setSoggettoMovimento(String soggettoMovimento) {

	this.soggettoMovimento = soggettoMovimento;
    }

    public String getSoggettoMovimento() {

	return soggettoMovimento;
    }

    public void setLetteretipo(Letteretipo letteretipo) {

	this.letteretipo = letteretipo;
    }

    public Letteretipo getLetteretipo() {

	return letteretipo;
    }

    public void setEntity(Istanze entity) {

	this.entity = entity;
    }

    public Istanze getEntity() {

	return entity;
    }

    public void setTipoInserimento(Integer tipoInserimento) {

	this.tipoInserimento = tipoInserimento;
    }

    public Integer getTipoInserimento() {

	return tipoInserimento;
    }

    public Integer getTipoRicerca() {

	return tipoRicerca;
    }

    public void setTipoRicerca(Integer tipoRicerca) {

	this.tipoRicerca = tipoRicerca;
    }

    public Istanze getIstanzaDaConfigurare() {

	return istanzaDaConfigurare;
    }

    public void setIstanzaDaConfigurare(Istanze istanzaDaConfigurare) {

	this.istanzaDaConfigurare = istanzaDaConfigurare;
    }

    public String getCodiceIstanzaOnline() {

	return codiceIstanzaOnline;
    }

    public void setCodiceIstanzaOnline(String codiceIstanzaOnline) {

	this.codiceIstanzaOnline = codiceIstanzaOnline;
    }

    public Boolean getIsCollegamentoPrecedente() {

	return isCollegamentoPrecedente;
    }

    public void setIsCollegamentoPrecedente(Boolean isCollegamentoPrecedente) {

	this.isCollegamentoPrecedente = isCollegamentoPrecedente;
    }

    public Integer getCodiceRicerca() {

	return codiceRicerca;
    }

    public void setCodiceRicerca(Integer codiceRicerca) {

	this.codiceRicerca = codiceRicerca;
    }

    public Istanzearee getIstanzearee() {

	return istanzearee;
    }

    public void setIstanzearee(Istanzearee istanzearee) {

	this.istanzearee = istanzearee;
    }

    public Integer getCodiceIstanzaAccessoAtti() {

	return codiceIstanzaAccessoAtti;
    }

    public void setCodiceIstanzaAccessoAtti(Integer codiceIstanzaAccessoAtti) {

	this.codiceIstanzaAccessoAtti = codiceIstanzaAccessoAtti;
    }

    public Integer getCodiceGruppoAnagrafetributaria() {

	return codiceGruppoAnagrafetributaria;
    }

    public void setCodiceGruppoAnagrafetributaria(Integer codiceGruppoAnagrafetributaria) {

	this.codiceGruppoAnagrafetributaria = codiceGruppoAnagrafetributaria;
    }
}
