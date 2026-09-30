package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentoHelper;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAllegatiService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.core.domain.DocumentoHelper;
import it.gruppoinit.pal.gp.core.domain.StcDomainHelper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaAllegatiServiceImpl implements NuovaIstanzaAllegatiService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaAllegatiServiceImpl.class);

    @Override
    public void clearStep(NuovaIstanzaCommand cmd) {

	log.debug("clearStep");
	cmd.setAllegatiProcedimenti(new ArrayList<ProcedimentoHelper>());
	cmd.setAllegatiProcedimentiCaricati(new ArrayList<DocumentoHelper>());
	cmd.setDocumentoCaricato(new DocumentoHelper(StcDomainHelper.getNewDocumentiType()));
	cmd.setAllegatiProcedimenti(new ArrayList<ProcedimentoHelper>());
	cmd.setAllegatiIntervento(new ArrayList<DocumentoHelper>());
	cmd.setAllegatiInterventoCaricati(new ArrayList<DocumentoHelper>());
    }
}
