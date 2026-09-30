package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.ArrayList;
import java.util.List;

public class GraduatoriedDTO {

    private PkId id;
    private Integer posizione;
    private IstanzeDTO istanza;
   
    private List<AutorizzazioniDTO> concESub;
    private AutorizzazioniDTO concessione;
    private List<CampigraduatoriaDTO> campigraduatorias = new ArrayList<CampigraduatoriaDTO>();
    private List<Istanzedyn2datiDTO> bandoOutputList = new ArrayList<Istanzedyn2datiDTO>();

    public GraduatoriedDTO() {

	id = new PkId();
	istanza = new IstanzeDTO();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Integer getPosizione() {

	return posizione;
    }

    public void setPosizione(Integer posizione) {

	this.posizione = posizione;
    }

    public IstanzeDTO getIstanza() {

	return istanza;
    }

    public void setIstanza(IstanzeDTO istanza) {

	this.istanza = istanza;
    }

    public List<CampigraduatoriaDTO> getCampigraduatorias() {

	return campigraduatorias;
    }

    public void setCampigraduatorias(List<CampigraduatoriaDTO> campigraduatorias) {

	this.campigraduatorias = campigraduatorias;
    }

    public List<Istanzedyn2datiDTO> getBandoOutputList() {

	return bandoOutputList;
    }

    public void setBandoOutputList(List<Istanzedyn2datiDTO> bandoOutputList) {

	this.bandoOutputList = bandoOutputList;
    }

    public List<AutorizzazioniDTO> getConcESub() {

	return concESub;
    }

    public void setConcESub(List<AutorizzazioniDTO> concESub) {

	this.concESub = concESub;
    }

    public AutorizzazioniDTO getConcessione() {

	return concessione;
    }

    public void setConcessione(AutorizzazioniDTO concessione) {

	this.concessione = concessione;
    }

    @Override
    public int hashCode() {

	return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

	return id.equals(((GraduatoriedDTO) obj).getId());
    }
}
