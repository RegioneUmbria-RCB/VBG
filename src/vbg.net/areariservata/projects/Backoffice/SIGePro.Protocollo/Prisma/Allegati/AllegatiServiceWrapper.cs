using Init.SIGePro.Protocollo.AllegatiPrismaService;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Allegati
{
    public class AllegatiServiceWrapper : ServiceWrapperBase
    {
        string _url;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;

        public AllegatiServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentialsInfo) : base(credentialsInfo)
        {
            this._url = url;
            this._logs = logs;
            this._serializer = serializer;
        }

        private AttachServiceClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("prismaHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL_ALLEGATI DELLA VERTICALIZZAZIONE PROTOCOLLO_PRISMA NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                return new AttachServiceClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        public byte[] Download(string idDocumento, string idOggetto)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();

                        _logs.Info($"RICHIESTA DI DOWNLOAD DELL'ALLEGATO CON CODICE OGGETTO {idOggetto}, DEL DOCUMENTO ID {idDocumento} CON L'UTENTE {base.Credentials.Username}");
                        var response = ws.downloadAttach(idDocumento, idOggetto, "", base.Credentials.Username);

                        if (response.result != "0")
                        {
                            throw new Exception(response.errStr);
                        }

                        _logs.Info($"RICHIESTA DI DOWNLOAD DELL'ALLEGATO CON CODICE OGGETTO {idOggetto}, DEL DOCUMENTO ID {idDocumento} CON L'UTENTE {base.Credentials.Username} TERMINATO");

                        return response.contentFile;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL DOWNLOAD DELL'ALLEGATO CODICE {idOggetto} DEL DOCUMENTO CON ID {idDocumento}, {ex.Message}", ex);
            }
        }
    }
}
