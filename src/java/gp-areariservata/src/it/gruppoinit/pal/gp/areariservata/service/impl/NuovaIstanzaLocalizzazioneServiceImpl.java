package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaLocalizzazioneService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.areariservata.web.util.StcDomainHelper;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaLocalizzazioneServiceImpl implements NuovaIstanzaLocalizzazioneService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaLocalizzazioneServiceImpl.class);

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setLocalizzazione(StcDomainHelper.getNewLocalizzazioneNelComuneType());
	cmd.setCatasto(new RiferimentoCatastaleType());
	cmd.setRiferimentiCatastali(new HashMap<Integer, RiferimentoCatastaleType>());
    }
}
