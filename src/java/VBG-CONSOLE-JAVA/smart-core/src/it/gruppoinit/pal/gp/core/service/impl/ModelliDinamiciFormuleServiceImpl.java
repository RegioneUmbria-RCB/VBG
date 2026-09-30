package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiScriptService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModelliScriptService;
import it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelliDinamiciFormuleServiceImpl extends BaseServiceImpl<Dyn2Modellit, PkId> implements ModelliDinamiciFormuleService {

    private static final Logger log = LoggerFactory.getLogger(ModelliDinamiciFormuleServiceImpl.class);
    private Dyn2CampiScriptService dyn2CampiScriptService;
    private Dyn2ModelliScriptService dyn2ModelliScriptService;

    @Autowired
    public void setDyn2CampiScriptService(Dyn2CampiScriptService dyn2CampiScriptService) {

	this.dyn2CampiScriptService = dyn2CampiScriptService;
    }

    @Autowired
    public void setDyn2ModelliScriptService(Dyn2ModelliScriptService dyn2ModelliScriptService) {

	this.dyn2ModelliScriptService = dyn2ModelliScriptService;
    }

    @Override
    public void updateLoadModelloIstanza(Integer codiceIstanza, Integer codiceModello) {

	executeScriptModelloIstanza(codiceIstanza, codiceModello, EventoModelli.Caricamento);
    }

    @Override
    public void updateSaveModelloIstanza(Integer codiceIstanza, Integer codiceModello) {

	executeScriptModelloIstanza(codiceIstanza, codiceModello, EventoModelli.Caricamento);
    }

    @Override
    public void updateLoadCampoIstanza(Integer codiceIstanza, Integer codiceCampo) {

	executeScriptCampoIstanza(codiceIstanza, codiceCampo, EventoModelli.Caricamento);
    }

    @Override
    public void updateSaveCampoIstanza(Integer codiceIstanza, Integer codiceCampo) {

	executeScriptCampoIstanza(codiceIstanza, codiceCampo, EventoModelli.Salvataggio);
    }

    @Override
    public void updateChangeCampoIstanza(Integer codiceIstanza, Integer codiceCampo) {

	executeScriptCampoIstanza(codiceIstanza, codiceCampo, EventoModelli.Modifica);
    }

    private void executeScriptCampoIstanza(Integer codiceIstanza, Integer codiceCampo, EventoModelli evento) {

	Dyn2CampiScript campoScript = dyn2CampiScriptService.findByCampoAndEvento(codiceCampo, evento.name());
	if (campoScript != null) {
	    byte[] bScriptDaEseguire = campoScript.getScript();
	    if (bScriptDaEseguire != null) {
		createEngineAndExecute(codiceIstanza, bScriptDaEseguire);
	    }
	}
    }

    private void putIstanza(ScriptEngine jsEngine, Integer codiceIstanza) {

    }

    private void executeScriptModelloIstanza(Integer codiceIstanza, Integer codiceModello, EventoModelli evento) {

	Dyn2ModelliScript modelloScript = dyn2ModelliScriptService.findByModelloAndEvento(codiceModello, evento.name());
	if (modelloScript != null) {
	    byte[] bScriptDaEseguire = modelloScript.getScript();
	    if (bScriptDaEseguire != null) {
		createEngineAndExecute(codiceIstanza, bScriptDaEseguire);
	    }
	}
    }

    /**
     * @param codiceIstanza
     * @param bScriptDaEseguire
     */
    private void createEngineAndExecute(Integer codiceIstanza, byte[] bScriptDaEseguire) {

	String scriptDaEseguire = new String(bScriptDaEseguire);
	ScriptEngine jsEngine = createEngine();
	putIstanza(jsEngine, codiceIstanza);
	String imports = setImports();
	String functions = setFunctions();
	scriptDaEseguire = imports + functions + scriptDaEseguire;
	log.debug("createEngineAndExecute: {}", scriptDaEseguire);
	try {
	    jsEngine.eval(scriptDaEseguire);
	} catch (ScriptException e) {
	    log.error("updateLoadModelloIstanza: {}", e.getMessage());
	}
    }

    private ScriptEngine createEngine() {

	log.debug("ModelliDinamiciFormuleServiceImpl: Inizializzo jsEngine");
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine jsEngine = mgr.getEngineByExtension("js");
	return jsEngine;
    }

    private String setImports() {

	String imports = "importPackage(Packages." + BaseService.class.getPackage().getName() + ");";
	imports += "\nimportPackage(Packages." + Comuni.class.getPackage().getName() + ");";
	imports += "\nimportPackage(Packages." + Utilities.class.getPackage().getName() + ");";
	imports += "\nimportPackage(Packages." + org.springframework.web.context.ContextLoader.class.getPackage().getName() + ");";
	return imports;
    }

    private String setFunctions() {

	String functions = "\nfunction getService(serviceName){";
	functions += "	\nvar result=null;";
	functions += "	\nresult = ContextLoader.getCurrentWebApplicationContext().getBean(serviceName);";
	functions += "	\nreturn result;";
	functions += "}\n";
	return functions;
    }

    @Override
    protected Class<Dyn2Modellit> getEntityClass() {

	return Dyn2Modellit.class;
    }

    @Override
    public Dyn2Modellit findById(PkId id) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(Dyn2Modellit entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Dyn2Modellit entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Dyn2Modellit entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Dyn2Modellit> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
