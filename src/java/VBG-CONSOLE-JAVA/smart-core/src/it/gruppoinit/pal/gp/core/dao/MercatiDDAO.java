package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.List;

public interface MercatiDDAO extends BaseDAO<MercatiD, PkId> {

    public List<MercatiD> findAllByMercato(Mercati mercati);

    /**
     * Metodo per determinare i posteggi abilitati o disabilitati di un mercato La lista è già ordinata per posteggi
     * 
     * @param mercati
     * @param posteggiEnum
     * @return
     */
    public List<MercatiD> findByMercato(Mercati mercati, PosteggiEnum posteggiEnum);
    /**
     * Metodo per determinare i posteggi abilitati o disabilitati di un mercato La lista è già ordinata per peso posteggio desc
     * 
     * @param mercati
     * @param posteggiEnum
     * @return
     */
    public List<MercatiD> findByMercatoOrderByPeso(Mercati mercati, PosteggiEnum posteggiEnum);

    /**
     * ritorna una lista di tutti i posteggi per il mercato e anno scelto che hanno almeno un conto settato La lista è
     * già ordinata per posteggi
     * 
     * @param mercati
     * @return
     */
    public List<MercatiD> findByPosteggiConConti(Mercati mercati, Integer anno);

    /**
     * Metodo per recuperare i posteggi di un mercato.
     * 
     * @param mercati
     *            deve essere settato l'uso.
     * @return
     */
    public List<MercatiD> findPosteggioByMercatiMercatoUso(Mercati mercati);

    /**
     * Metodo per recuperare il posteggio a partire dal codiceposteggio invece dell'ID.
     * 
     * @param codiceposteggio
     * @param mercati
     * @return
     */
    public MercatiD findPosteggioByCodicePosteggio(String codiceposteggio, Mercati mercati);

    /**
     * Restituisce i posteggi (filtrando per idcomune) ordinandoli per il campo codice
     */
    public List<MercatiD> findAll(Integer firstResult, Integer maxResult);

    /**
     * Metodo che restituisce una lista di posteggi filtrati traminte un oggetto posteggio e uso (se non viene passato
     * l'uso e viene posto a null verranno applicati solo i filtri presenti nell'oggetto mercatiD)
     * 
     * @param filter
     * @return
     */
    public List<MercatiD> findByMercatiD(MercatiD filter);

    public List<CodiceDescrizioneBean> findPosteggiNonAssegnatiByMercato(Integer codiceMercato, Integer codiceMercatiUso, PosteggiEnum tipo);
}
