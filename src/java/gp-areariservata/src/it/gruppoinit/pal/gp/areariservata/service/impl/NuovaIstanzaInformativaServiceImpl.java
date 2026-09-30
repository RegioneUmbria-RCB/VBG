package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.Informativa;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaInformativaService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaInformativaServiceImpl implements NuovaIstanzaInformativaService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaInformativaServiceImpl.class);

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setInformativa(new Informativa());
    }
}
