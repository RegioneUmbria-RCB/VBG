package it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;

public interface AutorizzazioniMetadatiDAO extends BaseDAO<AutorizzazioniMetadati, AutorizzazioniMetadatiId> {

    void deleteByIdAutorizzazioni(Integer codice);
}
