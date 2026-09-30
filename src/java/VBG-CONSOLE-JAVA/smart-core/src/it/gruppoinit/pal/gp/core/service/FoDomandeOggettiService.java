package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoDomandeOggettiDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoDomandeOggettiService extends BaseService<FoDomandeOggetti, FoDomandeOggettiId> {

    public static enum TIPO_FILE {
	ZIP_DOMANDA, ALLEGATO, RICEVUTA, RICEVUTA_ONERE, MDA_XML, MDA_PDF, MODULO_XML, COPERTINA_XML, COPERTINA_PDF
    };

    /**
     * @see FoDomandeOggettiDAO#findAll(Integer, Integer)
     */
    public List<FoDomandeOggetti> findAll(Integer firstResult, Integer maxResult);

    public List<FoDomandeOggetti> findByIdDomandaFo(String idComuneDomanda, Integer idDomandaFo);

    public List<FoDomandeOggetti> findByOggetto(String idComuneOgg, Integer codiceOgg);

    @Override
    public void insert(FoDomandeOggetti entity);

    @Override
    public void delete(FoDomandeOggetti entity);

    public FoDomandeOggetti findAllegatoZip(String idcomune, Integer idDomandaFo);

    public FoDomandeOggetti findAllegatoRicevuta(String idcomune, Integer idDomandaFo);

    public FoDomandeOggetti insertOrUpdateRicevuta(String idcomune, Integer idDomandaFo, File ricevuta) throws IOException;

    public void insertOrUpdateZipfile(String idcomune, Integer idDomandaFo, File zipFile) throws IOException;
}
