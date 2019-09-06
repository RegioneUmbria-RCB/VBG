using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.PECJIrideService;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.JIride.PEC
{
    internal class PECServiceWrapper
    {

        private static class Constants
        {
            public const string PEC_FILENAME_RESPONSE = "PECResponse.xml";
        }

        private string _endPointAddress;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;

        internal PECServiceWrapper(string endPoinAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _endPointAddress = endPoinAddress;
            _logs = logs;
            _serializer = serializer;
        }

        private WsPostaWebSoapClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di Invio PEC di J-IRIDE");

                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_PEC DELLA VERTICALIZZAZIONE PROTOCOLLO_JIRIDE NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new WsPostaWebSoapClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di Invio PEC J-IRIDE");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE PEC, {0}", ex.Message), ex);
            }
        }

        /// <summary>
        /// Passare null su parametri codiceAmministrazione e codiceAoo se l'installazione non è Multi DB.
        /// </summary>
        /// <param name="strXml"></param>
        /// <param name="codiceAmministrazione"></param>
        /// <param name="codiceAoo"></param>
        internal string InviaPEC(string strXmlSegnatura, string codiceAmministrazione, string codiceAoo)
        {
            string response = String.Empty;
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A INVIA PEC DEL WEB METHOD INVIAMAIL DI J-IRIDE, XML SEGNATURA: {0}, CODICE AMMINISTRAZIONE: {1}, CODICEAOO: {2}", strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    response = ws.InviaMail(strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    _logs.InfoFormat("RISPOSTA A CHIAMATA INVIA PEC DEL WEB METHOD INVIAMAIL DI J-IRIDE, RISPOSTA XML: {0}, CODICE AMMINISTRAZIONE: {1}, CODICEAOO: {2}", response, codiceAmministrazione, codiceAoo);

                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A INVIA PEC");
                    var objResponse = _serializer.Deserialize<MessaggioOut>(response);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A INVIA PEC AVVENUTA CORRETTAMENTE");

                    if (!String.IsNullOrEmpty(objResponse.Codice) && objResponse.Codice != "0")
                    {
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", objResponse.Codice, objResponse.Descrizione));
                    }

                    _logs.InfoFormat("MAIL PEC INVIATA CORRETTAMENTE");
                }
            }
            catch (Exception)
            {
                throw;
            }

            return response;
        }
    }
}
