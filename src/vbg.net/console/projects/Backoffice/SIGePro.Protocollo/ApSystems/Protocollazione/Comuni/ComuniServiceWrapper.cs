using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione.Comuni
{
    public class ComuniServiceWrapper : BaseServiceWrapper
    {
        public ComuniServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url)
            : base(logs, serializer, username, password, url, "")
        {

        }

        public comuni.comuneRow GetComuneByCodiceIstat(string codiceIstat)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (String.IsNullOrEmpty(codiceIstat))
                    {
                        Logs.Warn("WARNING GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT NON VALORIZZATO, LE FUNZIONALITA' ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO");
                        return null;
                    }

                    string codiceIstatNumerico = Convert.ToInt32(codiceIstat).ToString();

                    var response = ws.GetComune(Auth, "", codiceIstatNumerico, "", "", "");
                    var ds = new comuni();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                    {
                        Logs.WarnFormat("WARNING RESTITUITO DAL WEB METHOD GETCOMUNE() DEL WEB SERVICE DURANTE LA RICHIESTA PER CODICE ISTAT {0}, DETTAGLIO ERRORE: {1}, LE FUNZIONALITÀ ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO", codiceIstat, ds.GetDescrizioneErrore());
                        return null;
                    }

                    if (ds.comune.Rows.Count > 1)
                    {
                        Logs.WarnFormat("WARNING GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT: {0}, TROVATO PIU' DI UN RISULTATO, LE FUNZIONALITÀ ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO", codiceIstat);
                        return null;
                    }

                    if (ds.comune.Rows.Count == 0)
                    {
                        Logs.WarnFormat("WARNING GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT: {0}, COMUNE NON TROVATO, LE FUNZIONALITÀ ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO", codiceIstat);
                        return null;
                    }

                    return ds.comune[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEL COMUNE DAL CODICE ISTAT {0}", codiceIstat, ex.Message), ex);
            }
        }
    }
}
