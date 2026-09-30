package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

public class IstanzeAccessoAttiFilter {

    private Anagrafe anagrafe;
    private IstanzeFilter istanzeFilter;
    private Date dallaData;
    private Date allaData;
    private int maxRows;
    private IstanzeAccessoAttiLog istanzeAccessoAttiLog;
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

    public IstanzeAccessoAttiFilter() {

	this.anagrafe = new Anagrafe();
	this.istanzeFilter = new IstanzeFilter();
	this.istanzeAccessoAttiLog = new IstanzeAccessoAttiLog();
    }

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public IstanzeFilter getIstanzeFilter() {

	return istanzeFilter;
    }

    public void setIstanzeFilter(IstanzeFilter istanzeFilter) {

	this.istanzeFilter = istanzeFilter;
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

    public void setMaxRows(int maxRows) {

	this.maxRows = maxRows;
    }

    public int getMaxRows() {

	return maxRows;
    }

    public IstanzeAccessoAttiLog getIstanzeAccessoAttiLog() {

	return istanzeAccessoAttiLog;
    }

    public void setIstanzeAccessoAttiLog(IstanzeAccessoAttiLog istanzeAccessoAttiLog) {

	this.istanzeAccessoAttiLog = istanzeAccessoAttiLog;
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

    public OrderTypeEnum getOrderAscDesc() {

	return orderAscDesc;
    }

    public void setOrderAscDesc(OrderTypeEnum orderAscDesc) {

	this.firePropertyChange("label.ordinamento", this.orderAscDesc, orderAscDesc);
	this.orderAscDesc = orderAscDesc;
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
}
