using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4
{
    public class ParametriRegoleInfo
    {
        public ProtocolloLogs Logger { get; set; }
        public ProtocolloSerializer Serializer { get; set; }

        public string CodiceLivelloOrganigramma { get; internal set; }

        public bool InviaMail { get; internal set; }
        public long IdOperatore { get; internal set; }
        public string ClientID { get; internal set; }
        public string ClientSercret { get; internal set; }
        public string UrlOAuth { get; internal set; }
        public string UrlWS { get; set; }
        public string UrlUploadFile { get; set; }
        public string ProtocollazioneURL { get; internal set; }
        public string CodiceRegistro { get; set; }
        public string GrantType => "client_credentials";
        //public string Token { get; internal set; }
        public AuthenticationRestClient AuthenticationRestClient { get; set; }

        public string AttivaMonf { get; set; }
        public string CodiceUfficioOperante { get; set; }
        public Utente Utente { get; set; }
        public string DisabilitaAnnullaProtocollo { get; set; }
        public string DisabilitaValidazioneCapIta { get; set; }
        public string EscludiClassifica { get; set; }
        public string InviaPec { get; set; }
        public string MittentePec { get; set; }
        public string TipiDocumentoWs { get; set; }
        public string TipoAggiornamentoAnagrafica { get; set; }
        public string TipoGestionePec { get; set; }
        public string TipoUfficioIteratti { get; set; }
        public string UsaLivelliClassifica { get; set; }
        public string UsaWsClassifiche { get; set; }
        public string UsaPredisponiAnagrafica { get; set; }
        public string DisattivaCtrlDocs { get; set; }
    }
}
