package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ModellidinamiciTabellaHelper")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModellidinamiciTabellaHelper", propOrder = { "righe", "cacheMap" })
public class ModellidinamiciTabellaHelper {

    private static final String DEFAULT_CACHE_NAME = "default";
    @XmlElement(name = "righe", required = false)
    private List<ModellidinamiciRigaHelper> righe = new ArrayList<ModellidinamiciRigaHelper>();
    @XmlElement(name = "cacheMap", required = false)
    private HashMap<String, List<String>> cacheMap = new HashMap<String, List<String>>();

    public ModellidinamiciTabellaHelper() {

    }

    public void setRighe(List<ModellidinamiciRigaHelper> righe) {

	this.righe = righe;
    }

    public List<ModellidinamiciRigaHelper> getRighe() {

	return righe;
    }

    public List<String> getScripts() {

	return getScripts(DEFAULT_CACHE_NAME);
    }

    public void addScript(String script) {

	addScript(script, DEFAULT_CACHE_NAME);
    }

    public List<String> getScripts(String cacheName) {

	List<String> retScripts = cacheMap.get(cacheName);
	if (retScripts == null) {
	    retScripts = new ArrayList<String>();
	}
	return retScripts;
    }

    public void addScript(String script, String cacheName) {

	List<String> scripts = cacheMap.get(cacheName);
	if (scripts == null) {
	    scripts = new ArrayList<String>();
	    cacheMap.put(cacheName, scripts);
	}
	scripts.add(script);
    }

    public void clearScripts() {

	clearScripts(DEFAULT_CACHE_NAME);
    }

    public void clearScripts(String cacheName) {

	this.cacheMap.remove(cacheName);
    }

    public void clearAllScripts() {

	this.cacheMap.clear();
    }

    public int getMaxColonne() {

	int numColonne = 0;
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		if (riga.getColonne() != null) {
		    if (numColonne < riga.getColonne().size()) {
			numColonne = riga.getColonne().size();
		    }
		}
	    }
	}
	return numColonne;
    }

    /*
     * TODO metodo obsoleto da eliminare
     */
    public int calcolaNumeroColonne() {

	int numColonneLcl = 0;
	int numMaxCampi = 0;
	if (righe != null) {
	    if (righe.size() > 0) {
		for (ModellidinamiciRigaHelper riga : righe) {
		    if (riga.getColonne() != null) {
			if (riga.getColonne().size() > 0) {
			    if (numColonneLcl < riga.getColonne().size()) {
				numColonneLcl = riga.getColonne().size();
			    }
			    int lclMaxCampi = 0;
			    for (ModellidinamiciColonnaHelper colonna : riga.getColonne()) {
				if (colonna.getCampo() != null) {
				    lclMaxCampi += 1;
				}
			    }
			    if (lclMaxCampi > numMaxCampi) {
				numMaxCampi = lclMaxCampi;
			    }
			}
		    }
		}
		if (numMaxCampi > 0) {
		    numColonneLcl += numMaxCampi;
		}
	    }
	}
	return numColonneLcl;
    }

    public Dyn2Regole getRegolaAttivazioneUnica() {

	Dyn2Regole r = null;
	if (this.righe != null && righe.size() > 0) {
	    Dyn2Regole ruleTemp = null;
	    for (ModellidinamiciRigaHelper riga : righe) {
		ruleTemp = riga.getRegolaAttivazioneUnica();
		if(ruleTemp == null){
		    return null;
		}
		else{
		    if(r != null && !ruleTemp.getId().getCodice().equals(r.getId().getCodice())){
			return null;
		    }
		}
		r = ruleTemp;
	    }
	}
	return r;
    }

    public boolean containsCampo(Dyn2Campi d2c) {

	boolean contains = false;
	for (ModellidinamiciRigaHelper riga : getRighe()) {
	    for (ModellidinamiciColonnaHelper colonna : riga.getColonne()) {
		if (colonna.getCampo() != null) {
		    Dyn2Campi tempCampo = colonna.getCampo().getDyn2Campi();
		    if (tempCampo != null && d2c.getId().getCodice().equals(tempCampo.getId().getCodice())) {
			return true;
		    }
		}
	    }
	}
	return contains;
    }
}
