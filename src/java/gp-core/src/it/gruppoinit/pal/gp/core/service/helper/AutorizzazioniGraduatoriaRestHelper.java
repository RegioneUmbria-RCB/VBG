package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceNomeBean;
import it.gruppoinit.pal.gp.core.domain.helper.ComuneGraduatorieRestBean;
import it.gruppoinit.pal.gp.core.domain.helper.TitolareAutorizzazioneGradRestBean;

public class AutorizzazioniGraduatoriaRestHelper {

    private Integer id;
    private String numero;
    private Integer anno;
    private String protocollo;
    private String data_protocollo;
    private TitolareAutorizzazioneGradRestBean titolare;
    private ComuneGraduatorieRestBean comune_rilascio;
    private String originaria;
    private int numero_presenze;
    private CodiceNomeBean mercato;
    private CodiceNomeBean giorno;

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

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public String getProtocollo() {

	return protocollo;
    }

    public void setProtocollo(String protocollo) {

	this.protocollo = protocollo;
    }

    public String getData_protocollo() {

	return data_protocollo;
    }

    public void setData_protocollo(String data_protocollo) {

	this.data_protocollo = data_protocollo;
    }

    public TitolareAutorizzazioneGradRestBean getTitolare() {

	return titolare;
    }

    public void setTitolare(TitolareAutorizzazioneGradRestBean titolare) {

	this.titolare = titolare;
    }

    public ComuneGraduatorieRestBean getComune_rilascio() {

	return comune_rilascio;
    }

    public void setComune_rilascio(ComuneGraduatorieRestBean comune_rilascio) {

	this.comune_rilascio = comune_rilascio;
    }

    public String getOriginaria() {

	return originaria;
    }

    public void setOriginaria(String originaria) {

	this.originaria = originaria;
    }

    public int getNumero_presenze() {

	return numero_presenze;
    }

    public void setNumero_presenze(int numero_presenze) {

	this.numero_presenze = numero_presenze;
    }

    public CodiceNomeBean getMercato() {

	return mercato;
    }

    public void setMercato(CodiceNomeBean mercato) {

	this.mercato = mercato;
    }

    public CodiceNomeBean getGiorno() {

	return giorno;
    }

    public void setGiorno(CodiceNomeBean giorno) {

	this.giorno = giorno;
    }
}
