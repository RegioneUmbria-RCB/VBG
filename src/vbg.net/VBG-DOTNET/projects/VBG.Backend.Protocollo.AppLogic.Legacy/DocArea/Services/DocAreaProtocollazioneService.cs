using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Configuration.Utils;
using Microsoft.Web.Services2.Attachments;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Services
{
    public class DocAreaProtocollazioneService
    {
        protected ProtocolloLogs _logs;
        protected ProtocolloSerializer _serializer;
        protected string _endPointAddress;

        public DocAreaProtocollazioneService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._endPointAddress = endPointAddress;
        }

        protected DocAreaProxy CreaWebService()
        {
            try
            {
                this._logs.Debug("Creazione del webservice di protocollazione DocArea");
                if (String.IsNullOrEmpty(this._endPointAddress))
                    throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCAREA NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new DocAreaProxy { Url = this._endPointAddress };

                var endPointAddress = new EndpointAddress(this._endPointAddress);

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                {
                    //ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
                    new Tls12Utils().ApplicaImpostazioniTls12(endPointAddress.Uri.AbsoluteUri);
                    ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
                }


                this._logs.Debug("Fine creazione del webservice DOCAREA");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        internal string Login(string codiceEnte, string username, string password)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    this._logs.InfoFormat("Chiamata a Login del web service, codice ente: {0}, username: {1}, password: {2}", codiceEnte, username, password);
                    var response = ws.Login(codiceEnte, username, password);
                    if (response.lngErrNumber != 0)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    if (String.IsNullOrEmpty(response.strDST))
                        throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");

                    this._logs.InfoFormat("Autenticazione al web service avvenuta correttamente, token restituito: {0}", response.strDST);

                    return response.strDST;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE {0}", ex.Message), ex);
            }
        }

        internal void InserisciAllegatiDaMovimentoAvvio(IIstanzaDaProtocollare istanza, DataBase db, string idComune, List<ProtocolloAllegati> listAllegati)
        {
            try
            {
                if (istanza != null)
                {
                    var listMovAvvio = new MovimentiMgr(db).GetList(new Movimenti
                    {
                        IDCOMUNE = idComune,
                        CODICEISTANZA = istanza.CODICEISTANZA,
                        TIPOMOVIMENTO = istanza.TIPOMOVAVVIO
                    });

                    foreach (var movAvvio in listMovAvvio)
                    {
                        //var listTipiMovDocTipo = new TipiMovimentoDocTipoMgr(db).GetList(new TipiMovimentoDocTipo
                        //{
                        //    IDCOMUNE = idComune,
                        //    TIPOMOVIMENTO = movAvvio.CODICEMOVIMENTO
                        //});

                        var listMovAllegati = new MovimentiAllegatiMgr(db).GetList(new MovimentiAllegati
                        {
                            IDCOMUNE = idComune,
                            CODICEMOVIMENTO = movAvvio.CODICEMOVIMENTO
                        });

                        foreach (var movAllegati in listMovAllegati)
                        {
                            var oggettiMgr = new OggettiMgr(db);
                            var oggetti = oggettiMgr.GetById(idComune, Convert.ToInt32(movAllegati.CODICEOGGETTO));

                            if (oggetti == null) return;

                            var nomeFile = oggetti.NOMEFILE;
                            //var codiceOggetto = oggetti.CODICEOGGETTO;
                            var mimeType = oggettiMgr.GetContentType(oggetti.NOMEFILE);

                            listAllegati.Add(new ProtocolloAllegati
                            {
                                NOMEFILE = nomeFile,
                                IDCOMUNE = idComune,
                                OGGETTO = oggetti.OGGETTO,
                                Descrizione = movAllegati.DESCRIZIONE,
                                MimeType = mimeType
                            });
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("PROBLEMA DURANTE L'INSERIMENTO DELL'ALLEGATO DEL MOVIMENTO DI AVVIO, {0}", ex.Message), ex);
            }
        }

        internal void InserisciAllegati(List<ProtocolloAllegati> listAllegati, string token, string userName, bool inviaComeAttachmentID, bool isAbilitatoWarnVerificaFirma)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    if (listAllegati.Count == 0)
                        throw new Exception("NON SONO PRESENTI FILES ALLEGATI");

                    var filesToRemove = new List<ProtocolloAllegati>();

                    foreach (var all in listAllegati)
                    {
                        if (String.IsNullOrEmpty(all.NOMEFILE))
                            throw new Exception(String.Format("IL NOME FILE DELL'ALLEGATO CON CODICE OGGETTO: {0}, NON E' VALORIZZATO", all.CODICEOGGETTO));

                        if (all.OGGETTO == null)
                            throw new Exception(String.Format("IL BUFFER DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1} E' NULL", all.CODICEOGGETTO, all.NOMEFILE));

                        if (String.IsNullOrEmpty(all.MimeType))
                            throw new Exception(String.Format("IL CONTENT TYPE DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1}, NON E' VALORIZZATO", all.CODICEOGGETTO, all.NOMEFILE));

                        if (this._logs.IsDebugEnabled)
                        {
                            var path = Path.Combine(this._logs.Folder, all.NOMEFILE);
                            File.WriteAllBytes(path, all.OGGETTO);
                            this._logs.InfoFormat("SALVATO IL FILE {0}, CODICE ALLEGATO {1}", all.NOMEFILE, all.CODICEOGGETTO);
                        }

                        using (var stream = new MemoryStream(all.OGGETTO))
                        {
                            var attachment = inviaComeAttachmentID ? new Attachment(all.NOMEFILE, all.MimeType, stream) : new Attachment(all.MimeType, stream);

                            ws.RequestSoapContext.Attachments.Add(attachment);
                            var response = ws.Inserimento(userName, token);

                            if (response.lngErrNumber != 0)
                            {
                                //Inserito per poter protocollare anche quando la verifica della firma del ws non viene superata (succede spesso al Comune di Lucca).
                                if (isAbilitatoWarnVerificaFirma && response.lngErrNumber == -51)
                                {
                                    this._logs.WarnFormat("VERIFICA FIRMA DEL FILE {0} NON SUPERATA, IL FILE NON SARA' INVIATO AL PROTOCOLLO", all.NOMEFILE);
                                    filesToRemove.Add(all);
                                }
                                else
                                {
                                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD INSERIMENTO, ERRORE CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.lngErrNumber.ToString(), response.strErrString, all.NOMEFILE, all.CODICEOGGETTO));
                                }
                            }
                            else
                            {
                                this._logs.InfoFormat("Inserimento del file: {0}, codice oggetto: {1} avvenuto correttamente, ID restituito: {2}", all.NOMEFILE, all.CODICEOGGETTO, response.lngDocID.ToString());
                                all.ID = response.lngDocID;
                            }
                        }
                    }

                    filesToRemove.ForEach(x => listAllegati.Remove(x));
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'UPLOAD DEL FILE, {0}", ex.Message), ex);
            }
        }

        private void AllegaSegnatura(DocAreaProxy ws)
        {
            var pathSegnatura = Path.Combine(this._logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);
            var attachment = new Attachment("text/xml", pathSegnatura);

            this._logs.Info("Attachment del file segnatura.xml");
            ws.RequestSoapContext.Attachments.Add(attachment);
        }

        internal virtual ProtocollazioneRet Protocollazione(string userName, string token)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    this.AllegaSegnatura(ws);
                    this._logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, username: {1}, dati protocollo: {2}", token, userName, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    var response = ws.Protocollazione(userName, token);
                    this._serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.lngErrNumber != 0)
                    {
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));
                    }

                    this._logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.lngNumPG.ToString(), response.strDataPG, response.lngAnnoPG.ToString());
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE {0}", ex.Message), ex);
            }
        }
    }
}
