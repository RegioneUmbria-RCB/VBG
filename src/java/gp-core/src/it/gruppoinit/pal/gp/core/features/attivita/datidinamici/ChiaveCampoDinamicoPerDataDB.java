package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.Date;

public class ChiaveCampoDinamicoPerDataDB {

    private Date dataSnapshot;
    private Integer idCampo;
    private Integer indice;
    private Integer indiceMolteplicita;
    private String valore;
    private String valoreDecodificato;

    public Date getDataSnapshot() {

	return dataSnapshot;
    }

    public void setDataSnapshot(Date dataSnapshot) {

	this.dataSnapshot = dataSnapshot;
    }

    public Integer getIdCampo() {

	return idCampo;
    }

    public void setIdCampo(Integer idCampo) {

	this.idCampo = idCampo;
    }

    public Integer getIndice() {

	return indice;
    }

    public void setIndice(Integer indice) {

	this.indice = indice;
    }

    public Integer getIndiceMolteplicita() {

	return indiceMolteplicita;
    }

    public void setIndiceMolteplicita(Integer indiceMolteplicita) {

	this.indiceMolteplicita = indiceMolteplicita;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getValoreDecodificato() {

	return valoreDecodificato;
    }

    public void setValoreDecodificato(String valoreDecodificato) {

	this.valoreDecodificato = valoreDecodificato;
    }
}
