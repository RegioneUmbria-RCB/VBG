using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_PARIX il 26/08/2014 17.23.44
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se attivato permette di recuperare un'anagrafe persona giuridica dai servizi web di PARIX, la funzionalità viene attivata nelle anagrafiche richiedenti e tecnici sia in inserimento che per controlli successivi e durante la presentazione della dom,anda on-line. Questo modulo va abilitato contestualmente ad un altro modulo WSANAGRAFE_XXX (modulo "principale") dove XXX è CESENA,PIACENZA,... a condizione che il modulo "principale" sia sviluppato tenendo conto della compatibilità con l'integrazione PARIX. Le indicazioni se un modulo "principale" è compatibile con PARIX saranno scritte nella descrizione del modulo "principale".
    /// </summary>
    public partial class VerticalizzazioneWsanagrafeAdrier : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "WSANAGRAFE_ADRIER";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneWsanagrafeAdrier()
        {

        }

        public VerticalizzazioneWsanagrafeAdrier(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }


        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        public string ProxyAddress
        {
            get { return this.GetString("PROXY_ADDRESS"); }
            set { this.SetString("PROXY_ADDRESS", value); }
        }

        public string Username
        {
            get { return this.GetString("USER"); }
            set { this.SetString("USER", value); }
        }

        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        public string CercaSoloCf
        {
            get { return this.GetString("CERCA_SOLO_CF"); }
            set { this.SetString("CERCA_SOLO_CF", value); }
        }

        public string SwitchControl
        {
            get { return this.GetString("SWITCHCONTROL"); }
            set { this.SetString("SWITCHCONTROL", value); }
        }

        public string Xsd
        {
            get { return this.GetString("XSD"); }
            set { this.SetString("XSD", value); }
        }

    }
}
