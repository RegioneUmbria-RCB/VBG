package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni;

public class DettaglioRigaCommissione {

    private int idRigaAppello;
    private Integer codiceAnagrafe;
    private Integer codiceAmministrazione;
    private Integer codiceResponsabile;
    private String mail;

    public int getIdRigaAppello() {

	return idRigaAppello;
    }

    public void setIdRigaAppello(int idRigaAppello) {

	this.idRigaAppello = idRigaAppello;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public Integer getCodiceAmministrazione() {

	return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(Integer codiceAmministrazione) {

	this.codiceAmministrazione = codiceAmministrazione;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public void setCodiceResponsabile(Integer codiceResponsabile) {

	this.codiceResponsabile = codiceResponsabile;
    }

    public String getMail() {

	return mail;
    }

    public void setMail(String mail) {

	this.mail = mail;
    }
}
