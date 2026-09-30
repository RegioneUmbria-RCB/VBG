using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Sigedo.Proxies;
using Init.SIGePro.Protocollo.Sigedo.Builders;
using System.Net;
using Init.SIGePro.Protocollo.Sigedo.Proxies.QueryService;

namespace Init.SIGePro.Protocollo.Sigedo.Services
{
    public class SigedoQueryService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;
        string _usernameWs;
        string _passwordWs;

        public static class Constants
        {

            public const string METADATI_XML_FILENAME = "Metadati.xml";

            public const string METADATI_ALLEGATI_REQUEST_FILENAME = "MetadatiAllegatiSoapRequest.xml";
            public const string METADATI_MITTENTI_DESTINATARI_REQUEST_FILENAME = "MetadatiMittentiDestinatariSoapRequest.xml";
            public const string METADATI_PROTOCOLLO_REQUEST_FILENAME = "MetadatiProtocolloSoapRequest.xml";
            
            public const string METADATI_SMISTAMENTI_REQUEST_FILENAME = "MetadatiSmistamentiSoapRequest.xml";

            public const string PROTOCOLLAZIONE_ALLEGATI_RESPONSE_FILENAME = "ProtocollazioneAllegatiSoapResponse.xml";
            public const string PROTOCOLLAZIONE_MITTENTI_DESTINATARI_RESPONSE_FILENAME = "ProtocollazioneMittentiDestinatariSoapResponse.xml";

            public const string PROTOCOLLAZIONE_CERCA_CLASSIFICA_REQUEST = "ProtocollazioneCercaClassificaRequest.xml";
            public const string PROTOCOLLAZIONE_CERCA_CLASSIFICA_RESPONSE = "ProtocollazioneCercaClassificaResponse.xml";
            public const string PROTOCOLLAZIONE_CERCA_SMISTAMENTO_RESPONSE = "ProtocollazioneCercaSmistamentoResponse.xml";

            public const string METADATO_IDRIF = "IDRIF";
            public const string METADATO_CLASSIFICA = "CLASS_COD";

        }

