package it.gruppoinit.pal.gp.core.features.istanze.datidinamici;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@SuppressWarnings("rawtypes")
@Service
public class UpgrRichiamaFormuleCaricamentoESalvataggioServiceImpl extends BaseServiceImpl
	implements IUpgrRichiamaFormuleCaricamentoESalvataggioService {

    private IUpgrRichiamaFormuleCaricamentoESalvataggioDAO caricamentoESalvataggioDAO;
    private Dyn2ModellitService dyn2ModellitService;

    @Autowired
    public void setCaricamentoESalvataggioDAO(IUpgrRichiamaFormuleCaricamentoESalvataggioDAO caricamentoESalvataggioDAO) {

	this.caricamentoESalvataggioDAO = caricamentoESalvataggioDAO;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    public void elaboraFormule(UpgrRichiamaFormuleCaricamentoESalvataggioRequest request) {

	if (request == null || request.getIdScheda() == null || StringUtils.isBlank(request.getListaScId())) {
	    throw new IllegalArgumentException("Impossibile eseguire il task per l'invocazione delle formule senza passare una request corretta");
	}
	//1. recupero le pratiche in base ai filtri nella request
	Set<Integer> elencoInterventi = new HashSet<Integer>();
	for (String codice : request.getListaScId().split(",")) {
	    elencoInterventi.add(Integer.parseInt(codice));
	}
	TreeSet<Integer> codiciIstanza = this.caricamentoESalvataggioDAO.getCodiciIstanzaByInterventi(elencoInterventi);
	//2. per ognuna invoco le formule caricamento/salvataggio delle schede dinamiche,
	//   eventuali errori vengono messi in una variabile e sollevata un'eccezione alla fine
	StringBuilder logErrori = new StringBuilder();
	for (Integer codiceIstanza : codiciIstanza) {
	    try {
		String[] erroriSalvataggio = this.dyn2ModellitService.eseguiScriptAggiornamentoSchedaIstanza(codiceIstanza, request.getIdScheda());
		if (erroriSalvataggio != null && erroriSalvataggio.length > 0) {
		    for (String errore : erroriSalvataggio) {
			logErrori.append("ERRORE nell'elaborazione formule caricamento e salvataggio per l'.istanza ");
			logErrori.append(codiceIstanza);
			logErrori.append(" errore: ");
			logErrori.append(errore);
			logErrori.append("\r\n");
		    }
		}
	    } catch (Exception e) {
		logErrori.append("ERRORE nell'elaborazione formule caricamento e salvataggio per l'.istanza ");
		logErrori.append(codiceIstanza);
		logErrori.append(" errore: ");
		logErrori.append(e.getMessage());
		logErrori.append("\r\n");
	    }
	}
	//3. Se sono presenti errori di elaborazione viene rilanciata una RuntimeException
	if (StringUtils.isNotEmpty(logErrori.toString())) {
	    throw new RuntimeException(logErrori.toString());
	}
    }

    @Override
    public void insert(Object entity) {

	throw new NotImplementedException("");
    }

    @Override
    public void update(Object entity) {

	throw new NotImplementedException("");
    }

    @Override
    public void delete(Object entity) {

	throw new NotImplementedException("");
    }

    @Override
    public List findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("");
    }

    @Override
    public Object findById(Serializable id) {

	throw new NotImplementedException("");
    }

    @Override
    protected Class getEntityClass() {

	throw new NotImplementedException("");
    }
}
