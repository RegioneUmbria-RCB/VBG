package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocFoTop;
import it.gruppoinit.pal.gp.core.domain.InventarioprocFoTop;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerInterventiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerProcedimentiHelper;
import it.gruppoinit.pal.gp.core.service.AlberoprocFoTopService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CopiaAlberoprocAndInvetarioProcFOTopManager;
import it.gruppoinit.pal.gp.core.service.InventarioprocFoTopService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CopiaAlberoprocAndInvetarioProcFOTopManagerImpl extends BaseEnvironment implements CopiaAlberoprocAndInvetarioProcFOTopManager {

    private static final Logger log = LoggerFactory.getLogger(CopiaAlberoprocAndInvetarioProcFOTopManagerImpl.class);
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AlberoprocFoTopService alberoprocFoTopService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private InventarioprocFoTopService inventarioprocFoTopService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private SoftwareService softwareService;

    @Override
    public void eseguiCopia(String idComuneAlias, String software, List<String> softwares, Integer intervallogironi, Integer maxInterventi,
	    Integer maxProcedimenti) {

	log.debug("eseguiCopia# start...");
	log.debug("Setto i parametri idcomune e software nell' ORMHelper");
	if (StringUtils.isNotBlank(idComuneAlias)) {
	    if (StringUtils.isNotBlank(software)) {
		setORMHelperSoftware(idComuneAlias, software);
	    } else {
		setORMHelper(idComuneAlias);
	    }
	}
	// Ciclo tutti i software passati
	for (String softw : softwares) {
	    log.debug("Elimino tutti i vecchi record di AlberoprocFoTop per il software: {}", softw);
	    List<AlberoprocFoTop> alberoprocFoTops = alberoprocFoTopService.findBySoftware(softw);
	    for (AlberoprocFoTop alberoprocFoTop : alberoprocFoTops) {
		alberoprocFoTopService.delete(alberoprocFoTop);
	    }
	    log.debug("Elimino tutti i vecchi record di InventarioprocFoTop per software: {}", softw);
	    List<InventarioprocFoTop> inventarioprocFoTops = inventarioprocFoTopService.findBySoftware(softw);
	    for (InventarioprocFoTop inventarioprocFoTop : inventarioprocFoTops) {
		inventarioprocFoTopService.delete(inventarioprocFoTop);
	    }
	}
	for (String softw : softwares) {
	    softw = softw.trim();
	    Software _software = softwareService.findById(softw);
	    // 1. Eseguire query che ricerca gli albero proc più utilizzati 
	    log.debug("Query per la ricerca degli interventi più utilizzati per software: {}", softw);
	    Date toDate = new Date();
	    Date fromDate = Utilities.addAndremoveDays(toDate, intervallogironi, false);
	    List<IstanzePerInterventiHelper> list = istanzeService.countNumeroIstanzeGrupByInterventi(softw, fromDate, toDate, 0, maxInterventi);
	    for (IstanzePerInterventiHelper istanzePerInterventiHelper : list) {
		AlberoprocFoTop alberoprocFoTop = populateAlberoProcFOTop(istanzePerInterventiHelper, _software);
		alberoprocFoTopService.insert(alberoprocFoTop);
	    }
	    log.debug("Query per la ricerca dei procediment più utilizzati per software {}", softw);
	    List<IstanzePerProcedimentiHelper> listInter = istanzeService.countNumeroIstanzeGrupByProcedimenti(softw, fromDate, toDate, 0,
		    maxProcedimenti);
	    for (IstanzePerProcedimentiHelper istanzePerProcedimentiHelper : listInter) {
		InventarioprocFoTop inventarioprocFoTop = populateInventarioprocFoTop(istanzePerProcedimentiHelper, _software);
		inventarioprocFoTopService.insert(inventarioprocFoTop);
	    }
	}
    }

    private AlberoprocFoTop populateAlberoProcFOTop(IstanzePerInterventiHelper istanzePerInterventiHelper, Software software) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(istanzePerInterventiHelper.getCodiceIntervento()));
	AlberoprocFoTop alberoprocFoTop = new AlberoprocFoTop();
	alberoprocFoTop.setAlberoproc(alberoproc);
	alberoprocFoTop.setDescrizione(alberoproc.getScDescrizione());
	alberoprocFoTop.setOrdine(istanzePerInterventiHelper.getNumero());
	alberoprocFoTop.setSoftware(software);
	return alberoprocFoTop;
    }

    private InventarioprocFoTop populateInventarioprocFoTop(IstanzePerProcedimentiHelper istanzePerProcedimentiHelper, Software software) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(istanzePerProcedimentiHelper
		.getCodiceProcedimento()));
	InventarioprocFoTop inventarioprocFoTop = new InventarioprocFoTop();
	inventarioprocFoTop.setDescrizione(inventarioprocedimenti.getProcedimento());
	inventarioprocFoTop.setOrdine(istanzePerProcedimentiHelper.getNumero());
	inventarioprocFoTop.setInventarioprocedimenti(inventarioprocedimenti);
	inventarioprocFoTop.setSoftware(software);
	return inventarioprocFoTop;
    }
}
