using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper : BaseServiceWrapper
    {
        public LeggiProtocolloServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string url, string username, string password) : base(logs, serializer, username, password, url, "")
        {

        }

        public protocolli LeggiProtocollo(string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            try 
            {
                using (var ws = CreaWebService())
                {
                    Logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2}", numeroProtocollo, annoProtocollo, idProtocollo);
                    var response = ws.GetProtocolloGenerale(Auth, idProtocollo, annoProtocollo, numeroProtocollo, numeroProtocollo, "", "", "", "", "","","","","","");

                    var ds = new protocolli();

                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    var proto = ds.protocollo;

                    if (proto.Rows.Count == 1)
                    {
                        Logs.InfoFormat("CHIAMATA LEGGI PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2} AVVENUTA CON SUCCESSO", numeroProtocollo, annoProtocollo, idProtocollo);
                        Logs.InfoFormat("DATI RESTITUITI DA LEGGI_PROTOCOLLO: {0}", ds.GetXml());
                    }
                    else
                    {
                        if (proto.Rows.Count == 0)
                            throw new Exception("LA RICERCA NON HA PRODOTTO ALCUN RISULTATO");

                        if (proto.Rows.Count > 1)
                            throw new Exception("LA RICERCA HA PRODOTTO PIU' DI UN RISULTATO");
                    }

                    return ds;
                }
            }
            catch(Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2}, {3}", numeroProtocollo, annoProtocollo, idProtocollo, ex.Message), ex);
            }
        }
    }
}
