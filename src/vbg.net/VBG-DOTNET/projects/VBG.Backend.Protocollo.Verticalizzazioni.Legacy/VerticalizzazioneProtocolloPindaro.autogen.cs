using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Legacy
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_PINDARO il 26/08/2014 17.28.01
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// PINDARO è il sistema di protocollazione presente al Comune di Reggio Calabria, gestito da Recasi.
    /// </summary>
    public partial class VerticalizzazioneProtocolloPindaro : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PINDARO";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneProtocolloPindaro()
        {

        }

        public VerticalizzazioneProtocolloPindaro(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// E' il codice ente per il quale protocollare, deve essere fornito dall'amministratore del protocollo PINDARO.
        /// </summary>
        public string Codiceente
        {
            get { return this.GetString("CODICEENTE"); }
            set { this.SetString("CODICEENTE", value); }
        }

        /// <summary>
        /// E' l'operatore da utilizzare per protocollare con PINDARO, deve essere fornito dall'amministratore del protocollo PINDARO.
        /// </summary>
        public string Operatore
        {
            get { return this.GetString("OPERATORE"); }
            set { this.SetString("OPERATORE", value); }
        }

        /// <summary>
        /// E' la password per protocollare in PINDARO, deve essere fornita dall'amministratore del protocollo PINDARO.
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
