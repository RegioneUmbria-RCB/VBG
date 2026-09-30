using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SiprWeb.TipiDocumento
{
    public class TipiDocumentoService : BaseService
    {
        public TipiDocumentoService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, logs, serializer)
        {

        }

        private TipiDocumentoServiceProxy CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice TipiDocumento SiprWeb");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL_WS_LISTATIPIDOCUMENTO DELLA VERTICALIZZAZIONE PROTOCOLLO_SIPRWEB NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new TipiDocumentoServiceProxy(Url);

                Logs.Debug("Fine creazione del webservice TipiDocumento SIPRWEB");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        public TipoDocDocumentoResponse GetTipiDocumento(TipoDocDocumentoRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    Logs.InfoFormat("Chiamata al web method di TipiDocumento");

                    var response = ws.TipoDocDocumento(request);

                    if (Logs.IsDebugEnabled)
                        Serializer.Serialize(ProtocolloLogsConstants.TipiDocumentoSoapResponseFileName, response);

                    if (response.esito == Esito_Type.Item1)
                        throw new Exception(String.Format("DESCRIZIONE: {0}", response.DescrizioneErrore));

                    Logs.InfoFormat("RECUPERO TIPI DOCUMENTO AVVENUTO CON SUCCESSO, NUMERO TIPI DOCUMENTO RESTITUITI {0}", response.Documento.Length);

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception("IL WEB SERVICE HA RESTITUITO UN ERRORE", ex);
                }
            }
        }

    }
}
