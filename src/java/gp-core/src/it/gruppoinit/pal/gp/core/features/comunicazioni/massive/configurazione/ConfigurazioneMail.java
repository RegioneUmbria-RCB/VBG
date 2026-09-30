package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

public class ConfigurazioneMail {

    private int idMailTipo;
    private int senderAccount;

    public ConfigurazioneMail() {

    }

    public ConfigurazioneMail(int senderAccount, int idMailTipo) {

	this.senderAccount = senderAccount;
	this.idMailTipo = idMailTipo;
    }

    public int getIdMailTipo() {

	return idMailTipo;
    }

    public void setIdMailTipo(int idMailTipo) {

	this.idMailTipo = idMailTipo;
    }

    public int getSenderAccount() {

	return senderAccount;
    }

    public void setSenderAccount(int senderAccount) {

	this.senderAccount = senderAccount;
    }
}
