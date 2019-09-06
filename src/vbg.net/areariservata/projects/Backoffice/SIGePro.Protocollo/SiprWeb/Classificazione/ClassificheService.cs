using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SiprWeb.Classificazione
{
    public class ClassificheService : BaseService
    {
        public ClassificheService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer)
            : base(url, logs, serializer)
        {

        }

        private ClassificheServiceProxy CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice Classifiche SiprWeb");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL_WS_CLASSIFICHE DELLA VERTICALIZZAZIONE PROTOCOLLO_SIPRWEB NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new ClassificheServiceProxy(Url);

                Logs.Debug("Fine creazione del webservice Classifiche SIPRWEB");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        public VociTitolarioDocumentoResponse GetClassifica(VociTitolarioDocumentoRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    Logs.DebugFormat("Chiamata al web method di Classificazione, LIVELLO1: {0}, LIVELLO2: {1}, LIVELLO3: {2}", request.Chiave1, request.Chiave2, request.Chiave3);

                    var response = ws.VociTitolarioDocumento(request);

                    //Messo a debug perchè spesso ritorna un esito non positivo perchè non trova le classifiche 
                    //in base al filtro passato, questo succede spesso e andrebbe ad ingolfare i logs.
                    if (response.esito == Esito_Type.Item1)
                        Logs.DebugFormat("IL WEB SERVICE HA DATO UN ESITO NEGATIVO ALLA CHIAMATA, FILTRI: LIVELLO1: {0}, LIVELLO2: {1}, LIVELLO3: {2}, LIVELLO4: {3}, DESCRIZIONE: {4}", request.Chiave1, request.Chiave2, request.Chiave3, request.Chiave4, response.DescrizioneErrore);

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE HA RESTITUITO UN ERRORE, LIVELLO1: {0}, LIVELLO2: {1}, LIVELLO3: {2}, LIVELLO4: {3}", request.Chiave1, request.Chiave2, request.Chiave3, request.Chiave4), ex);
                }
            }
        }
    }
}
