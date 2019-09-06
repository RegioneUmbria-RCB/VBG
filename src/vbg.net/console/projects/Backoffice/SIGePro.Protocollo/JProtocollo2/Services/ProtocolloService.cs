using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;
using System.IO;

namespace Init.SIGePro.Protocollo.JProtocollo2.Services
{
    public class ProtocolloService
    {
        private static class Constants
        {
            public const string ESITO_OK = "OK";
            public const string ESITO_KO = "KO";
        }

        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _url;

        public ProtocolloService(ProtocolloLogs logs, ProtocolloSerializer serializer, string url)
        {
            _logs = logs;
            _serializer = serializer;
            _url = url;
        }

        private JProtocolloServicesService CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione JProtocollo2");
                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_JPROTOCOLLO2 NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new JProtocolloServicesService(_url);

                _logs.Debug("Fine creazione del webservice JPROTOCOLLO2");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public leggiProtocolloResponseRispostaLeggiProtocollo LeggiProtocollo(leggiProtocolloRichiestaLeggiProtocollo request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);

                    _logs.InfoFormat("Chiamata a Leggi Protocollo, numero: {0}, anno: {1}", request.riferimento.numero, request.riferimento.anno);

                    var response = ws.leggiProtocollo(request);

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

                    if (response.esito == Constants.ESITO_KO)
                        throw new Exception(String.Format("MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO2: {0}", response.messaggio));

                    _logs.Info("CHIAMATA A LEGGI PROTOCOLLO AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE HA RESTITUITO UN ERRORE SULLA CHIAMATA A LEGGIPROTOCOLLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        /// <summary>
        /// Il parametro codice oggetto serve solo per i log.
        /// </summary>
        /// <param name="request"></param>
        /// <param name="codiceOggetto"></param>
        public void InserisciDocumento(allegaDocumentoRichiestaAllegaDocumento request, string codiceOggetto)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    
                    _logs.InfoFormat("Chiamata a InserisciDocumento del file codice: {0}, nome file: {1} sul protocollo numero: {2} anno: {3}", codiceOggetto, request.documento.nomeFile, request.riferimento.numero, request.riferimento.anno);

                    var response = ws.allegaDocumento(request);

                    if (response.esito == Constants.ESITO_KO)
                        throw new Exception(response.messaggio);
                    
                    _logs.Info("INSERIMENTO FILE AVVENUTO CON SUCCESSO");

                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("Non e' stato possibile inserire il file {0} nel protocollo, errore: {1}; ", request.documento.nomeFile, ex.Message);
            }
        }

        public leggiAllegatoResponseRispostaLeggiAllegato LeggiAllegato(string progressivo, string annoProtocollo, string numeroProtocollo, string username)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("Chiamata a Leggi Allegato protocollo numero: {0}, anno: {1}, progressivo file: {2}, username: {3}", numeroProtocollo, annoProtocollo, progressivo, username);
                    var response = ws.leggiAllegato(new leggiAllegatoRichiestaLeggiAllegato
                    {
                        progressivo = progressivo,
                        riferimento = new riferimento { anno = annoProtocollo, numero = numeroProtocollo },
                        username = username
                    });

                    if (response.esito == Constants.ESITO_KO)
                        throw new Exception(String.Format("MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO2: {0}", response.messaggio));

                    _logs.Info("LETTURA ALLEGATO AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE HA RESTITUITO UN ERRORE SULLA CHIAMATA A LEGGI ALLEGATO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public inserisciArrivoResponseRispostaProtocolla ProtocollaArrivo(inserisciArrivoRichiestaProtocollaArrivo request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.Info("Chiamata a Protocollazione Arrivo");
                    var response = ws.inserisciArrivo(request);
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.esito == Constants.ESITO_KO)
                        throw new Exception(String.Format("MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO2: {0}", response.messaggio));

                    _logs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE HA RESTITUITO UN ERRORE SULLA CHIAMATA A INSERISCIARRIVO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public inserisciPartenzaResponseRispostaProtocolla ProtocollaPartenza(inserisciPartenzaRichiestaProtocollaPartenza request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.Info("Chiamata a Protocollazione Partenza");
                    var response = ws.inserisciPartenza(request);
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.esito == Constants.ESITO_KO)
                        throw new Exception(String.Format("MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO2: {0}", response.messaggio));

                    _logs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE HA RESTITUITO UN ERRORE SULLA CHIAMATA A INSERISCIPARTENZA, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void InviaPEC(inviaProtocolloRichiestaInviaProtocollo request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, request);
                    _logs.InfoFormat("CHIAMATA A INVIA PEC (metodo inviaProtocollo) DEL PROTOCOLLO NUMERO: {0}, ANNO: {1}", request.riferimento.numero, request.riferimento.numero);
                    var response = ws.inviaProtocollo(request);
                    _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecResponseFileName, response);
                    if (response.esito == Constants.ESITO_KO)
                        throw new Exception(response.messaggio);

                    _logs.InfoFormat("CHIAMATA A INVIA PEC (metodo inviaProtocollo) DEL PROTOCOLLO NUMERO: {0}, ANNO: {1} AVVENUTA CON SUCCESSO", request.riferimento.numero, request.riferimento.numero);
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE L'INVIO DI UNA PEC DAL PROTOCOLLO, ERRORE: {0}", ex.Message);
            }
        }

        public inserisciInternoResponseRispostaProtocolla ProtocollaInterno(inserisciInternoRichiestaProtocollaInterno request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.Info("Chiamata a Protocollazione Interno");
                    var response = ws.inserisciInterno(request);
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.esito == Constants.ESITO_KO)
                        throw new Exception(String.Format("MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO2: {0}", response.messaggio));

                    _logs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE HA RESTITUITO UN ERRORE SULLA CHIAMATA A INSERISCIINTERNO, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
