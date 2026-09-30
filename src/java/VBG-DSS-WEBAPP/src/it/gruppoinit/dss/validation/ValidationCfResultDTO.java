package it.gruppoinit.dss.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationCfResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6405374414974504754L;
	
	private List<String> cfpresenti;
	private List<String> cfassenti;
	private boolean esitofirma;
	private SimpleReportSummary simpleReportSummary;
	
	public List<String> getCfpresenti() {
		if(cfpresenti == null) {
			cfpresenti = new ArrayList<>();
		}
		return cfpresenti;
	}
	public void setCfpresenti(List<String> cfpresenti) {
		this.cfpresenti = cfpresenti;
	}
	public List<String> getCfassenti() {
		if(cfassenti == null) {
			cfassenti = new ArrayList<>();
		}
		return cfassenti;
	}
	public void setCfassenti(List<String> cfassenti) {
		this.cfassenti = cfassenti;
	}
	public boolean isEsitofirma() {
		return esitofirma;
	}
	public void setEsitofirma(boolean esitofirma) {
		this.esitofirma = esitofirma;
	}
	public SimpleReportSummary getSimpleReportSummary() {
		return simpleReportSummary;
	}
	public void setSimpleReportSummary(SimpleReportSummary simpleReportSummary) {
		this.simpleReportSummary = simpleReportSummary;
	}
		

}
