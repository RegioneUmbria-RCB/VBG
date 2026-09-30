using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public class VerticalizzazioneProtocolloItalSoft : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_ITALSOFT";

        private const string CODICE_UFFICIO = "CODICE_UFFICIO";
        private const string DOMAIN_CODE = "DOMAIN_CODE";
        private const string URL_PROTOCOLLAZIONE = "URL_PROTOCOLLAZIONE";
        private const string URL_FASCICOLAZIONE = "URL_FASCICOLAZIONE";
        private const string USER_NAME = "USER_NAME";
        private const string USER_PASSWORD = "USER_PASSWORD";


        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloItalSoft()
        {
            
        }

        public VerticalizzazioneProtocolloItalSoft(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        public string UrlProtocollazione => this.GetString(URL_PROTOCOLLAZIONE);

        public string CodiceUfficio => this.GetString(CODICE_UFFICIO);

        public string DomainCode => this.GetString(DOMAIN_CODE);
        public string UserName => this.GetString(USER_NAME);
        public string UserPassword => this.GetString(USER_PASSWORD);

        public string UrlFascicolazione => this.GetString(URL_FASCICOLAZIONE);
    }
}
