package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.List;

public class RicalcoloMaxRequest {

    private String alias;
    private List<String> ricalcoloAreeIdL;
    private String dataPresentazioneDa;
    private String dataPresentazioneA;
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
