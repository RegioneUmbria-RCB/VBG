using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;
using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Shared.Infrastructure.ServiceModel;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Autenticazione
{
    public class AuthenticationServiceFactory
    {
        public static IAuthenticationService Create(ResolveDatiProtocollazioneService datiProtoSrv, VerticalizzazioniConfiguration vert, ProtocolloLogs logs, ProtocolloSerializer serializer, TipoProvenienza provenienza, IBindingFactory bindingFactory)
        {
            var mgr = new ResponsabiliMgr(datiProtoSrv.Db);
            var responsabile = mgr.GetById(datiProtoSrv.IdComune, datiProtoSrv.CodiceResponsabileUtenteLoggato.Value);
            
            if (vert.IsLdapAuthentication && provenienza == TipoProvenienza.BACKOFFICE)
                return new AuthenticationServiceLDAP(datiProtoSrv.Token, responsabile.USERID, datiProtoSrv);
            else
                return new AuthenticationService(responsabile.COD_UTE_DOCER, responsabile.PASSWORD_UTE_DOCER, vert, logs, serializer, datiProtoSrv, bindingFactory);
        }
    }
}
