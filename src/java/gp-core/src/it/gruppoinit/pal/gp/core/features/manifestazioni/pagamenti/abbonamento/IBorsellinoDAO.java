package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;

public interface IBorsellinoDAO extends BaseDAO<Borsellino, PkId> {

    List<SituazioneBorsellinoPerSoglia> situazioneBorsellinoPerSoglia(Set<Integer> auts, BigDecimal soglia);

    List<Borsellino> findByCodiceAnagrafe(Integer codiceAnagrafe);

    Borsellino findBorsellinoAttivoByCodiceAnagrafe(Integer codiceAnagrafe) throws BorsellinoException;

    Borsellino findByUuid(String uuidBorsellino) throws BorsellinoException;

    List<AbbonamentoTabellaModelCompleta> findListaBorsellini(RicercaBorselliniRequest filtri);

    List<AutorizzazioniModel> findAllAutorizzazioni();

    List<Integer> findBorselliniPerAutorizzazione(String autorizzazione);

}
