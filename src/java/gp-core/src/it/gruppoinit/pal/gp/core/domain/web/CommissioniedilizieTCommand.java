package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommissioniedilizieTCommand extends BaseCommand {

    private CommissioniedilizieT entity;
    private CommissioniedilizieR commissioniedilizieR;
    // lista che raccoglie tutti i movimenti che possono essere discussi dalla commissione scelta
    private List<Movimenti> listaMovimentiDiscutibili = new ArrayList<Movimenti>();
    // memorizza la lista di tutti i codici dei movimenti scelti (utilizzato per selezionare più movimenti 
    //che vogliamo mandare in commissione)
    private String codiciMovimentiScelti;
    // Utilizzato per gestire la funzionalità di modifica dell'ordine delle commissioni edilizie r che sono 
    // configuarate come discutibili
    private String ordine;
    // Utilizzati per la gestione della ricerca e del salvatataggio delle possibili commissioni edilizie r
    // che possono essere discusse
    private String codiciCommissioniRScelte;
    private Date dataFiltro;
    // funzionalità di gestione dell'esito (funzionalità di siscussione delle commissioni edilizie r )
    private List<CommedilizieVotazioni> listaAppello = new ArrayList<CommedilizieVotazioni>();
    private CommedilizieTipopareri commedilizieTipopareri = new CommedilizieTipopareri();
    private List<CommedilizieTipopareri> listTipopareri = new ArrayList<CommedilizieTipopareri>();
    private Mailtipo mailtipo = new Mailtipo();
    private String parere;
    // campo transiet per la funzionalità di riordino delle commissioni edilizie r
    private Set<CommissioniedilizieR> listCommissioniedilizieRs = new HashSet<CommissioniedilizieR>();
    private Letteretipo letteretipo = new Letteretipo();

    public CommissioniedilizieTCommand() {

	super();
	this.entity = new CommissioniedilizieT();
	this.commissioniedilizieR = new CommissioniedilizieR();
    }

    public CommissioniedilizieT getEntity() {

	return entity;
    }

    public void setEntity(CommissioniedilizieT entity) {

	this.entity = entity;
    }

    public CommissioniedilizieR getCommissioniedilizieR() {

	return commissioniedilizieR;
    }

    public void setCommissioniedilizieR(CommissioniedilizieR commissioniedilizieR) {

	this.commissioniedilizieR = commissioniedilizieR;
    }

    public Date getDataFiltro() {

	return dataFiltro;
    }

    public void setDataFiltro(Date dataFiltro) {

	this.dataFiltro = dataFiltro;
    }

    public List<Movimenti> getListaMovimentiDiscutibili() {

	return listaMovimentiDiscutibili;
    }

    public void setListaMovimentiDiscutibili(List<Movimenti> listaMovimentiDiscutibili) {

	this.listaMovimentiDiscutibili = listaMovimentiDiscutibili;
    }

    public String getCodiciMovimentiScelti() {

	return codiciMovimentiScelti;
    }

    public void setCodiciMovimentiScelti(String codiciMovimentiScelti) {

	this.codiciMovimentiScelti = codiciMovimentiScelti;
    }

    public String getOrdine() {

	return ordine;
    }

    public void setOrdine(String ordine) {

	this.ordine = ordine;
    }

    public String getCodiciCommissioniRScelte() {

	return codiciCommissioniRScelte;
    }

    public void setCodiciCommissioniRScelte(String codiciCommissioniRScelte) {

	this.codiciCommissioniRScelte = codiciCommissioniRScelte;
    }

    public List<CommedilizieVotazioni> getListaAppello() {

	return listaAppello;
    }

    public void setListaAppello(List<CommedilizieVotazioni> listaAppello) {

	this.listaAppello = listaAppello;
    }

    public CommedilizieTipopareri getCommedilizieTipopareri() {

	return commedilizieTipopareri;
    }

    public void setCommedilizieTipopareri(CommedilizieTipopareri commedilizieTipopareri) {

	this.commedilizieTipopareri = commedilizieTipopareri;
    }

    public List<CommedilizieTipopareri> getListTipopareri() {

	return listTipopareri;
    }

    public void setListTipopareri(List<CommedilizieTipopareri> listTipopareri) {

	this.listTipopareri = listTipopareri;
    }

    public Mailtipo getMailtipo() {

	return mailtipo;
    }

    public void setMailtipo(Mailtipo mailtipo) {

	this.mailtipo = mailtipo;
    }

    public String getParere() {

	return parere;
    }

    public void setParere(String parere) {

	this.parere = parere;
    }

    public Set<CommissioniedilizieR> getListCommissioniedilizieRs() {

	return listCommissioniedilizieRs;
    }

    public void setListCommissioniedilizieRs(Set<CommissioniedilizieR> listCommissioniedilizieRs) {

	this.listCommissioniedilizieRs = listCommissioniedilizieRs;
    }

    public Letteretipo getLetteretipo() {

	return letteretipo;
    }

    public void setLetteretipo(Letteretipo letteretipo) {

	this.letteretipo = letteretipo;
    }
}
