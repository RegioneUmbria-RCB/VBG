package it.gruppoinit.pal.gp.core.features.alfresco;

import java.io.InputStream;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public interface IAlfrescoPersistenceRestAPIService {

    String creaDocumento(Integer codiceOggetto, String nomeFile, byte[] content);

    String aggiornaDocumento(Integer codiceOggetto, String nomeFile, String percorso, byte[] content);

    InputStream getContenuto(Integer codiceOggetto, String nomeFile, String percorso);

    void aggiornaMetadati(Integer codiceOggetto, String nomeFile, String percorso, Map<String, CodiceDescrizioneBean> mdcmisss);
}
