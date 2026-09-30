using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErProtocolloService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Protocollazione
{
    public class ProtocollazioneService
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private string _endPointAddress;
        private const string IntestazioneXml = "";

        public ProtocollazioneService(string endPoinAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPoinAddress;
        }

        private WSProtocollazionePortTypeClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione DOCER");

                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("DocErHttpBinding");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new System.Exception("IL PARAMETRO URL_PROTOCOLLAZIONE DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCER NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new WSProtocollazionePortTypeClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di protocollazione PROTOCOLLO_DOCER");

                return ws;

            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE DOCER, ERRORE: {0}", ex.Message), ex);
            }
        }

        /// <summary>
        /// Effettua la protocollazione
        /// </summary>
        /// <param name="token">Token restituito dall'autenticazione</param>
        /// <param name="documentId">Id del documento principale restituito dalla gestione documentale</param>
        public ProtocollazioneEsitoResponse Protocollazione(string token, long documentId, string datiProtocollo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, id documento principale: {1}, dati protocollo: {2}", token, documentId.ToString(), ProtocolloLogsConstants.SegnaturaXmlFileName);
                    string responseStringXml = "";
                    try
                    {
                        responseStringXml = ws.protocollaById(token, documentId, datiProtocollo);
                    }
                    catch (System.Exception ex)
                    {
                        return new ProtocollazioneEsitoResponse(null, ex.Message);
                    }

                    _logs.InfoFormat("DESERIALIZZAZIONE DELLA RISPOSTA DEL WS: {0}", responseStringXml);
                    var esitoWs = (esito)_serializer.Deserialize(responseStringXml, typeof(esito));
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DEL WS AVVENUTA CON SUCCESSO");
                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, id protocollo: {2}", esitoWs.dati_protocollo[0].NUM_PG, esitoWs.dati_protocollo[0].DATA_PG, esitoWs.codice);

                    return new ProtocollazioneEsitoResponse(esitoWs, "");
                }
            }
            catch (System.Exception)
            {
                throw;
            }
        }
    }
}
