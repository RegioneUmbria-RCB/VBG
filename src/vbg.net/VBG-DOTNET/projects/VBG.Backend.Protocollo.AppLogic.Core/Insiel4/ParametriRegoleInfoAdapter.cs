using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;
using SIGePro.Manager.VerticalizzazioniBase;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4
{
    public class ParametriRegoleInfoAdapter
    {
        protected VerticalizzazioneProtocolloInsielRest _vert;
        protected VerticalizzazioneProtocolloAttivo _base;
        protected string _token;
        protected string _matricola;

        private ProtocolloLogs _protocolloLogs;
        private ProtocolloSerializer _protocolloSerializer;

        public ParametriRegoleInfoAdapter(ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer,
            string token, string idComuneAlias, string software, string codiceComune, string matricola, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._protocolloLogs = protocolloLogs;
            this._protocolloSerializer = protocolloSerializer;
            this._vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsielRest>(idComuneAlias, software, codiceComune);
            this._base = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(idComuneAlias, software, codiceComune);
            this._token = token;
            this._matricola = matricola;

        }

        public ParametriRegoleInfo Adatta()
        {

            if (!this._vert.Attiva)
                throw new Exception($"La verticalizzazione {this._vert.NomeVerticalizzazione} non è attiva");
            try
            {

                var parametri = new ParametriRegoleInfo
                {
                    ClientID = this._vert.ClientID,
                    ClientSercret = this._vert.CliendSecret,
                    InviaMail = this._base.AbilitaIndirizziEmail,
                    UrlOAuth = this._vert.UrlOAuth2,
                    Logger = this._protocolloLogs,
                    Serializer = this._protocolloSerializer,
                    UrlWS = this._vert.UrlWS,
                    CodiceRegistro = this._vert.CodiceRegistro,
                    //UrlUploadFile = this._vert.UrlUploadFile,
                    AttivaMonf = this._vert.AttivaMonf,
                    CodiceUfficioOperante = this._vert.CodiceUfficioOperante,
                    Utente = new Services.Rest.Entities.Utente() { Codice = this._vert.CodiceUtente, CodiceFiscale = "" },
                    DisabilitaAnnullaProtocollo = this._vert.DisabilitaAnnullaProtocollo,
                    DisabilitaValidazioneCapIta = this._vert.DisabilitaValidazioneCapIta,
                    EscludiClassifica = this._vert.EscludiClassifica,
                    InviaPec = this._vert.InviaPec,
                    MittentePec = this._vert.MittentePec,
                    TipiDocumentoWs = this._vert.TipiDocumentoWs,
                    TipoAggiornamentoAnagrafica = this._vert.TipoAggiornamentoAnag,
                    TipoGestionePec = this._vert.TipoGesionePec,
                    TipoUfficioIteratti = this._vert.TipoUfficioIteratti,
                    UsaLivelliClassifica = this._vert.UsaLivelliClassifica,
                    UsaWsClassifiche = this._vert.UsaWsClassifiche,
                    UsaPredisponiAnagrafica = this._vert.UsaPredisponiAnagrafica,
                    DisattivaCtrlDocs = this._vert.DisattivaCtrlDocs
                };

                parametri.AuthenticationRestClient = new AuthenticationRestClient(parametri.UrlOAuth, parametri.ClientID, parametri.ClientSercret, this._protocolloLogs);

                //parametri.IdOperatore = Convert.ToInt32(this._matricola);
                //parametri.CodiceLivelloOrganigramma = this._vert.CodiceLivelloOrganigramma;

                return parametri;

            }
            catch (Exception ex)
            {
                throw new Exception($"RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE {this._vert.NomeVerticalizzazione} FALLITO, {ex.Message}", ex);
            }
        }
    }
}
