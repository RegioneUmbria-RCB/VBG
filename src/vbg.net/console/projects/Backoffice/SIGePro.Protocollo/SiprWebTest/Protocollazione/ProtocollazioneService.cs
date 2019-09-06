using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SiprWebTest.Protocollazione
{
    public class ProtocollazioneService : BaseService
    {
        public ProtocollazioneService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, logs, serializer)
        {

        }

        private ProtocollazioneServiceProxy CreaWebService()
        {
            try
            {

                Logs.Debug("Creazione del webservice di Protocollazione SiprWeb");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL_WS_PROTOCOLLO DELLA VERTICALIZZAZIONE PROTOCOLLO_SIPRWEB NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new ProtocollazioneServiceProxy(Url);

                Logs.Debug("Fine creazione del webservice Protocollazione SIPRWEB");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        public protDocumentoResponse Protocolla(protDocumentoRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    Logs.InfoFormat("Chiamata al web method di Protocollazione");

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    var response = ws.ProtocolloRegistrazione(request);
                    Serializer.Serialize(ProtocolloLogsConstants.AllegatoResponseFileName, response);

                    if (response.esito == Esito_Type.Item1)
                        throw new Exception(String.Format("DESCRIZIONE: {0}", response.DescrizioneErrore));

                    Logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, NUMERO PROTOCOLLO {0}", response.NumeroProtocollo);

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception("IL WEB SERVICE HA RESTITUITO UN ERRORE", ex);
                }
            }

        }

    }
}
