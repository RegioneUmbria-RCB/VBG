using Init.SIGePro.Protocollo.ApSystems.Protocollazione.Comuni;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Insert
{
    public class CorrispondentiInsertServiceWrapper : BaseServiceWrapper
    {
        public CorrispondentiInsertServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url, string operatore)
            : base(logs, serializer, username, password, url, operatore)
        {

        }

        public Insert.corrispondenti.corrispondenteRow InsertCorrispondente(IAnagraficaAmministrazione anag, string userName)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (String.IsNullOrEmpty(anag.CodiceFiscalePartitaIva))
                        throw new Exception("IL CODICE FISCALE O LA PARTITA IVA NON SONO STATI VALORIZZATI");

                    string codiceComune = String.Empty;
                    if (!String.IsNullOrEmpty(anag.CodiceIstatResidenza))
                    {
                        var comuniSrv = new ComuniServiceWrapper(Logs, Serializer, Auth.UserName, Auth.Password, Url);
                        var com = comuniSrv.GetComuneByCodiceIstat(anag.CodiceIstatResidenza);

                        if (com != null)
                            codiceComune = com.codice;
                    }
                    Logs.InfoFormat("INSERIMENTO ANAGRAFICA CODICE {0}, NOMINATIVO {1}, CODICE FISCALE / PARTITA IVA: {2}", anag.Codice, anag.NomeCognome, anag.CodiceFiscalePartitaIva);
                    var response = ws.InsertCorrispondente(Auth, anag.CodiceFiscalePartitaIva, anag.NomeCognome, anag.Indirizzo, anag.Cap, codiceComune, anag.Email, anag.Telefono, anag.Fax, userName);
                    Logs.Info("INSERIMENTO ANAGRAFICA AVVENUTO CON SUCCESSO");

                    var ds = new corrispondenti();

                    ds.Merge(response);

                    if (ds.ContieneErroreCorrispondente())
                        throw new Exception(ds.GetDescrizioneErroreCorrispondente());

                    return ds.corrispondente[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DURANTE L'INSERIMENTO DEL CORRISPONDENTE {0}, CODICE {1}, {2}", anag.NomeCognome, anag.Codice, ex.Message), ex);
            }

        }
    }
}
