using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Microsoft.Web.Services2.Attachments;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.IO;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Services
{
    public class DocProProtocollazioneService
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly string _endPointAddress;

        public DocProProtocollazioneService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._endPointAddress = endPointAddress;
        }

        private ProxyProtDocPro CreaWebService()
        {
            try
            {
                this._logs.Debug("Creazione del webservice di protocollazione DocPro");
                if (String.IsNullOrEmpty(this._endPointAddress))
                    throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCPRO NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new ProxyProtDocPro { Url = this._endPointAddress };
                ws.Timeout = 600000;

                this._logs.Debug("Fine creazione del webservice DOCPRO");

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
                    var response = ws.LoginUser(codiceEnte, username, password);
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
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE, {0}", ex.Message), ex);
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
                        //List<TipiMovimentoDocTipo> listTipiMovDocTipo = new TipiMovimentoDocTipoMgr(db).GetList(new TipiMovimentoDocTipo
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
                            //string codiceOggetto = oggetti.CODICEOGGETTO;
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

        internal void InserisciAllegati(List<ProtocolloAllegati> listAllegati, string token, string userName)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    if (listAllegati.Count == 0)
                        throw new Exception("NON SONO PRESENTI FILES ALLEGATI");

                    foreach (var all in listAllegati)
                    {
                        if (String.IsNullOrEmpty(all.NOMEFILE))
                            throw new Exception(String.Format("IL NOME FILE DELL'ALLEGATO CON CODICE OGGETTO: {0}, NON E' VALORIZZATO", all.CODICEOGGETTO));

                        if (all.OGGETTO == null)
                            throw new Exception(String.Format("IL BUFFER DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1} E' NULL", all.CODICEOGGETTO, all.NOMEFILE));

                        if (String.IsNullOrEmpty(all.MimeType))
                            throw new Exception(String.Format("IL CONTENT TYPE DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1}, NON E' VALORIZZATO", all.CODICEOGGETTO, all.NOMEFILE));

                        var path = Path.Combine(this._logs.Folder, all.NOMEFILE);
                        File.WriteAllBytes(path, all.OGGETTO);
                        this._logs.InfoFormat("SALVATO IL FILE {0}, CODICE ALLEGATO {1}", all.NOMEFILE, all.CODICEOGGETTO);

                        var attachment = new Attachment(all.MimeType, path);

                        ws.RequestSoapContext.Attachments.Add(attachment);
                        var response = ws.Inserimento(userName, token);

                        if (response.lngErrNumber != 0)
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD INSERIMENTO, ERRORE CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.lngErrNumber.ToString(), response.strErrString, all.NOMEFILE, all.CODICEOGGETTO));

                        this._logs.InfoFormat("Inserimento del file: {0}, codice oggetto: {1} avvenuto correttamente, ID restituito: {2}", all.NOMEFILE, all.CODICEOGGETTO, response.lngDocId.ToString());
                        all.ID = response.lngDocId;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'UPLOAD DEL FILE, {0}", ex.Message), ex);
            }
        }

        private void AllegaSegnatura(ProxyProtDocPro ws)
        {
            var pathSegnatura = Path.Combine(this._logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);
            Attachment attachment = new Attachment("text/xml", pathSegnatura);

            this._logs.Info("Attachment del file segnatura.xml");
            ws.RequestSoapContext.Attachments.Add(attachment);
        }

        internal _ProtocollazioneResponse Protocollazione(string userName, string token)
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
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber, response.strErrString));

                    this._logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.lngNumPG.ToString(), response.strDataPG, response.lngAnnoPG.ToString());
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }
    }
}
