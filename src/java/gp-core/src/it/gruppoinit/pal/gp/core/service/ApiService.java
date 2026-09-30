package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.service.helper.RiferimentiPraticaSTCRestBean;

import org.springframework.web.multipart.MultipartFile;

public interface ApiService {

    /**
     * Invoca l'API di creazione pratica a partire da un file ZIP che rispetta la modellazione definita per il servizio
     * 
     * @param zipFile
     *            la cartella Zip degli ALLEGATI
     * 
     * @return
     * @throws Exception
     */
    public RiferimentiPraticaSTCRestBean creaPraticaDaFileZip(String numero_protocollo, String data_protocollo, MultipartFile zipFile)
	    throws Exception;
}
