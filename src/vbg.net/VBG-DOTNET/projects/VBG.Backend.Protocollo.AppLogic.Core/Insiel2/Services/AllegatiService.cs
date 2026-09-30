using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using ProtocolloFilesInsielService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Services
{
    public class AllegatiService : BaseService
    {
        Utente _utente;
        private readonly ClientAllegatiServiceCreator _clientAllegatiServiceCreator;

        public AllegatiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string codiceUtente, string password) : base(url, logs, serializer)
        {
            _utente = new Utente { codice = codiceUtente, password = password };
            _clientAllegatiServiceCreator = new ClientAllegatiServiceCreator(logs, bindingFactory, url);
        }

        internal AttachmentData DownloadDocumento(DownloadDocumentoRequest request)
        {
            using (var ws = this._clientAllegatiServiceCreator.CreateClient())
            {
                try
                {
                    request.Utente = _utente;
                    Serializer.LogAndValidate(ProtocolloLogsConstants.LeggiAllegatoRequest, request);
                    Logs.InfoFormat("RICHIESTA DOWNLOAD DEL DOCUMENTO ID {0}", request.idDoc);
                    var response = ws.Service.downloadDocumento(request);
                    
                    if (!response.esito.Value)
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", response.Errore.codice, response.Errore.descrizione));

                    Logs.InfoFormat("DOWNLOAD DEL DOCUMENTO ID {0} AVVENUTA CORRETTAMENTE", request.idDoc);

                    return response.documento;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL DOWNLOAD DEL DOCUMENTO CON ID {0}, ERRORE: {1}", request.idDoc, ex.Message), ex);
                }
            }
        }

        internal UploadResponse Upload(UploadRequest request)
        {
            using (var ws = this._clientAllegatiServiceCreator.CreateClient())
            {
                try
                {
                    request.codiceUtente = _utente.codice;
                    request.passwordUtente = _utente.password;

                    Logs.Info("Chiamata al web method upload del web service di Upload File");
                    var response = ws.Service.upload(request);

                    if (!response.esito.Value)
                    {
                        var err = response.Errore;
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", response.Errore.codice, response.Errore.descrizione));
                    }

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception("IL WEB SERVICE DI UPLOAD FILE HA RESTITUITO UN ERRORE", ex);
                }
            }
        }
    }
}
