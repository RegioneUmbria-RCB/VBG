package it.gruppoinit.pal.gp.core.domain.helper;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;

public class AmministrazioniHelper {

    private Amministrazioni amministrazioni;
    // due campi utilizzati per gestiste i tempi di risposta di ogni singola amministrazioen
    private Short attesa;
    private Boolean calcoladainizioistanza;

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public Short getAttesa() {

	return attesa;
    }

    public void setAttesa(Short attesa) {

	this.attesa = attesa;
    }

    public Boolean getCalcoladainizioistanza() {

	return calcoladainizioistanza;
    }

    public void setCalcoladainizioistanza(Boolean calcoladainizioistanza) {

	this.calcoladainizioistanza = calcoladainizioistanza;
    }
    
    public String getRecapitiReferenteIstruttoria() {
	Amministrazioni amm = this.getAmministrazioni();
	StringBuffer buffer = new StringBuffer();
	if (amm != null) {
	    if (!StringUtils.isEmpty(amm.getUfficio())) {
		buffer.append("Ufficio: ").append(amm.getUfficio()).append(" ");
	    }
	    if (!StringUtils.isEmpty(amm.getReferente())) {
		buffer.append("Referente: ").append(amm.getReferente()).append(" ");
	    }
	    if (!StringUtils.isEmpty(amm.getIndirizzo())) {
		buffer.append("Indirizzo: ").append(amm.getIndirizzo()).append(" ");
	    }
	    if (!StringUtils.isEmpty(amm.getCitta())) {
		buffer.append("Città: ").append(amm.getCitta()).append(" ");
	    }
	    if (!StringUtils.isEmpty(amm.getCap())) {
		buffer.append("CAP: ").append(amm.getCap()).append(" ");
	    }
	    if (!StringUtils.isEmpty(amm.getProvincia())) {
		buffer.append("Provincia: ").append(amm.getProvincia()).append(" ");
	    }
	    if (!StringUtils.isEmpty(amm.getTelefono1())) {
		buffer.append("Telefono: ").append(amm.getTelefono1()).append(" ");
	    }
	    if (!StringUtils.isEmpty(amm.getEmail())) {
		buffer.append("Email: ").append(amm.getEmail()).append(" ");
	    }
	}
	return buffer.toString();
    }
}
