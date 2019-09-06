using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.EGrammata.Proxy.GetDati;

namespace Init.SIGePro.Protocollo.EGrammata.Services
{
    internal class EGrattamataLeggiProtocolloService : BaseEGrammataService
    {
        public EGrattamataLeggiProtocolloService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(endPointAddress, logs, serializer)
        {

        }

        private WSGetDatiUDService CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di lettura protocollo E-Grammata");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_LEGGI_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA NON È STATO VALORIZZATO.");

                var ws = new WSGetDatiUDService(_endPointAddress);

                _logs.Debug("Fine creazione del web service di lettura protocollo E-GRAMMATA");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }


        internal string LeggiProtocollo(string userId, string password, string idUnita, string livelloUnita, string segnaturaXml)
        {
            try
            {
                using (var client = CreaWebService())
                {
                    _logs.InfoFormat("Chiamata a leggi protocollo, userid: {0}, password: {1}, idunita: {2}, livellounita: {3}, segnatura64: {4}", segnaturaXml);
                    string response = client.service(userId, password, idUnita, livelloUnita, segnaturaXml);
                    _logs.InfoFormat("Lettura avvenuta con successo");
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

    }
}