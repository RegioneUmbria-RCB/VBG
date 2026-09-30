package it.gruppoinit.pal.gp.backoffice.schemas.messages.movimentimail;

import java.math.BigInteger;
import java.util.List;

public class MailRequestBase {

    private String token;
    private String software;
    private BigInteger idaccount;
    private String codicemovimento;
    private String messageId;
    private String mittente;
    private String destinatario;
    private String destinatariocc;
    private String destinatariobcc;
    private String oggetto;
    private String corpo;
    private List<AllegatoType> allegati;

    public static MailRequestBase fromMovimentiMailRequest2(MovimentiMailRequest2 req) {

	MailRequestBase ret = new MailRequestBase();
	ret.setAllegati(req.getAllegati());
	ret.setCodicemovimento(req.getCodicemovimento());
	ret.setCorpo(req.getCorpo());
	ret.setDestinatario(req.getDestinatario());
	ret.setDestinatariobcc(req.getDestinatariobcc());
	ret.setDestinatariocc(req.getDestinatariocc());
	ret.setIdaccount(req.getIdaccount());
	ret.setMessageId(req.getMessageId());
	ret.setMittente(req.getMittente());
	ret.setOggetto(req.getOggetto());
	ret.setSoftware(req.getSoftware());
	ret.setToken(req.getToken());
	return ret;
    }

    public static MailRequestBase fromMovimentiMailRequest(MovimentiMailRequest req) {

	MailRequestBase ret = new MailRequestBase();
	ret.setAllegati(req.getAllegati());
	ret.setCodicemovimento(req.getCodicemovimento());
	ret.setCorpo(req.getCorpo());
	ret.setDestinatario(req.getDestinatario());
	ret.setDestinatariobcc(req.getDestinatariobcc());
	ret.setDestinatariocc(req.getDestinatariocc());
	ret.setMessageId(req.getMessageId());
	ret.setMittente(req.getMittente());
	ret.setOggetto(req.getOggetto());
	ret.setSoftware(req.getSoftware());
	ret.setToken(req.getToken());
	return ret;
    }

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public BigInteger getIdaccount() {

	return idaccount;
    }

    public void setIdaccount(BigInteger idaccount) {

	this.idaccount = idaccount;
    }

    public String getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(String codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public String getMessageId() {

	return messageId;
    }

    public void setMessageId(String messageId) {

	this.messageId = messageId;
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

    public String getCorpo() {

	return corpo;
    }

    public void setCorpo(String corpo) {

	this.corpo = corpo;
    }

    public List<AllegatoType> getAllegati() {

	return allegati;
    }

    public void setAllegati(List<AllegatoType> allegati) {

	this.allegati = allegati;
    }
}
