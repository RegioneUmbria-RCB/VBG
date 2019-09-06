/*using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SiprWeb.Allegati
{
    public class AllegatiService : BaseService
    {
        public AllegatiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, logs, serializer)
        {
            
        }

        private AllegatiServiceProxy CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice Allegati Service SiprWeb");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL_WS_ALLEGATI DELLA VERTICALIZZAZIONE PROTOCOLLO_SIPRWEB NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new AllegatiServiceProxy(Url);

                Logs.Debug("Fine creazione del webservice Allegati Service SIPRWEB");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        public void Inserisci(inserisciAllegatiRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    Logs.InfoFormat("Chiamata al web method InserimentoAllegati");
                    
                    Serializer.Serialize(ProtocolloLogsConstants.AllegatoRequestFileName, request);
                    var response = ws.InserimentoAllegati(request);
                    Serializer.Serialize(ProtocolloLogsConstants.AllegatoResponseFileName, response);

                    if (response.esito == Esito_Type.Item1)
                        throw new Exception(String.Format("DESCRIZIONE: {0}", response.DescrizioneErrore));

                    Logs.Info("INSERIMENTO ALLEGATI AVVENUTO CON SUCCESSO");
                }
                catch (Exception ex)
                {
                    Logs.WarnFormat("Si' verificato il seguente errore durante l'inserimento degli allegati: {0}", ex.Message);
                    Logs.ErrorFormat("Dettaglio Errore Allegati: {0}", ex.ToString());
                }
            }
        }
    }
}
*/