using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using System.ServiceModel;
using System.ServiceModel.Description;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.Net;

namespace Init.SIGePro.Protocollo.InsielMercato2.Services
{
    public class ProtocollazioneService
    {
        private string _url;
        private ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        user _utenteProtocollo;

        public ProtocollazioneService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, user utenteProtocollo)
        {
            _url = url;
            _logs = logs;
            _serializer = serializer;
            _utenteProtocollo = utenteProtocollo;
        }

        private ProtocolloServicePortTypeClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_url);

                var binding = new BasicHttpBinding("insielMercatoHttpBinding");

                var client = new ProtocolloServicePortTypeClient(binding, endPointAddress);

                foreach (var operation in client.Endpoint.Contract.Operations)
                {
                    var behavior = operation.Behaviors.Find<DataContractSerializerOperationBehavior>() as DataContractSerializerOperationBehavior;
                    if (behavior != null)
                        behavior.MaxItemsInObjectGraph = int.MaxValue;
                }

                ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };

                return client;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE", ex.Message), ex);
            }
        }

        public void AggiornaProtocolliCollegati(protocolUpdateRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.user = _utenteProtocollo;
                    _logs.InfoFormat("CHIAMATA A UPDATE PROTOCOLLO PER L'AGGIORNAMENTO DEI PROTOCOLLI COLLEGATI, NUMERO PROTOCOLLO: {0}, ANNO PROTOCOLLO: {1}", request.recordIdentifier.number, request.recordIdentifier.year);
                    _serializer.Serialize(ProtocolloLogsConstants.UpdateProtocolloRequestFileName, request);
                    
                    var response = ws.protocolUpdate(request);
                    _serializer.Serialize(ProtocolloLogsConstants.UpdateProtocolloResponseFileName, response);

                    if (!response.result)
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.error.code, response.error.description));

                    if (response.recordIdentifierList.Length == 0)
                        throw new Exception("LA RISPOSTA DAL WEB SERVICE E' VUOTA");

                    _logs.InfoFormat("PROTOCOLLO NUMERO {0}, ANNO {1} AGGIORNATO CON SUCCESSO", request.documentDetails.number, request.documentDetails.year);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        public recordIdentifier Protocolla(protocolInsertRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.user = _utenteProtocollo;
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE, USERNAME: {0}, PASSWORD: {1}", request.user.code, request.user.password);
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    
                    var response = ws.protocolInsert(request);
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if(!response.result)
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.error.code, response.error.description));

                    if(response.recordIdentifierList.Length == 0)
                        throw new Exception("LA RISPOSTA DAL WEB SERVICE E' VUOTA");

                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, NUMERO PROTOCOLLO: {0}, DATA PROTOCOLLO: {1}", response.recordIdentifierList[0].number, response.recordIdentifierList[0].registrationDate.ToString("dd/MM/yyyy"));

                    return response.recordIdentifierList[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        public protocolDetail LeggiProtocollo(protocolDetailRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.user = _utenteProtocollo;

                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO, DOCUMENT PROG: {0}, MOVE PROG: {1}, USERNAME: {2}, PASSWORD: {3}", request.recordIdentifier.documentProg, request.recordIdentifier.moveProg, request.user.code, request.user.password);

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);

                    var response = ws.protocolDetail(request);

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

                    if (!response.result)
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.error.code, response.error.description));

                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO AVVENUTA CON SUCCESSO");

                    return response.protocolDetail;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE VERIFICATO DURANTE LA CHIAMATA AL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public anagraficResponse SearchAnagrafica(anagraficRequest request)
        {
            try
            {
                using(var ws = CreaWebService())
                {
                    request.user = _utenteProtocollo;
                    _logs.InfoFormat("RICERCA DELL'ANAGRAFICA {0}", request.anagrafic.anagraficDescription);
                    var response = ws.anagraficList(request);
                    _logs.InfoFormat("RICERCA DELL'ANAGRAFICA {0} AVVENUTA CON SUCCESSO, NUMERO ANAGRAFICHE TROVATE", request.anagrafic.anagraficDescription, response.anagraficList.Length);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE VERIFICATO DURANTE LA CHIAMATA AL WEB SERVICE SEARCH ANAGRAFICA, ERRORE: {0}", ex.Message), ex);
            }
        }

        public filing[] LeggiClassifiche(filingRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.user = _utenteProtocollo;
                    _logs.InfoFormat("CHIAMATA AL WEB METHOD FILINGLIST PER GENERARE LE CLASSIFICHE, USERNAME: {0}, PASSWORD: {1}", request.user.code, request.user.password);
                    var response = ws.filingList(request);

                    if (!response.result)
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.error.code, response.error.description));

                    _serializer.Serialize(ProtocolloLogsConstants.CreaFascicoloResponseFileName, response);

                    _logs.Info("CHIAMATA AL WEB METHOD FILINGLIST AVVENUTA CON SUCCESSO");
                    //_protocolloSerializer.Serialize(ProtocolloLogsConstants.ClassificheSoapResponseFileName, response);

                    return response.filingList;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, {0}", ex.Message), ex);
            }
        }
    }
}
