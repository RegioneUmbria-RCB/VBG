using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloDatagraphService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo
{
    public class LeggiProtocolloService : DatagraphServiceWrapperBase
    {
        public LeggiProtocolloService(string url, string token, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, logs, serializer)
        {
            base.Token = token;
        }

        public RegistrazioneRet LeggiProtocollo(int numero, int anno)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {

                    base.Logs.Info($"CHIAMATA A LEGGI PROTOCOLLO SENZA ALLEGATI (METODO GetRegistrazione), NUMERO: {numero}, ANNO: {anno}");
                    var response = ws.GetRegistrazione2(anno, numero, false, base.Token);

                    var responseXml = base.Serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
                    base.Logs.Info($"RISPOSTA DA LEGGIPROTOCOLLO SENZA ALLEGATI DEL PROTOCOLLO NUMERO {numero} ANNO {anno}: {responseXml}");

                    base.Logs.Info($"CHIAMATA A LEGGI PROTOCOLLO SENZA ALLEGATI (METODO GetRegistrazione2), NUMERO: {numero}, ANNO: {anno}, AVVENUTA CON SUCCESSO");
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, ERRORE: {ex.Message}", ex);
            }
        }

        public RegistrazioneConAllegatiResponse LeggiProtocolloConAllegati(int numero, int anno)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {
                    base.Logs.Info($"CHIAMATA A LEGGI PROTOCOLLO CON ALLEGATI (METODO GetRegistrazione), NUMERO: {numero}, ANNO: {anno}");
                    var response = ws.GetRegistrazione(anno, numero, false, base.Token);

                    var responseXml = base.Serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
                    base.Logs.Info($"RISPOSTA DA LEGGIPROTOCOLLO CON ALLEGATI DEL PROTOCOLLO NUMERO {numero} ANNO {anno}, NUMERO ALLEGATI {ws.ResponseSoapContext.Attachments}: {responseXml}");

                    base.Logs.Info($"CHIAMATA A LEGGI PROTOCOLLO CON ALLEGATI (METODO GetRegistrazione), NUMERO: {numero}, ANNO: {anno}, AVVENUTA CON SUCCESSO");
                    return new RegistrazioneConAllegatiResponse(response, ws.ResponseSoapContext.Attachments);
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
