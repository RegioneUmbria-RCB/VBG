using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper : BaseServiceWrapper
    {
        public LeggiProtocolloServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, VerticalizzazioniWrapper vert)
            : base(logs, serializer, vert.Username, vert.Password, vert.Url)
        {


        }

        public LeggiProtocolloResponse LeggiProtocollo(string codiceAoo, string anno, string numero, string sezione)
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    client.QueryString.Add(_nomeParametroMetodo, "getInterrogazioneProtocollo");
                    client.QueryString.Add("PRCORE03_99991013_IDAOO", codiceAoo);
                    client.QueryString.Add("PRCORE03_99991013_Anno", anno);
                    client.QueryString.Add("PRCORE03_99991013_DaNumero", numero);
                    client.QueryString.Add("PRCORE03_99991013_ANumero", numero);
                    client.QueryString.Add("PRCORE03_99991013_Sezione", sezione);

                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO REQUEST: {0}", Utility.NameValueCollectionToString(client.QueryString));
                    var xml = client.DownloadString(_url);

                    var response = _serializer.Deserialize<xapirestTypeGetInterrogazioneProtocollo>(xml);

                    if (response.getInterrogazioneProtocollo_Result.NumProtocolli == "0" && sezione != "I")
                    {
                        _logs.InfoFormat("PROTOCOLLO NUMERO: {0}, ANNO: {1}, SEZIONE: {2} NON TROVATO", numero, anno, sezione);
                        return LeggiProtocollo(codiceAoo, anno, numero, "I");
                    }

                    if (response.getInterrogazioneProtocollo_Result.NumProtocolli == "0")
                        throw new Exception(String.Format("NUMERO {0}, ANNO {1} NON TROVATO", numero, anno, sezione));

                    return new LeggiProtocolloResponse(response, xml);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
