using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloDocErFascicolazioneService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocEr.Fascicolazione
{
    public class FascicolazioneService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;
        string _token;

        public FascicolazioneService(string endPoinAddress, string token, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPoinAddress;
            _token = token;
        }

        private WSFascicolazionePortTypeClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di fascicolazione DOCER");

                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("DocErHttpBinding");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new System.Exception("IL PARAMETRO URL_FASCICOLAZIONE DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCER NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new WSFascicolazionePortTypeClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di fascicolazione PROTOCOLLO_DOCER");

                return ws;

            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI FASCICOLAZIONE DOCER, {0}", ex.Message), ex);
            }
        }

        public CreaFascicolo.esito CreaFascicolo(KeyValuePair[] metadati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.Info("CHIAMATA A CREA FASCICOLO");
                    var responseXml = ws.creaFascicolo(_token, metadati);
                    _logs.InfoFormat("CREAZIONE FASCICOLO AVVENUTO CON SUCCESSO, CREATO FASCICOLO {0}", responseXml);

                    var response = (CreaFascicolo.esito)_serializer.Deserialize(responseXml, typeof(CreaFascicolo.esito));

                    if (response == null)
                        throw new System.Exception("RISPOSTA AL CREAFASCICOLO NULL");

                    if (response.codice != "0")
                        throw new System.Exception(String.Format("ERRORE DURANTE IL CREA FASCICOLO, CODICE ERRORE: {0}, DESCRIZIONE: {1}", response.codice, response.descrizione));

                    if(response.esito_fascicolo.Length == 0)
                        throw new System.Exception("ERRORE DURANTE IL CREA FASCICOLO, NON SONO STATI RESTITUITI DATI");

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DI CREAZIONE FASCICOLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public esito Fascicola(long unitaDocumentale, string datiFascicolo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A FASCICOLABYID, unita documentale: {0}, dati fascicolo: {1}", unitaDocumentale.ToString(), datiFascicolo);
                    var responseXml = ws.fascicolaById(_token, unitaDocumentale, datiFascicolo);

                    var response = (esito)_serializer.Deserialize(responseXml, typeof(esito));
                    _logs.InfoFormat("FASCICOLAZIONE AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DI FASCICOLAZIONE, ERRORE: {0}", ex.Message), ex);
            }            
        }
    }
}
