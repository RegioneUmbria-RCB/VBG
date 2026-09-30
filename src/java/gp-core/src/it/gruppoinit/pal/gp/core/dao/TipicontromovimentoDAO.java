package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

import java.util.List;

public interface TipicontromovimentoDAO extends BaseDAO<Tipicontromovimento, PkId> {

    /**
     * 
     * @param amministrazioni
     * @return Una lista di tipi contro mivimenti filtrata per amministrazioni tipi movimento
     */
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiMovimento(Amministrazioni amministrazioni);

    /**
     * 
     * @param amministrazioni
     * @return Una lista di tipi contro movimenti filtrata per amministrazioni tipi contro movimento
     */
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiContromovimento(Amministrazioni amministrazioni);

    /**
     * 
     * @param tipomovimento
     * @param tipocontromovimento
     * @return L'oggetto tipo contro movimento filtrato per i campi tipomovimento e tipocontromovimento
     */
    public Tipicontromovimento findByTipoMovimentoAndTipoContromovimento(Tipimovimento tipomovimento, Tipimovimento tipocontromovimento);
}
