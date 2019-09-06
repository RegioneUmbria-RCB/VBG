using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloDocErRegistrazioneParticolareService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Registrazione
{
    public class RegistrazioneParticolareService
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private string _endPointAddress;

        public RegistrazioneParticolareService(string endPoinAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPoinAddress;
        }

        private WSRegistrazionePortTypeClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di registrazione DOCER");

                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("DocErHttpBinding");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new System.Exception("IL PARAMETRO URL_REG_PARTICOLARE DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCER NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new WSRegistrazionePortTypeClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di registrazione particolare PROTOCOLLO_DOCER");

                return ws;

            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI REGISTRAZIONE PARTICOLARE DOCER", ex.Message), ex);
            }
        }

        public esito Registra(string token, long idUnitaDocumentale, string registro, string segnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A REGISTRAZIONE PARTICOLARE token: {0}, id documento principale: {1}, registro: {2} dati registro: {3}", token, idUnitaDocumentale.ToString(), registro, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    var responseStringXml = ws.registraById(token, idUnitaDocumentale, registro, segnatura);

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
