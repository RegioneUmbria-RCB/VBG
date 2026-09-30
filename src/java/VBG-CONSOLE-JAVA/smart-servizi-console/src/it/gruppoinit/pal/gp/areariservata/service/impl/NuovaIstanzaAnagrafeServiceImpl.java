package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.AltriSoggettiTypeHelper;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAnagrafeService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.core.domain.StcDomainHelper;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.RuoloType;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaAnagrafeServiceImpl implements NuovaIstanzaAnagrafeService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaAnagrafeServiceImpl.class);

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setRichiedente(StcDomainHelper.getNewRichiedenteType());
	cmd.setRuoloRichiedente(new RuoloType());
	cmd.setAziendaRichiedente(StcDomainHelper.getNewPersonaGiuridicaType());
	cmd.setIntermediario(StcDomainHelper.getNewPersonaFisicaType());
	cmd.setAltroSoggettoPF(StcDomainHelper.getNewAltriSoggettiType());
	cmd.setAltroSoggettoPG(StcDomainHelper.getNewAltriSoggettiType());
	cmd.setAltroSoggettoPFH(new AltriSoggettiTypeHelper(StcDomainHelper.getNewAltriSoggettiType()));
	cmd.setAltroSoggettoPGH(new AltriSoggettiTypeHelper(StcDomainHelper.getNewAltriSoggettiType()));
	cmd.setAltriSoggetti(new HashMap<Integer, AltriSoggettiType>());
	cmd.setAltriSoggettiHelper(new ArrayList<AltriSoggettiTypeHelper>());
    }
}
