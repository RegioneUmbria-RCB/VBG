using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.GeProt.Proxy;
using System.ServiceModel;
using Init.SIGePro.Data;
using System.IO;
using Microsoft.Web.Services2.Attachments;
using System.Net;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.GeProt.Services
{
    public class ProtocollazioneService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _urlWs;
        string _token;

        public ProtocollazioneService(ProtocolloLogs logs, ProtocolloSerializer serializer, string urlWs)
        {
            _logs = logs;
            _serializer = serializer;
            _urlWs = urlWs;
        }

        private ProtocolloGeProtService CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione GeProt");
                if (String.IsNullOrEmpty(_urlWs))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_GEPROT NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new ProtocolloGeProtService(_urlWs);

                if (new Uri(_urlWs).Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };

                _logs.Debug("Fine creazione del webservice GEPROT");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE {0}", ex.Message), ex);
            }
        }

        public void Login(string username, string password)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("Login al web service, username: {0}, password: {1}", username, password);
                    var response = ws.doLogin(username, password);

                    if (String.IsNullOrEmpty(response))
                        throw new Exception("IL TOKEN RESTITUITO E' VUOTO!!");

                    _logs.InfoFormat("Login al web service avvenuto correttamente, token restituito: {0}", response);

                    _token = response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LOGIN AL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        public void Logout()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("Logout al web service di protocollazione, token: {0}", _token);
                    ws.doLogout(_token);
                    _logs.Info("Logout al web service di protocollazione effettuato con successo");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL LOGOUT DELL'APPLICATIVO HA GENERATO UN ERRORE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void InserisciAllegato(ProtocolloAllegati all, ProtocolloGeProtService ws)
        {
            try
            {
                if (String.IsNullOrEmpty(all.NOMEFILE))
                    throw new Exception(String.Format("IL NOME FILE DELL'ALLEGATO CON CODICE OGGETTO: {0}, NON E' VALORIZZATO", all.CODICEOGGETTO));

                if (all.OGGETTO == null)
                    throw new Exception(String.Format("IL BUFFER DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1} E' NULL", all.CODICEOGGETTO, all.NOMEFILE));

                if (String.IsNullOrEmpty(all.MimeType))
                    throw new Exception(String.Format("IL CONTENT TYPE DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1}, NON E' VALORIZZATO", all.CODICEOGGETTO, all.NOMEFILE));

                string path = Path.Combine(_logs.Folder, all.NOMEFILE);
                
                File.WriteAllBytes(path, all.OGGETTO);

                _logs.InfoFormat("SALVATO IL FILE {0}, CODICE ALLEGATO {1}", all.NOMEFILE, all.CODICEOGGETTO);

                var attachment = new Attachment(Path.GetFileName(path), all.MimeType, path);
                ws.RequestSoapContext.Attachments.Add(attachment);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ALLEGATO {0}, CODICE {1}, ERRORE: {2}", all.CODICEOGGETTO, all.NOMEFILE, ex.Message), ex);
            }
        }

        public string[] Protocolla(string segnatura, List<ProtocolloAllegati> allegati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    allegati.ForEach(x => InserisciAllegato(x, ws));

                    _logs.InfoFormat("CHIAMATA A PROTOCOLLA, token: {0}, segnatura: {1}", _token, segnatura);
                    var response = ws.protocolla(_token, segnatura);
                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, DATI RESTITUITI: {0}", String.Join("|", response));
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void InviaPec(string[] rifProto)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A INVIO PEC (metodo inviaEmail), token: {0}, numero protocollo: {1}, anno protocollo: {2}, codice amministrazione: {3}, codice aoo: {4}", _token, rifProto[3], rifProto[2], rifProto[0], rifProto[1]);
                    ws.inviaMail(_token, rifProto);
                    _logs.InfoFormat("INVIO PEC AVVENUTO CON SUCCESSO");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'INVIO PEC, ERRORE: {0}", ex.Message), ex);
            }
        }

        public string[] CreaFascicolo(string[] dati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A CREA FASCICOLO, token: {0}, dati: {1}", _token, String.Join("|", dati));
                    var response = ws.creaFascicolo(_token, dati);
                    _logs.InfoFormat("CREAZIONE FASCICOLO AVVENUTA CON SUCCESSO, DATI RESTITUITI {0}", String.Join("|", response));

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CREAZIONE FASCICOLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void FascicolaProtocollo(string[] datiProtocollo, string[] datiFascicolo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A FASCICOLAZIONE DI UN PROTOCOLLO, token: {0}, dati protocollo: {1}, dati fascicolo: {2}", _token, String.Join("|", datiProtocollo), String.Join("|", datiFascicolo));
                    ws.fascicolaRegistrazione(_token, datiProtocollo, datiFascicolo);
                    _logs.InfoFormat("FASCICOLAZIONE DI UN PROTOCOLLO AVVENUTA CON SUCCESSO");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN PROTOCOLLO, ERRORE: {0}", ex.Message));
            }
        }

        public string[] LeggiFascicolo(string[] datiProtocollo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA LEGGI FASCICOLO (getInfoFascicolo), token: {0}, dati protocollo: {1}", _token, String.Join("|", datiProtocollo));
                    var response = ws.getFascicoliRegistrazione(_token, datiProtocollo);
                    _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO (getInfoFascicolo) AVVENUTA CON SUCCESSO, DATI RESTITUITI: {0}", String.Join("|", response));

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DI UN FASCICOLO, ERRORE: {0}", ex.Message));
            }
        }
    }
}
