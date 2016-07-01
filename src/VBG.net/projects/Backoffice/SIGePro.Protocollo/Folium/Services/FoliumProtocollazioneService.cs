using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using System.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Protocollo;

namespace Init.SIGePro.Protocollo.Folium.Services
{
    public class FoliumProtocollazioneService
    {
        private static class ConstantsEsito
        {
            public const string SENZA_ERRORI = "000";
            public const string ERRORE_LOGIN = "107";
            public const string ERRORE_INTERNO = "108";
            public const string NESSUN_ALLEGATO = "001";
        }

        string _urlWs;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _bindingName;
        WSAuthentication _authentication;

        public FoliumProtocollazioneService(string urlWs, string bindingName, WSAuthentication authentication, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _urlWs = urlWs;
            _logs = logs;
            _serializer = serializer;
            _bindingName = bindingName;
            _authentication = authentication;
        }

        private ProtocolloWebServiceClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione Folium");
                if (String.IsNullOrEmpty(_urlWs))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_FOLIUM NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                if (String.IsNullOrEmpty(_bindingName))
                    _bindingName = "defaultHttpBinding";

                var endPointAddress = new EndpointAddress(_urlWs);
                var binding = new BasicHttpBinding(_bindingName);

                var ws = new ProtocolloWebServiceClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del webservice FOLIUM");

