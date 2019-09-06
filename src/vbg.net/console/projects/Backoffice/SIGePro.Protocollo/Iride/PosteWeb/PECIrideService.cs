using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Iride.Proxies;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.Iride.PosteWeb
{
    internal class PECIrideService
    {

        private static class Constants
        {
            public const string PEC_FILENAME_RESPONSE = "PECResponse.xml";
        }

        private string _endPointAddress;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        string _proxyAddress;

        internal PECIrideService(string endPoinAddress, string proxyAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _endPointAddress = endPoinAddress;
            _proxyAddress = proxyAddress;
            _logs = logs;
            _serializer = serializer;
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
                using (var ws = new WsPostaWeb(_endPointAddress, _proxyAddress))
                {
                    _logs.InfoFormat("Chiamata a Invia PEC del web method InviaMail di Iride, xml segnatura: {0}, codice amministrazione: {1}, codiceAoo: {2}", strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    response = ws.InviaMail(strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    _logs.InfoFormat("Risposta a chiamata Invia PEC del web method InviaMail di Iride, risposta xml: {0}, codice amministrazione: {1}, codiceAoo: {2}", response, codiceAmministrazione, codiceAoo);

                    _serializer.Serialize(Constants.PEC_FILENAME_RESPONSE, strXmlSegnatura);

                    MessaggioOut objResponse = (MessaggioOut)_serializer.Deserialize(response, typeof(MessaggioOut));

                    if (!String.IsNullOrEmpty(objResponse.Codice) && objResponse.Codice != "0")
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", objResponse.Codice, objResponse.Descrizione));

                    _logs.InfoFormat("MAIL PEC INVIATA CORRETTAMENTE");
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI POSTA PEC DI IRIDE SU WEB METHOD InviaMail, segnatura: {0}, codiceAmministrazione: {1}, codiceAoo: {2}, Eccezione: {3}", strXmlSegnatura, codiceAmministrazione, codiceAoo, ex.ToString());
            }

            return response;
        }

        /// <summary>
        /// Passare null su parametri codiceAmministrazione e codiceAoo se l'installazione non è Multi DB.
        /// </summary>
        /// <param name="strXml"></param>
        /// <param name="codiceAmministrazione"></param>
        /// <param name="codiceAoo"></param>
        internal string InviaPECInterop(string strXmlSegnatura, string codiceAmministrazione, string codiceAoo)
        {
            string response = String.Empty;
            try
            {
                using (var ws = new WsPostaWeb(_endPointAddress, _proxyAddress))
                {
                    _logs.InfoFormat("Chiamata a Invia PEC del web method InviaMailInterop di Iride, xml segnatura: {0}, codice amministrazione: {1}, codiceAoo: {2}", strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    response = ws.InviaMailInterop(strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    _logs.InfoFormat("Risposta a chiamata Invia PEC del web method InviaMailInterop di Iride, risposta xml: {0}, codice amministrazione: {1}, codiceAoo: {2}", response, codiceAmministrazione, codiceAoo);

                    _serializer.Serialize(Constants.PEC_FILENAME_RESPONSE, strXmlSegnatura);

                    MessaggioOut objResponse = (MessaggioOut)_serializer.Deserialize(response, typeof(MessaggioOut));
                    
                    if (!String.IsNullOrEmpty(objResponse.Codice) && objResponse.Codice != "0")
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", objResponse.Codice, objResponse.Descrizione));

                    _logs.InfoFormat("MAIL PEC INVIATA CORRETTAMENTE");
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI POSTA PEC DI IRIDE, Dettaglio Errore: {0}", ex.Message);
                _logs.ErrorFormat("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI POSTA PEC DI IRIDE SU WEB METHOD InviaMail, segnatura: {0}, codiceAmministrazione: {1}, codiceAoo: {2}, Eccezione: {3}", strXmlSegnatura, codiceAmministrazione, codiceAoo, ex.ToString());
            }

            return response;
        }
    }
}
