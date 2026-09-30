using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_DOCSPA il 26/08/2014 17.28.01
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// DOCSPA è il nome dell'applicativo di protocollo utilizzato ad ora dal Comune di Como.
    /// </summary>
    public partial class VerticalizzazioneProtocolloDocspa : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_DOCSPA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloDocspa()
        {

        }

        public VerticalizzazioneProtocolloDocspa(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// E' il codice del fascicolo da utilizzare per la fascicolazione. Può essere fisso o parametrico
        /// </summary>
        public string Codfascicolo
        {
            get { return this.GetString("CODFASCICOLO"); }
            set { this.SetString("CODFASCICOLO", value); }
        }

        /// <summary>
        /// E' l'operatore da utilizzare per protocollare con DOCSPA, deve essere fornito dall'amministratore del protocollo DOCSPA.
        /// </summary>
        public string Operatore
        {
            get { return this.GetString("OPERATORE"); }
            set { this.SetString("OPERATORE", value); }
        }

        /// <summary>
        /// E' la password per protocollare in DOCSPA, deve essere fornita dall'amministratore del protocollo DOCSPA.
        /// </summary>
        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        /// <summary>
        /// E' l'URl per invocare il protocollo. Non devono essere specificati i metodi.
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }


    }
}
