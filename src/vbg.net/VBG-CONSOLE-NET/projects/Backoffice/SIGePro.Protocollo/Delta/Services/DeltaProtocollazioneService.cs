using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDeltaService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Delta.Services
{
    public class DeltaProtocollazioneService : BaseDeltaService
    {
        public DeltaProtocollazioneService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string proxy)
            : base(url, logs, serializer, username, password, proxy)
        {

        }

        internal ProtocolloDeltaService.Protocollo Protocolla(ProtocolloDeltaService.Protocollo request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE, username: {0}, password: {1}, request: {2}", _username, _password, ProtocolloLogsConstants.ProtocollazioneRequestFileName);

                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    var response = ws.insertProtocollo(request, _username, _password);
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'INSERIMENTO DEL PROTOCOLLO", ex);
            }
        }

        internal ProtocolloDeltaService.Protocollo LeggiProtocollo(string codiceRegistro, string anno, string progressivo)
        {
            try
            {
                int annoParsed;
                if(!int.TryParse(anno, out annoParsed))
                    throw new Exception("IL PARAMETRO ANNO DEVE AVERE UN FORMATO NUMERICO");

                int progressivoParsed;
                if(!int.TryParse(progressivo, out progressivoParsed))
                    throw new Exception("IL PARAMETRO PROGRESSIVO (NUMERO PROTOCOLLO) DEVE AVERE UN FORMATO NUMERICO");

                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO, REGISTRO: {0}, ANNO: {1}, PROGRESSIVO: {2}", codiceRegistro, anno, progressivo);
                    var response = ws.getProtocollo(codiceRegistro, annoParsed, progressivoParsed, _username, _password);
                    _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
                    _logs.InfoFormat("LETTURA DEL PROTOCOLLO AVVENUTA CORRETTAMENTE");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO NUMERO (PROGRESSIVO) {0}, ANNO {1} DAL WEB SERVICE", progressivo, anno), ex);
            }
        }
    }
}
