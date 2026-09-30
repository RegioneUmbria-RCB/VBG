package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;

import java.util.List;

public interface NuovaIstanzaOneriService extends NuovaIstanzaBaseService {

    List<FoArjDomandeOneri> popolaOneri(NuovaIstanzaCommand command, Integer codiceDomanda);
}
