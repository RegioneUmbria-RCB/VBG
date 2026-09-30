package it.gruppoinit.pal.gp.areariservata.domain;

import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.SchedaType;

import java.util.ArrayList;
import java.util.List;

public class SchedaHelper {

    private SchedaType scheda;
    private boolean confirmed;
    private boolean obbligatorio;
    private List<CampoSchedaHelper> campi;
    private List<DocumentoHelper> allegatiScheda;

    public SchedaHelper(SchedaType scheda) {

	this.scheda = scheda;
	campi = new ArrayList<CampoSchedaHelper>();
	this.allegatiScheda = new ArrayList<DocumentoHelper>();
    }

    public SchedaType getScheda() {

	return scheda;
    }

    public void setScheda(SchedaType scheda) {

	this.scheda = scheda;
    }

    public List<CampoSchedaHelper> getCampi() {

	return campi;
    }

    public boolean isConfirmed() {

	return confirmed;
    }

    public void setConfirmed(boolean confirmed) {

	this.confirmed = confirmed;
    }

    public boolean isObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }
    
    public List<DocumentoHelper> getAllegatiScheda() {
    
        return allegatiScheda;
    }
    

    public void addCampo(String key, String valore, String valoreDecodificato) {

	CampoSchedaHelper campoH = CampoSchedaHelper.fromFieldName(key);
	ValoreParametroTypeHelper vtpH = new ValoreParametroTypeHelper();
	ElementoValoreCampoDinamicoType vpt = new ElementoValoreCampoDinamicoType();
	vpt.setCodice(valore);
	vpt.setDescrizione(valoreDecodificato);
	vtpH.setVpt(vpt);
	vtpH.setIdxMolteplicita(campoH.getMolteplicita());
	boolean multiplo = false;
	for (CampoSchedaHelper _campoH : this.getCampi()) {
	    if (_campoH.getCampo().getCodice().equals(campoH.getCampo().getCodice())) {
		//campo multiplo, aggiungo i valori rispettando la molteplicità
		//_campoH.getCampo().getCampoDinamico().getValoreUtente().getValore().add(campoH.getMolteplicita(), vpt);
		_campoH.getVtpH().add(vtpH);
		multiplo = true;
		break;
	    }
	}
	if (!multiplo) {
	    campoH.getCampo().getCampoDinamico().getValoreUtente().setNome(campoH.getCampo().getCodice());
	    //campoH.getCampo().getCampoDinamico().getValoreUtente().getValore().add(vpt);
	    campoH.getVtpH().add(vtpH);
	    this.getCampi().add(campoH);
	}
    }
}
