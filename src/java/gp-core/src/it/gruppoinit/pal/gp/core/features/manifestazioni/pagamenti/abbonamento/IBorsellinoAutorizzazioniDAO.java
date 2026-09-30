package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioniId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;

public interface IBorsellinoAutorizzazioniDAO extends BaseDAO<BorsellinoAutorizzazioni, BorsellinoAutorizzazioniId> {

    boolean isBorsellinoAttivo(Integer idAutorizzazione);

    Integer findBorsellinoAttivo(Integer idAutorizzazione);

    BigDecimal proiezione(Integer idBorsellino, BigDecimal importo);

    List<BorsellinoAutorizzazioni> findAutorizzazioniByBorsellino(Integer idBorsellino);

    List<BorsellinoAutorizzazioni> findByIdAutorizzazione(Integer idAutorizzazione);

    EsitoOperazioneAggiornamento collegaAutorizzazione(String uuidBorsellino, Integer idAutorizzazione) throws BorsellinoException;

    void scollegaAutorizzazione(Integer idAutorizzazione);
}
