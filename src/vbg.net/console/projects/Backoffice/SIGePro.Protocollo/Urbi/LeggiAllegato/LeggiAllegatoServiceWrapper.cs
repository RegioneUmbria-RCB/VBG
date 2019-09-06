using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.LeggiAllegato
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
