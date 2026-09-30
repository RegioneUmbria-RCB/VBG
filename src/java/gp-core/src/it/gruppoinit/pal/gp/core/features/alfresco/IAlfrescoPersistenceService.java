package it.gruppoinit.pal.gp.core.features.alfresco;

import java.io.InputStream;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public interface IAlfrescoPersistenceService {

    public static final String CMIS_WORKSPACE_FILE_IDENTIFIER = "workspace://SpacesStore/";

    enum STRATEGY {
	CMIS,
	API_REST
    }

    String creaDocumento(Integer codiceOggetto, String nomeFile, byte[] content, STRATEGY strategy);

    String aggiornaDocumento(Integer codiceOggetto, String nomeFile, String percorso, byte[] content, STRATEGY strategy);

    InputStream getContenuto(Integer codiceOggetto, String nomeFile, String percorso, STRATEGY strategy);

    void aggiornaMetadati(Integer codiceOggetto, String nomeFile, String percorso, STRATEGY strategy, Map<String, CodiceDescrizioneBean> mdcmisss);
}
