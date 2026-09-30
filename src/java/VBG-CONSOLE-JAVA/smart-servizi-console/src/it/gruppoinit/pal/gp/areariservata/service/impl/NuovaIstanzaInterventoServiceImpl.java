package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAllegatiService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAnagrafeService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaInterventoService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaLocalizzazioneService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaOneriService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaProcedimentiService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaSchedeService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.init.sigepro.rte.types.InterventoType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaInterventoServiceImpl implements NuovaIstanzaInterventoService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaInterventoServiceImpl.class);
    @Autowired
    private NuovaIstanzaAnagrafeService nuovaIstanzaAnagrafeService;
    @Autowired
    private NuovaIstanzaLocalizzazioneService nuovaIstanzaLocalizzazioneService;
    @Autowired
    private NuovaIstanzaProcedimentiService nuovaIstanzaProcedimentiService;
    @Autowired
    private NuovaIstanzaAllegatiService nuovaIstanzaAllegatiService;
    @Autowired
    private NuovaIstanzaSchedeService nuovaIstanzaSchedeService;
    @Autowired
    private NuovaIstanzaOneriService nuovaIstanzaOneriService;

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setIntervento(new InterventoType());
    }

    @Override
    public void clearStepsCambioIntervento(NuovaIstanzaCommand cmd) {

	log.debug("clearStepsCambioIntervento");
	nuovaIstanzaAnagrafeService.clearStep(cmd);
	nuovaIstanzaOneriService.clearStep(cmd);
	nuovaIstanzaProcedimentiService.clearStep(cmd);
	nuovaIstanzaAllegatiService.clearStep(cmd);
	nuovaIstanzaSchedeService.clearStep(cmd);
    }
}
