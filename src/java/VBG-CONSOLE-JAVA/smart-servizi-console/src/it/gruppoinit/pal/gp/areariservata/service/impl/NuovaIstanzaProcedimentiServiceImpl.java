package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentiHelper;
import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentoHelper;
import it.gruppoinit.pal.gp.areariservata.service.AlberoProcARJService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaProcedimentiService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaProcedimentiServiceImpl implements NuovaIstanzaProcedimentiService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaProcedimentiServiceImpl.class);
    @Autowired
    private AlberoProcARJService alberoProcARJService;
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setProcedimentiHelper(null);
	cmd.getProcedimentiSelezionati().clear();
	cmd.getEstremiAtto().clear();
    }

    @Override
    public ProcedimentiHelper getProcedimenti(Integer codiceIntervento) {

	Alberoproc alberoproc = alberoProcARJService.findById(new PkId(codiceIntervento));
	List<AlberoprocEndo> alberoprocEndo = alberoProcARJService.findListaEndoPubblicati(alberoproc);
	ProcedimentiHelper procedimentiHelper = new ProcedimentiHelper(alberoprocEndo,alberoprocEndoService);
	procedimentiHelper.elabora();
	return procedimentiHelper;
    }

    @Override
    public ProcedimentoHelper getProcedimento(Integer codiceProcedimento) {

	Inventarioprocedimenti invProc = inventarioprocedimentiService.findById(new PkId(codiceProcedimento));
	ProcedimentoHelper procedimentoHelper = new ProcedimentoHelper(invProc);
	return procedimentoHelper;
    }
}
