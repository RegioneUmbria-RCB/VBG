package it.gruppoinit.service.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.service.DeployProperties;
import it.gruppoinit.service.ParserI1GMUTAFaldoneTelematicoService;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.definitions.StcWSClient;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;

@Service
public class CreazionePraticaSistemaEsternoHelper {

    @Autowired
    private StcWSClient stcWSClient;
    @Autowired
    ParserI1GMUTAFaldoneTelematicoService parserI1GMUTAFaldoneTelematicoService;
    @Autowired
    DeployProperties deployProperties;

    public RiferimentiPraticaType creaPratica(InserimentoPraticaNLARequest request, SportelloType mittente, SportelloType destinatario,
	    String tokenStc, boolean popolaProcedimenti) throws Exception {

	List<DocumentiType> documenti = request.getDettaglioPratica().getDocumenti();
	DettaglioPraticaType dettaglioPraticaType = parserI1GMUTAFaldoneTelematicoService.getDettaglioPraticaType(documenti, popolaProcedimenti);
	if (dettaglioPraticaType != null) {
	    dettaglioPraticaType.setDataPratica(request.getDettaglioPratica().getDataPratica());
	    dettaglioPraticaType.setDataProtocolloGenerale(request.getDettaglioPratica().getDataProtocolloGenerale());
	    dettaglioPraticaType.setNumeroProtocolloGenerale(request.getDettaglioPratica().getNumeroProtocolloGenerale());
	    InserimentoPraticaResponse inserisciPraticaResponse = stcWSClient.inserisciPratica(mittente, destinatario, tokenStc,
		    dettaglioPraticaType);
	    return inserisciPraticaResponse.getDettaglioPratica();
	}
	return null;
    }
}
