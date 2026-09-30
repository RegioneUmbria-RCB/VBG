/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author francol
 *
 */
public class TipoProcedimentoGenerale implements Serializable {

    private static final long serialVersionUID = -9139867251480606751L;
    private Integer id;
    private String nome;
    private String descrizione;
    private String riferimentiNormativi;
    private String categoria;
    private List<String> categorieAnticorruzione;
    private Boolean rischioCorruzione;
    private String missione;
    private List<String> categorieDestinatario;
    private String modalitaConclusione;
    private String terminiConclusione;
    private String strumentiTutela;
    private String specificheDimensionali;
    private String settoreAttivita;
    private String tipologiaRegime;
    private String giustificazioneRegime;
    private String amministrazioneCompetente;
    private Boolean pubblicato;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getRiferimentiNormativi() {

	return riferimentiNormativi;
    }

    public void setRiferimentiNormativi(String riferimentiNormativi) {

	this.riferimentiNormativi = riferimentiNormativi;
    }

    public String getCategoria() {

	return categoria;
    }

    public void setCategoria(String categoria) {

	this.categoria = categoria;
    }

    public List<String> getCategorieAnticorruzione() {

	return categorieAnticorruzione;
    }

    public void setCategorieAnticorruzione(List<String> categorieAnticorruzione) {

	this.categorieAnticorruzione = categorieAnticorruzione;
    }

    public Boolean isRischioCorruzione() {

	return rischioCorruzione;
    }

    public void setRischioCorruzione(Boolean rischioCorruzione) {

	this.rischioCorruzione = rischioCorruzione;
    }

    public String getRischioCorruzioneFormatted() {

	return BooleanUtils.toString(rischioCorruzione, "Si", "No", "");
    }
    public String getMissione() {

	return missione;
    }

    public void setMissione(String missione) {

	this.missione = missione;
    }

    public List<String> getCategorieDestinatario() {

	return categorieDestinatario;
    }

    public String getCategorieDestinatarioFormatted() {

	if(this.getCategorieDestinatario()==null) {
	    return "";
	}
	return StringUtils.join(this.getCategorieDestinatario().iterator(), ", ");
    }

    public void setCategorieDestinatario(List<String> categorieDestinatario) {

	this.categorieDestinatario = categorieDestinatario;
    }

    public String getModalitaConclusione() {

	return modalitaConclusione;
    }

    public void setModalitaConclusione(String modalitaConclusione) {

	this.modalitaConclusione = modalitaConclusione;
    }

    public String getTerminiConclusione() {

	return terminiConclusione;
    }

    public void setTerminiConclusione(String terminiConclusione) {

	this.terminiConclusione = terminiConclusione;
    }

    public String getStrumentiTutela() {

	return strumentiTutela;
    }

    public void setStrumentiTutela(String strumentiTutela) {

	this.strumentiTutela = strumentiTutela;
    }

    public String getSpecificheDimensionali() {

	return specificheDimensionali;
    }

    public void setSpecificheDimensionali(String specificheDimensionali) {

	this.specificheDimensionali = specificheDimensionali;
    }

    public String getSettoreAttivita() {

	return settoreAttivita;
    }

    public void setSettoreAttivita(String settoreAttivita) {

	this.settoreAttivita = settoreAttivita;
    }

    public String getTipologiaRegime() {

	return tipologiaRegime;
    }

    public void setTipologiaRegime(String tipologiaRegime) {

	this.tipologiaRegime = tipologiaRegime;
    }

    public String getGiustificazioneRegime() {

	return giustificazioneRegime;
    }

    public void setGiustificazioneRegime(String giustificazioneRegime) {

	this.giustificazioneRegime = giustificazioneRegime;
    }

    public String getAmministrazioneCompetente() {

	return amministrazioneCompetente;
    }

    public void setAmministrazioneCompetente(String amministrazioneCompetente) {

	this.amministrazioneCompetente = amministrazioneCompetente;
    }

    public Boolean isPubblicato() {

	return pubblicato;
    }

    public void setPubblicato(Boolean pubblicato) {

	this.pubblicato = pubblicato;
    }

    public String getPubblicatoFormatted() {

	return BooleanUtils.toString(pubblicato, "Si", "No", "");
    }
}
