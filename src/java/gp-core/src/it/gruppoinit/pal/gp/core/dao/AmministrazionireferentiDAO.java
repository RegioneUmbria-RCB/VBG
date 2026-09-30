package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AmministrazionireferentiDAO extends BaseDAO<Amministrazionireferenti, PkId> {

    /**
     * 
     * @param amministrazioni
     * @return lista di uffici filtrati per amministrazione
     */
    public List<Amministrazionireferenti> findByAmministrazioni(Amministrazioni amministrazioni);
}
