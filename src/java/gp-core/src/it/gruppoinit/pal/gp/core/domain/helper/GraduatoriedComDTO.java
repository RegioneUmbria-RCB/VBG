package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public class GraduatoriedComDTO {

    private PkId id;
    private Integer oggetto;
    private GraduatorietCom graduatorietCom;
    private GraduatoriedDTO graduatoried;
    private Integer movimenti;
    private String descMovimenti;
    private Integer movimentimail;
    private List<Istanzeeventi> istanzeeventis;
    private Istanzeeventi istanzeeventi;
    // Variabili utilizzate per settare se una mail è stata accetta dal server di posta
    // e se è stata consegnata al destinatario
    private Boolean accettata;
    private Boolean consegnata;

    public GraduatoriedComDTO() {

	this.accettata = Boolean.FALSE;
	this.consegnata = Boolean.FALSE;
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Integer getOggetto() {

	return oggetto;
    }

    public void setOggetto(Integer oggetto) {

	this.oggetto = oggetto;
    }

    public GraduatorietCom getGraduatorietCom() {

	return graduatorietCom;
    }

    public void setGraduatorietCom(GraduatorietCom graduatorietCom) {

	this.graduatorietCom = graduatorietCom;
    }

    public GraduatoriedDTO getGraduatoried() {

	return graduatoried;
    }

    public void setGraduatoried(GraduatoriedDTO graduatoried) {

	this.graduatoried = graduatoried;
    }

    public Integer getMovimenti() {

	return movimenti;
    }

    public void setMovimenti(Integer movimenti) {

	this.movimenti = movimenti;
    }

    public String getDescMovimenti() {

	return descMovimenti;
    }

    public void setDescMovimenti(String descMovimenti) {

	this.descMovimenti = descMovimenti;
    }

    public Integer getMovimentimail() {

	return movimentimail;
    }

    public void setMovimentimail(Integer movimentimail) {

	this.movimentimail = movimentimail;
    }

    public List<Istanzeeventi> getIstanzeeventis() {

	return istanzeeventis;
    }

    public void setIstanzeeventis(List<Istanzeeventi> istanzeeventis) {

	this.istanzeeventis = istanzeeventis;
    }

    public Istanzeeventi getIstanzeeventi() {

	return istanzeeventi;
    }

    public void setIstanzeeventi(Istanzeeventi istanzeeventi) {

	this.istanzeeventi = istanzeeventi;
    }

    public Boolean getAccettata() {

	return accettata;
    }

    public void setAccettata(Boolean accettata) {

	this.accettata = accettata;
    }

    public Boolean getConsegnata() {

	return consegnata;
    }

    public void setConsegnata(Boolean consegnata) {

	this.consegnata = consegnata;
    }
}
