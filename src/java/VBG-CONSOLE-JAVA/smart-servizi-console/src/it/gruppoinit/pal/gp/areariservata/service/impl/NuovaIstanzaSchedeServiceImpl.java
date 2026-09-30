package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaSchedeService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.core.domain.SchedaHelper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaSchedeServiceImpl implements NuovaIstanzaSchedeService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaSchedeServiceImpl.class);

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setSchede(new ArrayList<SchedaHelper>());
    }
}
