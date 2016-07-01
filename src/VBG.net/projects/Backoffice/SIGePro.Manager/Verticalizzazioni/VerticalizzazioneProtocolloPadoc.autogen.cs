
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_PADOC il 12/12/2014 9.28.25
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// E' il protocollo gestito e creato dal Comune di Padova e gestito dalla ditta im-tech, il protocollo si chiama P@Doc, e, i web service hanno la particolarità di lavorare in maniera asincrona, ossia la risposta non avviene subito, (a meno che non venga generato un errore durante la chiamata), ma successivamente dopo l'avvio di un processo schedulato da parte del server. Altra particolarità è che il web service non lavora con il protocollo SOAP, ma la richiesta viene inviata in POST ad un indirizzo http.
    /// </summary>
    public partial class VerticalizzazioneProtocolloPadoc : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PADOC";

        public VerticalizzazioneProtocolloPadoc(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        /// <summary>
        /// Codice dell'amministrazione che utilizza il sistema di protocollo, ad esempio per il comune di Livorno è COMLIV
        /// </summary>
        public string CodiceAmministrazione
        {
            get { return GetString("CODICE_AMMINISTRAZIONE"); }
            set { SetString("CODICE_AMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Codice Aoo che utilizza il sistema di protocollo, ad esempio al Comune di Livorno il valore è aoocli
        /// </summary>
        public string CodiceAoo
        {
            get { return GetString("CODICE_AOO"); }
            set { SetString("CODICE_AOO", value); }
        }

        /// <summary>
        /// Dominio entro cui è stato generato il documento, ad esempio al comune di Livorno corrisponde a webvbgdue.comuneli.local
        /// </summary>
        public string Dominio
        {
            get { return GetString("DOMINIO"); }
            set { SetString("DOMINIO", value); }
        }

        /// <summary>
        /// Indiricare l'url del servizio rest che è messo a disposizione per poter protocollare.
        /// </summary>
        public string UrlProto
        {
            get { return GetString("URL_PROTO"); }
            set { SetString("URL_PROTO", value); }
        }

        /// <summary>
        /// Url che indica la servlet che il sistema di protocollo va ad invocare restituendo i propri dati dati dopo la protocollazione (numero, anno....). Questo tipo di dato è parziale, nel senso che viene completato durante il processo che viene effettuato nei componenti .net e che andranno a completare l'url in base al fatto che si tratti di un errore in fase di protocollazione o meno, in questi casi infatti l'indirizzo della servlet cambia.
        /// </summary>
        public string UrlResponseService
        {
            get { return GetString("URL_RESPONSE_SERVICE"); }
            set { SetString("URL_RESPONSE_SERVICE", value); }
        }

        /// <summary>
        /// Url del servizio rest messo a disposizione dal fornitore di protocollo che serve per fare una lettura di protocollo. Al servizio devono essere indicati i parametri di autenticazione USERNAME e PASSWORD. Il servizio risponde in maniera sincrona a differenza del ws di protocollazione.
        /// </summary>
        public string UrlLeggiProto
        {
            get { return GetString("URL_LEGGI_PROTO"); }
            set { SetString("URL_LEGGI_PROTO", value); }
        }

        /// <summary>
        /// Parametro login da indicare al web service di lettura protocollo.
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }


        /// <summary>
        /// Parametro password da indicare al web service di lettura protocollo.
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }
    }
}
