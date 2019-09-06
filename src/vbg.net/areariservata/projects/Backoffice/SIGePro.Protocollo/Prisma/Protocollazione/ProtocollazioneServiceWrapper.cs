using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloPrismaService;
using System.ServiceModel;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public class ProtocollazioneServiceWrapper : DocAreaServiceWrapperBase
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;

        public ProtocollazioneServiceWrapper(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentials) : base(endPointAddress, logs, serializer, credentials)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._endPointAddress = endPointAddress;
        }

        public long InserimentoAllegato(ProtocolloAllegati all)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        if (String.IsNullOrEmpty(all.NOMEFILE))
                        {
                            throw new Exception(String.Format("IL NOME FILE DELL'ALLEGATO CON CODICE OGGETTO: {0}, NON E' VALORIZZATO", all.CODICEOGGETTO));
                        }

                        if (all.OGGETTO == null)
                        {
                            throw new Exception(String.Format("IL BUFFER DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1} E' NULL", all.CODICEOGGETTO, all.NOMEFILE));
                        }

                        if (String.IsNullOrEmpty(all.MimeType))
                        {
                            throw new Exception(String.Format("IL CONTENT TYPE DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1}, NON E' VALORIZZATO", all.CODICEOGGETTO, all.NOMEFILE));
                        }

                        this._logs.InfoFormat("CHIAMATA A INSERIMENTO DEL FILE {0}, CODICE ALLEGATO {1}", all.NOMEFILE, all.CODICEOGGETTO);
                        var response = ws.inserimento(base.Credentials.Username, base.Credentials.Token, all.OGGETTO);

                        if (response.lngErrNumber != 0)
                        {
                            throw new Exception(String.Format("ERRORE CODICE:{0}, DESCRIZIONE: {1}", response.lngErrNumber.ToString(), response.strErrString));
                        }

                        this._logs.InfoFormat("INSERIMENTO DEL FILE: {0}, CODICE OGGETTO: {1} AVVENUTO CORRETTAMENTE, ID RESTITUITO: {2}", all.NOMEFILE, all.CODICEOGGETTO, response.lngDocID.ToString());
                        return response.lngDocID;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'UPLOAD DEL FILE {0}, {1}", all.NOMEFILE, ex.Message), ex);
            }
        }

        public ProtocollazioneRet Protocolla(DocAreaSegnaturaInput request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, request);
                        var s = base.Serializer.SerializeToStream<DocAreaSegnaturaInput>(request);

                        this._logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, username: {1}, dati protocollo: {2}", base.Credentials.Token, base.Credentials.Username, ProtocolloLogsConstants.SegnaturaXmlFileName);
                        var response = ws.protocollazione(base.Credentials.Username, base.Credentials.Token, s.ToArray());
                        this._serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                        if (response.lngErrNumber != 0)
                        {
                            throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));
                        }

                        this._logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.lngNumPG.ToString(), response.strDataPG, response.lngAnnoPG.ToString());
                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE {0}", ex.Message), ex);
            }
        }
    }
}
