package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoMovimentiDAO extends BaseDAO<BorsellinoMovimenti, PkId> {

    Integer findIdNonStornatoByRiferimenti(Integer idPosteggio, Integer idGiornata, Integer idAutorizzazione);

    List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino);
    
    List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino, Date dalladata, Date alladata, Integer firstResult, Integer maxResult, List<TipoEnum> tipoenums);

    List<BorsellinoMovimenti> findByPosizioneDebitoria(Integer dettPosizioneDebitoriaId);
    
    boolean isPresenzaMovimentata(Integer idGiornata);

    boolean isAutorizzazioneMovimentata(Integer idAutorizzazione);

    /**
     * Il metodo verifica se esiste un record per quel posteggio/autorizzazione/giornata pagato che non abbia un
     * movimento di storno (FKID_MERCATID, FKID_MERCATIPRESENZET, FKID_AUTORIZZAZIONI)
     * 
     * @param idGiornata
     * @param idPosteggio
     * @param idAutorizzazione
     * @return
     */
    boolean isPresenzaPagataDaBorsellino(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio);    
    
}