                _logs.InfoFormat("AUTENTICAZIONE, username: {0}, password: {1}, codice ente: {2}, applicativo: {3}, aoo: {4}", _authentication.username, _authentication.password, _authentication.ente, _authentication.applicazione, _authentication.aoo);

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }

        }

        /// <summary>
        /// Questo metodo consente di inserire gli allegati secondari in quanto quello primario (il primo della lista) viene inviato contestualmente alla protocollazione.
        /// </summary>
        internal void InsertAllegati(List<ProtocolloFoliumService.Allegato> allegati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    foreach (var allegato in allegati)
                    {
                        _logs.InfoFormat("CHIAMATA A inserisciAllegato, ID PROTOCOLLO: {0}, NOME FILE: {1}, DESCRIZIONE FILE: {2}", allegato.idProfilo.Value.ToString(), allegato.nomeFile, allegato.descrizione);
                        ws.inserisciAllegato(_authentication, allegato);
                        _logs.InfoFormat("INSERIMENTO ALLEGATO AVVENUTO CON SUCCESSO");
                    }
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("L'INSERIMENTO DEGLI ALLEGATI HA GENERATO IL SEGUENTE ERRORE {0}, IL PROTOCOLLO E' STATO COMUNQUE INSERITO", ex.Message);
            }
        }

        internal DocumentoProtocollato LeggiProtocollo(Ricerca request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A RICERCAPROTOCOLLI, NUMERO: {0}, DATA DA: {1}, DATA A: {2}", request.numeroDa, request.dataProtocolloDa.Value.ToString("dd/MM/yyyy"), request.dataProtocolloA.Value.ToString("dd/MM/yyyy"));
                    var response = ws.ricercaProtocolli(_authentication, request);

                    if (response.Length == 0)
                        throw new Exception(String.Format("LA RICERCA DEL PROTOCOLLO NUMERO {0} DATA DA: {1}, DATA A: {2}", request.numeroDa, request.dataProtocolloDa.Value.ToString("dd/MM/yyyy"), request.dataProtocolloA.Value.ToString("dd/MM/yyyy")));

                    if (response[0].esito.codiceEsito != ConstantsEsito.SENZA_ERRORI)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response[0].esito.codiceEsito, response[0].esito.descrizioneEsito));

                    if (response.Length > 1)
                    {
                        _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
                        throw new Exception(String.Format("LA RICERCA DEL PROTOCOLLO NUMERO {0} DATA DA {1}, DATA A {2} HA PRODOTTO PIU' DI UN RISULTATO", request.numeroDa, request.dataProtocolloDa.Value.ToString("dd/MM/yyyy"), request.dataProtocolloA.Value.ToString("dd/MM/yyyy")));
                    }

                    _logs.InfoFormat("CHIAMATA A RICERCAPROTOCOLLI TERMINATA CON SUCCESSO");

                    return response[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CHIAMATA AL METODO LEGGIPROTOCOLLO DEL WEB SERVICE", ex);
            }
        }

        internal DocumentoProtocollato Protocolla(DocumentoProtocollato request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLA");

                    var response = ws.protocolla(_authentication, request);

                    if (response == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE E' NULL");

                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.esito == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE NON HA VALORIZZATO L'ESITO");

                    if (response.esito.codiceEsito != ConstantsEsito.SENZA_ERRORI)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.esito.codiceEsito, response.esito.descrizioneEsito));

                    _logs.InfoFormat("PROTOCOLLO CREATO CON SUCCESSO, NUMERO PROTOCOLLO: {0}, DATA PROTOCOLLO: {1}", response.numeroProtocollo, response.dataProtocollo.Value.ToString("dd/MM/yyyy"));

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CHIAMATA AL METODO PROTOCOLLA DEL WEB SERVICE", ex);
            } 
        }

        internal ProtocolloFoliumService.Assegnazione Assegna(ProtocolloFoliumService.Assegnazione assegnazione)
        {
            var response = new ProtocolloFoliumService.Assegnazione();

            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.Info("CHIAMATA AL WEB METHOD ASSEGNA");
                    _serializer.Serialize(ProtocolloLogsConstants.AssegnazioneRequest, assegnazione);
                    response = ws.assegna(_authentication, assegnazione);

                    if (response == null)
                        throw new ProtocolloRemotoException("LA RISPOSTA E' NULL");

                    if (response.esito.codiceEsito != ConstantsEsito.SENZA_ERRORI)
                        throw new ProtocolloRemotoException(String.Format("CODICE: {0}, DESCRIZIONE: {1}", response.esito.codiceEsito, response.esito.descrizioneEsito));


                    _serializer.Serialize(ProtocolloLogsConstants.AssegnazioneResponse, response);

                    _logs.InfoFormat("CHIAMATA AL WEB METHOD ASSEGNA AVVENUTA CON SUCCESSO");
                }
            }
            catch (ProtocolloRemotoException prex)
            {
                _logs.WarnFormat("DURANTE L'ASSEGNAZIONE IL WEB SERVICE HA RESTITUITO IL SEGUENTE ERRORE: {0}, IL PROTOCOLLO E' STATO COMUNQUE INSERITO", prex.Message);
            }
            catch (Exception ex)
            {
                _logs.WarnFormat(ex.ToString());
            }

            return response;
        }

        internal VoceTitolario[] GetClassifiche()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.Info("CHIAMATA A GETIMMAGINEDOCUMENTALE");
                    var response = ws.ricercaTitolarioPerCodiceDescrizione(_authentication, new VoceTitolario());

                    if (response == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE E' NULL");

                    if (response[0].esito.codiceEsito != ConstantsEsito.SENZA_ERRORI)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response[0].esito.codiceEsito, response[0].esito.descrizioneEsito));


                    _logs.InfoFormat("CHIAMATA A GETIMMAGINEDOCUMENTALE AVVENUTA CON SUCCESSO, NUMERO VOCI DI TITOLARIO RESTITUITE: {0}", response.Length);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE RESTITUITO DURANTE LA CHIAMATA A RECUPERAALLEGATO DEL WEB SERVICE, RIFERITA AL RECUPERO DEGLI ALLEGATI DEL PROTOCOLLO", ex);
            }
        }

        internal ProtocolloFoliumService.Allegato GetAllegato(long idAllegato)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A GETALLEGATO ID ALLEGATO: {0}", idAllegato);
                    var response = ws.getAllegato(_authentication, idAllegato);

                    if (response == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE E' NULL");

                    if (response.esito.codiceEsito != ConstantsEsito.SENZA_ERRORI)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.esito.codiceEsito, response.esito.descrizioneEsito));

                    _logs.InfoFormat("CHIAMATA A GETALLEGATO AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE RESTITUITO DURANTE LA CHIAMATA A GETALLEGATO DEL WEB SERVICE, RIFERITA AL RECUPERO DEGLI ALLEGATI DEL PROTOCOLLO", ex);
            }
            
        }

        internal IEnumerable<ProtocolloFoliumService.Allegato> LeggiAllegati(long idProtocollo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A GETIMMAGINEDOCUMENTALE DEL PROTOCOLLO ID: {0}", idProtocollo.ToString());
                    var response = ws.getAllegati(_authentication, idProtocollo);

                    if (response == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE E' NULL");

                    if (response[0].esito.codiceEsito != ConstantsEsito.SENZA_ERRORI && response[0].esito.codiceEsito != ConstantsEsito.NESSUN_ALLEGATO)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response[0].esito.codiceEsito, response[0].esito.descrizioneEsito));

                    _logs.InfoFormat("CHIAMATA A GETIMMAGINEDOCUMENTALE AVVENUTA CON SUCCESSO, NUMERO ALLEGATI RESTITUITI: {0}", response.Length);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE RESTITUITO DURANTE LA CHIAMATA A RECUPERAALLEGATO DEL WEB SERVICE, RIFERITA AL RECUPERO DEGLI ALLEGATI DEL PROTOCOLLO", ex);
            }
        }
    }
}
