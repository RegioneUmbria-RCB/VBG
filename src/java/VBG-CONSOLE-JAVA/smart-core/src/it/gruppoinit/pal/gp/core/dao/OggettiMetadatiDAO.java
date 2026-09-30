package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;

public interface OggettiMetadatiDAO extends BaseDAO<OggettiMetadati, OggettiMetadatiId> {

    public void deleteByOggetto(Integer codiceOggetto, String idcomune);

    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore, String idcomune);

    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore, String idcomune);
}
