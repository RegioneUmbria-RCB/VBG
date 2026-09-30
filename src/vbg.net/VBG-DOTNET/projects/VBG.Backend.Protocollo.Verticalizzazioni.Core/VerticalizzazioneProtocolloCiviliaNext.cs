using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public class VerticalizzazioneProtocolloCiviliaNext : Verticalizzazione
    {
        private static class Constants
        {
            public const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_CIVILIANEXT";
            public const string ClientID = "CLIENT_ID";
            public const string CliendSecret = "CLIENT_SECRET";
            public const string CodiceLivelloOrganigramma = "CODICELIVELLOORGANIGRAMMA";
            public const string IdCasellaEmail = "IDCASELLAEMAIL";
            public const string IdCodiceAOO = "IDCODICEAOO";
            public const string IdRegistro = "IDREGISTRO";
            public const string UrlOAuth2 = "URL_OAUTH";
            public const string UrlWsAssegnazione = "URL_WSASSEGNAZIONE";
            public const string UrlWSAggiungiAllegato = "URL_WSAGGIUNGIALLEGATO";
            public const string UrlWSAnnullaProtocollo = "URL_WSANNULLAPROTOCOLLO";
            public const string UrlWSCercaPratiche = "URL_WSCERCAPRATICHE";
            public const string UrlWSEstraiTitolario = "URL_WSESTRAITITOLARIO";
            public const string UrlWSGetAllegati = "URL_WSGETALLEGATI";
            public const string UrlWSGetAllegato = "URL_WSGETALLEGATO";
            public const string UrlWSInviaProtocollo = "URL_WSINVIAPROTOCOLLO";
            public const string UrlWSProtocollo = "URL_WSPROTOCOLLO";
            public const string UrlWSRicercaLivello = "URL_WSRICERCALIVELLO";
            public const string UrlWSResource = "URL_WSRESOURCE";
            public const string MarcaAllegati = "MARCA_ALLEGATI";
        }

        public override string NomeVerticalizzazione => Constants.NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloCiviliaNext()
        {
            
        }

        public VerticalizzazioneProtocolloCiviliaNext(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, Constants.NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        public string ClientID => this.GetString(Constants.ClientID);

        public string CliendSecret => this.GetString(Constants.CliendSecret);

        public string CodiceLivelloOrganigramma => this.GetString(Constants.CodiceLivelloOrganigramma);

        public string IdCasellaEmail => this.GetString(Constants.IdCasellaEmail);

        public string IdCodiceAOO => this.GetString(Constants.IdCodiceAOO);

        public string IdRegistro => this.GetString(Constants.IdRegistro);

        public string UrlOAuth2 => this.GetString(Constants.UrlOAuth2);


        public string UrlWSAggiungiAllegato => this.GetString(Constants.UrlWSAggiungiAllegato);

        public string UrlWSAnnullaProtocollo => this.GetString(Constants.UrlWSAnnullaProtocollo);

        public string UrlWsAssegnazione => this.GetString(Constants.UrlWsAssegnazione);

        public string UrlWSCercaPratiche => this.GetString(Constants.UrlWSCercaPratiche);

        public string UrlWSEstraiTitolario => this.GetString(Constants.UrlWSEstraiTitolario);

        public string UrlWSGetAllegati => this.GetString(Constants.UrlWSGetAllegati);

        public string UrlWSGetAllegato => this.GetString(Constants.UrlWSGetAllegato);

        public string UrlWsInviaProtocollo => this.GetString(Constants.UrlWSInviaProtocollo);

        public string UrlWSProtocollo => this.GetString(Constants.UrlWSProtocollo);

        public string UrlWSResource => this.GetString(Constants.UrlWSResource);

        public string UrlWSRicercaLivello => this.GetString(Constants.UrlWSRicercaLivello);

        public string MarcaAllegati => this.GetString(Constants.MarcaAllegati);
    }
}
