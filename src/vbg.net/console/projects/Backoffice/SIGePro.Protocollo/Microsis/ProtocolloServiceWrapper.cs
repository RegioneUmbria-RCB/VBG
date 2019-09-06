using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Microsis.Classifiche;
using Init.SIGePro.Protocollo.Microsis.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloMicrosisServiceProxy;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis
{
    public class ProtocolloServiceWrapper
    {
        string _url;
        string _username;
        string _password;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;

        public ProtocolloServiceWrapper(VerticalizzazioniWrapper vert, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _url = vert.Url;
            _username = vert.Username;
            _password = vert.Password;

            _logs = logs;
            _serializer = serializer;
        }

        private WS_ProtocolloSoapClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                return new WS_ProtocolloSoapClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public Inserisci_Protocollo ProtocollaArrivo(ProtocollazioneArrivoRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE ARRIVO, PARAMETRI: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                    var responseXml = ws.Inserisci_Protocollo(_username, _password, request.TipoDocumento, request.Oggetto, request.CodiceTitolario, request.Ufficio,
                                            request.DataDocumento, request.ModalitaTrasmissione, request.Riservatezza, request.Originale, request.PartitaIvaMittente,
                                            request.RagioneSocialeMittente, request.CodiceFiscaleMittente, request.NomeMittente, request.CognomeMittente, request.ProtocolloMittente,
                                            request.DataProtocolloMittente, request.Note, request.ServizioDestinatario, request.TipoDate);

                    try
                    {
                        var response = _serializer.Deserialize<Inserisci_Protocollo>(responseXml);
                        return response;
                    }
                    catch (Exception)
                    {
                        throw new Exception(responseXml);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL METODO INSERISCI_PROTOCOLLO DEL WEB SERVICE DI PROTOCOLLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public Lista_Titolario GetClassifiche()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.Info("CHIAMATA AL METODO Lista_Titolario");
                    var responseString = ws.Lista_Titolario(_username, _password);
                    _logs.InfoFormat("CHIAMATA AL METODO Lista_Titolario AVVENUTA CORRETTAMENTE, RISPOSTA: {0}", responseString);

                    try
                    {
                        var response = _serializer.Deserialize<Lista_Titolario>(responseString);
                        return response;
                    }
                    catch (Exception)
                    {
                        throw new Exception(responseString);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL METODO LISTA_TITOLARIO DEL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void TrasmettiAllegato(List<ProtocolloAllegati> request, string numeroProtocollo, string annoProtocollo)
        {
            using (var ws = CreaWebService())
            {
                string errori = "";
                foreach (var allegato in request)
                {
                    try
                    {
                        _logs.InfoFormat("TRASMISSIONE DELL'ALLEGATO AL SISTEMA DI PROTOCOLLO, PROTOCOLLO NUMERO: {0}, ANNO: {1}, FILE CODICE: {2}, NOME {3}, DIMENSIONI: {4} KB", numeroProtocollo, annoProtocollo, allegato.CODICEOGGETTO, allegato.NOMEFILE, Convert.ToInt32(allegato.OGGETTO.Length / 1024));
                        var responseAllegati = ws.Trasmetti_Allegato(allegato.OGGETTO, allegato.NOMEFILE, numeroProtocollo, annoProtocollo, _username, _password);
                        _logs.InfoFormat("RISPOSTA DAL WS {0}", responseAllegati);
                    }
                    catch (Exception ex)
                    {
                        errori += String.Format("TRASMISSIONE DELL'ALLEGATO NOME FILE {0} (CODICE {1}) AL SISTEMA DI PROTOCOLLO TRAMITE WEB SERVICE, ERRORE: {2}<br>", allegato.NOMEFILE, allegato.CODICEOGGETTO, ex.Message);
                    }
                }

                if (!String.IsNullOrEmpty(errori))
                {
                    _logs.WarnFormat("LA TRASMISSIONE DEGLI ALLEGATI HA GENERATO DEGLI ERRORI, NON TUTTI I FILE SONO STATI TRASMESSI AL PROTOCOLLO NUMERO: {0} ANNO: {1}<br>ERRORI:<br>{2}", numeroProtocollo, annoProtocollo, errori);
                }
            }
        }
    }
}

