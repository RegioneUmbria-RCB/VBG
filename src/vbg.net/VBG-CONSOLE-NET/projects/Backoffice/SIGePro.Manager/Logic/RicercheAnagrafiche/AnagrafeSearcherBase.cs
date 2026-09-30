using Init.SIGePro.Data;
using Init.SIGePro.Verticalizzazioni;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
    public enum TipoPersona
    {
        PersonaFisica,
        PersonaGiuridica
    }

    /// <summary>
    /// Definisce le caratteristiche di un oggetto per le ricerche in anagrafica
    /// La sequenza delle invocazioni sarà SEMPRE:
    ///		1) Init
    ///		2) ByXXXXX
    ///		3) Cleanup
    ///	
    ///	I metodi Init e CleanUp possono essere implementati secondo necessità
    ///	
    ///	La proprietà Configuration permette di accedere ai valori di una sezione di configurazione specifica del 
    ///	componente. Il nome della sezione deve essere WSAnagrafe_IdComune (x es. WSAnagrafe_E256) e deve essere definita all'interno del file web.config
    ///	vd. http://www.c-sharpcorner.com/Code/2002/June/ConfigApp.asp e http://msdn.microsoft.com/library/default.asp?url=/library/en-us/cpguide/html/cpcondeclaringcustomconfigurationsections.asp
    ///	per ulteriori informazioni sull'utilizzo delle sezioni di configurazione
    /// </summary>
    public abstract class AnagrafeSearcherBase : IAnagrafeSearcher
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AnagrafeSearcherBase));


        /// <summary>
        /// Espone l'idComune con cui è stato inizializzato l'oggetto
        /// </summary>
        protected string IdComune { get; private set; }

        protected string Alias { get; private set; }

        /// <summary>
        /// Espone la connessione al db per l'idcomune in cui è stato inizializzato l'oggetto
        /// </summary>
        protected DataBase SigeproDb { get; private set; }

        protected string NomeVerticalizzazione { get; private set; }

        /// <summary>
        /// Costruttore dell'oggetto di ricerca anagrafe. Il parametro nomeVerticalizzazione è il nome che identifica la verticalizzazione da cui leggere i parametri di configurazione per questo oggetto di ricerca
        /// </summary>
        /// <param name="verticalizzazioniFactory"></param>
        /// <param name="nomeVerticalizzazione">Nome della verticalizzazione senza il prefisso "WSANAGRAFE_" (es. "PARIX_CLOUD", "RAVENNA", etc)</param>
        protected AnagrafeSearcherBase(string nomeVerticalizzazione)
        {
            this.NomeVerticalizzazione = nomeVerticalizzazione;
        }

        protected void LogMessage(string sMessageLog)
        {
            this._log.DebugFormat("Messaggio del componente di ricerca anagrafiche: {0}", sMessageLog);
        }

        /// <summary>
        /// Implementa la ricerca per codice fiscale
        /// </summary>
        /// <param name="codiceFiscale">Codice fiscale da ricercare</param>
        /// <returns>Struttura <see cref="Anagrafe"/> trovata o null se non è stato trovato niente</returns>
        public abstract Anagrafe ByCodiceFiscaleImp(string codiceFiscale);


        /// <summary>
        /// Implementa la ricerca per codice fiscale e tipo persona
        /// </summary>
        /// <param name="tipoPersona">Tipo persona da ricercare</param>
        /// <param name="codiceFiscale">Codice fiscale da ricercare</param>
        /// <returns>Struttura <see cref="Anagrafe"/> trovata o null se non è stato trovato niente</returns>
        public abstract Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale);

        /// <summary>
        /// Implementa la ricerca per partita IVA
        /// </summary>
        /// <param name="partitaIva">Partita IVA da ricercare</param>
        /// <returns>Struttura <see cref="Anagrafe"/> trovata o null se non è stato trovato niente</returns>
        public abstract Anagrafe ByPartitaIvaImp(string partitaIva);

        /// <summary>
        /// Implementa la ricerca per nome e/o cognome
        /// </summary>
        /// <param name="nome">Nome da ricercare</param>
        /// <param name="cognome">Cognome da ricercare</param>
        /// <returns>Struttura <see cref="Anagrafe"/> trovata o lista vuota se non è stato trovato niente</returns>
        public abstract List<Anagrafe> ByNomeCognomeImp(string nome, string cognome);

        /// <summary>
        /// Verifica le variazioni delle anagrafiche in un determinato periodo di tempo
        /// </summary>
        /// <param name="from"></param>
        /// <param name="to"></param>
        /// <returns></returns>
        public virtual IEnumerable<Anagrafe> GetVariazioni(DateTime from, DateTime to)
        {
            throw new NotImplementedException("METODO GetVariazioni non implementato");
        }

        /// <summary>
        /// Permette di effettuare l'inizializzazione del componente. 
        /// E' sempre chiamato prima dell'invocazione di <see cref="ByCodiceFiscaleImp"/> e di <see cref="ByPartitaIvaImp"/>
        /// </summary>
        public virtual void Init()
        {
        }

        /// <summary>
        /// Permette di effettuare la pulizia delle risorse utilizzate dal componente. 
        /// E' sempre chiamato dopo l'invocazione di <see cref="ByCodiceFiscaleImp"/> e di <see cref="ByPartitaIvaImp"/>
        /// </summary>
        public virtual void CleanUp()
        {
        }


        /// <summary>
        /// Inizializza i parametri interni dell'oggetto come l'idComune e la connessione al db (solo per uso interno)
        /// </summary>
        /// <param name="idComune">Id comune</param>
        /// <param name="db">Connessione as db</param>
        [EditorBrowsable(EditorBrowsableState.Never)]
        public virtual void InitParams(string idComune, string alias, DataBase db)
        {
            this.IdComune = idComune;
            this.Alias = alias;
            this.SigeproDb = db;
        }

        protected Dictionary<string, string> _configuration;
        /// <summary>
        /// Espone i valori di configurazione di un'oggeto di ricerca. 
        /// I valori devono essere contenuti in una verticalizzazione che si chiama WSANAGRAFE_nomeclasse
        /// </summary>
        public Dictionary<string, string> Configuration
        {
            get
            {

                if (this._configuration == null)
                {
                    Verticalizzazione vert = new Verticalizzazione(this.Alias, "WSANAGRAFE_" + this.NomeVerticalizzazione);

                    if (vert == null)
                        throw new ConfigurationErrorsException("Verticalizzazione WSANAGRAFE_" + this.NomeVerticalizzazione + " non trovata, impossibile configurare l'oggetto di ricerca anagrafe " + this.GetType().ToString());

                    if (!vert.Attiva)
                        throw new ConfigurationErrorsException("Verticalizzazione WSANAGRAFE_" + this.NomeVerticalizzazione + " non attiva, impossibile configurare l'oggetto di ricerca anagrafe " + this.GetType().ToString());

                    this._configuration = vert.Parametri;
                }

                return this._configuration;
            }
        }
    }
}
