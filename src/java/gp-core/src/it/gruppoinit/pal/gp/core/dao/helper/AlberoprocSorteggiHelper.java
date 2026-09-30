package it.gruppoinit.pal.gp.core.dao.helper;

public class AlberoprocSorteggiHelper {

    private Integer codiceAlberoproc;
    private String scCodice;
    private String descrizioneAlberoproc;
    private Integer peso;
    private Boolean necessario;

    public AlberoprocSorteggiHelper() {

	peso = Integer.valueOf(0);
	necessario = Boolean.FALSE;
    }

    public AlberoprocSorteggiHelper(Integer codiceAlberoproc, String descrizioneAlberoproc, Integer peso, Boolean necessario, String scCodice) {

	super();
	this.codiceAlberoproc = codiceAlberoproc;
	this.descrizioneAlberoproc = descrizioneAlberoproc;
	this.peso = peso;
	this.necessario = necessario;
	this.scCodice = scCodice;
    }

    public Integer getCodiceAlberoproc() {

	return codiceAlberoproc;
    }

    public void setCodiceAlberoproc(Integer codiceAlberoproc) {

	this.codiceAlberoproc = codiceAlberoproc;
    }

    public String getScCodice() {

	return scCodice;
    }

    public void setScCodice(String scCodice) {

	this.scCodice = scCodice;
    }

    public String getDescrizioneAlberoproc() {

	return descrizioneAlberoproc;
    }

    public void setDescrizioneAlberoproc(String descrizioneAlberoproc) {

	this.descrizioneAlberoproc = descrizioneAlberoproc;
    }

    public Integer getPeso() {

	return peso;
    }

    public void setPeso(Integer peso) {

	this.peso = peso;
    }

    public Boolean getNecessario() {

	return necessario;
    }

    public void setNecessario(Boolean necessario) {

	this.necessario = necessario;
    }
}
