using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloArchiFlowServiceReference;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Archiflow.Protocollazione
{
    public class ProtocollazioneServiceWrapper
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly Login _credenziali;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public ProtocollazioneServiceWrapper(string url, Login credenziali, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._credenziali = credenziali;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
        }

        private string Login()
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._logs.InfoFormat("RICHIESTA DI AUTENTICAZIONE, USERNAME: {0}, PASSWORD: {1}, CODICE ENTE: {2}", this._credenziali.Username, this._credenziali.Password, this._credenziali.CodEnte);
                    var response = ws.Service.Login(this._credenziali);

                    if (response.error_number != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.error_number, response.error_description));

                    this._logs.InfoFormat("AUTENTICAZIONE AVVENUTA CORRETTAMENTE, TOKEN: {0}", response.Token);

                    return response.Token;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public SuapProtoResponse ProtocollazioneArrivo(SuapInsertProto metadati)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    var token = this.Login();

                    this._serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, metadati);
                    this._logs.Info("RICHIESTA DI PROTOCOLLAZIONE");
                    var response = ws.Service.SuapGetProto(metadati, token);

                    if (response.ErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.ErrNumber, response.ErrDescription));

                    this._logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE, NUMERO: {0}, DATA: {1}", response.Numeroprotocollo.ToString(), response.dataProtocollo);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void InserimentoDocumentoPrincipale(Guid guidCard, ProtocolloAllegati doc)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    var token = this.Login();

                    this._logs.InfoFormat("INSERIMENTO DEL DOCUMENTO PRINCIPALE, CODICE OGGETTO: {0}, NOME: {1}", doc.CODICEOGGETTO, doc.NOMEFILE);
                    var response = ws.Service.SuapInsertDoc(guidCard, token, doc.OGGETTO, doc.Extension);

                    if (response.ErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.ErrNumber, response.ErrDescription));

                    this._logs.InfoFormat("INSERIMENTO DEL DOCUMENTO PRINCIPALE AVVENUTO CORRETTAMENTE, CODICE OGGETTO: {0}, NOME: {1}", doc.CODICEOGGETTO, doc.NOMEFILE);
                }
            }
            catch (Exception ex)
            {
                this._logs.WarnFormat("ERRORE GENERATO DURANTE L'INSERIMENTO DEL DOCUMENTO PRINCIPALE, ERRORE: {0}", ex.Message);
            }
        }

        public void InserimentoAllegati(Guid guidCard, oAttachmentCard[] docs)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    var token = this.Login();
                    int numeroErrori = 0;

                    foreach (var doc in docs)
                    {
                        this._logs.Info($"INSERIMENTO ALLEGATO {doc.Filename}");
                        var response = ws.Service.InsertAttchmentEx(guidCard, token, new oAttachmentCard[] { doc });

                        if (response.ErrNumber != 0)
                        {
                            this._logs.Error($"ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {response.ErrNumber}, DESCRIZIONE ERRORE: {response.ErrDescription}");
                            numeroErrori++;
                        }
                    }

                    if (numeroErrori > 0)
                    {
                        this._logs.Warn($"INSERIMENTO ALLEGATI AVVENUTO CON WARNINGS, {numeroErrori} FILE NON SONO STATI INSERITI");
                    }
                    else
                    {
                        this._logs.InfoFormat("INSERIMENTO DEGLI ALLEGATI AVVENUTO CORRETTAMENTE");
                    }
                }
            }
            catch (Exception ex)
            {
                this._logs.WarnFormat(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DEGLI ALLEGATI, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
