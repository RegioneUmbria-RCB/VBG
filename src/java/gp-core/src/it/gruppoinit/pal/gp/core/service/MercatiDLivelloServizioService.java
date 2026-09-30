package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatiDLivelloServizioDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioHelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiDLivelloServizioService extends BaseService<MercatiDLivelloServizio, PkId> {

    /**
     * @see MercatiDLivelloServizioDAO#findAll(Integer, Integer)
     */
    public List<MercatiDLivelloServizio> findAll(Integer firstResult, Integer maxResult);

    public List<MercatiDLivelloServizioDTO> findByPosteggio(Integer codiceposteggio, boolean attivi, boolean scaduti);

    public List<MercatiDLivelloServizio> findByPosteggioAndUso(Integer codiceposteggio, Integer uso, boolean attivi, boolean scaduti);

    public List<MercatiDLivelloServizioHelper> findMercatiDLivelloServizioHelperByPosteggio(Integer codiceposteggio, boolean attivi, boolean scaduti);

    public void insertMultiplo(MercatiDLivelloServizio entity, String[] listacodici);

    public List<MercatiDLivelloServizio> findByServizio(Integer codice);
    
    public void insertMultiPosteggioMultiUso(List<Integer> idposteggi, List<Integer> idLivelliServizio, 
	    BigDecimal fattoreMoltiplicativo, Date dataInizio, Date dataFine, boolean usaMqPosteggio);
}
