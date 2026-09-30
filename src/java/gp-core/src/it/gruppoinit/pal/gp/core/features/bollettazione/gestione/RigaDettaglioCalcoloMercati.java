package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author riccardob
 *
 */
public class RigaDettaglioCalcoloMercati extends RigaDettaglioCalcolo {

    private String provenienza;
    private Integer idGiornata;
    
    private Date dataGiornata;

    private boolean assenzaGiustificata;
    private Integer idAutorizzazioneConcessione;
    private boolean subentro;
    // 
    private boolean concpresente;
    private boolean spuntpresente;
    private String catmerc;
    

    public String getProvenienza() {

	return provenienza;
    }

    public void setProvenienza(String provenienza) {

	this.provenienza = provenienza;
    }

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public void setIdGiornata(Integer idGiornata) {

	this.idGiornata = idGiornata;
    }

    public Date getDataGiornata() {

	return dataGiornata;
    }

    public void setDataGiornata(Date dataGiornata) {

	this.dataGiornata = dataGiornata;
    }


    public boolean isAssenzaGiustificata() {

	return assenzaGiustificata;
    }

    public void setAssenzaGiustificata(boolean assenzaGiustificata) {

	this.assenzaGiustificata = assenzaGiustificata;
    }

    public boolean isConcpresente() {

	return concpresente;
    }

    public void setConcpresente(boolean concpresente) {

	this.concpresente = concpresente;
    }

    public boolean isSpuntpresente() {

	return spuntpresente;
    }

    public void setSpuntpresente(boolean spuntpresente) {

	this.spuntpresente = spuntpresente;
    }

    public String getCatmerc() {

	return catmerc;
    }

    public void setCatmerc(String catmerc) {

	this.catmerc = catmerc;
    }

    public Integer getIdAutorizzazioneConcessione() {

	return idAutorizzazioneConcessione;
    }

    public void setIdAutorizzazioneConcessione(Integer idAutorizzazioneConcessione) {

	this.idAutorizzazioneConcessione = idAutorizzazioneConcessione;
    }

    public boolean isSubentro() {

	return subentro;
    }

    public void setSubentro(boolean subentro) {

	this.subentro = subentro;
    }

    public String getChiaveRiferimentoAutorizzazione() {

	return new ChiaveCalcoloRiferimentoAutorizzazione(this.getIdRiferimento(), this.isSubentro(), this.getIdPosteggio(), this.getIdUso()).getChiave();
    }


    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
