package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;

public interface LayouttestiDAO extends BaseDAO<Layouttesti, LayouttestiId> {

    String resolveCode(String code, String software);

    /**
     * torna la lista delle etichette filtrate per il prefisso % ed ordinate per titolo,software opzionale asc. la query
     * torna le etichette con lo stesso nome ordinate prima per software TT (opzionale o) e poi per il software corrente
     * in modo tale che la mappa sovrascrive se presente l'etichetta con software TT con quella del software corrente
     * 
     * @param prefissoEtichette
     * @return
     */
    List<Layouttesti> findByPrefissoOrderBySoftware(String prefissoEtichette);

    List<LayoutTestiDTO> findTesti();
}
