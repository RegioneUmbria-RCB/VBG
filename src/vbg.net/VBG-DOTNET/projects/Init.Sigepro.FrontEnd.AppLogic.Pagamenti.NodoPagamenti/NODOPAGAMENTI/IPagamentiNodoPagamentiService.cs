using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI.GenerazioneUrlRitorno;
using System.Collections.Generic;
using VBG.Pagamenti.NodoPagamenti.Attivazione;
using VBG.Pagamenti.NodoPagamenti.Verifica;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti
{
    public class FlagsAttivazioneNodoPagamenti
    {
        public bool NodoPagamentiAttivo { get; internal set; }
        public bool PagamentoOTFAttivo { get; internal set; }
        public bool PagamentoOfflineAttivo { get; internal set; }
    }


    public interface IPagamentiNodoPagamentiService
    {
        FlagsAttivazioneNodoPagamenti GetFlagsNodoPagamenti(int idDomanda);


        /// <summary>
        /// Restituisce true se il nodo pagamenti supporta l'operazione Pago dopo
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        bool PagoDopoAttivo(int idDomanda);

        bool PagamentoOnlineAttivo(int idDomanda);

        /// <summary>
        /// Restituisce true se il nodo pagamenti è attivo nell'area riservata per l'alias e il software attuali
        /// </summary>
        bool NodoPagamentoAttivo(int idDomanda);

        DatiAvvisoPagamento ScaricaAvvisoPagamento(int idDomanda, string uidOnere);

        DatiRicevutaTelematica ScaricaRicevutaTelematica(int idDomanda, string uidOnere);

        bool IsPagamentoModello3(int idDomanda);

        /// <summary>
        /// Aggiorna lo stato di tutte le operazioni di pagamento della domanda corrispondente all'id passato.
        /// Se non sono state avviate operazioni di pagamento esce in silenzio
        /// </summary>
        /// <param name="idDomanda"></param>
        void AggiornaStatoPagamenti(int idDomanda);

        /// <summary>
        /// Annulla (o almeno ci prova) tutti i pagamenti che non si trovino in stato "pagamento riuscito" (es. falliti o in corso) 
        /// a seconda del flag passato come secondo argomento.
        /// Se la procedura di pagamento non è stata avviata esce senza sollevare errori. 
        /// Se non è possibile annullare una delle posizioni debitorie della domanda solleva un'eccezione (e potenzialmente lascia la domanda in uno stato non valido)
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <param name="flagEliminazione"></param>
        void AnnullaPagamenti(int idDomanda, FlagEliminazionePagamenti flagEliminazione);

        /// <summary>
        /// Annulla (o almeno ci prova) tutti i pagamenti che non si trovino in stato "Fallito" (equivalente a AnnullaPagamenti(idDomanda, FlagEliminazionePagamenti.PagamentiFalliti))
        /// a seconda del flag passato come secondo argomento.
        /// Se la procedura di pagamento non è stata avviata esce senza sollevare errori. 
        /// Se non è possibile annullare una delle posizioni debitorie della domanda solleva un'eccezione (e potenzialmente lascia la domanda in uno stato non valido)
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <param name="flagEliminazione"></param>
        void AnnullaPagamentiFalliti(int idDomanda);

        /// <summary>
        /// Restituisce true se la domanda corrispondente all'id passato permette il pagamento online degli oneri, altrimenti false.
        /// Una domanda richiede il pagamento online nel caso in cui ci siano oneri pronti per il pagamento oppure oneri con pagamento in sospeso.
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        bool DomandaRichiedePagamentoOnline(int idDomanda);

        /// <summary>
        /// Restituisce la lista dei pagamenti che risultano in sospeso all'interno di una domanda 
        /// (tutti gli oneri che non hanno ancora un esito di pagamento definitivo).
        /// ATTENZIONE! La chiamata non causa un aggiornamento dello stato dei pagamenti
        /// Se si vuole aggiornare lo stato del pagamento chiamare il metodo AggiornaStatoPagamenti
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        StatoSessionePagamentoOnLine GetStatoPagamentiInSospeso(int idDomanda);


        /// <summary>
        /// Verifica lo stato di tutte le posizioni aperte (in corso di pagamento, riuscite o fallite)
        /// ATTENZIONE! La chiamata non causa un aggiornamento dello stato dei pagamenti
        /// Se si vuole aggiornare lo stato del pagamento chiamare il metodo AggiornaStatoPagamenti
        /// Se le operazioni di pagamento non sono state avviate solleva un'eccezione
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        StatoSessionePagamentoOnLine GetStatoPosizioni(int idDomanda);

        /// <summary>
        /// Legge dal nodo pagamenti lo stato delle posizioni debitorie di una pratica
        /// ATTENZIONE! La chiamata non causa un aggiornamento dello stato dei pagamenti
        /// Se si vuole aggiornare lo stato del pagamento chiamare il metodo AggiornaStatoPagamenti
        /// Se le operazioni di pagamento non sono state avviate solleva un'eccezione
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        IEnumerable<DatiOperazioneSuNodoPagamenti> GetDatiPosizioniDebitorie(int idDomanda);

        /// <summary>
        /// Avvia le operazioni di pagamento su una domanda sulla quale non siano già state avviate.
        /// Nel caso in cui sulla domanda siano già state avviate delle operazioni di pagamento solleva un'eccezione.
        /// </summary>
        /// <param name="estremiDomanda"></param>
        /// <returns>Url verso cui effettuare il redirect per effettuare il pagamento</returns>
        IEsitoAttivazionePagamento AvviaPagamentoOnTheFly(EstremiDomandaNodoPagamenti estremiDomanda, IUrlRitornoPagamentiProvider urlRitornoPagamentiProvider);

        /// <summary>
        /// Restituisce true se à stata avviata una procedura di pagamento per la domanda corrispondente all'id passato
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        bool PagamentoAvviato(int idDomanda);

        /// <summary>
        /// Attiva un pagamento differito tramite apertura di una posizione debitoria
        /// </summary>
        /// <param name="estremiDomanda"></param>
        bool AvviaPagamentoDifferito(EstremiDomandaNodoPagamenti estremiDomanda);

        IEnumerable<ModalitaPagamentoNodoPagamenti> GetModalitaPagamentoSupportate(int idDomanda, bool permettiGiaPagatoConPagaDopoAttivo, bool disbilitaNonDovuto);
    }
}