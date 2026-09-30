using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.Allegati
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

                    Serializer.LogAndValidate(ProtocolloLogsConstants.AllegatoRequestFileName, request);
                    var response = ws.InserimentoAllegati(request);
                    if (response != null)
                    {
                        Serializer.LogAndValidate(ProtocolloLogsConstants.AllegatoResponseFileName, response);

                        if (response.esito == Esito_Type.Item1)
                            throw new Exception(String.Format("DESCRIZIONE: {0}", response.DescrizioneErrore));
                    }

                    Logs.Info("INSERIMENTO ALLEGATI AVVENUTO CON SUCCESSO");
                }
                catch (Exception ex)
                {
                    Logs.WarnFormat("ERRORE RESTITUITO DAL WEB SERVICE DURANTE L'INSERIMENTO DEGLI ALLEGATI: {0}", ex.Message);
                    Logs.ErrorFormat("Dettaglio Errore Allegati: {0}", ex.ToString());
                }
            }
        }
    }
}
