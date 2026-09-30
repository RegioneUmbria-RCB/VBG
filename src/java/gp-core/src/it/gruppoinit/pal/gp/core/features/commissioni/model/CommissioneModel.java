package it.gruppoinit.pal.gp.core.features.commissioni.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;

import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;

public class CommissioneModel {

    private Integer id;
    private String numeroProtocollo;
    private String descrizione;
    private String note;
    private boolean aperta;
    private Date dataFine;
    private Integer codiceTipologia;
    private String tipologia;
    private boolean sincrona;
    private Integer idConvocazione;
    private List<ConvocazioneModel> convocazioni;
    private Integer codiceMovimento;
    private String odg;
    private boolean showODG;

    public CommissioneModel() {

	this.convocazioni = new ArrayList<ConvocazioneModel>();
	this.aperta = true;
	this.sincrona = true;
    }

    public static CommissioneModel fromCommissioniedilizieT(CommissioniedilizieT commissione) {

	if (commissione == null || commissione.getId() == null || commissione.getId().getCodice() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile invocare il metodo statico FromCommissioniedilizieT senza passare una commissione valida");
	}
	CommissioneModel model = new CommissioneModel();
	model.id = commissione.getId().getCodice();
	model.numeroProtocollo = commissione.getNumprotocollo();
	model.descrizione = commissione.getDescrizione();
	model.note = commissione.getNote();
	model.aperta = commissione.getFlagaperta() == null ? Boolean.TRUE : commissione.getFlagaperta();
	model.dataFine = commissione.getDataFine();
	model.odg = commissione.getOdg();
	model.showODG = false;
	if (commissione.getCommedilizieTipologie() != null && commissione.getCommedilizieTipologie().getId() != null) {
	    model.codiceTipologia = commissione.getCommedilizieTipologie().getId().getCodice();
	    model.tipologia = commissione.getCommedilizieTipologie().getDescrizione();
	    Set<CommedilizieTipologiedett> commedilizieTipologiedetts = commissione.getCommedilizieTipologie().getCommedilizieTipologiedetts();
	    for (CommedilizieTipologiedett commedilizieTipologiedett : commedilizieTipologiedetts) {
		if (BooleanUtils.isTrue(commedilizieTipologiedett.getTipimovimento().getFlagCds())) {
		    model.showODG = true;
		    break;
		}
	    }
	}
	model.sincrona = commissione.getFlagSincrona() == null ? Boolean.TRUE : commissione.getFlagSincrona();
	model.idConvocazione = commissione.getIdconvocazione();
	for (CommedilizieConvocazioni convocazione : commissione.getCommedilizieConvocazionis()) {
	    model.convocazioni.add(ConvocazioneModel.fromCommedilizieConvocazioni(convocazione));
	}
	return model;
    }

    public Integer getId() {

	return id;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public String getNote() {

	return note;
    }

    public boolean isAperta() {

	return aperta;
    }

    public Date getDataFine() {

	return dataFine;
    }

    public Integer getCodiceTipologia() {

	return codiceTipologia;
    }

    public String getTipologia() {

	return tipologia;
    }

    public boolean isSincrona() {

	return sincrona;
    }

    public Integer getIdConvocazione() {

	return idConvocazione;
    }

    public List<ConvocazioneModel> getConvocazioni() {

	return convocazioni;
    }

    public int getNumeroConvocazioni() {

	return this.convocazioni.size();
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public void setAperta(boolean aperta) {

	this.aperta = aperta;
    }

    public void setDataFine(Date dataFine) {

	this.dataFine = dataFine;
    }

    public void setCodiceTipologia(Integer codiceTipologia) {

	this.codiceTipologia = codiceTipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }

    public void setSincrona(boolean sincrona) {

	this.sincrona = sincrona;
    }

    public void setIdConvocazione(Integer idConvocazione) {

	this.idConvocazione = idConvocazione;
    }

    public void setConvocazioni(List<ConvocazioneModel> convocazioni) {

	this.convocazioni = convocazioni;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public void setOdg(String odg) {

	this.odg = odg;
    }

    public String getOdg() {

	return odg;
    }

    public boolean isShowODG() {

	return showODG;
    }

    public void setShowODG(boolean showODG) {

	this.showODG = showODG;
    }

    /**
     * Serve per creare un template per crfeare una nuova commissione
     * 
     * @param movimento
     * @return
     */
    public static CommissioneModel FromMovimento(Movimenti movimento, CommedilizieTipologie tipologia) {

	if (movimento == null || movimento.getId() == null || movimento.getId().getCodice() == null) {
	    throw new IllegalArgumentException("Impossibile invocare il metodo statico FromMovimento senza passare un movimento");
	}
	CommissioneModel model = new CommissioneModel();
	model.id = null;
	model.numeroProtocollo = movimento.getIstanza().getNumeroistanza();
	model.descrizione = movimento.getIstanza().getNumeroistanza();
	model.aperta = true;
	model.dataFine = null;
	model.codiceMovimento = movimento.getId().getCodice();
	model.showODG = false;
	if (tipologia != null && tipologia.getId() != null) {
	    model.codiceTipologia = tipologia.getId().getCodice();
	    model.tipologia = tipologia.getDescrizione();
	    model.descrizione = model.tipologia + " Pratica " + movimento.getIstanza().getNumeroistanza();
	    Set<CommedilizieTipologiedett> commedilizieTipologiedetts = tipologia.getCommedilizieTipologiedetts();
	    for (CommedilizieTipologiedett commedilizieTipologiedett : commedilizieTipologiedetts) {
		if (BooleanUtils.isTrue(commedilizieTipologiedett.getTipimovimento().getFlagCds())) {
		    model.showODG = true;
		    break;
		}
	    }
	}
	model.sincrona = false;
	model.idConvocazione = null;
	return model;
    }
}
