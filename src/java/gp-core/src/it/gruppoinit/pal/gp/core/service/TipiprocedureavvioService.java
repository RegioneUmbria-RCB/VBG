package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureavvioDAO;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureavvioId;

import java.util.List;

public interface TipiprocedureavvioService extends BaseService<Tipiprocedureavvio, TipiprocedureavvioId> {

    /**
     * @see TipiprocedureavvioDAO#isMovimentoAvvioDefault(Tipiprocedureavvio tipiprocedureavvio)
     */
    public Boolean isMovimentoAvvioDefault(Tipiprocedureavvio tipiprocedureavvio);

    /**
     * @see TipiprocedureavvioDAO#findTipiprocedureavvioDeafult(Tipiprocedureavvio tipiprocedureavvio)
     */
    public Tipiprocedureavvio findTipiprocedureavvioDeafult(Tipiprocedureavvio tipiprocedureavvio);

    /**
     * Torna tutti i record di TIPIPROCEDUREAVVIO dove TIPOMOVIMENTO è uguale a quello passato. i record vengono
     * ordinati per TIPIPROCEDUREAVVIO.CODICEPROCEDURA.SOFTWARE.ORDINE ASC,
     * TIPIPROCEDUREAVVIO.CODICEPROCEDURA.SOFTWARE.DESCRIZIONE ASC, TIPIPROCEDUREAVVIO.CODICEPROCEDURA.PROCEDURA ASC
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipiprocedureavvio> findTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);
}
