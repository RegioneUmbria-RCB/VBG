using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
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