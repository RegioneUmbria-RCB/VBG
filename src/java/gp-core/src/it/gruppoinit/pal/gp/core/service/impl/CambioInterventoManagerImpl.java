package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCommand;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCompareHelper;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoComparePropertiesHelper;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CambioInterventoManager;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CambioInterventoManagerImpl implements CambioInterventoManager {

    private AlberoprocService alberoprocService;
    private DocumentiistanzaService documentiistanzaService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Override
    public CambioInterventoCommand popolaCambioInterventoCommandDocumenti(Istanze istanza, CambioInterventoCommand cambioInterventoCommand) {

	if (cambioInterventoCommand == null) {
	    cambioInterventoCommand = new CambioInterventoCommand();
	}
	Alberoproc ap = alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
	AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(ap);
	cambioInterventoCommand.setEntity(istanza);
	////////////////////////INIZIO DOCUMENTI //////////////////////////////////////
	Set<AlberoprocDocumenti> docsAP = helper.getAlberoprocDocumentis();
	List<Documentiistanza> docsIstanza = documentiistanzaService.findByIstanza(istanza.getId().getCodice(), false);
	//aggiungo nei set in modo da avere il nome univoco
	Set<String> docs = new TreeSet<String>();
	for (Documentiistanza documentiistanza : docsIstanza) {
	    docs.add(documentiistanza.getDocumento());
	}
	Map<String, Boolean> docRichiesti = new HashMap<String, Boolean>();
	for (AlberoprocDocumenti documentiistanza : docsAP) {
	    if (BooleanUtils.isTrue(documentiistanza.getRichiesto())) {
		docRichiesti.put(documentiistanza.getDescrizione(), Boolean.TRUE);
	    }
	    docs.add(documentiistanza.getDescrizione());
	}
	List<CambioInterventoCompareHelper> docsCI = cambioInterventoCommand.getDocs();
	for (String doc : docs) {
	    CambioInterventoCompareHelper cich = new CambioInterventoCompareHelper();
	    cich.setCodice(doc);
	    cich.setDescrizione(doc);
	    CambioInterventoComparePropertiesHelper attuale = new CambioInterventoComparePropertiesHelper();
	    for (Documentiistanza documentiistanza : docsIstanza) {
		if (documentiistanza.getDocumento().equalsIgnoreCase(doc)) {
		    attuale.setPresente(true);
		    attuale.setChecked(true);
		    if (documentiistanza.getOggetto() != null) {
			if (documentiistanza.getOggetto().getId() != null) {
			    if (documentiistanza.getOggetto().getId().getCodice() != null) {
				attuale.setReadonly(true);
			    }
			}
		    }
		    if (StringUtils.isNotBlank(documentiistanza.getStcIddocumento())) {
			attuale.setReadonly(true);
		    }
		    break;
		}
	    }
	    CambioInterventoComparePropertiesHelper nuovo = new CambioInterventoComparePropertiesHelper();
	    for (AlberoprocDocumenti documentiistanza : docsAP) {
		if (documentiistanza.getDescrizione().equalsIgnoreCase(doc)) {
		    nuovo.setPresente(true);
		    // nuovo.setChecked(attuale.isChecked());
		    nuovo.setChecked(Boolean.TRUE);
		    nuovo.setReadonly(attuale.isReadonly());
		    if (docRichiesti.get(doc) != null) {
			cich.setDocumentoRichiesto(Boolean.TRUE);
		    }
		    break;
		}
	    }
	    if (attuale.isPresente() && attuale.isChecked() && nuovo.isPresente() && nuovo.isPresente()) {
		attuale.setReadonly(Boolean.TRUE);
		nuovo.setReadonly(Boolean.TRUE);
	    }
	    cich.setAttuale(attuale);
	    cich.setNuovo(nuovo);
	    docsCI.add(cich);
	}
	Collections.sort(docsCI);
	cambioInterventoCommand.setDocs(docsCI);
	//////////////////////// FINE DOCUMENTI/////////////////
	return cambioInterventoCommand;
    }
}
