package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CambioInterventoCommand extends BaseCommand {

    private Istanze entity;
    private CodiceDescrizioneBean intervento = new CodiceDescrizioneBean();
    private CodiceDescrizioneBean proceduraOld = new CodiceDescrizioneBean();
    private CodiceDescrizioneBean proceduraNew = new CodiceDescrizioneBean();
    private CodiceDescrizioneBean movAvvioOld = new CodiceDescrizioneBean();
    private CodiceDescrizioneBean movAvvioNew = new CodiceDescrizioneBean();
    private CodiceDescrizioneBean azioneOld = new CodiceDescrizioneBean();
    private CodiceDescrizioneBean azioneNew = new CodiceDescrizioneBean();
    private List<CambioInterventoCompareHelper> docs = new ArrayList<CambioInterventoCompareHelper>();
    private List<CambioInterventoCompareHelper> endos = new ArrayList<CambioInterventoCompareHelper>();
    private List<CambioInterventoCompareHelper> schedes = new ArrayList<CambioInterventoCompareHelper>();
    private List<CambioInterventoCompareHelper> ruolis = new ArrayList<CambioInterventoCompareHelper>();
    private List<CambioInterventoCompareHelper> permessis = new ArrayList<CambioInterventoCompareHelper>();

    public void resetAllProperties() {

	this.proceduraOld = new CodiceDescrizioneBean();
	this.proceduraNew = new CodiceDescrizioneBean();
	this.movAvvioOld = new CodiceDescrizioneBean();
	this.movAvvioNew = new CodiceDescrizioneBean();
	this.azioneOld = new CodiceDescrizioneBean();
	this.azioneNew = new CodiceDescrizioneBean();
	this.docs = new ArrayList<CambioInterventoCompareHelper>();
	this.endos = new ArrayList<CambioInterventoCompareHelper>();
	this.schedes = new ArrayList<CambioInterventoCompareHelper>();
	this.ruolis = new ArrayList<CambioInterventoCompareHelper>();
	this.permessis = new ArrayList<CambioInterventoCompareHelper>();
    }

    public List<CambioInterventoCompareHelper> getDocs() {

	return docs;
    }

    public void setDocs(List<CambioInterventoCompareHelper> docs) {

	this.docs = docs;
    }

    public List<CambioInterventoCompareHelper> getEndos() {

	return endos;
    }

    public void setEndos(List<CambioInterventoCompareHelper> endos) {

	this.endos = endos;
    }

    public List<CambioInterventoCompareHelper> getSchedes() {

	return schedes;
    }

    public void setSchedes(List<CambioInterventoCompareHelper> schedes) {

	this.schedes = schedes;
    }

    public List<CambioInterventoCompareHelper> getRuolis() {

	return ruolis;
    }

    public void setRuolis(List<CambioInterventoCompareHelper> ruolis) {

	this.ruolis = ruolis;
    }

    public List<CambioInterventoCompareHelper> getPermessis() {

	return permessis;
    }

    public void setPermessis(List<CambioInterventoCompareHelper> permessis) {

	this.permessis = permessis;
    }

    public CodiceDescrizioneBean getProceduraOld() {

	return proceduraOld;
    }

    public void setProceduraOld(CodiceDescrizioneBean proceduraOld) {

	this.proceduraOld = proceduraOld;
    }

    public CodiceDescrizioneBean getProceduraNew() {

	return proceduraNew;
    }

    public void setProceduraNew(CodiceDescrizioneBean proceduraNew) {

	this.proceduraNew = proceduraNew;
    }

    public CodiceDescrizioneBean getMovAvvioOld() {

	return movAvvioOld;
    }

    public void setMovAvvioOld(CodiceDescrizioneBean movAvvioOld) {

	this.movAvvioOld = movAvvioOld;
    }

    public CodiceDescrizioneBean getMovAvvioNew() {

	return movAvvioNew;
    }

    public void setMovAvvioNew(CodiceDescrizioneBean movAvvioNew) {

	this.movAvvioNew = movAvvioNew;
    }

    public CodiceDescrizioneBean getAzioneOld() {

	return azioneOld;
    }

    public void setAzioneOld(CodiceDescrizioneBean azioneOld) {

	this.azioneOld = azioneOld;
    }

    public CodiceDescrizioneBean getAzioneNew() {

	return azioneNew;
    }

    public void setAzioneNew(CodiceDescrizioneBean azioneNew) {

	this.azioneNew = azioneNew;
    }

    public Istanze getEntity() {

	return entity;
    }

    public void setEntity(Istanze entity) {

	this.entity = entity;
    }

    public void setIntervento(CodiceDescrizioneBean intervento) {

	this.intervento = intervento;
    }

    public CodiceDescrizioneBean getIntervento() {

	return intervento;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
