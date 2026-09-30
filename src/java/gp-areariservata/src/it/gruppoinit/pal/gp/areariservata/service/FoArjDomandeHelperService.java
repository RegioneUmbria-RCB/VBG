package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.init.sigepro.rte.types.NuovaIstanzaType;

public interface FoArjDomandeHelperService {

    public void populateNuovaIstanzaCommand(NuovaIstanzaCommand cmd, NuovaIstanzaType nuovaIstanzaType);

    public NuovaIstanzaType populateNuovaIstanzaType(NuovaIstanzaCommand cmd);
}
