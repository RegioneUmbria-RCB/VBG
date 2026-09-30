package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;

public interface OggettiMetadatiDAO extends BaseDAO<OggettiMetadati, OggettiMetadatiId> {

    public void deleteByOggetto(Integer codiceOggetto);

    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore);

    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore);
}
