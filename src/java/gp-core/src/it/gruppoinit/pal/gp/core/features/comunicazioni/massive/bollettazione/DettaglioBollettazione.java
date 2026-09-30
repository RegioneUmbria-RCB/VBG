package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

public class DettaglioBollettazione {

    private int idRigaDettaglio;
    private int codiceAnagrafe;
    private String mail;

    public int getIdRigaDettaglio() {

	return idRigaDettaglio;
    }

    public void setIdRigaDettaglio(int idRigaDettaglio) {

	this.idRigaDettaglio = idRigaDettaglio;
    }

    public int getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(int codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public String getMail() {

	return mail;
    }

    public void setMail(String mail) {

	this.mail = mail;
    }
}
