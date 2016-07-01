using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.WsSigeproSecurity;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public class AuthenticationServiceFactory
    {
        public static IAuthenticationService Create(ResolveDatiProtocollazioneService datiProtoSrv, VerticalizzazioniConfiguration vert, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            var mgr = new ResponsabiliMgr(datiProtoSrv.Db);
            var responsabile = mgr.GetById(datiProtoSrv.IdComune, datiProtoSrv.CodiceResponsabile.Value);

            if (vert.IsLdapAuthentication)
                return new AuthenticationServiceLDAP(datiProtoSrv.Token, responsabile.USERID, datiProtoSrv);
            else
                return new AuthenticationService(responsabile.COD_UTE_DOCER, responsabile.PASSWORD_UTE_DOCER, vert, logs, serializer, datiProtoSrv);
        }
    }
}
