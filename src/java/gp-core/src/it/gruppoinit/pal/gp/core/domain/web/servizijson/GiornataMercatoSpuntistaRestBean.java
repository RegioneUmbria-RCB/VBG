package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public class GiornataMercatoSpuntistaRestBean extends BaseSoggettoMercatoRestBean {

    private Integer id;
    private String descrizione;
    private CodiceDescrizioneBean filtraCategoria;
    private List<CodiceDescrizioneBean> categorieMerceologiche;
    private String stato;
    private boolean presenteUltimoMercato;
    private boolean presenteAnnoScorso;
    private int totalePresenze;
    private BigDecimal totalePresenzePerOrdinamento;
    private String posteggioRinunciato;
    private String tipologiaBattitore;
    private Integer posteggioOccupato;
    private AnagraferestBean titolare;

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

    public CodiceDescrizioneBean getFiltraCategoria() {

	return filtraCategoria;
    }

    public void setFiltraCategoria(CodiceDescrizioneBean filtraCategoria) {

	this.filtraCategoria = filtraCategoria;
    }

    public List<CodiceDescrizioneBean> getCategorieMerceologiche() {

	if (this.categorieMerceologiche == null) {
	    return new ArrayList<CodiceDescrizioneBean>();
	}
	return categorieMerceologiche;
    }

    public void setCategorieMerceologiche(List<CodiceDescrizioneBean> categorieMerceologiche) {

	this.categorieMerceologiche = categorieMerceologiche;
    }

    public boolean getPresenteUltimoMercato() {

	return presenteUltimoMercato;
    }

    public void setPresenteUltimoMercato(boolean presenteUltimoMercato) {

	this.presenteUltimoMercato = presenteUltimoMercato;
    }

    public boolean getPresenteAnnoScorso() {

	return presenteAnnoScorso;
    }

    public void setPresenteAnnoScorso(boolean presenteAnnoScorso) {

	this.presenteAnnoScorso = presenteAnnoScorso;
    }

    public int getTotalePresenze() {

	return totalePresenze;
    }

    public void setTotalePresenze(int totalePresenze) {

	this.totalePresenze = totalePresenze;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getPosteggioRinunciato() {

	return posteggioRinunciato;
    }

    public void setPosteggioRinunciato(String posteggioRinunciato) {

	this.posteggioRinunciato = posteggioRinunciato;
    }

    public String getTipologiaBattitore() {

	return tipologiaBattitore;
    }

    public void setTipologiaBattitore(String tipologiaBattitore) {

	this.tipologiaBattitore = tipologiaBattitore;
    }

    public Integer getPosteggioOccupato() {

	return posteggioOccupato;
    }

    public void setPosteggioOccupato(Integer posteggioOccupato) {

	this.posteggioOccupato = posteggioOccupato;
    }

    public AnagraferestBean getTitolare() {

	return titolare;
    }

    public void setTitolare(AnagraferestBean titolare) {

	this.titolare = titolare;
    }

    public BigDecimal getTotalePresenzePerOrdinamento() {

	return totalePresenzePerOrdinamento;
    }

    public void setTotalePresenzePerOrdinamento(BigDecimal totalePresenzePerOrdinamento) {

	this.totalePresenzePerOrdinamento = totalePresenzePerOrdinamento;
    }
}
