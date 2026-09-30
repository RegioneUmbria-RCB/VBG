package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.util.List;

public class IASnapshotSchedaHelper implements Serializable {

    private static final long serialVersionUID = 2697832848741523152L;
    private int maxIndice;
    private int maxIndiceMolteplicita;
    private String descrizione;
    private String toolTip;
    private List<IASnapshotCampoHelper> listaCampi;

    public int getMaxIndice() {

	return maxIndice;
    }

    public void setMaxIndice(int maxIndice) {

	this.maxIndice = maxIndice;
    }

    public int getMaxIndiceMolteplicita() {

	return maxIndiceMolteplicita;
    }

    public void setMaxIndiceMolteplicita(int maxIndiceMolteplicita) {

	this.maxIndiceMolteplicita = maxIndiceMolteplicita;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getToolTip() {

	return toolTip;
    }

    public void setToolTip(String toolTip) {

	this.toolTip = toolTip;
    }

    public List<IASnapshotCampoHelper> getListaCampi() {

	return listaCampi;
    }

    public void setListaCampi(List<IASnapshotCampoHelper> listaCampi) {

	this.listaCampi = listaCampi;
    }
}
