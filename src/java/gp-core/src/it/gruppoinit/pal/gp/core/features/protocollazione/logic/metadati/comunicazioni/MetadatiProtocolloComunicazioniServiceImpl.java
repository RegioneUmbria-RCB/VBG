package it.gruppoinit.pal.gp.core.features.protocollazione.logic.metadati.comunicazioni;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.features.metadati.MetadatoComune;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.ws.client.ProtocolloWSClient;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfMetadatoType;
import it.gruppoinit.protocollo.schemas.messages.IProtocollazioneService;
import it.gruppoinit.protocollo.schemas.messages.MetadatoType;

@Service
public class MetadatiProtocolloComunicazioniServiceImpl implements IMetadatiProtocolloComunicazioniService {

    private static final Logger logger = LoggerFactory.getLogger(MetadatiProtocolloComunicazioniServiceImpl.class);
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public List<MetadatoComune> get() {

	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    List<Verticalizzazioni> protocolliAttivi = this.verticalizzazioniService
		    .findAttivazioni(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	    List<MetadatoComune> retVal = new ArrayList<MetadatoComune>(0);
	    for (Verticalizzazioni protocolloAttivo : protocolliAttivi) {
		//1. Verifico che il software per il quale è attivo il protocollo sia TT o identico a quello su cui sto lavorando
		if (protocolloAttivo.getSoftware().getCodice().equalsIgnoreCase(ORMHelper.getSoftware())
			|| protocolloAttivo.getSoftware().getCodice().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
		    String token = ORMHelper.getToken();
		    String software = protocolloAttivo.getSoftware().getCodice();
		    String codiceComune = protocolloAttivo.getComune() != null ? protocolloAttivo.getComune().getCodicecomune() : null;
		    String comune = protocolloAttivo.getComune() != null ? protocolloAttivo.getComune().getComune() : null;
		    ArrayOfMetadatoType metadati = port.recuperaMetadati(token, software, codiceComune);
		    logger.debug("recuperaMetadati: result [{}]", metadati);
		    for (MetadatoType metadato : metadati.getMetadatoType()) {
			retVal.add(new MetadatoComune(codiceComune, comune, metadato.getChiave(), metadato.getValore()));
		    }
		}
	    }
	    return retVal;
	} catch (Exception e) {
	    logger.error("recuperaMetadati: {}", e.getMessage());
	    throw new RuntimeException(e);
	}
    }
}
