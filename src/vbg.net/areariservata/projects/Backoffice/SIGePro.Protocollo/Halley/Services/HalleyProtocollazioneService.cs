using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;
using System.IO;
using Microsoft.Web.Services2.Attachments;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.HalleyProtoService;
using Init.SIGePro.Protocollo.Halley.Adapters;
using Init.SIGePro.Protocollo.Halley.Builders;
using Init.SIGePro.Protocollo.Halley.Builders.Errors;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Halley.Services
{
    public class HalleyProtocollazioneService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;
        string _proxy;

        public HalleyProtocollazioneService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, string proxy)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPointAddress;
            _proxy = proxy;
        }

        private DOCAREAProtoSoapClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione Halley");
                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_HALLEY NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");


                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("HalleyProtoSoap");

                if (!String.IsNullOrEmpty(_proxy))
                {
                    binding.UseDefaultWebProxy = false;
                    binding.ProxyAddress = new Uri(_proxy);
                }

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                {
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
                }

                var ws = new DOCAREAProtoSoapClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del webservice HALLEY");

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
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("Chiamata a Login del web service, codice ente: {0}, username: {1}, password: {2}", codiceEnte, username, password);
                    var response = ws.Login(codiceEnte, username, password);
                    if (response.lngErrNumber != 0)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    if (String.IsNullOrEmpty(response.strDST))
                        throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");

                    _logs.InfoFormat("Autenticazione al web service avvenuta correttamente, token restituito: {0}", response.strDST);

                    return response.strDST;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE {0}", ex.Message), ex);
            }
        }

        internal void InserisciAllegatiDaMovimentoAvvio(Istanze istanza, DataBase db, string idComune, List<ProtocolloAllegati> listAllegati)
        {
            try
            {
                if (istanza != null)
                {
                    List<Movimenti> listMovAvvio = new MovimentiMgr(db).GetList(new Movimenti
                    {
                        IDCOMUNE = idComune,
                        CODICEISTANZA = istanza.CODICEISTANZA,
                        TIPOMOVIMENTO = istanza.TIPOMOVAVVIO
                    });

                    foreach (var movAvvio in listMovAvvio)
                    {
                        List<TipiMovimentoDocTipo> listTipiMovDocTipo = new TipiMovimentoDocTipoMgr(db).GetList(new TipiMovimentoDocTipo
                        {
                            IDCOMUNE = idComune,
                            TIPOMOVIMENTO = movAvvio.CODICEMOVIMENTO
                        });

                        List<MovimentiAllegati> listMovAllegati = new MovimentiAllegatiMgr(db).GetList(new MovimentiAllegati
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
                            string codiceOggetto = oggetti.CODICEOGGETTO;
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
                using (var ws = CreaWebService())
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

                        File.WriteAllBytes(Path.Combine(_logs.Folder, all.NOMEFILE), all.OGGETTO);

                        var response = ws.Inserimento(userName, token, all.NOMEFILE, Convert.ToBase64String(all.OGGETTO));

                        if (response.lngErrNumber != 0)
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD INSERIMENTO, ERRORE CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.lngErrNumber.ToString(), response.strErrString, all.NOMEFILE, all.CODICEOGGETTO));

                        _logs.InfoFormat("INSERIMENTO DEL FILE: {0}, CODICE OGGETTO: {1} AVVENUTO CORRETTAMENTE, ID RESTITUITO: {2}", all.NOMEFILE, all.CODICEOGGETTO, response.lngDocID.ToString());
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
            using (var ws = CreaWebService())
            {
                _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, username: {1}, dati protocollo: {2}", token, userName, ProtocolloLogsConstants.SegnaturaXmlFileName);

                var response = ws.Protocollazione(userName, token, segnatura.SegnaturaString);
                
                _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                if (response.lngErrNumber != 0)
                {
                    var errori = new HalleyErroriProtocollazioneResponseAdapter();
                    var errore = errori.Adatta(response.lngErrNumber.ToString());

                    var e = ErroriFactory.Create(errore.Key, segnatura.Segnatura, _serializer);
                    if (e != null && inviaCf)
                    {
                        _logs.InfoFormat("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}, IL SISTEMA CERCHERA' DI PROTOCOLLARE MODIFICANDO IL MITTENTE / DESTINATARIO", errore.Key, errore.Value);

                        var segn = e.GetSegnatura();
                        return Protocollazione(userName, token, segn, false);
                    }

                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", errore.Key, errore.Value));
                }

                _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.lngNumPG.ToString(), response.strDataPG, response.lngAnnoPG.ToString());
                return response;
            }
        }
    }
}
