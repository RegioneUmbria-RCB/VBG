package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.Date;

public class TaskBean {

    private Integer id;
    private String descrizione;
    private String operazione;
    private Integer intervallo;
    private Date prossimaEsecuzione;
    private Boolean attivo;
    private Boolean inEsecuzione;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getOperazione() {

	return operazione;
    }

    public void setOperazione(String operazione) {

	this.operazione = operazione;
    }

    public Integer getIntervallo() {

	return intervallo;
    }

    public void setIntervallo(Integer intervallo) {

	this.intervallo = intervallo;
    }

    public String getDescrizioneIntervallo() {

	return DescrizioneIntervalloBean.fromIntervallo(this.intervallo).toString();
    }

    public Date getProssimaEsecuzione() {

	return prossimaEsecuzione;
    }

    public void setProssimaEsecuzione(Date prossimaEsecuzione) {

	this.prossimaEsecuzione = prossimaEsecuzione;
    }

    public Boolean getAttivo() {

	return attivo;
    }

    public void setAttivo(Boolean attivo) {

	this.attivo = attivo;
    }

    public Boolean getInEsecuzione() {

	return inEsecuzione;
    }

    public void setInEsecuzione(Boolean inEsecuzione) {

	this.inEsecuzione = inEsecuzione;
    }
}
