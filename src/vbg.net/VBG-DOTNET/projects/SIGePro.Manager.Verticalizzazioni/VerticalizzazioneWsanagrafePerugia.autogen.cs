using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_PERUGIA il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Parametri di configurazione dell'oggetto responsabile per le ricerche anagrafiche del comune di PERUGIA
    /// </summary>
    public partial class VerticalizzazioneWsanagrafePerugia : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_PERUGIA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneWsanagrafePerugia()
        {

        }

        public VerticalizzazioneWsanagrafePerugia(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// E' la ulr per accedere ai servizi PARIX. Es. https://servizicner.regione.emilia-romagna.it/parixgate/services/gate?wsdl
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }


    }
}
