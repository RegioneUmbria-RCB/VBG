package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeDaConsolidareHelper;

import java.util.List;

public interface MercatipresenzeDDAO extends BaseDAO<MercatipresenzeD, PkId> {

    /**
     * Trova la presenza di una giornata e di un posteggio
     * 
     * @param giornoMercato
     *            - la giornata per la quale filtrare la richiesta
     * @param posteggio
     *            - il posteggio
     * @return una riga della tabella Mercatipresenze_D
     */
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(MercatipresenzeT giornoMercato, MercatiD posteggio);

    /**
     * metodo per il recupero della lista dei posteggi e dei concessionari ordinata per codiceposteggio
     * 
     * @param giorno
     * @return
     */
    //public List<MercatipresenzeD> findListaPosteggi(MercatipresenzeT giorno);
    public List<MercatipresenzeDDTO> findListaPosteggi(MercatipresenzeT giorno);

    public List<MercatipresenzeD> findListaPresentiSenzaPosteggio(MercatipresenzeT giorno);

    /**
     * metodo per determinare i concessionari o occupanti del posteggio
     * 
     * @param posteggio
     * @return
     */
    public MercatipresenzeD findByMercatiPosteggio(MercatiD posteggio);

    public MercatipresenzeDDTO findByIdLazy(Integer codicePresenza);

    public List<PresenzeDaConsolidareHelper> findPresenzeDaConsolidarePerAnno(Integer codiceMercato, Integer anno);

    public MercatipresenzeD findUltimaPresenzaPerMercatoAnagrafeAndAutorizzazione(Integer codiceMercato, Integer anno, Integer codiceAnagrafe,
	    Integer codiceAutorizzazione);

    public void updateAggiornaAZeroTutteLePresenze(Integer codiceMercato, Integer anno);
}
