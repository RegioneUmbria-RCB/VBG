package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipimovimentoComunicazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoComunicazioni;
import it.gruppoinit.pal.gp.core.service.helper.TipoComunicazionemovimentoEnum;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipimovimentoComunicazioniService extends BaseService<TipimovimentoComunicazioni, PkId> {

    /**
     * @see TipimovimentoComunicazioniDAO#findAll(Integer, Integer)
     */
    public List<TipimovimentoComunicazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna il record che rappresenta la comunicazione per la funzione passata e per il tipo movimento. Può tornare
     * vuoto, significa che il tipo movimento non prevede cominicazioni per la funzione passata, un record ,prevede una
     * comunicazione per la funzione passata.
     * 
     * @param tipomovimento
     * @param funzione
     * @return
     */
    public TipimovimentoComunicazioni findByTipoMovAndFunzione(String tipomovimento, String funzione);

    /**
     * Recupera la lista di TipimovimentoComunicazioni filtrando per movimento
     * 
     * @param codiceTipmov
     * @return
     */
    public List<TipimovimentoComunicazioni> findByTipoMov(String codiceTipmov);

    /**
     * Il metodo esegue tutte le comunicazioni configurate per il movimento
     * 
     * @param movimenti
     *            : Movimento per cui si devono fare le comunicazioni
     * @param listAllegati
     *            : lista allegati del movimento che possono essere inclusi nella comunicazione
     */
    public void eseguiComunicazione(Movimenti movimenti, List<Movimentiallegati> listAllegati, Boolean isGestioneEventi,
	    TipoComunicazionemovimentoEnum tipoComunicazionemovimentoEnum);
}
