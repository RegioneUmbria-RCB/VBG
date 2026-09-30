package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface Dyn2ModellitDAO extends BaseDAO<Dyn2Modellit, PkId> {

    public List<Dyn2Modellit> findByDescrizione(Dyn2Modellit entity, boolean isComuneBase);

    /**
     * torna la lista dei modelli di tutti i moduli software ordinati per Modulo Software, Descrizione
     * 
     * @param descrizione
     * @return
     */
    public List<Dyn2Modellit> findAllByDescrizione(String descrizione);

    /**
     * Torna la lista dei modelli del moduli software passato, e ordinati per il campo Descrizione
     * 
     * @param entity
     * @return
     */
    public List<Dyn2Modellit> findByDescrizioneAndSoftware(Dyn2Modellit entity, String codicesoftware);
}
