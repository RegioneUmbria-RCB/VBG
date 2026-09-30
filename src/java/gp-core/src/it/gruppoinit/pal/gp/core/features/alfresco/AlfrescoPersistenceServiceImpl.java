package it.gruppoinit.pal.gp.core.features.alfresco;

import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

@Service
public class AlfrescoPersistenceServiceImpl implements IAlfrescoPersistenceService {

    @Autowired
    private IAlfrescoPersistenceCMISService cmisService;
    @Autowired
    private IAlfrescoPersistenceRestAPIService restAPIService;

    @Override
    public String creaDocumento(Integer codiceOggetto, String nomeFile, byte[] content, STRATEGY strategy) {

	checkStrategy(strategy);
	if (strategy.equals(STRATEGY.CMIS)) {
	    return cmisService.creaDocumento(codiceOggetto, nomeFile, content);
	} else if (strategy.equals(STRATEGY.API_REST)) {
	    return restAPIService.creaDocumento(codiceOggetto, nomeFile, content);
	}
	throw new IllegalArgumentException("Strategia di persistenza alfresco non valida " + strategy);
    }

    private void checkStrategy(STRATEGY strategy) {

	if (strategy == null) {
	    throw new IllegalArgumentException("Strategia di persistenza alfresco nulla ");
	}
    }

    @Override
    public String aggiornaDocumento(Integer codiceOggetto, String nomeFile, String percorso, byte[] content, STRATEGY strategy) {

	checkStrategy(strategy);
	if (strategy.equals(STRATEGY.CMIS)) {
	    return cmisService.aggiornaDocumento(codiceOggetto, nomeFile, percorso, content);
	} else if (strategy.equals(STRATEGY.API_REST)) {
	    return restAPIService.aggiornaDocumento(codiceOggetto, nomeFile, percorso, content);
	}
	throw new IllegalArgumentException("Strategia di persistenza alfresco non valida " + strategy);
    }

    @Override
    public InputStream getContenuto(Integer codiceOggetto, String nomeFile, String percorso, STRATEGY strategy) {

	checkStrategy(strategy);
	if (strategy.equals(STRATEGY.CMIS)) {
	    return cmisService.getContenuto(codiceOggetto, nomeFile, percorso);
	} else if (strategy.equals(STRATEGY.API_REST)) {
	    return restAPIService.getContenuto(codiceOggetto, nomeFile, percorso);
	}
	throw new IllegalArgumentException("Strategia di persistenza alfresco non valida " + strategy);
    }

    @Override
    public void aggiornaMetadati(Integer codiceOggetto, String nomeFile, String percorso, STRATEGY strategy,
	    Map<String, CodiceDescrizioneBean> mdcmisss) {

	checkStrategy(strategy);
	if (strategy.equals(STRATEGY.CMIS)) {
	    cmisService.aggiornaMetadati(codiceOggetto, nomeFile, percorso, mdcmisss);
	} else if (strategy.equals(STRATEGY.API_REST)) {
	    restAPIService.aggiornaMetadati(codiceOggetto, nomeFile, percorso, mdcmisss);
	}
    }
}
