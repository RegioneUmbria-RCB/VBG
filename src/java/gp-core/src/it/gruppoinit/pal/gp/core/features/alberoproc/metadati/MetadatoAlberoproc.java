package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

public class MetadatoAlberoproc {

    public Integer codiceIntervento;
    public String descrizioneIntervento;

    public String getDescrizioneIntervento() {

	return descrizioneIntervento;
    }

    public void setDescrizioneIntervento(String descrizioneIntervento) {

	this.descrizioneIntervento = descrizioneIntervento;
    }

    public String chiave;
    public String valore;

    public MetadatoAlberoproc() {

	// TODO Auto-generated constructor stub
    }

    public MetadatoAlberoproc(Integer codiceIntervento, String descrizioneIntervento, String chiave, String valore) {

	this.codiceIntervento = codiceIntervento;
	this.descrizioneIntervento = descrizioneIntervento;
	this.chiave = chiave;
	this.valore = valore;
    }

    public void setCodiceIntervento(Integer codiceIntervento) {

	this.codiceIntervento = codiceIntervento;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public Integer getCodiceIntervento() {

	return codiceIntervento;
    }

    public String getChiave() {

	return chiave;
    }

    public String getValore() {

	return valore;
    }
}
