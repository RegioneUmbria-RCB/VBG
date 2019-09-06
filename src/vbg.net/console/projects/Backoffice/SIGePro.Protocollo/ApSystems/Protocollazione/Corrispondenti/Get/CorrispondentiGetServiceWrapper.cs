using Init.SIGePro.Protocollo.ApSystems.Protocollazione.Comuni;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Get
{
    public class CorrispondentiGetServiceWrapper : BaseServiceWrapper
    {
        public CorrispondentiGetServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url)
            : base(logs, serializer, username, password, url, "")
        {

        }

        public corrispondenti.corrispondenteRow GetCorrispondenteByCodice(string codiceCorrispondente)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (String.IsNullOrEmpty(codiceCorrispondente))
                        throw new Exception("CODICE CORRISPONDENTE NON VALORIZZATO");

                    var response = ws.GetCorrispondente(Auth, codiceCorrispondente, "", "", "", "", "", "");
                    var ds = new corrispondenti();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    if (ds.corrispondente.Rows.Count > 1)
                        throw new Exception("TROVATO PIU' DI UN RISULTATO");

                    if (ds.corrispondente.Rows.Count == 0)
                        throw new Exception("NESSUN CORRISPONDENTE TROVATO");


                    return ds.corrispondente[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI PER CODICE {0}, {1}", codiceCorrispondente, ex.Message), ex);
            }
        }

        public corrispondenti.corrispondenteRow GetCorrispondenteByCodiceUfficio(string codiceUfficio)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (String.IsNullOrEmpty(codiceUfficio))
                        throw new Exception("CODICE UFFICIO NON VALORIZZATO");

                    var response = ws.GetCorrispondente(Auth, codiceUfficio, "", "", "", "", "", "");
                    var ds = new corrispondenti();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    if (ds.corrispondente.Rows.Count > 1)
                        throw new Exception("TROVATO PIU' DI UN RISULTATO");

                    if (ds.corrispondente.Rows.Count == 0)
                        throw new Exception("NESSUN CORRISPONDENTE TROVATO");


                    return ds.corrispondente[0];
                }

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI PER CODICE UFFICIO {0}, {1}", codiceUfficio, ex.Message), ex);
            }
        }

        public corrispondenti.corrispondenteDataTable GetCorrispondenteByCodiceFiscale(string codiceFiscale)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (String.IsNullOrEmpty(codiceFiscale))
                        throw new Exception("CODICE FISCALE (O PARTITA IVA) DEL CORRISPONDENTE NON VALORIZZATO");


                    var response = ws.GetCorrispondente(Auth, "", codiceFiscale, "", "", "", "", "");
                    var ds = new corrispondenti();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    if (ds.corrispondente.Rows.Count > 1)
                        throw new Exception("TROVATO PIU' DI UN RISULTATO");

                    return ds.corrispondente;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI PER CODICE FISCALE {0}, {1}", codiceFiscale, ex.Message), ex);
            }
        }
    }
}
