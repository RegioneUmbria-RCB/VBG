package it.gruppoinit.pal.gp.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.helper.ListaAutorizzazioniPerPeriodo;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDPagamentiDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.PosteggioPerAutBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.model.DettaglioPresenzaComunicazioneModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatiPresenzeConPosDebBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.presenze.MercatipresenzeDBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.utils.PresenzeNonPagateBean;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeDaConsolidareHelper;

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
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(Integer idGiornataMercato, Integer idPosteggio);

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

    /**
     * somma delle presenze ricavate da MERCATIPRESENZE_T per quel mercato, uso, autorizzazione
     * 
     * @param autorizzazione
     *            OBBLIGATORIO
     * @param mercato
     *            OBBLIGATORIO
     * @param uso
     *            OBBLIGATORIO
     * @param posteggio
     *            OPZIONALE (se specificato ricava la somma delle presenze fatte sul posteggio)
     * @param catMerc
     *            OPZIONALE (se specificato ricava la somma delle presenze fatte con la categoria merceologica
     *            specificata)
     * @param anno
     *            OPZIONALE (se specificato ricava la somma delle presenze fatte nell'anno)
     * @param giorno
     *            (OPZIONALE per i mercati) (OBBLIGATORIO per le fiere, il calcolo deve essere fatto per l'ultimo
     *            giorno)
     * @param sommaAssenzeGiustificate
     *            false: utilizza l'autorizzazione come filtro nella colonna FK_AUTORIZZAZIONI_ID di
     *            MERCATIPRESENZE_D.<br >
     *            true: utilizza l'autorizzazione come filtro nella colonna AUT_CONCESSIONARIO di MERCATIPRESENZE_D e
     *            FLAG_ASSENZA_GIUST=1
     * @return
     */
    public MercatiPresenzeDTO findSommaDellePresenzeDaiCalendari(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno, MercatipresenzeT giorno, boolean sommaAssenzeGiustificate);

    public MercatipresenzeDDTO findByIdLazy(Integer codicePresenza);

    public List<PresenzeDaConsolidareHelper> findPresenzeDaConsolidarePerAnno(Integer codiceMercato, Integer anno);

    public MercatipresenzeD findUltimaPresenzaPerMercatoAndAutorizzazione(Integer codiceMercato, Integer anno, Integer codiceAutorizzazione);

    public void updateAggiornaAZeroTutteLePresenze(Integer codiceMercato, Integer anno);

    public List<MercatipresenzeDDTO> findByMercatipresenzaT(Integer codiceMercatopresenzaT);

    public List<MercatipresenzeDPagamentiDTO> findPagamentiByCf(String cfOccupante);

    public void updateAzzeraPresenzeByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso);
    //public void updatePresenza(Integer codiceMercatopresenzaD, boolean presente);

    public ListaAutorizzazioniPerPeriodo findPresenzeSpuntistiRestHelper(Integer idGiornata, Integer codiceMercato, Integer codiceUso,
	    Date inizioMese, Date fineMese);

    public ListaAutorizzazioniPerPeriodo findPresenzeConcessionariRestHelper(Integer idGiornata, Integer codiceMercato, Integer codiceUso,
	    Date inizioMese, Date fineMese);

    public List<MercatipresenzeDDTO> findByMercatiPresenzeTAndPosteggi(Integer idGiornata, List<Long> idPosteggi);

    public List<ValoriLivelloServizio> findServiziConfiguratiByGiornataAndPosteggioAndUso(Date dataGiornata, Integer idPosteggio, Integer codiceUso);

    public List<ValoriLivelloServizio> livelliDiServizioConfiguratiPerGiornataAndPosteggioAndUso(Date dataGiornata, Integer idPosteggio,
	    Integer idUso);

    public List<Integer> findPresenzeConcessionariSenzaPosizioniDebitorie(Date data);

    public List<MercatipresenzeD> findByIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria);

    public List<PresenzeNonPagateBean> findPresenzeNonAssociateAMetodoDiPagamento(Date dalladata, Date alladata, String[] codiceIstat);

    public List<MercatiPresenzeConPosDebBean> findPresenzeConInfoPosDeb(Integer[] idAutorizzazioni, String[] stati,
	    Date consideraIPagamentiDallaData);

    public List<DettaglioPresenzaComunicazioneModel> findPresenzeScalateDalBorsellino(int idGiornata);

    public List<MercatipresenzeDBean> findPresenzePerAutorizzazioneDallaData(Integer idAutConc, Date dataCessazione);

    public List<PosteggioPerAutBean> findAutorizzazioniSganciateDaSIAP(Set<Integer> autSenzaPosteggi);
    
    public List<Object[]> findPresenzeByAutorizzazione(int idautorizzazione);
}
