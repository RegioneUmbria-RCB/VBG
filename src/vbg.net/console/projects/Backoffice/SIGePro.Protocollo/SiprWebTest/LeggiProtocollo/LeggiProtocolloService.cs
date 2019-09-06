using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo
{
    public class LeggiProtocolloService : BaseService
    {
        public LeggiProtocolloService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, logs, serializer)
        {

        }

        private LeggiProtocolloServiceProxy CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice LeggiProtocollo SiprWeb");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL_LEGGI DELLA VERTICALIZZAZIONE PROTOCOLLO_SIPRWEB NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new LeggiProtocolloServiceProxy(Url);

                Logs.Debug("Fine creazione del webservice LeggiProtocollo SIPRWEB");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        public leggiDocumentoResponse LeggiProtocollo(leggiDocumentoRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    Logs.InfoFormat("Chiamata al web method di LeggiProtocollo");

                    var response = ws.LeggiDocumento(request);

                    if (Logs.IsDebugEnabled)
                        Serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

                    if (response.esito == Esito_Type.Item1)
                        throw new Exception(String.Format("DESCRIZIONE: {0}", response.DescrizioneErrore));

                    Logs.Info("LETTURA PROTOCOLLO AVVENUTA CON SUCCESSO");

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
