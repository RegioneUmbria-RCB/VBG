package it.alveo.ricalcoloaree.reqdata;

import java.util.List;

public class RicalcoloRequest {

    private String alias;
    private List<String> ricalcoloAreeIdL;
    private String dataPresentazioneDa; //DD-MM-YYYY
    private String dataPresentazioneA; //DD-MM-YYYY
    private List<Integer> idAree;

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public List<String> getRicalcoloAreeIdL() {
        return ricalcoloAreeIdL;
    }

    public void setRicalcoloAreeIdL(List<String> ricalcoloAreeIdL) {
        this.ricalcoloAreeIdL = ricalcoloAreeIdL;
    }

    public String getDataPresentazioneDa() {

	return dataPresentazioneDa;
    }

    public void setDataPresentazioneDa(String dataPresentazioneDa) {

	this.dataPresentazioneDa = dataPresentazioneDa;
    }

    public String getDataPresentazioneA() {

	return dataPresentazioneA;
    }

    public void setDataPresentazioneA(String dataPresentazioneA) {

	this.dataPresentazioneA = dataPresentazioneA;
    }

    public List<Integer> getIdAree() {

	return idAree;
    }

    public void setIdAree(List<Integer> idAree) {

	this.idAree = idAree;
    }
}
