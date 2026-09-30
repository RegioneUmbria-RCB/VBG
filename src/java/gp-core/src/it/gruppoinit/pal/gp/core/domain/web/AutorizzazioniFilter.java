package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

public class AutorizzazioniFilter {

    private Date dallaData;
    private Date allaData;
    private Date dallaDataScadenza;
    private Date allaDataScadenza;
    private String autoriznumero;
    private VwEntilocali autorizcomune;
    private Tipologiaregistri tipologiaregistro;
    private Anagrafe anagrafe;
    private boolean includiCessate;
    private boolean cercaTraSubentrate;
    private boolean escludiConcessioni;
    private boolean escludiLeAutorizzazioniCollegate;
    private boolean soloFlagManifestazioni;
    private Integer codiceIstanzaDaEscludere;
    private Mercati mercati;
    private MercatiUso mercatiUso;
    private MercatiD mercatiD;
    private IstanzeFilter istanzeFilter;
    private Date dataFineAffitto;
    private int maxRows;
    //Campi per gestire l'ordinamento 
    private String orderBy;
    private OrderTypeEnum orderAscDesc = OrderTypeEnum.ASC;
    // Contientiene il nome della proprietà che rappresenta il campo per cui ordiniamo la lista 
    //utilizzata dalla funzionalità di stampa  MS (Es. istanza.numeroistanza---> Codice Istanza)
    private String orderByMappingMsApplication;
    // Contientiene il nome della proprietà che rappresenta il tipo di ordinamento della lista 
    //utilizzata dalla funzionalità di stampa  MS (Es. ASC ---> Crescente)
    private String orderAscDescMappingMsApplication;
    private Map<String, Object> proprietaModificate;

    public AutorizzazioniFilter() {

	this.autorizcomune = new VwEntilocali();
	this.tipologiaregistro = new Tipologiaregistri();
	this.anagrafe = new Anagrafe();
	this.mercati = new Mercati();
	this.mercatiUso = new MercatiUso();
	this.mercatiD = new MercatiD();
	this.istanzeFilter = new IstanzeFilter();
	this.escludiLeAutorizzazioniCollegate = false;
    }

    public boolean isEscludiLeAutorizzazioniCollegate() {

	return escludiLeAutorizzazioniCollegate;
    }

    public void setEscludiLeAutorizzazioniCollegate(boolean escludiLeAutorizzazioniCollegate) {

	this.escludiLeAutorizzazioniCollegate = escludiLeAutorizzazioniCollegate;
    }

    public Date getDallaDataScadenza() {

	return dallaDataScadenza;
    }

    public void setDallaDataScadenza(Date dallaDataScadenza) {

	this.dallaDataScadenza = dallaDataScadenza;
    }

    public Date getAllaDataScadenza() {

	return allaDataScadenza;
    }

    public void setAllaDataScadenza(Date allaDataScadenza) {

	this.allaDataScadenza = allaDataScadenza;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    public String getAutoriznumero() {

	return autoriznumero;
    }

    public void setAutoriznumero(String autoriznumero) {

	this.autoriznumero = autoriznumero;
    }

    public VwEntilocali getAutorizcomune() {

	return autorizcomune;
    }

    public void setAutorizcomune(VwEntilocali autorizcomune) {

	this.autorizcomune = autorizcomune;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setTipologiaregistro(Tipologiaregistri tipologiaregistro) {

	this.tipologiaregistro = tipologiaregistro;
    }

    public Tipologiaregistri getTipologiaregistro() {

	return tipologiaregistro;
    }

    public void setIncludiCessate(boolean includiCessate) {

	this.includiCessate = includiCessate;
    }

    public boolean getIncludiCessate() {

	return includiCessate;
    }

    public boolean getCercaTraSubentrate() {

	return cercaTraSubentrate;
    }

    public void setCercaTraSubentrate(boolean cercaTraSubentrate) {

	this.cercaTraSubentrate = cercaTraSubentrate;
    }

    public void setCodiceIstanzaDaEscludere(Integer codiceIstanzaDaEscludere) {

	this.codiceIstanzaDaEscludere = codiceIstanzaDaEscludere;
    }

    public Integer getCodiceIstanzaDaEscludere() {

	return codiceIstanzaDaEscludere;
    }

    public void setEscludiConcessioni(boolean escludiConcessioni) {

	this.escludiConcessioni = escludiConcessioni;
    }

    public boolean isEscludiConcessioni() {

	return escludiConcessioni;
    }

    public Mercati getMercati() {

	return mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public MercatiD getMercatiD() {

	return mercatiD;
    }

    public void setMercatiD(MercatiD mercatiD) {

	this.mercatiD = mercatiD;
    }

    public IstanzeFilter getIstanzeFilter() {

	return istanzeFilter;
    }

    public void setIstanzeFilter(IstanzeFilter istanzeFilter) {

	this.istanzeFilter = istanzeFilter;
    }

    public Date getDataFineAffitto() {

	return dataFineAffitto;
    }

    public void setDataFineAffitto(Date dataFineAffitto) {

	this.dataFineAffitto = dataFineAffitto;
    }

    public void setMaxRows(int maxRows) {

	this.maxRows = maxRows;
    }

    public int getMaxRows() {

	return maxRows;
    }

    public String getOrderBy() {

	return orderBy;
    }

    public void firePropertyChange(String label, Object oldvalue, Object newValue) {

	if (proprietaModificate == null) {
	    proprietaModificate = new HashMap<String, Object>();
	}
	proprietaModificate.put(label, newValue);
    }

    public void setOrderBy(String orderBy) {

	this.firePropertyChange("label.ordinare_la_lista_per", this.orderBy, orderBy);
	this.orderBy = orderBy;
    }

    public void setOrderAscDesc(OrderTypeEnum orderAscDesc) {

	this.firePropertyChange("label.ordinamento", this.orderAscDesc, orderAscDesc);
	this.orderAscDesc = orderAscDesc;
    }

    public OrderTypeEnum getOrderAscDesc() {

	return orderAscDesc;
    }

    public String getOrderByMappingMsApplication() {

	return orderByMappingMsApplication;
    }

    public void setOrderByMappingMsApplication(String orderByMappingMsApplication) {

	this.orderByMappingMsApplication = orderByMappingMsApplication;
    }

    public String getOrderAscDescMappingMsApplication() {

	return orderAscDescMappingMsApplication;
    }

    public void setOrderAscDescMappingMsApplication(String orderAscDescMappingMsApplication) {

	this.orderAscDescMappingMsApplication = orderAscDescMappingMsApplication;
    }

    public boolean isSoloFlagManifestazioni() {

	return soloFlagManifestazioni;
    }

    public void setSoloFlagManifestazioni(boolean soloFlagManifestazioni) {

	this.soloFlagManifestazioni = soloFlagManifestazioni;
    }
}
