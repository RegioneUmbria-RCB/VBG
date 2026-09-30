using VBG.Shared.Infrastructure.ServiceModel;
using PECJIrideService;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.PEC
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
        private readonly ClientPecServiceCreator _clientPecServiceCreator;

        internal PECServiceWrapper(string endPoinAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _endPointAddress = endPoinAddress;
            _logs = logs;
            _serializer = serializer;
            _clientPecServiceCreator = new ClientPecServiceCreator(logs, bindingFactory, endPoinAddress);
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
                using (var ws = this._clientPecServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A INVIA PEC DEL WEB METHOD INVIAMAIL DI J-IRIDE, XML SEGNATURA: {0}, CODICE AMMINISTRAZIONE: {1}, CODICEAOO: {2}", strXmlSegnatura, codiceAmministrazione, codiceAoo);
                    response = ws.Service.InviaMail(strXmlSegnatura, codiceAmministrazione, codiceAoo);
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

        internal VerificaInvioOut VerificaInvio(string strXmlSegnatura, string codiceAmministrazione, string codiceAoo)
        {
            try
            {
                using (var ws = this._clientPecServiceCreator.CreateClient())
                {

                    var response = ws.Service.VerificaInvio(strXmlSegnatura, codiceAmministrazione, codiceAoo);


                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A VERIFICA INVIO PEC");
                    var objResponse = _serializer.Deserialize<VerificaInvioOut>(response);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A VERIFICA INVIO PEC CORRETTAMENTE");

                    if (!String.IsNullOrEmpty(objResponse.Codice) && objResponse.Codice != "0")
                    {
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", objResponse.Codice, objResponse.Descrizione));
                    }

                    _logs.InfoFormat("VERIFICA INVIO PEC TERMINATO CORRETTAMENTE");

                    return objResponse;
                }
            }
            catch (Exception)
            {
                throw;
            }
        }
    }
}
