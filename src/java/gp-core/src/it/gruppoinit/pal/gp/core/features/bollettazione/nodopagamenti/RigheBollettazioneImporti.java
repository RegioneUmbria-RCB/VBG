package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;

public class RigheBollettazioneImporti {

    private List<ImportoBean> importoBeans = new ArrayList<ImportoBean>();
    private List<Integer> idRigheBollettazione = new ArrayList<Integer>();
    private String codiceComune;

    public RigheBollettazioneImporti(List<ImportoBean> importoBeans, List<Integer> idRigheBollettazione, String codiceComune) {

	super();
	this.importoBeans = importoBeans;
	this.idRigheBollettazione = idRigheBollettazione;
	this.codiceComune = codiceComune;
    }

    public void add(List<ImportoBean> importoBeans, List<Integer> idRigheBollettazione) {

	this.importoBeans.addAll(importoBeans);
	this.idRigheBollettazione.addAll(idRigheBollettazione);
    }

    public List<ImportoBean> getImportoBeans() {

	return importoBeans;
    }

    public List<Integer> getIdRigheBollettazione() {

	return idRigheBollettazione;
    }

    public String getCodiceComune() {

	return codiceComune;
    }
}
