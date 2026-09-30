package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public class GiornataMercatoPosteggioRestBean {

    private Integer id;
    private String numero;
    private String superficie;
    private String lunghezza;
    private String larghezza;
    private String stato;
    private Integer idSpuntistaAssociato;
    private List<CodiceDescrizioneBean> filtraCategorie;
    private List<CodiceDescrizioneBean> categorieMerceologicheAmmesse;
    private Integer ordinamentoPercorso;
    private Integer idMercatiPresenzaD;
    private String note;
    private ConcessionarioRestBean concessionario;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getSuperficie() {

	return superficie;
    }

    public void setSuperficie(String superficie) {

	this.superficie = superficie;
    }

    public Integer getIdSpuntistaAssociato() {

	return idSpuntistaAssociato;
    }

    public void setIdSpuntistaAssociato(Integer idSpuntistaAssociato) {

	this.idSpuntistaAssociato = idSpuntistaAssociato;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public Integer getIdMercatiPresenzaD() {

	return idMercatiPresenzaD;
    }

    public void setIdMercatiPresenzaD(Integer idMercatiPresenzaD) {

	this.idMercatiPresenzaD = idMercatiPresenzaD;
    }

    public List<CodiceDescrizioneBean> getCategorieMerceologicheAmmesse() {

	if (categorieMerceologicheAmmesse == null) {
	    categorieMerceologicheAmmesse = new ArrayList<CodiceDescrizioneBean>();
	}
	return categorieMerceologicheAmmesse;
    }

    public void setCategorieMerceologicheAmmesse(List<CodiceDescrizioneBean> categorieMerceologicheAmmesse) {

	this.categorieMerceologicheAmmesse = categorieMerceologicheAmmesse;
    }

    public List<CodiceDescrizioneBean> getFiltraCategorie() {

	if (filtraCategorie == null) {
	    filtraCategorie = new ArrayList<CodiceDescrizioneBean>();
	}
	return filtraCategorie;
    }

    public void setFiltraCategorie(List<CodiceDescrizioneBean> filtraCategorie) {

	this.filtraCategorie = filtraCategorie;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Integer getOrdinamentoPercorso() {

	return ordinamentoPercorso;
    }

    public void setOrdinamentoPercorso(Integer ordinamentoPercorso) {

	this.ordinamentoPercorso = ordinamentoPercorso;
    }

    public ConcessionarioRestBean getConcessionario() {

	return concessionario;
    }

    public void setConcessionario(ConcessionarioRestBean concessionario) {

	this.concessionario = concessionario;
    }

    public String getLunghezza() {

	return lunghezza;
    }

    public void setLunghezza(String lunghezza) {

	this.lunghezza = lunghezza;
    }

    public String getLarghezza() {

	return larghezza;
    }

    public void setLarghezza(String larghezza) {

	this.larghezza = larghezza;
    }
}
