package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;

public class IASnapshotCampoHelper implements Serializable {

    private static final long serialVersionUID = 9198919228201474397L;
    private String nomeCampo;
    private String descrizione;
    private String toolTip;
    private int codice;

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

    public int getCodice() {

	return codice;
    }

    public void setCodice(int codice) {

	this.codice = codice;
    }

    public String getNomeCampo() {

	return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {

	this.nomeCampo = nomeCampo;
    }
}
