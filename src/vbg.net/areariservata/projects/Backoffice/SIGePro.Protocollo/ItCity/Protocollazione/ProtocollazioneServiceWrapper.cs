using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public class ProtocollazioneServiceWrapper : ServiceWrapperBase
    {
        public ProtocollazioneServiceWrapper(string url, LoginWsInfo login, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, login, logs, serializer)
        {

        }

        public ProtocolloOutput Protocolla(ProtocollazioneRequestInfo request, IEnumerable<byte[]> buffers)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {
                    var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    base.Logs.Info($"CHIAMATA A PROTOCOLLAZIONE, USERNAME WS: {base.LoginInfo.Username}, PASSWORD WS: {base.LoginInfo.Password}, ID UNITA OPERATIVA: {base.LoginInfo.Identificativo.IdUnitaOperativa}, ID UTENTE: {base.LoginInfo.Identificativo.IdUtente}, REQUEST: {requestXml}");

                    var response = ws.ProtocollaAllegati(base.LoginInfo.Username, base.LoginInfo.Password, base.LoginInfo.Identificativo, request.CoordinateArchivioInfo, request.MittenteInternoInfo, request.MittentiEsterniInfo, request.DestinatariInterniInfo, request.DestinatariEsterniInfo, null, request.Allegati, buffers.ToArray());

                    var responseXml = base.Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);
                    base.Logs.Info($"RESPONSE DELLA CHIAMATA A PROTOCOLLAZIONE: {responseXml}");

                    if (response.Exitcode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    base.Logs.Info($"CHIAMATA A PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE, NUMERO PROTOCOLLO: {response.Protocollo.NumeroProtocollo}, DATA PROTOCOLLO: {response.Protocollo.DataProtocollo}, ID PROTOCOLLO: {response.Protocollo.IdDocumento}");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"CHIAMATA A METODO PROTOCOLLAALLEGATI FALLITA, {ex.Message}", ex);
            }
        }
    }
}
