package it.gruppoinit.domain;

import java.io.Serializable;

public class AlberoInterventiBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3852297845338307215L;
    private Integer scId;
    private String software;
    private String scCodice;
    private String scDescrizione;
    private Integer scPadre;
    private String scStatoControllo;
    private Integer scOrdine;
    private Integer fkidazione;
    private String atribTipologiaintervento;
    private Integer scAttivo;
    private String descrizioneCompleta;

    public AlberoInterventiBean() {

	super();
    }

    public AlberoInterventiBean(Integer scId, String software, String scCodice, String scDescrizione, Integer scPadre, String scStatoControllo,
	    Integer scOrdine, Integer fkidazione, String atribTipologiaintervento, Integer scAttivo, String descrizioneCompleta) {

	this();
	this.scId = scId;
	this.software = software;
	this.scCodice = scCodice;
	this.scDescrizione = scDescrizione;
	this.scPadre = scPadre;
	this.scStatoControllo = scStatoControllo;
	this.scOrdine = scOrdine;
	this.fkidazione = fkidazione;
	this.atribTipologiaintervento = atribTipologiaintervento;
	this.scAttivo = scAttivo;
	this.descrizioneCompleta = descrizioneCompleta;
    }

    public Integer getScId() {

	return scId;
    }

    public void setScId(Integer scId) {

	this.scId = scId;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getScCodice() {

	return scCodice;
    }

    public void setScCodice(String scCodice) {

	this.scCodice = scCodice;
    }

    public String getScDescrizione() {

	return scDescrizione;
    }

    public void setScDescrizione(String scDescrizione) {

	this.scDescrizione = scDescrizione;
    }

    public Integer getScPadre() {

	return scPadre;
    }

    public void setScPadre(Integer scPadre) {

	this.scPadre = scPadre;
    }

    public String getScStatoControllo() {

	return scStatoControllo;
    }

    public void setScStatoControllo(String scStatoControllo) {

	this.scStatoControllo = scStatoControllo;
    }

    public Integer getScOrdine() {

	return scOrdine;
    }

    public void setScOrdine(Integer scOrdine) {

	this.scOrdine = scOrdine;
    }

    public Integer getFkidazione() {

	return fkidazione;
    }

    public void setFkidazione(Integer fkidazione) {

	this.fkidazione = fkidazione;
    }

    public String getAtribTipologiaintervento() {

	return atribTipologiaintervento;
    }

    public void setAtribTipologiaintervento(String atribTipologiaintervento) {

	this.atribTipologiaintervento = atribTipologiaintervento;
    }

    public Integer getScAttivo() {

	return scAttivo;
    }

    public void setScAttivo(Integer scAttivo) {

	this.scAttivo = scAttivo;
    }

    public String getDescrizioneCompleta() {

	return descrizioneCompleta;
    }

    public void setDescrizioneCompleta(String descrizioneCompleta) {

	this.descrizioneCompleta = descrizioneCompleta;
    }
}
