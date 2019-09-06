using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{

    /// <summary>
    /// Servizio responsabile della lettura dei dati di un movimento effettuato nel backoffice
    /// </summary>
    public interface IMovimentiBackofficeService
    {
        /// <summary>
        /// Ottiene i dati di un movimento del backoffice a partire dal relativo identificativo univoco
        /// </summary>
        /// <param name="idMovimento">Identificativo univoco del movimento</param>
        /// <returns>Dati del movimento letto o null se ilmovimento non è stato trovato</returns>
        //MovimentoDiOrigine GetById(int idMovimento);

        /// <summary>
        /// Carica il datastore relativo al movimento corrispondente all'identificativo specificato
        /// </summary>
        /// <param name="idMovimento">Identificativo univoco del movimento di cui va caricato il datastore</param>
        /// <returns>Datastore del movimento corrispondente all'identificativo specificato o null se il movimento non ha ancora un datastore</returns>
        GestioneMovimentiDataStore GetDataStore(int idMovimento);

        /// <summary>
        /// Salva il datastore relativo al movimento corrispondente all'identificativo specificato
        /// </summary>
        /// <param name="idMovimento"></param>
        /// <param name="dataStore"></param>
        void Save(int idMovimento, GestioneMovimentiDataStore dataStore);

        /// <summary>
        /// Imposta il flag trasmesso == 1 nel movimento corrispondente all'identificativo specificato
        /// </summary>
        /// <param name="idMovimento">Identificativo univoco del movimento di cui è stata effettuata la trasmisisone</param>
        void ImpostaComeTrasmesso(int idMovimento);


        /// <summary>
        /// Restituisce la lista di documenti sostituibili
        /// </summary>
        /// <param name="idMovimento"></param>
        /// <returns></returns>
        DocumentiIstanzaSostituibili GetDocumentiSostituibili(int idMovimento);

        ConfigurazioneMovimentoDaEffettuare GetConfigurazioneMovimento(int idMovimento);
    }
}
