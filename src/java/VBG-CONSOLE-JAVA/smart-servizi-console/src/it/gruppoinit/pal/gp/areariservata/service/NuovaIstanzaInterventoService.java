package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;

public interface NuovaIstanzaInterventoService extends NuovaIstanzaBaseService {

    public void clearStepsCambioIntervento(NuovaIstanzaCommand cmd);
}
