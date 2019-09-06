using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Smistamento
{
    public class SmistamentoServiceWrapper : DocAreaServiceWrapperBase
    {
        public SmistamentoServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentials) : base(url, logs, serializer, credentials)
        {

        }

        public void Smista(SmistamentoInXML request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();

                        base.Serializer.Serialize(ProtocolloLogsConstants.SegnaturaSmistamentoFileName, request);
                        var obj = base.Serializer.SerializeToStream<SmistamentoInXML>(request).ToArray();
                        base.Logs.Info($"CHIAMATA A SMISTAMENTO ACTION, DEL PROTOCOLLO NUMERO {request.Intestazione.Identificatore.NumeroProtocollo}, ANNO {request.Intestazione.Identificatore.AnnoProtocollo}");
                        var response = ws.smistamentoAction(base.Credentials.Username, base.Credentials.Token, obj);

                        if (response.lngErrNumber != 0)
                        {
                            throw new Exception($"NUMERO ERRORE: {response.lngErrNumber}, DESCRIZIONE ERRORE: {response.strErrString}");
                        }

                        base.Logs.Info($"CHIAMATA A SMISTAMENTO ACTION, DEL PROTOCOLLO NUMERO {request.Intestazione.Identificatore.NumeroProtocollo}, ANNO {request.Intestazione.Identificatore.AnnoProtocollo} AVVENUTA CON SUCCESSO");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"SI E' VERIFICATO UN PROBLEMA DURANTE LO SMISTAMENTO, {ex.Message}", ex);
            }
        }
    }
}
