using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Allegati
{
    public class AllegatiServiceWrapper : BaseServiceWrapper
    {
        public AllegatiServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url, string operatore)
            : base(logs, serializer, username, password, url, operatore)
        {

        }

        public void InserisciAllegatoaProtocolloGenerale(string codiceProtocollo, string numeroProtocollo, string dataProtocollo, byte[] oggetto, string nomeFile, string codiceAllegato)
        {
            try
            {
                using(var ws = CreaWebService())
                {
                    Logs.InfoFormat("INSERIMENTO ALLEGATO {0} CODICE {1} AL PROTOCOLLO NUMERO {2} DEL {3} CODICE PROTOCOLLO {4}", nomeFile, codiceAllegato, numeroProtocollo, dataProtocollo, codiceProtocollo);
                    ws.InsertAllegatoProtocolloGenerale(Auth, codiceProtocollo, oggetto, nomeFile, Operatore);
                    Logs.InfoFormat("INSERIMENTO ALLEGATO {0} CODICE {1} AL PROTOCOLLO NUMERO {2} DEL {3} CODICE PROTOCOLLO {4} AVVENUTO CON SUCCESSO", nomeFile, codiceAllegato, numeroProtocollo, dataProtocollo, codiceProtocollo);
                }
            }
            catch (Exception ex)
            {
                Logs.WarnFormat("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ALLEGATO {0} CODICE {1} AL PROTOCOLLO GENERALE NUMERO {2} DEL {3} CODICE PROTOCOLLO {4}, ERRORE {5}", nomeFile, codiceAllegato, numeroProtocollo, dataProtocollo, codiceProtocollo, ex.Message);
            }
        }

        public byte[] DownloadAllegato(string codiceAllegato, string numeroProtocolo, string annoProtocollo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    Logs.InfoFormat("RICHIESTA DI DOWNLOAD ALLEGATO CODICE {0} RELATIVO AL PROTOCOLLO NUMERO {1} ANNO {2}", codiceAllegato, numeroProtocolo, annoProtocollo);
                    var response = ws.GetAllegato(Auth, codiceAllegato, false);
                    Logs.InfoFormat("RICHIESTA DI DOWNLOAD ALLEGATO CODICE {0} RELATIVO AL PROTOCOLLO NUMERO {1} ANNO {2} AVVENUTA CON SUCCESSO", codiceAllegato, numeroProtocolo, annoProtocollo);
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL DOWNLOAD DELL'ALLEGATO CODICE {0} RELATIVO AL PROTOCOLLO NUMERO {1} ANNO {2}, {3}", codiceAllegato, numeroProtocolo, annoProtocollo, ex.Message), ex);
            }
        }
    }
}
