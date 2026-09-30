package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoRicaricheDAO extends BaseDAO<BorsellinoRicariche, PkId> {

    List<BorsellinoRicariche> findByCodiceComune(String codiceComune);

    void deleteById(Integer id);

    /**
     * Verifica se per l'ente è già stato configurato un importo Libero. <br />
     * Ne può essere configurato solamente uno libero
     * 
     * @param ric
     * @return
     */
    boolean importoLiberoConfiguratoPerComune(String codiceComune);

    void deleteImportiLiberoPerComune(String codicecomune);
}
