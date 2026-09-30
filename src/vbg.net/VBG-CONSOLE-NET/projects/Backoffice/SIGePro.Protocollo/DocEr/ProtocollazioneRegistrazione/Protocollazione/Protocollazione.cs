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

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Protocollazione
{
    public class Protocollazione : BaseProtocollazioneRegistrazione
    {
        ProtocollazioneService _protocollazioneWrapper;

        public long IdUnitaDocumentale { get; private set; }

        public Protocollazione(ProtocollazioneService protocollazioneWrapper, VerticalizzazioniConfiguration vert, IAuthenticationService authWrapper, IDatiProtocollo datiProto, ProtocolloLogs logs, ProtocolloSerializer serializer, ResolveDatiProtocollazioneService datiProtoSrv)
            : base(vert, authWrapper, datiProto, logs, serializer, datiProtoSrv)
        {
            _protocollazioneWrapper = protocollazioneWrapper;
        }

        public esito Protocolla()
        {
            IdUnitaDocumentale = CreaUnitaDocumentale();
            string segnatura = CreaSegnatura();
            
            var response = _protocollazioneWrapper.Protocollazione(AuthWrapper.Token, IdUnitaDocumentale, segnatura);
            if (response.IsErrore)
            {
                _logs.ErrorFormat("ERRORE ALLA PRIMA CHIAMATA A PROTOCOLLAZIONE, ERRORE: {0}", response.Errore);
                for (int i = 0; i < _vert.TentativiRetry; i++)
                {
                    _logs.ErrorFormat("RETRY N. {0}", i + 1);
                    response = _protocollazioneWrapper.Protocollazione(AuthWrapper.Token, IdUnitaDocumentale, segnatura);
                    if (!response.IsErrore)
                        break;

                    _logs.ErrorFormat("ERRORE RETRY N. {0}, ERRORE: {1}", i + 1, response.Errore);
                }

                if(response.IsErrore)
                    throw new System.Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", response.Errore));
            }

            return response.EsitoProtocollazione;
        }
    }
}
