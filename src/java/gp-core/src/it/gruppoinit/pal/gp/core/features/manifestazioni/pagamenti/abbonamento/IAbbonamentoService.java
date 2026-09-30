package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoOccupanteModificato;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AbbonamentoConfigModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiornaConfigurazioneBaseModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiInformativaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiRicaricaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.DettaglioComuneModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.MessaggioNodoPagNonDispModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.StatoBorsellinoPerAnagrafe;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoAggiornamentoBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoAggiornamentoRicariche;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoRicaricaBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.RicaricaBorsellinoRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.SaldoBorsellinoAppModel;

public interface IAbbonamentoService {

    List<AbbonamentoTabellaModel> findAbbonamenti(RicercaBorselliniRequest filtri);

    Map<Integer, SituazioneBorsellinoPerSoglia> situazioneBorsellinoPerSoglia(Set<Integer> auts, String codiceComune);

    void inserisci(AbbonamentoTabellaModel borsellino) throws BorsellinoException;

    AbbonamentoConfigModel findAbbonamentoConfig();
    
    AbbonamentoConfigModel findAbbonamentoConfig(boolean findComuni);

    void salvaMessaggioNodoPagNonDisponibile(MessaggioNodoPagNonDispModel messaggio);

    DettaglioComuneModel findAbbonamentoConfigComune(String codiceComune);

    void eliminaMessaggioNodoPagNonDisponibile(MessaggioNodoPagNonDispModel msg);

    EsitoAggiornamentoRicariche aggiungiRicarica(AggiungiRicaricaModel ricarica);

    void aggiungiInformativa(AggiungiInformativaModel informativa);

    void rimuoviRicarica(int id);

    void rimuoviInformativa(int id);

    void salvaConfigurazioneBase(AggiornaConfigurazioneBaseModel cfg);

    void aggiornaStato(Integer id, StatoBorsellinoEnum sb);

    /**
     * Ritorna l'identificativo del movimento di ricarica
     * 
     * @param borsellino
     * @param importo
     * @param codiceComune
     * @param inserisciPosizioneDebitoria
     * @return
     */
    Integer ricaricaBorsellino(Integer idBorsellino, BigDecimal importo, String codiceComune, boolean inserisciPosizioneDebitoria)
	    throws BorsellinoException;
    
    /**
     * Ritorna l'identificativo del movimento di rimborso
     * 
     * @param borsellino
     * @param importo
     * @param codiceComune
     * @return
     */
    Integer rimborsoBorsellino(Integer idBorsellino, BigDecimal importo, String codiceComune)
	    throws BorsellinoException;

    AbbonamentoTabellaModel findAbbonamentoByAnagrafica(Integer codiceanagrafe) throws BorsellinoException;

    public List<String> selectStatoBorsellino();

    AbbonamentoTabellaModel findAbbonamentoById(Integer idBorsellino);

    List<AutorizzazioniModel> findAllAutorizzazioni();

    /**
     * Trova i riferimenti dei borsellini per il numero autorizzazione passato con like %autorizzazione%
     * 
     * @param autorizzazione
     * @return
     */
    List<Integer> findBorselliniPerAutorizzazione(String autorizzazione);

    BorsellinoAppModel getBorsellino(Integer codiceAnagrafe) throws BorsellinoException;
    
    BorsellinoAppModel getBorsellinoFromId(Integer idBorsellino) throws BorsellinoException;

    EsitoAggiornamentoBorsellino collegaAutorizzazione(String uuidBorsellino, Integer idAutorizzazione) throws BorsellinoException;

    EsitoAggiornamentoBorsellino rimuoviAutorizzazione(String uuidBorsellino, Integer idAutorizzazione) throws BorsellinoException;

    EsitoRicaricaBorsellino ricaricaBorsellino(RicaricaBorsellinoRequest request, Integer codiceAnagrafe);

    BorsellinoAppMovimenti getMovimentoComune(Integer idBorsellino, Integer idMovimento);

    EsitoOperazioneAggiornamento rimuoviRicarica(String uuid, Integer idRicarica);

    void gestisciSubentroAutorizzazione(Integer idAutorizzazioneSubentri) throws BorsellinoException;

    EsitoElaborazioneEvento checkPossoModificareOccupante(Integer idAutOConc, Integer nuovoOccupante);

    void gestisciModificaOccupanteAutorizzazione(EventoOccupanteModificato e) throws BorsellinoException;

    String findBorsellinoUUID(Integer codiceAnagrafe) throws BorsellinoException;

    StatoBorsellinoPerAnagrafe checkStatoBorsellinoPerAnagrafe(Integer codiceAnagrafe);

    void collegaAlBorsellino(Integer codiceOccupante, Integer idAutorizzazione) throws BorsellinoException;

    /**
     * Trova i riferimenti dei borsellini per l'identificativo dell'autorizzazione
     * 
     * @param autorizzazione
     * @return
     */
    List<Integer> findBorselliniPerIdAutorizzazione(Integer idAutorizzazione);

    List<BorsellinoAppMovimenti> getMovimentiFilteredPaginated(Integer codiceAnagrafe, Date dalladata, Date alladata, Integer firstresults,
	    Integer maxresults, List<TipoEnum> tipoenums);

    SaldoBorsellinoAppModel findSaldoBorsellinoForApp(Integer codiceAnagrafe) throws BorsellinoException;
    
}
