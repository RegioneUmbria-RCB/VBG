using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloDocErRegistrazioneParticolareService;
using System;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.Registrazione
{
    public class RegistrazioneParticolareService
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private string _endPointAddress;
        private readonly ClientRegistrazioneServiceCreator _clientRegistrazioneServiceCreator;

        public RegistrazioneParticolareService(string endPoinAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPoinAddress;
            _clientRegistrazioneServiceCreator = new ClientRegistrazioneServiceCreator(_logs, bindingFactory, _endPointAddress);
        }

        public esito Registra(string token, long idUnitaDocumentale, string registro, string segnatura)
        {
            try
            {
                using (var ws = this._clientRegistrazioneServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A REGISTRAZIONE PARTICOLARE token: {0}, id documento principale: {1}, registro: {2} dati registro: {3}", token, idUnitaDocumentale.ToString(), registro, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    var responseStringXml = ws.Service.registraById(token, idUnitaDocumentale, registro, segnatura);

                    var response = (esito)_serializer.Deserialize(responseStringXml, typeof(esito));
                    _logs.InfoFormat("REGISTRAZIONE AVVENUTA CON SUCCESSO, numero: {0}, data: {1}, id: {2}", response.dati_registro[0].NumeroRegistrazione, response.dati_registro[0].DataRegistrazione, response.codice);

                    return response;

                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DI REGISTRAZIONE PARTICOLARE, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
