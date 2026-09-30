package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DettaglioCommissioneModel {

    private Integer id;
    private String numeroProtocollo;
    private boolean aperta = true;
    private String descrizione;
    private Date data;
    private String oraInizio;
    private String oraFine;
    private List<RigaCommissioneModel> righe = new ArrayList<RigaCommissioneModel>(0);

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public boolean isAperta() {

	return aperta;
    }

    public void setAperta(boolean aperta) {

	this.aperta = aperta;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getOraInizio() {

	return oraInizio;
    }

    public void setOraInizio(String oraInizio) {

	this.oraInizio = oraInizio;
    }

    public String getOraFine() {

	return oraFine;
    }

    public void setOraFine(String oraFine) {

	this.oraFine = oraFine;
    }

    public List<RigaCommissioneModel> getRighe() {

	return righe;
    }

    public void setRighe(List<RigaCommissioneModel> righe) {

	this.righe = righe;
    }
}
