package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.StarConfigOneri;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class StarConfigOneriCommand extends StarBaseCommand implements Serializable {

    private static final long serialVersionUID = 6541480956529915933L;
    private StarConfigOneri cfgRegionale = new StarConfigOneri();
    private Map<String, StarConfigOneri> cfgComuni = new HashMap<String, StarConfigOneri>();
    private StarConfigOneri cfgOneri = new StarConfigOneri();
    
    public StarConfigOneriCommand(){
	this.setComuneLocalizzazione(new Comuni());
    }

    public StarConfigOneri getCfgRegionale() {

	return cfgRegionale;
    }

    public void setCfgRegionale(StarConfigOneri cfgRegionale) {

	this.cfgRegionale = cfgRegionale;
    }

    public Map<String, StarConfigOneri> getCfgComuni() {

	return cfgComuni;
    }

    public void setConfigComuni(Collection<StarConfigOneri> confOneriGruppo) {

	this.cfgComuni.clear();
	if (confOneriGruppo != null) {
	    for (StarConfigOneri configOneri : confOneriGruppo) {
		Comuni comune = configOneri.getComune();
		if (comune == null) {
		    comune = new Comuni();
		    configOneri.setComune(comune);
		}
		this.cfgComuni.put(comune.getCodicecomune(), configOneri);
	    }
	}
    }

    public StarConfigOneri getCfgComune() {

	return this.cfgComuni.get(ORMHelper.getIdente());
    }

    public StarConfigOneri getCfgComune(String codComune) {

	return this.cfgComuni.get(codComune);
    }

    public void addConfigComune(StarConfigOneri cfgComune) {

	if (cfgComune != null) {
	    String codCom = null;
	    if (cfgComune.getComune() != null) {
		codCom = cfgComune.getComune().getCodicecomune();
	    }
	    this.cfgComuni.put(codCom, cfgComune);
	}
    }

    public StarConfigOneri getCfgOneri() {

	return cfgOneri;
    }

    public void setCfgOneri(StarConfigOneri cfgOneri) {

	this.cfgOneri = cfgOneri;
    }
}
