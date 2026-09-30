package it.alveo.ricalcoloaree.respdata;

public class RespSessionData {

    private String status;
    private String messaggio;
    private int fatte;
    private int daFaree;
    private int totali;

    // Costruttore
    public RespSessionData(String status, String messaggio, int fatte, int daFaree, int totali) {

	this.status = status;
	this.messaggio = messaggio;
	this.fatte = fatte;
	this.daFaree = daFaree;
	this.totali = totali;
    }

    // Getters and Setters
    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public int getFatte() {

	return fatte;
    }

    public void setFatte(int fatte) {

	this.fatte = fatte;
    }

    public int getDaFaree() {

	return daFaree;
    }

    public void setDaFaree(int daFaree) {

	this.daFaree = daFaree;
    }

    public int getTotali() {

	return totali;
    }

    public void setTotali(int totali) {

	this.totali = totali;
    }
}
