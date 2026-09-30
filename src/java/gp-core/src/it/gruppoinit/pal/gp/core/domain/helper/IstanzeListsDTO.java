package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoli;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettaglior;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettagliot;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontribattiv;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioni;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Istanzefidejussioni;
import it.gruppoinit.pal.gp.core.domain.Istanzefrontoffice;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriDettaglio;
import it.gruppoinit.pal.gp.core.domain.Istanzepeopled;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Istanzereplicate;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Istanzeruoli;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettaglior;
import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettagliot;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribr;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribrRiduz;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribt;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribtBto;
import it.gruppoinit.pal.gp.core.domain.OIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoDis;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class IstanzeListsDTO implements Serializable {

    private static final long serialVersionUID = 7360460872045702957L;
    private Set<Istanzedyn2dati> istanzedyn2datis = new HashSet<Istanzedyn2dati>(0);
    private Set<Istanzedyn2modellit> istanzedyn2modellit = new HashSet<Istanzedyn2modellit>(0);
    private Set<Graduatoried> graduatorieds = new HashSet<Graduatoried>(0);
    private Set<Istanzeprocedimenti> istanzeprocedimentis = new HashSet<Istanzeprocedimenti>(0);
    private Set<Istanzeattivita> istanzeattivitas = new HashSet<Istanzeattivita>(0);
    private Set<Movimenti> istanzemovimentis = new HashSet<Movimenti>(0);
    private Set<Istanzestradario> istanzestradarios = new HashSet<Istanzestradario>(0);
    private Set<Documentiistanza> documentiistanzas = new HashSet<Documentiistanza>(0);
    private Set<Istanzeallegati> istanzeallegatis = new HashSet<Istanzeallegati>(0);
    private Set<Istanzerichiedenti> istanzerichiedentis = new HashSet<Istanzerichiedenti>(0);
    private Set<Istanzepeopled> istanzepeopleds = new HashSet<Istanzepeopled>();
    private Set<Istanzeruoli> istanzeruolis = new HashSet<Istanzeruoli>(0);
    private Set<Istanzemappali> istanzemappalis = new HashSet<Istanzemappali>(0);
    private Set<Orariaperturatestata> orariaperturatestatas = new HashSet<Orariaperturatestata>(0);
    private Set<Autorizzazioni> autorizzazionis = new HashSet<Autorizzazioni>(0);
    private Set<AutorizzazioniSubentri> autorizzazionisubentris = new HashSet<AutorizzazioniSubentri>(0);
    private Set<CcIcalcoliDettaglior> ccIcalcoliDettagliors = new HashSet<CcIcalcoliDettaglior>(0);
    private Set<CcIcalcoli> ccIcalcolis = new HashSet<CcIcalcoli>(0);
    private Set<CcIcalcoliDettagliot> ccIcalcoliDettagliots = new HashSet<CcIcalcoliDettagliot>(0);
    private Set<CcIcalcoloDcontributo> ccIcalcoloDcontributos = new HashSet<CcIcalcoloDcontributo>(0);
    private Set<CcIcalcoloDcontribattiv> ccIcalcoloDcontribattivs = new HashSet<CcIcalcoloDcontribattiv>(0);
    private Set<CcIcalcoloTcontributo> ccIcalcoloTcontributos = new HashSet<CcIcalcoloTcontributo>(0);
    private Set<CcIcalcolotcontributoRiduz> ccIcalcolotcontributoRiduzs = new HashSet<CcIcalcolotcontributoRiduz>(0);
    private Set<CcIcalcolotot> ccIcalcolotots = new HashSet<CcIcalcolotot>(0);
    private Set<CcItabella1> ccItabella1s = new HashSet<CcItabella1>(0);
    private Set<CcItabella2> ccItabella2s = new HashSet<CcItabella2>(0);
    private Set<CcItabella3> ccItabella3s = new HashSet<CcItabella3>(0);
    private Set<CcItabella4> ccItabella4s = new HashSet<CcItabella4>(0);
    private Set<IAttivita> attivitas = new HashSet<IAttivita>(0);
    private Set<IstanzecalcolocanoniT> istanzecalcolocanoniTs = new HashSet<IstanzecalcolocanoniT>(0);
    private Set<Istanzeeventi> istanzeeventis = new HashSet<Istanzeeventi>(0);
    private Set<Istanzefidejussioni> istanzefidejussionis = new HashSet<Istanzefidejussioni>(0);
    private Set<Istanzearee> istanzearees = new HashSet<Istanzearee>(0);
    private Set<Istanzefrontoffice> istanzefrontoffices = new HashSet<Istanzefrontoffice>(0);
    private Set<IstanzelavoriT> istanzelavoriTs = new HashSet<IstanzelavoriT>(0);
    private Set<Istanzeoneri> istanzeoneris = new HashSet<Istanzeoneri>(0);
    private Set<IstanzeoneriDettaglio> istanzeoneriDettaglios = new HashSet<IstanzeoneriDettaglio>(0);
    private Set<OIcalcolocontribr> oIcalcolocontribrs = new HashSet<OIcalcolocontribr>(0);
    private Set<OIcalcolocontribrRiduz> oIcalcolocontribrRiduzs = new HashSet<OIcalcolocontribrRiduz>(0);
    private Set<OIcalcolocontribt> oIcalcolocontribts = new HashSet<OIcalcolocontribt>(0);
    private Set<OIcalcolocontribtBto> oIcalcolocontribtBtos = new HashSet<OIcalcolocontribtBto>(0);
    private Set<OIcalcoloDettaglior> oIcalcoloDettagliors = new HashSet<OIcalcoloDettaglior>(0);
    private Set<OIcalcoloDettagliot> oIcalcoloDettagliots = new HashSet<OIcalcoloDettagliot>(0);
    private Set<OIcalcolotot> oIcalcolotots = new HashSet<OIcalcolotot>(0);
    private Set<Permistanze> permistanzes = new HashSet<Permistanze>(0);
    private Set<Registrazioni> registrazionis = new HashSet<Registrazioni>(0);
    private Set<Sorteggidettaglio> sorteggidettaglios = new HashSet<Sorteggidettaglio>(0);
    private Set<Istanzereplicate> istanzesForFkIstanzapadre = new HashSet<Istanzereplicate>(0);
    private Set<Istanzereplicate> istanzesForFkIstanzafiglia = new HashSet<Istanzereplicate>(0);
    private Set<Istanzeaffissioni> istanzeaffissionis = new HashSet<Istanzeaffissioni>(0);
    private Set<Cds> cdss = new HashSet<Cds>(0);
    private Set<BatchScadenzario> batchScadenzarios = new HashSet<BatchScadenzario>(0);
    private Set<TipimovimentoDis> tipimovimentoDis = new HashSet<TipimovimentoDis>(0);
    private Set<Istanzecollegate> istanzecollegates = new HashSet<Istanzecollegate>(0);
    private Set<Istanzecollegate> istanzecollegatesForFkCollegata = new HashSet<Istanzecollegate>();
    private Set<Istanzeprocure> istanzeprocures = new HashSet<Istanzeprocure>();

    public IstanzeListsDTO() {

    }

    public Set<Istanzedyn2dati> getIstanzedyn2datis() {

	return istanzedyn2datis;
    }

    public void setIstanzedyn2datis(Set<Istanzedyn2dati> istanzedyn2datis) {

	this.istanzedyn2datis = istanzedyn2datis;
    }

    public Set<Istanzedyn2modellit> getIstanzedyn2modellit() {

	return istanzedyn2modellit;
    }

    public void setIstanzedyn2modellit(Set<Istanzedyn2modellit> istanzedyn2modellit) {

	this.istanzedyn2modellit = istanzedyn2modellit;
    }

    public Set<Graduatoried> getGraduatorieds() {

	return graduatorieds;
    }

    public void setGraduatorieds(Set<Graduatoried> graduatorieds) {

	this.graduatorieds = graduatorieds;
    }

    public Set<Istanzeprocedimenti> getIstanzeprocedimentis() {

	return istanzeprocedimentis;
    }

    public void setIstanzeprocedimentis(Set<Istanzeprocedimenti> istanzeprocedimentis) {

	this.istanzeprocedimentis = istanzeprocedimentis;
    }

    public Set<Istanzeattivita> getIstanzeattivitas() {

	return istanzeattivitas;
    }

    public void setIstanzeattivitas(Set<Istanzeattivita> istanzeattivitas) {

	this.istanzeattivitas = istanzeattivitas;
    }

    public Set<Movimenti> getIstanzemovimentis() {

	return istanzemovimentis;
    }

    public void setIstanzemovimentis(Set<Movimenti> istanzemovimentis) {

	this.istanzemovimentis = istanzemovimentis;
    }

    public Set<Istanzestradario> getIstanzestradarios() {

	return istanzestradarios;
    }

    public void setIstanzestradarios(Set<Istanzestradario> istanzestradarios) {

	this.istanzestradarios = istanzestradarios;
    }

    public Set<Documentiistanza> getDocumentiistanzas() {

	return documentiistanzas;
    }

    public void setDocumentiistanzas(Set<Documentiistanza> documentiistanzas) {

	this.documentiistanzas = documentiistanzas;
    }

    public Set<Istanzeallegati> getIstanzeallegatis() {

	return istanzeallegatis;
    }

    public void setIstanzeallegatis(Set<Istanzeallegati> istanzeallegatis) {

	this.istanzeallegatis = istanzeallegatis;
    }

    public Set<Istanzerichiedenti> getIstanzerichiedentis() {

	return istanzerichiedentis;
    }

    public void setIstanzerichiedentis(Set<Istanzerichiedenti> istanzerichiedentis) {

	this.istanzerichiedentis = istanzerichiedentis;
    }

    public Set<Istanzepeopled> getIstanzepeopleds() {

	return istanzepeopleds;
    }

    public void setIstanzepeopleds(Set<Istanzepeopled> istanzepeopleds) {

	this.istanzepeopleds = istanzepeopleds;
    }

    public Set<Istanzeruoli> getIstanzeruolis() {

	return istanzeruolis;
    }

    public void setIstanzeruolis(Set<Istanzeruoli> istanzeruolis) {

	this.istanzeruolis = istanzeruolis;
    }

    public Set<Istanzemappali> getIstanzemappalis() {

	return istanzemappalis;
    }

    public void setIstanzemappalis(Set<Istanzemappali> istanzemappalis) {

	this.istanzemappalis = istanzemappalis;
    }

    public Set<Orariaperturatestata> getOrariaperturatestatas() {

	return orariaperturatestatas;
    }

    public void setOrariaperturatestatas(Set<Orariaperturatestata> orariaperturatestatas) {

	this.orariaperturatestatas = orariaperturatestatas;
    }

    public Set<Autorizzazioni> getAutorizzazionis() {

	return autorizzazionis;
    }

    public void setAutorizzazionis(Set<Autorizzazioni> autorizzazionis) {

	this.autorizzazionis = autorizzazionis;
    }

    public Set<AutorizzazioniSubentri> getAutorizzazionisubentris() {

	return autorizzazionisubentris;
    }

    public void setAutorizzazionisubentris(Set<AutorizzazioniSubentri> autorizzazionisubentris) {

	this.autorizzazionisubentris = autorizzazionisubentris;
    }

    public Set<CcIcalcoliDettaglior> getCcIcalcoliDettagliors() {

	return ccIcalcoliDettagliors;
    }

    public void setCcIcalcoliDettagliors(Set<CcIcalcoliDettaglior> ccIcalcoliDettagliors) {

	this.ccIcalcoliDettagliors = ccIcalcoliDettagliors;
    }

    public Set<CcIcalcoli> getCcIcalcolis() {

	return ccIcalcolis;
    }

    public void setCcIcalcolis(Set<CcIcalcoli> ccIcalcolis) {

	this.ccIcalcolis = ccIcalcolis;
    }

    public Set<CcIcalcoliDettagliot> getCcIcalcoliDettagliots() {

	return ccIcalcoliDettagliots;
    }

    public void setCcIcalcoliDettagliots(Set<CcIcalcoliDettagliot> ccIcalcoliDettagliots) {

	this.ccIcalcoliDettagliots = ccIcalcoliDettagliots;
    }

    public Set<CcIcalcoloDcontributo> getCcIcalcoloDcontributos() {

	return ccIcalcoloDcontributos;
    }

    public void setCcIcalcoloDcontributos(Set<CcIcalcoloDcontributo> ccIcalcoloDcontributos) {

	this.ccIcalcoloDcontributos = ccIcalcoloDcontributos;
    }

    public Set<CcIcalcoloDcontribattiv> getCcIcalcoloDcontribattivs() {

	return ccIcalcoloDcontribattivs;
    }

    public void setCcIcalcoloDcontribattivs(Set<CcIcalcoloDcontribattiv> ccIcalcoloDcontribattivs) {

	this.ccIcalcoloDcontribattivs = ccIcalcoloDcontribattivs;
    }

    public Set<CcIcalcoloTcontributo> getCcIcalcoloTcontributos() {

	return ccIcalcoloTcontributos;
    }

    public void setCcIcalcoloTcontributos(Set<CcIcalcoloTcontributo> ccIcalcoloTcontributos) {

	this.ccIcalcoloTcontributos = ccIcalcoloTcontributos;
    }

    public Set<CcIcalcolotcontributoRiduz> getCcIcalcolotcontributoRiduzs() {

	return ccIcalcolotcontributoRiduzs;
    }

    public void setCcIcalcolotcontributoRiduzs(Set<CcIcalcolotcontributoRiduz> ccIcalcolotcontributoRiduzs) {

	this.ccIcalcolotcontributoRiduzs = ccIcalcolotcontributoRiduzs;
    }

    public Set<CcIcalcolotot> getCcIcalcolotots() {

	return ccIcalcolotots;
    }

    public void setCcIcalcolotots(Set<CcIcalcolotot> ccIcalcolotots) {

	this.ccIcalcolotots = ccIcalcolotots;
    }

    public Set<CcItabella1> getCcItabella1s() {

	return ccItabella1s;
    }

    public void setCcItabella1s(Set<CcItabella1> ccItabella1s) {

	this.ccItabella1s = ccItabella1s;
    }

    public Set<CcItabella2> getCcItabella2s() {

	return ccItabella2s;
    }

    public void setCcItabella2s(Set<CcItabella2> ccItabella2s) {

	this.ccItabella2s = ccItabella2s;
    }

    public Set<CcItabella3> getCcItabella3s() {

	return ccItabella3s;
    }

    public void setCcItabella3s(Set<CcItabella3> ccItabella3s) {

	this.ccItabella3s = ccItabella3s;
    }

    public Set<CcItabella4> getCcItabella4s() {

	return ccItabella4s;
    }

    public void setCcItabella4s(Set<CcItabella4> ccItabella4s) {

	this.ccItabella4s = ccItabella4s;
    }

    public Set<IAttivita> getAttivitas() {

	return attivitas;
    }

    public void setAttivitas(Set<IAttivita> attivitas) {

	this.attivitas = attivitas;
    }

    public Set<IstanzecalcolocanoniT> getIstanzecalcolocanoniTs() {

	return istanzecalcolocanoniTs;
    }

    public void setIstanzecalcolocanoniTs(Set<IstanzecalcolocanoniT> istanzecalcolocanoniTs) {

	this.istanzecalcolocanoniTs = istanzecalcolocanoniTs;
    }

    public Set<Istanzeeventi> getIstanzeeventis() {

	return istanzeeventis;
    }

    public void setIstanzeeventis(Set<Istanzeeventi> istanzeeventis) {

	this.istanzeeventis = istanzeeventis;
    }

    public Set<Istanzefidejussioni> getIstanzefidejussionis() {

	return istanzefidejussionis;
    }

    public void setIstanzefidejussionis(Set<Istanzefidejussioni> istanzefidejussionis) {

	this.istanzefidejussionis = istanzefidejussionis;
    }

    public Set<Istanzearee> getIstanzearees() {

	return istanzearees;
    }

    public void setIstanzearees(Set<Istanzearee> istanzearees) {

	this.istanzearees = istanzearees;
    }

    public Set<Istanzefrontoffice> getIstanzefrontoffices() {

	return istanzefrontoffices;
    }

    public void setIstanzefrontoffices(Set<Istanzefrontoffice> istanzefrontoffices) {

	this.istanzefrontoffices = istanzefrontoffices;
    }

    public Set<IstanzelavoriT> getIstanzelavoriTs() {

	return istanzelavoriTs;
    }

    public void setIstanzelavoriTs(Set<IstanzelavoriT> istanzelavoriTs) {

	this.istanzelavoriTs = istanzelavoriTs;
    }

    public Set<Istanzeoneri> getIstanzeoneris() {

	return istanzeoneris;
    }

    public void setIstanzeoneris(Set<Istanzeoneri> istanzeoneris) {

	this.istanzeoneris = istanzeoneris;
    }

    public Set<IstanzeoneriDettaglio> getIstanzeoneriDettaglios() {

	return istanzeoneriDettaglios;
    }

    public void setIstanzeoneriDettaglios(Set<IstanzeoneriDettaglio> istanzeoneriDettaglios) {

	this.istanzeoneriDettaglios = istanzeoneriDettaglios;
    }

    public Set<OIcalcolocontribr> getoIcalcolocontribrs() {

	return oIcalcolocontribrs;
    }

    public void setoIcalcolocontribrs(Set<OIcalcolocontribr> oIcalcolocontribrs) {

	this.oIcalcolocontribrs = oIcalcolocontribrs;
    }

    public Set<OIcalcolocontribrRiduz> getoIcalcolocontribrRiduzs() {

	return oIcalcolocontribrRiduzs;
    }

    public void setoIcalcolocontribrRiduzs(Set<OIcalcolocontribrRiduz> oIcalcolocontribrRiduzs) {

	this.oIcalcolocontribrRiduzs = oIcalcolocontribrRiduzs;
    }

    public Set<OIcalcolocontribt> getoIcalcolocontribts() {

	return oIcalcolocontribts;
    }

    public void setoIcalcolocontribts(Set<OIcalcolocontribt> oIcalcolocontribts) {

	this.oIcalcolocontribts = oIcalcolocontribts;
    }

    public Set<OIcalcolocontribtBto> getoIcalcolocontribtBtos() {

	return oIcalcolocontribtBtos;
    }

    public void setoIcalcolocontribtBtos(Set<OIcalcolocontribtBto> oIcalcolocontribtBtos) {

	this.oIcalcolocontribtBtos = oIcalcolocontribtBtos;
    }

    public Set<OIcalcoloDettaglior> getoIcalcoloDettagliors() {

	return oIcalcoloDettagliors;
    }

    public void setoIcalcoloDettagliors(Set<OIcalcoloDettaglior> oIcalcoloDettagliors) {

	this.oIcalcoloDettagliors = oIcalcoloDettagliors;
    }

    public Set<OIcalcoloDettagliot> getoIcalcoloDettagliots() {

	return oIcalcoloDettagliots;
    }

    public void setoIcalcoloDettagliots(Set<OIcalcoloDettagliot> oIcalcoloDettagliots) {

	this.oIcalcoloDettagliots = oIcalcoloDettagliots;
    }

    public Set<OIcalcolotot> getoIcalcolotots() {

	return oIcalcolotots;
    }

    public void setoIcalcolotots(Set<OIcalcolotot> oIcalcolotots) {

	this.oIcalcolotots = oIcalcolotots;
    }

    public Set<Permistanze> getPermistanzes() {

	return permistanzes;
    }

    public void setPermistanzes(Set<Permistanze> permistanzes) {

	this.permistanzes = permistanzes;
    }

    public Set<Registrazioni> getRegistrazionis() {

	return registrazionis;
    }

    public void setRegistrazionis(Set<Registrazioni> registrazionis) {

	this.registrazionis = registrazionis;
    }

    public Set<Sorteggidettaglio> getSorteggidettaglios() {

	return sorteggidettaglios;
    }

    public void setSorteggidettaglios(Set<Sorteggidettaglio> sorteggidettaglios) {

	this.sorteggidettaglios = sorteggidettaglios;
    }

    public Set<Istanzereplicate> getIstanzesForFkIstanzapadre() {

	return istanzesForFkIstanzapadre;
    }

    public void setIstanzesForFkIstanzapadre(Set<Istanzereplicate> istanzesForFkIstanzapadre) {

	this.istanzesForFkIstanzapadre = istanzesForFkIstanzapadre;
    }

    public Set<Istanzereplicate> getIstanzesForFkIstanzafiglia() {

	return istanzesForFkIstanzafiglia;
    }

    public void setIstanzesForFkIstanzafiglia(Set<Istanzereplicate> istanzesForFkIstanzafiglia) {

	this.istanzesForFkIstanzafiglia = istanzesForFkIstanzafiglia;
    }

    public Set<Istanzeaffissioni> getIstanzeaffissionis() {

	return istanzeaffissionis;
    }

    public void setIstanzeaffissionis(Set<Istanzeaffissioni> istanzeaffissionis) {

	this.istanzeaffissionis = istanzeaffissionis;
    }

    public Set<Cds> getCdss() {

	return cdss;
    }

    public void setCdss(Set<Cds> cdss) {

	this.cdss = cdss;
    }

    public Set<BatchScadenzario> getBatchScadenzarios() {

	return batchScadenzarios;
    }

    public void setBatchScadenzarios(Set<BatchScadenzario> batchScadenzarios) {

	this.batchScadenzarios = batchScadenzarios;
    }

    public void setTipimovimentoDis(Set<TipimovimentoDis> tipimovimentoDis) {

	this.tipimovimentoDis = tipimovimentoDis;
    }

    public Set<TipimovimentoDis> getTipimovimentoDis() {

	return tipimovimentoDis;
    }

    public Set<Istanzecollegate> getIstanzecollegates() {

	return istanzecollegates;
    }

    public void setIstanzecollegates(Set<Istanzecollegate> istanzecollegates) {

	this.istanzecollegates = istanzecollegates;
    }

    public Set<Istanzecollegate> getIstanzecollegatesForFkCollegata() {

	return istanzecollegatesForFkCollegata;
    }

    public void setIstanzecollegatesForFkCollegata(Set<Istanzecollegate> istanzecollegatesForFkCollegata) {

	this.istanzecollegatesForFkCollegata = istanzecollegatesForFkCollegata;
    }

    public Set<Istanzeprocure> getIstanzeprocures() {

	return istanzeprocures;
    }

    public void setIstanzeprocures(Set<Istanzeprocure> istanzeprocures) {

	this.istanzeprocures = istanzeprocures;
    }
}
