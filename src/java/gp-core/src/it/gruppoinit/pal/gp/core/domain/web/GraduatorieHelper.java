package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GraduatorieHelper {

    private Boolean mercatoIsPresente;
    private Graduatoriet graduatoriet;
    private List<Tipibandooutput> tipibandooutputList;
    private Set<GraduatoriedDTO> graduatoriedDTO2s = new LinkedHashSet<GraduatoriedDTO>();
    private Integer sizeCriteri;
    private Boolean mostraRilasciaEdAssegnaConcessione;
    private Boolean mostraButtonRilasciaConcessione;
    private Boolean mostraButtonRilasciaPosizione;
    private boolean presentiComunicazioni;

    public Boolean getMercatoIsPresente() {

	if (null == mercatoIsPresente) {
	    mercatoIsPresente = Boolean.FALSE;
	}
	return mercatoIsPresente;
    }

    public void setMercatoIsPresente(Boolean mercatoIsPresente) {

	this.mercatoIsPresente = mercatoIsPresente;
    }

    public Graduatoriet getGraduatoriet() {

	return graduatoriet;
    }

    public void setGraduatoriet(Graduatoriet graduatoriet) {

	this.graduatoriet = graduatoriet;
    }

    public List<Tipibandooutput> getTipibandooutputList() {

	if (tipibandooutputList == null) {
	    tipibandooutputList = new ArrayList<Tipibandooutput>();
	}
	return tipibandooutputList;
    }

    public void setTipibandooutputList(List<Tipibandooutput> tipibandooutputList) {

	this.tipibandooutputList = tipibandooutputList;
    }

    public Set<GraduatoriedDTO> getGraduatoriedDTO2s() {

	if (graduatoriedDTO2s == null) {
	    graduatoriedDTO2s = new LinkedHashSet<GraduatoriedDTO>();
	}
	return graduatoriedDTO2s;
    }

    public void setGraduatoriedDTO2s(Set<GraduatoriedDTO> graduatoriedDTO2s) {

	this.graduatoriedDTO2s = graduatoriedDTO2s;
    }

    public Integer getSizeCriteri() {

	if (null == sizeCriteri) {
	    sizeCriteri = Integer.valueOf(0);
	}
	return sizeCriteri;
    }

    public void setSizeCriteri(Integer sizeCriteri) {

	this.sizeCriteri = sizeCriteri;
    }

    public Boolean getMostraRilasciaEdAssegnaConcessione() {

	if (null == mostraRilasciaEdAssegnaConcessione) {
	    mostraRilasciaEdAssegnaConcessione = Boolean.FALSE;
	}
	return mostraRilasciaEdAssegnaConcessione;
    }

    public void setMostraRilasciaEdAssegnaConcessione(Boolean mostraRilascaEdAssegnaConcessione) {

	this.mostraRilasciaEdAssegnaConcessione = mostraRilascaEdAssegnaConcessione;
    }

    public Boolean getMostraButtonRilasciaConcessione() {

	if (null == mostraButtonRilasciaConcessione) {
	    mostraButtonRilasciaConcessione = Boolean.FALSE;
	}
	return mostraButtonRilasciaConcessione;
    }

    public void setMostraButtonRilasciaConcessione(Boolean mostraButtonRilasciaConcessione) {

	this.mostraButtonRilasciaConcessione = mostraButtonRilasciaConcessione;
    }

    public Boolean getMostraButtonRilasciaPosizione() {

	if (null == mostraButtonRilasciaPosizione) {
	    mostraButtonRilasciaPosizione = Boolean.FALSE;
	}
	return mostraButtonRilasciaPosizione;
    }

    public void setMostraButtonRilasciaPosizione(Boolean mostraButtonRilasciaPosizione) {

	this.mostraButtonRilasciaPosizione = mostraButtonRilasciaPosizione;
    }

    public boolean isPresentiComunicazioni() {

	return presentiComunicazioni;
    }

    public void setPresentiComunicazioni(boolean presentiComunicazioni) {

	this.presentiComunicazioni = presentiComunicazioni;
    }
}
