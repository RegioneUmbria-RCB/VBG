package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.MessaggiMail;

public class DettagliMailComunicazione {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "mittente")
    private String mittente;
    @XmlElement(name = "destinatario")
    private String destinatario;
    @XmlElement(name = "destinatariocc")
    private String destinatariocc;
    @XmlElement(name = "destinatariobcc")
    private String destinatariobcc;
    @XmlElement(name = "oggetto")
    private String oggetto;
    @XmlElement(name = "data_invio")
    private Date dataInvio;
    @XmlElement(name = "message_id")
    private String messageId;
    @XmlElement(name = "corpo")
    private String corpo;
    @XmlElement(name = "ricevute")
    private List<DettagliMailComunicazione> ricevute;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getMittente() {

	return mittente;
    }

    public void setMittente(String mittente) {

	this.mittente = mittente;
    }

    public String getDestinatario() {

	return destinatario;
    }

    public void setDestinatario(String destinatario) {

	this.destinatario = destinatario;
    }

    public String getDestinatariocc() {

	return destinatariocc;
    }

    public void setDestinatariocc(String destinatariocc) {

	this.destinatariocc = destinatariocc;
    }

    public String getDestinatariobcc() {

	return destinatariobcc;
    }

    public void setDestinatariobcc(String destinatariobcc) {

	this.destinatariobcc = destinatariobcc;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public Date getDataInvio() {

	return dataInvio;
    }

    public void setDataInvio(Date dataInvio) {

	this.dataInvio = dataInvio;
    }

    public String getMessageId() {

	return messageId;
    }

    public void setMessageId(String messageId) {

	this.messageId = messageId;
    }

    public List<DettagliMailComunicazione> getRicevute() {

	if (this.ricevute == null) {
	    this.ricevute = new ArrayList<DettagliMailComunicazione>();
	}
	return ricevute;
    }

    public void setRicevute(List<DettagliMailComunicazione> ricevute) {

	this.ricevute = ricevute;
    }

    public String getCorpo() {

	return corpo;
    }

    public void setCorpo(String corpo) {

	this.corpo = corpo;
    }

    public static DettagliMailComunicazione fromMessaggiMail(MessaggiMail m) {

	DettagliMailComunicazione o = new DettagliMailComunicazione();
	o.setId(m.getId().getCodice());
	o.setCorpo(m.getcorpo());
	o.setDataInvio(m.getdataInvio());
	o.setDestinatario(m.getdestinatario());
	o.setDestinatariobcc(m.getdestinatarioabcc());
	o.setDestinatariocc(m.getdestinatariocc());
	o.setMessageId(m.getmessageId());
	o.setMittente(m.getmittente());
	o.setOggetto(m.getoggetto());
	Set<MessaggiMail> mailCollegate = m.getMailCollegate();
	for (MessaggiMail messaggiMail : mailCollegate) {
	    o.getRicevute().add(fromMessaggiMail(messaggiMail));
	}
	return o;
    }
}
