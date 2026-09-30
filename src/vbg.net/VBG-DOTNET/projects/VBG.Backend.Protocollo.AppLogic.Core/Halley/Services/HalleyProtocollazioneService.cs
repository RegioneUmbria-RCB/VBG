using HalleyProtoService;
using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Adapters;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders.Errors;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Services
{
    public class HalleyProtocollazioneService
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly string _endPointAddress;
        private readonly string _proxy;
        private readonly ClientProtocollazioneServiceCreator _clientProtocollazioneServiceCreator;

        public HalleyProtocollazioneService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string proxy)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._endPointAddress = endPointAddress;
            this._proxy = proxy;
            this._clientProtocollazioneServiceCreator = new ClientProtocollazioneServiceCreator(logs, bindingFactory, endPointAddress, proxy);
        }

        internal string Login(string codiceEnte, string username, string password)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    this._logs.InfoFormat("Chiamata a Login del web service, codice ente: {0}, username: {1}, password: {2}", codiceEnte, username, password);
                    var response = ws.Service.Login(codiceEnte, username, password);
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

                            string nomeFile = oggetti.NOMEFILE;
                            //string codiceOggetto = oggetti.CODICEOGGETTO;
                            string mimeType = oggettiMgr.GetContentType(oggetti.NOMEFILE);


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
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
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

                        File.WriteAllBytes(Path.Combine(this._logs.Folder, all.NOMEFILE), all.OGGETTO);

                        var response = ws.Service.Inserimento(userName, token, all.NOMEFILE, Convert.ToBase64String(all.OGGETTO));

                        if (response.lngErrNumber != 0)
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD INSERIMENTO, ERRORE CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.lngErrNumber.ToString(), response.strErrString, all.NOMEFILE, all.CODICEOGGETTO));

                        this._logs.InfoFormat("INSERIMENTO DEL FILE: {0}, CODICE OGGETTO: {1} AVVENUTO CORRETTAMENTE, ID RESTITUITO: {2}", all.NOMEFILE, all.CODICEOGGETTO, response.lngDocID.ToString());
                        all.ID = response.lngDocID;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'UPLOAD DEL FILE, {0}", ex.Message), ex);
            }
        }

        internal ProtocollazioneRet Protocollazione(string userName, string token, HalleySegnaturaBuilder.SegnaturaRequest segnatura, bool inviaCf)
        {
            using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
            {
                this._logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, username: {1}, dati protocollo: {2}", token, userName, ProtocolloLogsConstants.SegnaturaXmlFileName);

                var response = ws.Service.Protocollazione(userName, token, segnatura.SegnaturaString);

                this._serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                if (response.lngErrNumber != 0)
                {
                    var errori = new HalleyErroriProtocollazioneResponseAdapter();
                    var errore = errori.Adatta(response.lngErrNumber.ToString());

                    var e = ErroriFactory.Create(errore.Key, segnatura.Segnatura, this._serializer);
                    if (e != null && inviaCf)
                    {
                        this._logs.InfoFormat("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}, IL SISTEMA CERCHERA' DI PROTOCOLLARE MODIFICANDO IL MITTENTE / DESTINATARIO", errore.Key, errore.Value);

                        var segn = e.GetSegnatura();
                        return this.Protocollazione(userName, token, segn, false);
                    }

                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", errore.Key, errore.Value));
                }

                this._logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.lngNumPG.ToString(), response.strDataPG, response.lngAnnoPG.ToString());
                return response;
            }
        }
    }
}
