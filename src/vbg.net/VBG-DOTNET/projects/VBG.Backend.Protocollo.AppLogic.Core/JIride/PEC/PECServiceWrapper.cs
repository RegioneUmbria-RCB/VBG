using VBG.Backend.Protocollo.AppLogic.Core.JIride.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.PEC
{
    internal class PECServiceWrapper
    {
        private static class Constants
        {
            public const string PEC_FILENAME_RESPONSE = "PECResponse.xml";
        }

        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly WsPostaWebClientServiceCreator _wsPostaWebClientServiceCreator;

        internal PECServiceWrapper(string endPoinAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;

            this._wsPostaWebClientServiceCreator = new WsPostaWebClientServiceCreator(logs, bindingFactory, endPoinAddress);
        }

        /// <summary>
        /// Passare null su parametri codiceAmministrazione e codiceAoo se l'installazione non è Multi DB.
        /// </summary>
        /// <param name="strXml"></param>
        /// <param name="codiceAmministrazione"></param>
        /// <param name="codiceAoo"></param>
        internal string InviaPEC(string strXmlSegnatura, string codiceAmministrazione, string codiceAoo)
        {
            return this._wsPostaWebClientServiceCreator.Call(ws =>
            {
                var response = String.Empty;

                this._logs.InfoFormat("CHIAMATA A INVIA PEC DEL WEB METHOD INVIAMAIL DI J-IRIDE, XML SEGNATURA: {0}, CODICE AMMINISTRAZIONE: {1}, CODICEAOO: {2}", strXmlSegnatura, codiceAmministrazione, codiceAoo);
                response = ws.InviaMail(strXmlSegnatura, codiceAmministrazione, codiceAoo);
                this._logs.InfoFormat("RISPOSTA A CHIAMATA INVIA PEC DEL WEB METHOD INVIAMAIL DI J-IRIDE, RISPOSTA XML: {0}, CODICE AMMINISTRAZIONE: {1}, CODICEAOO: {2}", response, codiceAmministrazione, codiceAoo);

                this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A INVIA PEC");
                var objResponse = this._serializer.Deserialize<MessaggioOut>(response);
                this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A INVIA PEC AVVENUTA CORRETTAMENTE");

                if (!String.IsNullOrEmpty(objResponse.Codice) && objResponse.Codice != "0")
                {
                    throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", objResponse.Codice, objResponse.Descrizione));
                }

                this._logs.InfoFormat("MAIL PEC INVIATA CORRETTAMENTE");

                return response;
            });
        }

        internal VerificaInvioOut VerificaInvio(string strXmlSegnatura, string codiceAmministrazione, string codiceAoo)
        {
            return this._wsPostaWebClientServiceCreator.Call(ws =>
            {
                var response = ws.VerificaInvio(strXmlSegnatura, codiceAmministrazione, codiceAoo);

                this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A VERIFICA INVIO PEC");
                var objResponse = this._serializer.Deserialize<VerificaInvioOut>(response);
                this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A VERIFICA INVIO PEC CORRETTAMENTE");

                if (!String.IsNullOrEmpty(objResponse.Codice) && objResponse.Codice != "0")
                {
                    throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", objResponse.Codice, objResponse.Descrizione));
                }

                this._logs.InfoFormat("VERIFICA INVIO PEC TERMINATO CORRETTAMENTE");

                return objResponse;
            });
        }
    }
}
