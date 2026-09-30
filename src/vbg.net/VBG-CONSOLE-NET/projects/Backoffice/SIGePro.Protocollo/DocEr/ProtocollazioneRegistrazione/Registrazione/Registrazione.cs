using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocEr.Autenticazione;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Registrazione
{
    public class Registrazione : BaseProtocollazioneRegistrazione
    {
        RegistrazioneParticolareService _registrazioneWrapper;
        string _registro;

        public long IdUnitaDocumentale { get; private set; }

        public Registrazione(RegistrazioneParticolareService registrazioneWrapper, string registro, VerticalizzazioniConfiguration vert, IAuthenticationService authWrapper, IDatiProtocollo datiProto, ProtocolloLogs logs, ProtocolloSerializer serializer, ResolveDatiProtocollazioneService datiProtoSrv)
            : base(vert, authWrapper, datiProto, logs, serializer, datiProtoSrv)
        {
            _registrazioneWrapper = registrazioneWrapper;
            _registro = registro;
        }

        public esito Registra()
        {
            IdUnitaDocumentale = CreaUnitaDocumentale();
            string segnatura = CreaSegnatura();

            var response = _registrazioneWrapper.Registra(AuthWrapper.Token, IdUnitaDocumentale, _registro, segnatura);
            return response;
        }


        
    }
}
