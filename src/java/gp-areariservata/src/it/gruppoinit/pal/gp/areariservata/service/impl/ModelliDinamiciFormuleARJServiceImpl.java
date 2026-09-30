package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.CampoSchedaHelper;
import it.gruppoinit.pal.gp.areariservata.domain.SchedaHelper;
import it.gruppoinit.pal.gp.areariservata.domain.ValoreParametroTypeHelper;
import it.gruppoinit.pal.gp.areariservata.service.ModelliDinamiciFormuleARJService;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiScriptService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModelliScriptService;
import it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService.EventoModelli;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;

import java.util.List;
import java.util.SortedSet;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelliDinamiciFormuleARJServiceImpl extends BaseServiceImpl<Dyn2Modellit, PkId> implements ModelliDinamiciFormuleARJService {

    private static final Logger log = LoggerFactory.getLogger(ModelliDinamiciFormuleARJServiceImpl.class);
    @Autowired
    private Dyn2CampiScriptService dyn2CampiScriptService;
    @Autowired
    private Dyn2ModelliScriptService dyn2ModelliScriptService;

    @Override
    public void updateLoadModello(DettaglioPraticaType pratica, SchedaHelper schedaH) {

	executeScriptModello(pratica, schedaH, EventoModelli.Caricamento);
    }

    @Override
    public void updateSaveModello(DettaglioPraticaType pratica, SchedaHelper schedaH) {

	executeScriptModello(pratica, schedaH, EventoModelli.Salvataggio);
    }

    @Override
    public void updateLoadCampo(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo) {

	executeScriptCampoScheda(pratica, schedaH, codiceCampo, EventoModelli.Caricamento);
    }

    @Override
    public void updateSaveCampo(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo) {

	executeScriptCampoScheda(pratica, schedaH, codiceCampo, EventoModelli.Salvataggio);
    }

    @Override
    public void updateChangeCampo(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo) {

	executeScriptCampoScheda(pratica, schedaH, codiceCampo, EventoModelli.Modifica);
    }

    private void executeScriptCampoScheda(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo, EventoModelli evento) {

	Dyn2CampiScript campoScript = dyn2CampiScriptService.findByCampoAndEvento(codiceCampo, evento.name());
	if (campoScript != null) {
	    byte[] bScriptDaEseguire = campoScript.getScript();
	    if (bScriptDaEseguire != null) {
		createEngineAndExecute(pratica, schedaH, codiceCampo, bScriptDaEseguire);
	    }
	}
    }

    private void executeScriptModello(DettaglioPraticaType pratica, SchedaHelper schedaH, EventoModelli evento) {

	Dyn2ModelliScript modelloScript = dyn2ModelliScriptService.findByModelloAndEvento(Integer.valueOf(schedaH.getScheda().getCodice()),
		evento.name());
	if (modelloScript != null) {
	    byte[] bScriptDaEseguire = modelloScript.getScript();
	    if (bScriptDaEseguire != null) {
		createEngineAndExecute(pratica, schedaH, null, bScriptDaEseguire);
	    }
	}
    }

    /**
     * @param codiceIstanza
     * @param bScriptDaEseguire
     */
    private void createEngineAndExecute(DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo, byte[] bScriptDaEseguire) {

	String scriptDaEseguire = new String(bScriptDaEseguire);
	ScriptEngine jsEngine = createEngine();
	String imports = setImports();
	String functions = setFunctions();
	putToJsEngine(jsEngine, pratica, schedaH, codiceCampo);
	scriptDaEseguire = imports + functions + scriptDaEseguire;
	log.debug("createEngineAndExecute: {}", scriptDaEseguire);
	try {
	    jsEngine.eval(scriptDaEseguire);
	} catch (ScriptException e) {
	    log.error("createEngineAndExecute: {}", e.getMessage());
	}
    }

    private void putToJsEngine(ScriptEngine jsEngine, DettaglioPraticaType pratica, SchedaHelper schedaH, Integer codiceCampo) {

	jsEngine.put("pratica", pratica);
	jsEngine.put("scheda", schedaH);
	jsEngine.put("codiceCampo", codiceCampo);
    }

    private ScriptEngine createEngine() {

	log.debug("ModelliDinamiciFormuleARJServiceImpl: Inizializzo jsEngine");
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

    @Override
    public Dyn2Modellit findById(PkId id) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<Dyn2Modellit> getEntityClass() {

	return Dyn2Modellit.class;
    }

    @Override
    public void aggiornaValoreCampo(SchedaHelper scheda, String codiceCampoDaAggiornare, String valoreCampoDaAggiornare) {

	List<CampoSchedaHelper> campi = scheda.getCampi();
	boolean campoTrovato = false;
	for (int i = 0; i < campi.size(); i++) {
	    CampoSchedaHelper campo = campi.get(i);
	    if (campo.getCampo().getCodice().equals(codiceCampoDaAggiornare)) {
		campoTrovato = true;
		SortedSet<ValoreParametroTypeHelper> vals = campo.getVtpH();
		if (vals.size() > 0) {
		    vals.first().getVpt().setCodice(valoreCampoDaAggiornare);
		    vals.first().getVpt().setDescrizione(valoreCampoDaAggiornare);
		} else {
		    ValoreParametroTypeHelper valo = new ValoreParametroTypeHelper();
		    ElementoValoreCampoDinamicoType elValo = new ElementoValoreCampoDinamicoType();
		    elValo.setCodice(valoreCampoDaAggiornare);
		    elValo.setDescrizione(valoreCampoDaAggiornare);
		    elValo.setIndice(0);
		    elValo.setIndiceMolteplicita(0);
		    valo.setVpt(elValo);
		    valo.setIdxMolteplicita(0);
		    vals.add(valo);
		}
		break;
	    }
	}
	if (!campoTrovato) {
	    CampoSchedaHelper campoH = new CampoSchedaHelper();
	    ValoreParametroTypeHelper vtpH = new ValoreParametroTypeHelper();
	    ElementoValoreCampoDinamicoType vpt = new ElementoValoreCampoDinamicoType();
	    vpt.setCodice(valoreCampoDaAggiornare);
	    vpt.setDescrizione(valoreCampoDaAggiornare);
	    vpt.setIndice(0);
	    vpt.setIndiceMolteplicita(0);
	    vtpH.setVpt(vpt);
	    vtpH.setIdxMolteplicita(0);
	    campoH.getCampo().setCodice(codiceCampoDaAggiornare);
	    campoH.setMolteplicita(0);
	    campoH.getVtpH().add(vtpH);
	    campoH.getCampo().getCampoDinamico().getValoreUtente().setNome(codiceCampoDaAggiornare);
	    campoH.getCampo().getCampoDinamico().getValoreUtente().getValore().add(vpt);
	    scheda.getCampi().add(campoH);
	}
    }

    public static void main(String[] args) {

	String codiceCampoDaAggiornare = "756";
	DettaglioPraticaType pratica = new DettaglioPraticaType();
	String valoreCampoDaAggiornare = pratica.getRichiedente().getAnagrafica().getCognome() + " "
		+ pratica.getRichiedente().getAnagrafica().getNome();
	SchedaHelper scheda = new SchedaHelper(null);
	List<CampoSchedaHelper> campi = scheda.getCampi();
	for (int i = 0; i < campi.size(); i++) {
	    CampoSchedaHelper campo = campi.get(i);
	    if (campo.getCampo().getCodice().equals(codiceCampoDaAggiornare)) {
		SortedSet<ValoreParametroTypeHelper> vals = campo.getVtpH();
		if (vals.size() > 0) {
		    ValoreParametroTypeHelper val = vals.first();
		    val.getVpt().setCodice(valoreCampoDaAggiornare);
		    val.getVpt().setDescrizione(valoreCampoDaAggiornare);
		} else {
		    ValoreParametroTypeHelper valo = new ValoreParametroTypeHelper();
		    ElementoValoreCampoDinamicoType elValo = new ElementoValoreCampoDinamicoType();
		    elValo.setCodice(valoreCampoDaAggiornare);
		    elValo.setDescrizione(valoreCampoDaAggiornare);
		    elValo.setIndice(0);
		    elValo.setIndiceMolteplicita(0);
		    valo.setVpt(elValo);
		    valo.setIdxMolteplicita(0);
		    vals.add(valo);
		}
	    }
	}
    }
}
