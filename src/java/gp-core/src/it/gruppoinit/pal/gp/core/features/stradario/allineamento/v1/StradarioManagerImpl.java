package it.gruppoinit.pal.gp.core.features.stradario.allineamento.v1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;

@Component
public class StradarioManagerImpl extends BaseEnvironment implements StradarioManager {

    private static final Logger log = LoggerFactory.getLogger(StradarioManagerImpl.class);
    private ComuniassociatiService comuniassociatiService;
    private StradarioService stradarioService;

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Override
    public synchronized void allineaStradario(Set<String> codiciComune) {

	try {
	    boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO);
	    if (isAttiva) {
		log.debug("allineaStradario# Allineamento stradario per l'installazione {}", ORMHelper.getIdcomune());
		if (codiciComune == null || codiciComune.isEmpty()) {
		    List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
		    codiciComune = new HashSet<String>();
		    for (Comuniassociati comuniassociati : comuniassociatis) {
			codiciComune.add(comuniassociati.getComune().getCodicecomune());
		    }
		}
		stradarioService.updateAllineaStradario(codiciComune, null);
	    } else {
		log.debug("allineaStradario# Sit non attivo per l'installazione {}", ORMHelper.getIdcomuneAlias());
	    }
	} catch (Exception e) {
	    log.error("allineaStradario# Errore durante l'allineamento dello stradario per l'installazione {}", ORMHelper.getIdcomuneAlias(), e);
	} finally {
	    resetThreadLocalVars();
	}
	log.info("allineaStradario# termine processo di allineamento stradario");
    }
}
