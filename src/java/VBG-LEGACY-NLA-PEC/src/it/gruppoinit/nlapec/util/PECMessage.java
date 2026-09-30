package it.gruppoinit.nlapec.util;

import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;

public class PECMessage {

    private String id;
    private String tipo;
    private String rifMsgId;
    private Address[] from;
    private Address[] to;
    private Address[] cc;
    private String subject;
    private String body;
    private Date date;
    private BodyPart originalMessage;
    private boolean seen;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getRifMsgId() {

	return rifMsgId;
    }

    public void setRifMsgId(String rifMsgId) {

	this.rifMsgId = rifMsgId;
    }

    public Address[] getFrom() {

	return from;
    }

    public void setFrom(Address[] from) {

	this.from = from;
    }

    public Address[] getTo() {

	return to;
    }

    public void setTo(Address[] to) {

	this.to = to;
    }

    public String getSubject() {

	return subject;
    }

    public void setSubject(String subject) {

	this.subject = subject;
    }

    public BodyPart getOriginalMessage() {

	return originalMessage;
    }

    public void setOriginalMessage(BodyPart originalMessage) {

	this.originalMessage = originalMessage;
    }

    public void setDate(Date date) {

	this.date = date;
    }

    public Date getDate() {

	return date;
    }

    public String getBody() {

	return body;
    }

    public void setBody(String body) {

	this.body = body;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public boolean isSeen() {

	return seen;
    }

    public void setSeen(boolean seen) {

	this.seen = seen;
    }

    public Address[] getCc() {

	return cc;
    }

    public void setCc(Address[] cc) {

	this.cc = cc;
    }
}
