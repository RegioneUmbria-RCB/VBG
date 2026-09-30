package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureavvioId;

public interface TipiprocedureavvioDAO extends BaseDAO<Tipiprocedureavvio, TipiprocedureavvioId> {

    /**
     * Il metodo ritorna true se è presente un movimento di avvio di default per la tipo procedure scelta; altrimenti
     * ritorna false
     * 
     * @param tipiprocedure
     * @return
     */
    public Boolean isMovimentoAvvioDefault(Tipiprocedureavvio tipiprocedureavvio);

    /**
     * Il metoto ritorna un oggetto Tipo procedura avvio se esiste un tipo procedura avvio configurato, per il tipo
     * procedimento in esame, come di default (campo defaultsn=1) altrimenti restituisce null;
     * 
     * @param tipiprocedureavvio
     * @return
     */
    public Tipiprocedureavvio findTipiprocedureavvioDeafult(Tipiprocedureavvio tipiprocedureavvio);
}
