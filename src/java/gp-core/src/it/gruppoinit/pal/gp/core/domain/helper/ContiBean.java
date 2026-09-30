package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public class ContiBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5657378718487976364L;
    private PkId id;
    private CodiceDescrizioneBean software;
    private String descrizione;
    private String note;
    private String descrizioneConto;
    private Integer iva;
    private Date dataScadenza;

    public ContiBean() {

	super();
    }

    public ContiBean(Conti conti) {

	this();
	if (conti != null) {
	    this.id = conti.getId();
	    this.software = new CodiceDescrizioneBean(conti.getSoftware().getCodice(), conti.getSoftware().getDescrizione());
	    this.descrizione = conti.getDescrizione();
	    this.descrizioneConto = conti.getDescrizioneConto();
	    this.note = conti.getNote();
	    this.iva = conti.getIva();
	    this.dataScadenza = conti.getDataScadenza();
	}
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public CodiceDescrizioneBean getSoftware() {

	return software;
    }

    public void setSoftware(CodiceDescrizioneBean software) {

	this.software = software;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getDescrizioneConto() {

	return descrizioneConto;
    }

    public void setDescrizioneConto(String descrizioneConto) {

	this.descrizioneConto = descrizioneConto;
    }

    public Integer getIva() {

	return iva;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }
}
