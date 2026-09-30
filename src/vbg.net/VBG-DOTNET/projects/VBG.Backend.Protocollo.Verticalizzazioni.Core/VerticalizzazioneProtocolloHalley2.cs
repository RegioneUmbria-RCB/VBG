using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public class VerticalizzazioneProtocolloHalley2 : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_HALLEY2";


        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloHalley2()
        {
            
        }

        public VerticalizzazioneProtocolloHalley2(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        public string CasellaEmail
        {
            get { return this.GetString("CASELLAEMAIL"); }
            set { this.SetString("CASELLAEMAIL", value); }
        }

        public string UserName
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }
        }

        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        public string UsernameAuri
        {
            get { return this.GetString("USERNAMEAURI"); }
            set { this.SetString("USERNAMEAURI", value); }
        }

        public string PasswordAuri
        {
            get { return this.GetString("PASSWORDAURI"); }
            set { this.SetString("PASSWORDAURI", value); }
        }

        public string UrlEstraiProto
        {
            get { return this.GetString("URLESTRAIPROTO"); }
            set { this.SetString("URLESTRAIPROTO", value); }
        }

        public string UrlConsultaDoc
        {
            get { return this.GetString("URLCONSULTADOCUMENTO"); }
            set { this.SetString("URLCONSULTADOCUMENTO", value); }
        }

        public string UrlFascicolaProtocollo
        {
            get { return this.GetString("URLFASCICOLAPROT"); }
            set { this.SetString("URLFASCICOLAPROT", value); }
        }

        public string UrlServiziAggiuntivi
        {
            get { return this.GetString("URLUTILITY"); }
            set { this.SetString("URLUTILITY", value); }
        }
    }
}
