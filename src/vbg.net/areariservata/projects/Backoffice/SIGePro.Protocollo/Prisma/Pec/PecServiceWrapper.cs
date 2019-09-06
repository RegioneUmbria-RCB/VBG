using Init.SIGePro.Protocollo.InvioPecAdsService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Pec
{
    public class PecServiceWrapper : ServiceWrapperBase
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _url;

        public PecServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo info) : base(info)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._url = url;
        }

        private PecSOAPImplClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this._url);
                var binding = new BasicHttpBinding("prismaHttpBinding");

                if (String.IsNullOrEmpty(this._url))
                    throw new Exception("IL PARAMETRO URL_PEC DELLA VERTICALIZZAZIONE PROTOCOLLO_PRISMA NON È STATO VALORIZZATO.");

                return new PecSOAPImplClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI INVIO PEC, {0}", ex.Message), ex);
            }
        }

        public void InviaPec(ParametriIngressoPG request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        AggiungiCredenzialiAContextScope();
                        var requestXml = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, request);

                        _logs.InfoFormat("CHIAMATA A INVIO PEC PRISMA, PROTOCOLLO NUMERO: {0}, ANNO: {1}, request: {2}", request.numero, request.anno, requestXml);
                        var response = ws.invioPecPG(request);

                        if (response.codice != 0 && !String.IsNullOrEmpty(response.msgId))
                        {
                            throw new Exception($"CODICE ERRORE: {response.codice}, DESCRIZIONE ERRORE: {response.descrizione}");
                        }

                        _logs.Info($"CHIAMATA A INVIO PEC PRISMA, PROTOCOLLO NUMERO: {request.numero}, ANNO: {request.anno}, AVVENUTA CON SUCCESSO, RISPOSTA DAL WS, CODICE: {response.codice}, DESCRIZIONE: {response.descrizione}, MSGID: {response.msgId}");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"{ex.Message}", ex);
            }
        }
    }
}
