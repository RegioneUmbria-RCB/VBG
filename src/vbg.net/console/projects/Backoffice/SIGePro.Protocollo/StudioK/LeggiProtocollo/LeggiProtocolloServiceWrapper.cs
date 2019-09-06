using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper : BaseServiceWrapper
    {
        public LeggiProtocolloServiceWrapper(string url, string connectionString, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, connectionString, logs, serializer)
        {

        }

        public Segnatura Leggi(long numeroProtocollo, long annoProtocollo, string codiceAoo)
        {
            try
            {
                using(var ws = CreaWebService())
                {
                    Logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO,  NUMERO PROTOCOLLO: {0}, ANNO PROTOCOLLO: {1}, AOO: {2}", ConnectionString, numeroProtocollo, annoProtocollo, codiceAoo);
                    var xml = ws.infoProtocollo(ConnectionString, numeroProtocollo, annoProtocollo, codiceAoo);
                    Logs.DebugFormat("XML RESTITUITO DALLA RISPOSTA AL METODO LEGGI PROTOCOLLO {0}", xml);
                    var response = Serializer.Deserialize<Segnatura>(xml);

                    Logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO,  NUMERO PROTOCOLLO: {0}, ANNO PROTOCOLLO: {1}, AOO: {2} EFFETTUATA CORRETTAMENTE", ConnectionString, numeroProtocollo, annoProtocollo, codiceAoo);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DURANTE LA LETTURA DEL PROTOCOLLO NUMERO {0} ANNO {1}, {2}", numeroProtocollo.ToString(), annoProtocollo.ToString(), ex.Message), ex);
            }
        }

        public byte[] DownloadAllegato(long numero, long anno, string nomeFile, string codiceAoo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    Logs.InfoFormat("DOWNLOAD FILE NOME {0},  NUMERO PROTOCOLLO: {1}, ANNO PROTOCOLLO: {2}", nomeFile, numero.ToString(), anno.ToString());
                    var xml = ws.getDocumento(ConnectionString, numero, anno, nomeFile, codiceAoo);
                    Logs.DebugFormat("XML RESTITUITO DALLA RISPOSTA AL METODO GET DOCUMENTO {0}", xml);
                    if (ws.ResponseSoapContext.Attachments.Count == 1)
                    {
                        using (var ms = new MemoryStream())
                        {
                            ws.ResponseSoapContext.Attachments[0].Stream.CopyTo(ms);
                            Logs.InfoFormat("DOWNLOAD FILE NOME {0},  NUMERO PROTOCOLLO: {1}, ANNO PROTOCOLLO: {2} AVVENUTO CORRETTAMENTE", nomeFile, numero.ToString(), anno.ToString());
                            return ms.ToArray();
                        }
                    }

                    throw new Exception("FILE NON TROVATO");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DURANTE DOWNLOAD DEL FILE NOME {0}, NUMERO PROTOCOLLO {1}, ANNO {2}, {3}", nomeFile, numero.ToString(), anno.ToString(), ex.Message), ex);
            }        
        }
    }
}
