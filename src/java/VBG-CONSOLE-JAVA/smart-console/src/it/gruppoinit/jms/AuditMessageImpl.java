package it.gruppoinit.jms;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.util.Calendar;
import java.util.Date;

public class AuditMessageImpl implements AuditMessage {

    /**
     * 
     */
    private static final long serialVersionUID = 2114802535663443929L;
    private String appChiamante;
    private String domainId;
    private String utente;
    private String indirizzoIp;
    private String tipoMessaggio;
    private byte[] messaggio;
    private Date dataOra;
    private String azione;

    public AuditMessageImpl(String utente, String indirizzoIP, String tipoMessaggio, String azione, byte[] messaggio) {

	super();
	this.utente = utente;
	this.indirizzoIp = indirizzoIP;
	this.tipoMessaggio = tipoMessaggio;
	this.azione = azione;
	this.messaggio = messaggio;
	this.dataOra = Calendar.getInstance().getTime();
	this.appChiamante = WebConstants.JMS_APPLICATION_ID;
	this.domainId = ORMHelper.getIdcomune();
    }

    public String getAppChiamante() {

	return appChiamante;
    }

    public void setAppChiamante(String appChiamante) {

	this.appChiamante = appChiamante;
    }

    public String getUtente() {

	return utente;
    }

    public void setUtente(String utente) {

	this.utente = utente;
    }

    public String getIndirizzoIp() {

	return indirizzoIp;
    }

    public void setIndirizzoIp(String indirizzoIp) {

	this.indirizzoIp = indirizzoIp;
    }

    public String getTipoMessaggio() {

	return tipoMessaggio;
    }

    public void setTipoMessaggio(String tipoMessaggio) {

	this.tipoMessaggio = tipoMessaggio;
    }

    public byte[] getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(byte[] messaggio) {

	this.messaggio = messaggio;
    }

    public Date getDataOra() {

	return dataOra;
    }

    public void setDataOra(Date dataOra) {

	this.dataOra = dataOra;
    }

    public void setDomainId(String domainId) {

	this.domainId = domainId;
    }

    public String getDomainId() {

	return domainId;
    }

    @Override
    public String getAzione() {

	return this.azione;
    }

    @Override
    public void setAzione(String azione) {

	this.azione = azione;
    }
}
