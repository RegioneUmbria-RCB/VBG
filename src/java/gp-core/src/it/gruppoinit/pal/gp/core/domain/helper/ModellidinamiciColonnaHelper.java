package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;

import java.util.List;

public class ModellidinamiciColonnaHelper {

    private int numColonna;
    private ModellidinamiciCampoHelper campo;
    private List<ModellidinamiciTabellaHelper> tabelle;
    private Dyn2Modellid dyn2Modellid;

    public Dyn2Modellid getDyn2Modellid() {

	return dyn2Modellid;
    }

    public void setDyn2Modellid(Dyn2Modellid dyn2Modellid) {

	this.dyn2Modellid = dyn2Modellid;
    }

    private ModellidinamiciColonnaHelper() {

	super();
    }

    public ModellidinamiciColonnaHelper(int numColonna) {

	this();
	this.numColonna = numColonna;
    }

    public int getNumColonna() {

	return numColonna;
    }

    public void setNumColonna(int numColonna) {

	this.numColonna = numColonna;
    }

    public ModellidinamiciCampoHelper getCampo() {

	return campo;
    }

    public void setCampo(ModellidinamiciCampoHelper campo) {

	this.campo = campo;
    }

    public List<ModellidinamiciTabellaHelper> getTabelle() {

	return tabelle;
    }

    public void setTabelle(List<ModellidinamiciTabellaHelper> tabelle) {

	this.tabelle = tabelle;
    }

    public String getTesto() {

	String testo = null;
	if (getDyn2Modellid() != null) {
	    if (dyn2Modellid.getDyn2Modellidtesti() != null && dyn2Modellid.getDyn2Modellidtesti().getDyn2Basetipitesto().getId().equals("TI")) {
		testo = dyn2Modellid.getDyn2Modellidtesti().getTesto();
	    }
	}
	return testo;
    }

    public String getTestoesteso() {

	String testoesteso = null;
	if (getDyn2Modellid() != null) {
	    if (dyn2Modellid.getDyn2Modellidtesti() != null && dyn2Modellid.getDyn2Modellidtesti().getDyn2Basetipitesto().getId().equals("TE")) {
		testoesteso = dyn2Modellid.getDyn2Modellidtesti().getTesto();
	    }
	}
	return testoesteso;
    }

    public Dyn2Regole getRegolaAttivazione() {

	if (this.dyn2Modellid != null) {
	    Dyn2Regole regola = dyn2Modellid.getDyn2RegoleAttivo();
	    if (regola != null && regola.getId().getCodice() != null) {
		return regola;
	    } else {
		return null;
	    }
	} else if (this.tabelle != null && tabelle.size() > 0) {
	    return tabelle.get(0).getRegolaAttivazioneUnica();
	} else {
	    return null;
	}
    }
}
