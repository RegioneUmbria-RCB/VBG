package it.gruppoinit.pal.gp.areariservata.service.impl;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAllegatiSchedeService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;

@Service
public class NuovaIstanzaAllegatiSchedeServiceImpl implements NuovaIstanzaAllegatiSchedeService {

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	cmd.getListaPDFSchede().clear();
    }
}
