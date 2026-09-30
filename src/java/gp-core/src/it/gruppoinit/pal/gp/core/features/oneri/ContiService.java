/**
 * 
 */
package it.gruppoinit.pal.gp.core.features.oneri;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * @author lucap
 * 
 */
public interface ContiService extends BaseService<Conti, PkId> {

    public List<Conti> findByDescrizione(String descrizione);

    public List<Conti> findAllActive();

    public List<Conti> findContiAttivi(Date dataRiferimento);

    /**
     * Recupera il conto attivo per la casuale onere indicata come argomento. Ce ne può essere solamente uno. Se non
     * trovato torna oggetto nullo e se trovati più di uno rilancia una eccezione
     * 
     * @param idCausaleOnere
     * @return
     * @throws InvalidConfigurationException;
     */
    public Conti findContoAttivoByIdCausaleOnere(Integer idCausaleOnere) throws InvalidConfigurationException;
}
