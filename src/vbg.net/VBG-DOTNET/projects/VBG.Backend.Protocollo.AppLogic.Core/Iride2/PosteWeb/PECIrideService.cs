using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.PosteWeb
{
    internal class PECIrideService
    {

        private static class Constants
        {
            public const string PEC_FILENAME_RESPONSE = "PECResponse.xml";
        }

        private readonly string _endPointAddress;
        private readonly string _proxyAddress;
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly WsPostaWebClientServiceCreator _protocolloClientServiceCreator;

        internal PECIrideService(string endPoinAddress, string proxyAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _endPointAddress = endPoinAddress;
            _proxyAddress = proxyAddress;
            _logs = logs;
            _serializer = serializer;
            _protocolloClientServiceCreator = new WsPostaWebClientServiceCreator(logs, bindingFactory, proxyAddress, endPoinAddress);
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
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("Chiamata a Invia PEC del web method InviaMail di Iride, xml segnatura: {0}, codice amministrazione: {1}, codiceAoo: {2}", strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    response = ws.Service.InviaMail(strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    _logs.InfoFormat("Risposta a chiamata Invia PEC del web method InviaMail di Iride, risposta xml: {0}, codice amministrazione: {1}, codiceAoo: {2}", response, codiceAmministrazione, codiceAoo);

                    _serializer.LogAndValidate(Constants.PEC_FILENAME_RESPONSE, strXmlSegnatura);

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
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("Chiamata a Invia PEC del web method InviaMailInterop di Iride, xml segnatura: {0}, codice amministrazione: {1}, codiceAoo: {2}", strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    response = ws.Service.InviaMailInterop(strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    _logs.InfoFormat("Risposta a chiamata Invia PEC del web method InviaMailInterop di Iride, risposta xml: {0}, codice amministrazione: {1}, codiceAoo: {2}", response, codiceAmministrazione, codiceAoo);

                    _serializer.LogAndValidate(Constants.PEC_FILENAME_RESPONSE, strXmlSegnatura);

                    try
                    {
                        var objResponse = _serializer.Deserialize<MessaggioOut>(response);
                        if (!String.IsNullOrEmpty(objResponse.Codice) && objResponse.Codice != "0")
                            throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", objResponse.Codice, objResponse.Descrizione));

                    }
                    catch (Exception)
                    {
                        throw new Exception(response);
                    }


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
