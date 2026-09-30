using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiAllegato
{
    public class LeggiAllegatoServiceWrapper : BaseServiceWrapper
    {
        public LeggiAllegatoServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, VerticalizzazioniWrapper vert)
            : base(logs, serializer, vert.Username, vert.Password, vert.Url)
        {

        }

        public byte[] DownloadAllegato(string idAllegato, string versione)
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    client.QueryString.Add(_nomeParametroMetodo, "StreamDoc");
                    client.QueryString.Add("Testata", idAllegato);
                    client.QueryString.Add("Versione", versione);

                    _logs.InfoFormat("CHIAMATA A DOWNLOAD ALLEGATO REQUEST: {0}", Utility.NameValueCollectionToString(client.QueryString));
                    var buffer = client.DownloadData(_url);

                    return buffer;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL DOWNLOAD DELL'ALLEGATO ID {0} VERSIONE {1}, ERRORE: {2}", idAllegato, versione, ex.Message), ex);
            }
        }
    }
}
