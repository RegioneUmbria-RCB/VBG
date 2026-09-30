using Newtonsoft.Json;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.Classificazione
{
    public class ClassificazioneServiceWrapper : BaseProtocolloServiceWrapper
    {
        const string _modelKey = "it.pal.cw2.model.media.atd.Titolario";
        string _token;

        public ClassificazioneServiceWrapper(ProtocolloLogs logs, string baseUrlWs, string token) : base(logs, baseUrlWs)
        {
            _token = token;
        }

        public RootObject GetClassifica()
        {
            try
            {
                var uri = new Uri(new Uri(_baseUrlWs), _modelKey);

                using (var client = GetHttpClient(_token))
                {
                    client.QueryString.Add("skipPagination", "true");
                    _logs.Info("CHIAMATA A LEGGI CLASSIFICHE");
                    var jsonResponse = client.DownloadString(uri);
                    _logs.InfoFormat("DESERIALIZZAZIONE DELLA RISPOSTA {0}", jsonResponse);
                    var result = JsonConvert.DeserializeObject<RootObject>(jsonResponse);
                    _logs.InfoFormat("CHIAMATA A LEGGI CLASSIFICHE AVVENUTA CON SUCCESSO");

                    return result;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE AVVENUTO DURANTE LA LETTURA DELLE CLASSIFICHE, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
