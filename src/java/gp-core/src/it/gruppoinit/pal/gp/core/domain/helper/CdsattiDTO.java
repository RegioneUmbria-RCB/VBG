package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class CdsattiDTO {

    private PkId id;
    private Date data;
    private String ora;
    private String verbale;
    private String note;
    private Date dataconvocazione;
    private String oraconvocazione;
    private Date dataconvocazione2;
    private String oraconvocazione2;
    private String chiusa;
    private String fileverbale;
    private Boolean positivia;
    private Integer codiceoggetto;
    private String nomefile;
    private Integer dimensioneFile;
    // Campo transient utilizzato per segnare le procure da inviare nella
    // funzionalità : invia email
    private boolean transientSegnaPerInvio;
    private boolean transientSegnaPerInvioPec;

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getOra() {

	return ora;
    }

    public void setOra(String ora) {

	this.ora = ora;
    }

    public String getVerbale() {

	return verbale;
    }

    public void setVerbale(String verbale) {

	this.verbale = verbale;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Date getDataconvocazione() {

	return dataconvocazione;
    }

    public void setDataconvocazione(Date dataconvocazione) {

	this.dataconvocazione = dataconvocazione;
    }

    public String getOraconvocazione() {

	return oraconvocazione;
    }

    public void setOraconvocazione(String oraconvocazione) {

	this.oraconvocazione = oraconvocazione;
    }

    public Date getDataconvocazione2() {

	return dataconvocazione2;
    }

    public void setDataconvocazione2(Date dataconvocazione2) {

	this.dataconvocazione2 = dataconvocazione2;
    }

    public String getOraconvocazione2() {

	return oraconvocazione2;
    }

    public void setOraconvocazione2(String oraconvocazione2) {

	this.oraconvocazione2 = oraconvocazione2;
    }

    public String getChiusa() {

	return chiusa;
    }

    public void setChiusa(String chiusa) {

	this.chiusa = chiusa;
    }

    public String getFileverbale() {

	return fileverbale;
    }

    public void setFileverbale(String fileverbale) {

	this.fileverbale = fileverbale;
    }

    public Boolean getPositivia() {

	return positivia;
    }

    public void setPositivia(Boolean positivia) {

	this.positivia = positivia;
    }

    public Integer getCodiceoggetto() {

	return codiceoggetto;
    }

    public void setCodiceoggetto(Integer codiceoggetto) {

	this.codiceoggetto = codiceoggetto;
    }

    public String getNomefile() {

	return nomefile;
    }

    public void setNomefile(String nomefile) {

	this.nomefile = nomefile;
    }

    public Integer getDimensioneFile() {

	return dimensioneFile;
    }

    public void setDimensioneFile(Integer dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
    }

    public boolean getTransientSegnaPerInvio() {

	return transientSegnaPerInvio;
    }

    public void setTransientSegnaPerInvio(boolean transientSegnaPerInvio) {

	this.transientSegnaPerInvio = transientSegnaPerInvio;
    }

    public boolean isTransientSegnaPerInvioPec() {

	return transientSegnaPerInvioPec;
    }

    public void setTransientSegnaPerInvioPec(boolean transientSegnaPerInvioPec) {

	this.transientSegnaPerInvioPec = transientSegnaPerInvioPec;
    }
}
