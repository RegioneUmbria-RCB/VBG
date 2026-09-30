package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatiLivelloServizioDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiLivelloServizioHelper;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiLivelloServizioService extends BaseService<MercatiLivelloServizio, PkId> {

    /**
     * @see MercatiLivelloServizioDAO#findAll(Integer, Integer)
     */
    public List<MercatiLivelloServizio> findAll(Integer firstResult, Integer maxResult);

    public List<MercatiLivelloServizioHelper> findByMecato(Integer codicemercato);

    public List<MercatiLivelloServizioHelper> findByMecatoUso(Integer codiceuso);

    public List<MercatiLivelloServizio> findByUso(Integer codiceuso);

    public void insert(MercatiLivelloServizio entity, List<String> _usi);

    public List<MercatiLivelloServizio> findByUsoAndServizio(Integer codiceuso, Integer codiceservizio);

    public List<MercatiLivelloServizio> findByBeforeDataDaAndLivelloServizio(Date date, Integer codiceLivelloServizio);

    public List<MercatiLivelloServizio> findByDescrizioneAndUso(String textToSearch, Integer codiceUso);

    public List<MercatiLivelloServizio> findByMecatoUso(Integer codiceUso, boolean attivi, boolean nonscaduti);

    public void aggiornaTariffa(MercatiLivelloServizio daAggiornare, MercatiLivelloServizio entity);

    public void updateServzioAndServizioPosteggio(MercatiLivelloServizio entity);

    List<MercatiLivelloServizio> findByMultiUso(Integer[] codiciUso);

    void insert(MercatiLivelloServizio entity, MercatiDLivelloServizio dentity, List<String> _usi, List<Integer> idposteggi);
}
