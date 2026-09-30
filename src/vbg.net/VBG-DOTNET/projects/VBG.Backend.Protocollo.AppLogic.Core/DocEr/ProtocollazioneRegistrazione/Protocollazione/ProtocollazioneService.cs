using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloDocErProtocolloService;
using System;
using System.Runtime.CompilerServices;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.Protocollazione
{
    public class ProtocollazioneService
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private string _endPointAddress;
        private const string IntestazioneXml = "";
        private readonly ProtocollazioneClientServiceCreator _protocollazioneClientServiceCreator;

        public ProtocollazioneService(string endPoinAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPoinAddress;
            _protocollazioneClientServiceCreator = new ProtocollazioneClientServiceCreator(_logs, bindingFactory, _endPointAddress);
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
                using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, id documento principale: {1}, dati protocollo: {2}", token, documentId.ToString(), ProtocolloLogsConstants.SegnaturaXmlFileName);
                    string responseStringXml = "";
                    try
                    {
                        responseStringXml = ws.Service.protocollaById(token, documentId, datiProtocollo);
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
