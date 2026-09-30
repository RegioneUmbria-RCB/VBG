package it.gruppoinit.pal.gp.core.features.messaggimail.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessaggiMailDTO implements IMessaggiMail {

    private Integer id;
    private String mittente;
    private String destinatario;
    private String destinatariocc;
    private String destinatariobcc;
    private String oggetto;
    private Date dataInvio;
    private String messageId;
    private String corpo;
    private Integer accountId;
    private Integer idPadre;
    private List<IAllegatoMail> allegati;

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

    public String getCorpo() {

	return corpo;
    }

    public void setCorpo(String corpo) {

	this.corpo = corpo;
    }

    public Integer getAccountId() {

	return accountId;
    }

    public void setAccountId(Integer accountId) {

	this.accountId = accountId;
    }

    public Integer getIdPadre() {

	return idPadre;
    }

    public void setIdPadre(Integer idPadre) {

	this.idPadre = idPadre;
    }

    @Override
    public List<IAllegatoMail> getAllegati() {

	if (this.allegati == null) {
	    this.allegati = new ArrayList<IAllegatoMail>();
	}
	return this.allegati;
    }
}
