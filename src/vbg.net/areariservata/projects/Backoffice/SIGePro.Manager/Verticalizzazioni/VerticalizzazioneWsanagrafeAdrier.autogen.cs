using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
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

        public VerticalizzazioneWsanagrafeAdrier()
        {

        }

        public VerticalizzazioneWsanagrafeAdrier(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }


        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        public string ProxyAddress
        {
            get { return GetString("PROXY_ADDRESS"); }
            set { SetString("PROXY_ADDRESS", value); }
        }

        public string Username
        {
            get { return GetString("USER"); }
            set { SetString("USER", value); }
        }

        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        public string CercaSoloCf
        {
            get { return GetString("CERCA_SOLO_CF"); }
            set { SetString("CERCA_SOLO_CF", value); }
        }

        public string SwitchControl
        {
            get { return GetString("SWITCHCONTROL"); }
            set { SetString("SWITCHCONTROL", value); }
        }

        public string Xsd
        {
            get { return GetString("XSD"); }
            set { SetString("XSD", value); }
        }

    }
}
