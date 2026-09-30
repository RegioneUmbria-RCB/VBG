package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaBenvenutoService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaBenvenutoServiceImpl implements NuovaIstanzaBenvenutoService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaBenvenutoServiceImpl.class);

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
    }
}
