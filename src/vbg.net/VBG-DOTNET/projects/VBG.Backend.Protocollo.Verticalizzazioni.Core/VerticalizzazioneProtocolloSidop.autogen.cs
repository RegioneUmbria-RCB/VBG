using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SIDOP il 26/08/2014 17.28.01
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// SIDOP è il sistema di protocollazione presente al Comune di Perugia, è l'unico sistema di protocollo che non passa per web service ma vengono fatte delle chiamate direttamente al base dati.
    /// </summary>
    public partial class VerticalizzazioneProtocolloSidop : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SIDOP";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneProtocolloSidop()
        {

        }

        public VerticalizzazioneProtocolloSidop(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Anno del fascicolo per la protocollazione SIDOP
        /// </summary>
        public string Annofasc
        {
            get { return this.GetString("ANNOFASC"); }
            set { this.SetString("ANNOFASC", value); }
        }

        /// <summary>
        /// Stringa di connessione del DB in cui risiede la stored procedure per la protocollazione SIDOP
        /// </summary>
        public string Connectionstring
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Identificativo dell'indice per la protocollazione SIDOP
        /// </summary>
        public string Idind
        {
            get { return this.GetString("IDIND"); }
            set { this.SetString("IDIND", value); }
        }

        /// <summary>
        /// Percorso completo dell'eseguibile utilizzato per la stampa delle etichette
        /// </summary>
        public string Pathprinterexe
        {
            get { return this.GetString("PATHPRINTEREXE"); }
            set { this.SetString("PATHPRINTEREXE", value); }
        }

        /// <summary>
        /// Progressivo del fascicolo per la protocollazione SIDOP
        /// </summary>
        public string Progrfasc
        {
            get { return this.GetString("PROGRFASC"); }
            set { this.SetString("PROGRFASC", value); }
        }

        /// <summary>
        /// Provider del DB in cui risiede la stored procedure per la protocollazione SIDOP
        /// </summary>
        public string Provider
        {
            get { return this.GetString("PROVIDER"); }
            set { this.SetString("PROVIDER", value); }
        }

        /// <summary>
        /// Nome della stored procedure da eseguire per la stampa delle etichette
        /// </summary>
        public string StoredprocedureEtic
        {
            get { return this.GetString("STOREDPROCEDURE_ETIC"); }
            set { this.SetString("STOREDPROCEDURE_ETIC", value); }
        }

        /// <summary>
        /// Nome della stored procedure da eseguire per la protocollazione SIDOP
        /// </summary>
        public string StoredprocedureProt
        {
            get { return this.GetString("STOREDPROCEDURE_PROT"); }
            set { this.SetString("STOREDPROCEDURE_PROT", value); }
        }

        /// <summary>
        /// E' il nome della vista dalla quale si ricavano le informazioni sull'utente che intende protocollare
        /// </summary>
        public string View
        {
            get { return this.GetString("VIEW"); }
            set { this.SetString("VIEW", value); }
        }


    }
}
