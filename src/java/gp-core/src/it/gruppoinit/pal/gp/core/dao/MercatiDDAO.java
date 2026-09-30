package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.web.MercatiDFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioMercatoBean;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;

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
     * Metodo per determinare i posteggi abilitati o disabilitati di un mercato La lista è già ordinata per peso
     * posteggio desc
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

    public List<CodiceDescrizioneBean> findPosteggiNonAssegnatiByMercato(Integer codiceMercato, Integer codiceMercatiUso, Integer posteggioEscluso,
	    PosteggiEnum tipo);

    /**
     * 
     * @return Ritorna la lista dei posteggi passata di un determinato mercato contente le informazioni del posteggio e
     *         la lista delle concessioni e subentri. La query è fatta in SQL per ottimizzare la memoria in fase di
     *         visulaizzazione sulla jsp
     */
    public List<PosteggioMercatiHelper> findMercatiDWithConcessioniSQL(Integer codicemercato, MercatiD mercatiD, boolean isSingoloPosteggio,
	    Integer codiceMercatoUso);

    //public List<PosteggioMercatiHelper> findMercatiDWithConcessioniSQL(Integer codicemercato, MercatiD mercatiD);
    public Integer findPosizioneMaxByMercato(Integer codice);

    public void exportModalitaPentaho(MercatiD mercatiD, Esportazioni esportazioni, String email, TipicontestoesportazioniEnum posteggiMercato,
	    boolean isInvioMail);

    public List<MercatiDDTO> findByMercatiDDTO(MercatiDFilter filter);

    public MercatiDDTO findById(Integer codiceposteggio);

    public List<Integer> findByCodiceByMercato(Integer codicemercato, PosteggiEnum posteggiEnum);

    public List<PosteggioMercatoBean> findByIdGiornata(Integer idGiornata);
}
