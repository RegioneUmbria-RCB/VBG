package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.model;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;

public class DettaglioPresenzaComunicazioneModel {

    private int codiceAnagrafe;
    private int idPresenza;
    private String email;
    private String pec;

    public int getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(int codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public int getIdPresenza() {

	return idPresenza;
    }

    public void setIdPresenza(int idPresenza) {

	this.idPresenza = idPresenza;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    /**
     * Se occupante nullo allora torna oggetto nullo
     * 
     * @param pres
     * @return
     */
    public static DettaglioPresenzaComunicazioneModel forOccupantefromMercatipresenzeD(MercatipresenzeD pres) {

	if (pres == null || pres.getOccupante() == null) {
	    return null;
	}
	DettaglioPresenzaComunicazioneModel ret = new DettaglioPresenzaComunicazioneModel();
	ret.setCodiceAnagrafe(pres.getOccupante().getId().getCodice());
	ret.setIdPresenza(pres.getId().getCodice());
	ret.setEmail(pres.getOccupante().getEmail());
	ret.setPec(pres.getOccupante().getPec());
	return ret;
    }
}
