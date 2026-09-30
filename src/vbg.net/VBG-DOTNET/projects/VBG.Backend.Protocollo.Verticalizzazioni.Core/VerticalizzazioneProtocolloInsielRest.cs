using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public class VerticalizzazioneProtocolloInsielRest : Verticalizzazione
    {
        private static class Constants
        {
            public const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_INSIELREST";

            public const string AttivaMonf = "ATTIVA_MONF";
            public const string ClientID = "CLIENT_ID";
            public const string CliendSecret = "CLIENT_SECRET";
            public const string CodiceRegistro = "CODICEREGISTRO";
            public const string CodiceUtente = "CODICEUTENTE";
            public const string CodiceUfficioOperante = "CODICE_UFFICIO_OPERANTE";
            public const string DisabilitaAnnullaProtocollo = "DISABILITA_ANNULLA_PROTOCOLLO";
            public const string DisabilitaValidazioneCapIta = "DISABILITA_VALIDAZ_CAP_ITA";
            public const string EscludiClassifica = "ESCLUDI_CLASSIFICA";
            public const string InviaPec = "INVIA_PEC";
            public const string MittentePec = "MITTENTE_PEC";
            public const string TipiDocumentoWs = "TIPI_DOCUMENTO_WS";
            public const string TipoAggiornamentoAnag = "TIPO_AGGIORNAMENTO_ANAG";
            public const string TipoGesionePec = "TIPO_GESTIONE_PEC";
            public const string TipoUfficioIteratti = "TIPO_UFFICIO_ITERATTI";
            public const string UrlOAuth2Produzione = "URL_OAUTH";
            public const string UrlOAuth2Test = "URL_OAUTH_TEST";
            public const string UrlWSCollaudo = "URL_WSCOLLAUDO";
            public const string UrlWsProduzione = "URL_WSPRODUZIONE";
            public const string UsaLivelliClassifica = "USA_LIVELLI_CLASSIFICA";
            public const string UsaPredisponiAnagrafica = "USA_PREDISPONI_ANAGRAFICA";
            public const string UsaWsClassifiche = "USA_WS_CLASSIFICHE";
            public const string UsaWsTest = "USA_WS_TEST";
            public const string DisattivaCtrlDocs = "DISATTIVA_CTRL_DOCS";
        }

        public override string NomeVerticalizzazione => Constants.NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloInsielRest()
        {
            
        }
        public VerticalizzazioneProtocolloInsielRest(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, Constants.NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        public string ClientID => this.GetString(Constants.ClientID);

        public string CliendSecret => this.GetString(Constants.CliendSecret);

        public string UrlOAuth2Produzione => this.GetString(Constants.UrlOAuth2Produzione);

        public string UrlOAuth2Test => this.GetString(Constants.UrlOAuth2Test);

        private string UrlWsProduzione => this.GetString(Constants.UrlWsProduzione);

        private string UrlWSCollaudo => this.GetString(Constants.UrlWSCollaudo);

        private string UsaWsTest => this.GetString(Constants.UsaWsTest);

        public string UrlWS => UsaWsTest == "1" ? UrlWSCollaudo : UrlWsProduzione;

        public string UrlOAuth2 => UsaWsTest == "1" ? UrlOAuth2Test : UrlOAuth2Produzione;

        public string CodiceRegistro => this.GetString(Constants.CodiceRegistro);

        //public string UrlUploadFile => this.GetString(Constants.UrlUploadFile);

        public string AttivaMonf => this.GetString(Constants.AttivaMonf);

        public string CodiceUfficioOperante => this.GetString(Constants.CodiceUfficioOperante);

        public string CodiceUtente => this.GetString(Constants.CodiceUtente);

        public string DisabilitaAnnullaProtocollo => this.GetString(Constants.DisabilitaAnnullaProtocollo);

        public string DisabilitaValidazioneCapIta => this.GetString(Constants.DisabilitaValidazioneCapIta);

        public string EscludiClassifica => this.GetString(Constants.EscludiClassifica);

        public string InviaPec => this.GetString(Constants.InviaPec);

        public string MittentePec => this.GetString(Constants.MittentePec);

        public string TipiDocumentoWs => this.GetString(Constants.TipiDocumentoWs);

        public string TipoAggiornamentoAnag => this.GetString(Constants.TipoAggiornamentoAnag);

        public string TipoGesionePec => this.GetString(Constants.TipoGesionePec);

        public string TipoUfficioIteratti => this.GetString(Constants.TipoUfficioIteratti);

        public string UsaLivelliClassifica => this.GetString(Constants.UsaLivelliClassifica);

        public string UsaWsClassifiche => this.GetString(Constants.UsaWsClassifiche);

        public string UsaPredisponiAnagrafica => this.GetString(Constants.UsaPredisponiAnagrafica);

        public string DisattivaCtrlDocs => this.GetString(Constants.DisattivaCtrlDocs);
    }
}
