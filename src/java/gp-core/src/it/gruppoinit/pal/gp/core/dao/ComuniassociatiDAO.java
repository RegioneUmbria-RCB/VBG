package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;

public interface ComuniassociatiDAO extends BaseDAO<Comuniassociati, ComuniassociatiId> {

    /**
     * torna la lista dei comuni associati per l'idcomune passato
     * 
     * @param idcomune
     * @return
     */
    public List<Comuniassociati> findByIdcomune(String idcomune);

    /**
     * Ritorna la lista dei comuni associati che non sono presenti nella tabella COMUNIASSOCIATIESCLUSIONI
     * 
     * @param codicecomuni
     * @param software
     * @return
     */
    public List<Comuniassociati> findByComuniEsclusioni(String[] codicecomuni);
}