        public SigedoQueryService(ProtocolloLogs logs, ProtocolloSerializer serializer, string endPointAddress, string usernameWs, string passwordWs)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPointAddress;
            _usernameWs = usernameWs;
            _passwordWs = passwordWs;
        }

        private QueryServiceService CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice QueryService Sigedo");
                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new QueryServiceService
                {
                    Url = _endPointAddress,
                    Credentials = new NetworkCredential(_usernameWs, _passwordWs),
                    PreAuthenticate = true
                };

                _logs.Debug("Fine creazione del webservice SIGEDO");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }

        }

        public RisultatoRicerca LeggiMittentiDestinatari(string area, string modello, string stato, string utenteApplicativo, int operatore, string idRiferimento)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var metadati = new SigedoMetadatiRicercaBuilder(Constants.METADATO_IDRIF, operatore, idRiferimento);

                    if(_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.METADATI_MITTENTI_DESTINATARI_REQUEST_FILENAME, metadati.MetadatiRicerca);

                    _logs.InfoFormat("Chiamata a web method ricerca mittenti destinatari, area: {0}, modello: {1}, stato: {2}, utente applicativo: {3}, operatore: {4}, metadati (solo se il log è impostato a debug): {5}", area, modello, stato, utenteApplicativo, operatore.ToString(), Constants.METADATI_XML_FILENAME);
                    var response = ws.ricerca(area, modello, stato, utenteApplicativo, metadati.MetadatiRicerca);
                    _logs.Info("Chiamata a web method ricerca mittenti / destainatari effettuata con successo");
                    
                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.PROTOCOLLAZIONE_MITTENTI_DESTINATARI_RESPONSE_FILENAME, response);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEI DATI DEGLI SOGGETTI (MITTENTI / DESTINATARI), ERRORE: {0}", ex.Message), ex);
            }
        }

        public RisultatoRicerca LeggiAllegati(string area, string modello, string stato, string utenteApplicativo, int operatore, string idRiferimento)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var metadati = new SigedoMetadatiRicercaBuilder(Constants.METADATO_IDRIF, operatore, idRiferimento);
                    
                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.METADATI_ALLEGATI_REQUEST_FILENAME, metadati.MetadatiRicerca);

                    _logs.InfoFormat("Chiamata a web method ricerca allegati, area: {0}, modello: {1}, stato: {2}, utente applicativo: {3}, operatore: {4}, metadati (solo se il log è impostato a debug): {5}", area, modello, stato, utenteApplicativo, operatore.ToString(), Constants.METADATI_XML_FILENAME);
                    var response = ws.ricerca(area, modello, stato, utenteApplicativo, metadati.MetadatiRicerca);
                    _logs.Info("Chiamata a web method ricerca allegati secondati effettuata con successo");
                    
                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.PROTOCOLLAZIONE_ALLEGATI_RESPONSE_FILENAME, response);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEI DATI DEGLI ALLEGATI SECONDARI, ERRORE: {0}", ex.Message), ex);
            }
        }

        public RisultatoRicerca LeggiProtocollo(string area, string modello, string stato, string utenteApplicativo, int operatore, string numero, string anno)
        {
            try
            {
                using (var ws = CreaWebService())
                { 
                    var metadati = new SigedoMetadatiRicercaProtocolloBuilder(operatore, numero, anno);
                    
                    if(_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.METADATI_PROTOCOLLO_REQUEST_FILENAME, metadati.MetadatiRicerca);

                    _logs.InfoFormat("Chiamata a web method ricerca, area: {0}, modello: {1}, stato: {2}, utente applicativo: {3}, operatore: {4}, metadati (solo se il log è impostato a debug): {5}", area, modello, stato, utenteApplicativo, operatore.ToString(), Constants.METADATI_XML_FILENAME);
                    var response = ws.ricerca(area, modello, stato, utenteApplicativo, metadati.MetadatiRicerca);
                    _logs.Info("Chiamata a web method ricerca effettuata con successo");
                    
                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);
                    
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEI DATI DI PROTOCOLLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public RisultatoRicerca LeggiSmistamenti(string area, string modello, string stato, string utenteApplicativo, int operatore, string idRiferimento)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var metadati = new SigedoMetadatiRicercaBuilder(Constants.METADATO_IDRIF, operatore, idRiferimento);
                    
                    if(_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.METADATI_SMISTAMENTI_REQUEST_FILENAME, metadati.MetadatiRicerca);
                    
                    _logs.InfoFormat("Chiamata a web method ricerca, ricerca degli smistamenti , area: {0}, modello: {1}, stato: {2}, utente applicativo: {3}, operatore: {4}, metadati (solo se il log è impostato a debug): {5}", area, modello, stato, utenteApplicativo, operatore.ToString(), Constants.METADATI_SMISTAMENTI_REQUEST_FILENAME);
                    var response = ws.ricerca(area, modello, stato, utenteApplicativo, metadati.MetadatiRicerca);
                    _logs.Info("Chiamata a web method ricerca smistamenti effettuata con successo");
                    
                    if(_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.PROTOCOLLAZIONE_CERCA_SMISTAMENTO_RESPONSE, response);
                    
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEI DATI DI PROTOCOLLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public RisultatoRicerca LeggiClassifica(string area, string modello, string stato, int operatore, string codiceClassifica, string utenteApplicativo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var metadati = new SigedoMetadatiRicercaBuilder(Constants.METADATO_CLASSIFICA, operatore, codiceClassifica);

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.PROTOCOLLAZIONE_CERCA_CLASSIFICA_REQUEST, metadati.MetadatiRicerca);

                    _logs.InfoFormat("Chiamata a web method ricerca classifica, area: {0}, modello: {1}, stato: {2}, utente applicativo: {3}, operatore: {4}, metadati (solo se il log è impostato a debug): {5}", area, modello, stato, utenteApplicativo, operatore.ToString(), Constants.METADATI_XML_FILENAME);
                    var response = ws.ricerca(area, modello, stato, utenteApplicativo, metadati.MetadatiRicerca);
                    _logs.Info("Chiamata a web method ricerca classifica secondati effettuata con successo");

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.PROTOCOLLAZIONE_CERCA_CLASSIFICA_RESPONSE, response);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DELLA CLASSIFICA, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
