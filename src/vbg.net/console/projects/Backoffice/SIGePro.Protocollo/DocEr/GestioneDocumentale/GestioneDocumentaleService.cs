using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using System.IO;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocEr.Autenticazione;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.DocEr.Fascicolazione.CreaFascicolo;
using Init.SIGePro.Protocollo.DocEr.Fascicolazione;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Fascicolazione;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Anagrafiche;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale
{
    public class GestioneDocumentaleService
    {
        private static class Constants
        {
            public const string TYPE_ID_TITOLARIO = "TITOLARIO";
            public const string TYPE_ID_FASCICOLO = "FASCICOLO";
        }

        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;
        string _token;

        public GestioneDocumentaleService(string endPoinAddress, string token, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPoinAddress;
            _token = token;
        }

        private DocerServicesPortTypeClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di gestione documentale DOCER");

                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("DocErHttpBinding");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new System.Exception("IL PARAMETRO URL_GESTIONE_DOC DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCER NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new DocerServicesPortTypeClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di gestione documentale PROTOCOLLO_DOCER");

                return ws;

            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI GESTIONE DOCUMENTALE DOCER, ERRORE: {0}", ex.Message), ex);
            }
        }

        private long ParseIdDocumento(string response)
        {
            long idDocumento;

            var parse = long.TryParse(response, out idDocumento);

            if (!parse)
                throw new System.Exception("PARSING DELL'ID RESTITUITO DAL WEB SERVICE DI CREAZIONE DOCUMENTO NON CORRETTO");

            return idDocumento;
        }

        public KeyValuePair[] GetFascicolo(KeyValuePair[] fascicoloId)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    
                    _logs.Info("RECUPERO DEL FASCICOLO");
                    var response = ws.getFascicolo(_token, fascicoloId);
                    _logs.Info("RECUPERO DEL FASCICOLO AVVENUTO CORRETTAMENTE");

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, METODO GETFASCICOLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public SearchItem[] SearchFascicoli(KeyValuePair[] metadati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.Info("RECUPERO DEI FASCICOLI");
                    var response = ws.searchAnagrafiche(_token, Constants.TYPE_ID_FASCICOLO, metadati);
                    _logs.Info("RECUPERO DEI FASCICOLI AVVENUTO CORRETTAMENTE");

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, METODO SEARCHFASCICOLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        private bool SetAclDocument(DocerServicesPortTypeClient ws, IAuthenticationService auth, string docId)
        {
            try
            {
                var ruoli = auth.GetRuoli(this);
                var ruoliDocEr = ruoli.Keys.Select(x => new KeyValuePair { key = x, value = ruoli[x] }).ToArray();

                if(_logs.IsDebugEnabled)
                    _serializer.Serialize(ProtocolloLogsConstants.RuoliMetadatiRequestSetAclDocument, ruoli);

                _logs.InfoFormat("VALORIZZAZIONE ACL DOCUMENT, DOCID {0}, UTENTE {1}", docId, auth.Username);
                var response = ws.setACLDocument(_token, docId, ruoliDocEr);
                
                if (!response)
                    _logs.WarnFormat("CHIAMATA A SETACLDOCUMENT, DOCID {0}, UTENTE {1} AVVENUTA CORRETTAMENTE NON ANDATA A BUON FINE", auth.Username, docId);
                else
                    _logs.InfoFormat("CHIAMATA A SETACLDOCUMENT, DOCID {0}, UTENTE {1} AVVENUTA CORRETTAMENTE", auth.Username, docId);

                return response;
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA VALORIZZAZIONE DELLE ACL DOCUMENT, ERRORE: {0}", ex.Message), ex);
            }
        }

        public long InserisciDocumentoPrimario(IAuthenticationService auth, ProtocolloAllegati allegatoPrimario, string typeId, string codiceEnte, string codiceAoo, string tipoDocPrincipale, ResolveDatiProtocollazioneService datiProtoService, bool disabilitaMetadati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var metadati = new GestioneDocumentaleMetadataAdapter(allegatoPrimario, datiProtoService, typeId, codiceEnte, codiceAoo, tipoDocPrincipale, _logs);
                    var keys = metadati.Adatta(disabilitaMetadati);
                    _serializer.Serialize(ProtocolloLogsConstants.MetadatiDocsRequestPrimarioFileName, keys);
                    _logs.InfoFormat("INSERIMENTO DOCUMENTO PRIMARIO: {0} CODICE OGGETTO: {1}", allegatoPrimario.NOMEFILE, allegatoPrimario.CODICEOGGETTO);
                    string responsePrimario = ws.createDocument(_token, keys, allegatoPrimario.OGGETTO);
                    _logs.InfoFormat("INSERIMENTO DOCUMENTO PRIMARIO: {0} CODICE OGGETTO: {1}, AVVENUTO CON SUCCESSO, ID RESTITUITO {2}", allegatoPrimario.NOMEFILE, allegatoPrimario.CODICEOGGETTO, responsePrimario);

                    SetAclDocument(ws, auth, responsePrimario);
                    
                    var idDoc = ParseIdDocumento(responsePrimario);
                    
                    allegatoPrimario.ID = idDoc;

                    return idDoc;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DEL DOCUMENTO PRIMARIO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void InserisciDocumentiAllegati(IAuthenticationService auth, string docPrimario, IEnumerable<ProtocolloAllegati> docsAllegati, string typeId, string codiceEnte, string codiceAoo, string tipoDocAllegati, ResolveDatiProtocollazioneService datiProtoService, bool disabilitaMetadati)
        {
            try
            {
                if (docsAllegati.Count() == 0)
                {
                    _logs.WarnFormat("Allegati al documento {0} non presenti", docPrimario);
                    return;
                }

                using (var ws = CreaWebService())
                {
                    var rel = new List<string>();

                    foreach (var doc in docsAllegati)
                    {
                        _logs.InfoFormat("INSERIMENTO DOCUMENTO ALLEGATO: {0} CODICE OGGETTO: {1}", doc.NOMEFILE, doc.CODICEOGGETTO);
                        var metadati = new GestioneDocumentaleMetadataAdapter(doc, datiProtoService, typeId, codiceEnte, codiceAoo, tipoDocAllegati, _logs);
                        var keys = metadati.Adatta(disabilitaMetadati);
                        
                        _serializer.Serialize(String.Concat(ProtocolloLogsConstants.MetadatiDocsRequestAllegatiFileName, ".", doc.CODICEOGGETTO, ".xml"), keys);
                        string responseAllegato = ws.createDocument(_token, keys, doc.OGGETTO);
                        _logs.InfoFormat("INSERIMENTO DOCUMENTO ALLEGATO: {0} CODICE OGGETTO: {1}, AVVENUTO CON SUCCESSO, ID RESTITUITO {2}", doc.NOMEFILE, doc.CODICEOGGETTO, responseAllegato);
                         
                        rel.Add(responseAllegato);

                        SetAclDocument(ws, auth, responseAllegato);

                        doc.ID = ParseIdDocumento(responseAllegato);
                    }

                    ws.addRelated(_token, docPrimario, rel.ToArray());
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DEI DOCUMENTI ALLEGATI, ERRORE: {0}", ex.Message), ex);
            }
        }

        public KeyValuePair[] LeggiDocumento(string idUnitaDocumentale)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A GETPROFILEDOCUMENT, documento: {0}", idUnitaDocumentale);
                    var response = ws.getProfileDocument(_token, idUnitaDocumentale);
                    _logs.InfoFormat("CHIAMATA A GETPROFILEDOCUMENT AVVENUTA CORRETTAMENTE, documento: {0}", idUnitaDocumentale);

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
                    
                    return response;

                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DOCUMENTALE DURANTE IL RECUPERO DEI DATI DEL PROFILO DOCUMENTO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public SearchItem[] GetClassifiche(KeyValuePair[] metadati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize("GetClassificheRequest.xml", metadati);
                    _logs.Info("RECUPERO DELLE CLASSIFICHE");
                    var response = ws.searchAnagrafiche(_token, Constants.TYPE_ID_TITOLARIO, metadati);
                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.ListaClassificheResponse, response );

                    _logs.Info("RECUPERO DELLE CLASSIFICHE AVVENUTO CORRETTAMENTE");
                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI DATI DEL TITOLARIO DAL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public KeyValuePair[] GetTipiDocumento(string codiceEnte, string codiceAoo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A GETDOCUMENTTYPES");
                    var response = ws.getDocumentTypesByAOO(_token, codiceEnte, codiceAoo);
                    _logs.InfoFormat("CHIAMATA A GETDOCUMENTTYPES AVVENUTA CORRETTAMENTE");

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.TipiDocumentoSoapResponseFileName, response);

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE DURANTE LA CHIAMATA AL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public string[] GetRelatedDocuments(string idUnitaDocumentale)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A GETRELATEDDOCUMENT, documento: {0}", idUnitaDocumentale);
                    var response = ws.getRelatedDocuments(_token, idUnitaDocumentale);
                    _logs.InfoFormat("CHIAMATA A GETRELATEDDOCUMENT AVVENUTA CORRETTAMENTE, DOCUMENTI RESTITUITI {0}", response != null ? String.Join(",", response) : "");

                    if (_logs.IsDebugEnabled)
                    _serializer.Serialize(ProtocolloLogsConstants.LeggiRelatedDocumentsResponse, response);

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DOCUMENTALE DURANTE IL RECUPERO DEGLI ALLEGATI AL DOCUMENTO PRINCIPALE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public StreamDescriptor DownloadDocument(string idDocumento)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A DOWNLOADDOCUMENT, documento: {0}", idDocumento);
                    var response = ws.downloadDocument(_token, idDocumento);
                    _logs.InfoFormat("CHIAMATA A DOWNLOADDOCUMENT AVVENUTA CON SUCCESSO, documento: {0}", idDocumento);

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DOCUMENTALE DURANTE IL DOWNLOAD DEL DOCUMENTO ID {0}, ERRORE: {1}", idDocumento, ex.Message), ex);
            }
        }

        public void SetAclFascicolo(Fascicolo datiFasc, Dictionary<string, string> ruoli, string codiceEnte, string codiceAoo, string username)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (ruoli == null)
                    {
                        _logs.WarnFormat("ATTENZIONE, L'UTENTE {0} NON HA SETTATO ALCUN RUOLO IN QUANTO IL DATO PROVIENE DALLA VERTICALIZZAZIONE", username);
                        return;
                    }

                    var ruoliDocEr = ruoli.Keys.Select(x => new KeyValuePair { key = x, value = ruoli[x] }).ToArray();

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.RuoliMetadatiRequestSetAclFascicolo, ruoli);

                    _logs.InfoFormat("CHIAMATA A SETACLFASCICOLO");

                    var metadatiAdapter = new SetAclCreaFascicoloMetadataAdapter(datiFasc, codiceEnte, codiceAoo);
                    var metadati = metadatiAdapter.Adatta();

                    var response = ws.setACLFascicolo(_token, metadati, ruoliDocEr);

                    if (!response)
                    {
                        _logs.WarnFormat("CHIAMATA A SETACLFASCICOLO NON ANDATA A BUON FINE");
                        return;
                    }

                    _logs.InfoFormat("CHIAMATA A SETACLFASCICOLO AVVENUTA CON SUCCESSO");
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DOCUMENTALE DURANTE IL SET ACL FASCICOLO, ERRORE: {1}", ex.Message), ex);
            }
        }

        public KeyValuePair[] GetGroup(string groupId)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A GetGroup del gruppo {0} PER VERIFICARE CHE IL GRUPPO SIA ESISTENTE E CHE SIA ABILITATO", groupId);
                    var response = ws.getGroup(_token, groupId);

                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(ProtocolloLogsConstants.GetGruppiDocErResponse, response);

                    _logs.Info("CHIAMATA A GetGroup AVVENUTA CORRETTAMENTE");
                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE ALLA CHIAMATA A GETGROUP, PER LA VERIFICA DEL GRUPPO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void UpdateAnagrafica(IAmministrazioneAnagraficaVbg anagrafe, KeyValuePair[] customId, VerticalizzazioniConfiguration vert)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("AGGIORNAMENTO DELL'ANAGRAFICA CODICE: {0}, NOMINATIVO: {1}, CODICE FISCALE: {2}, TIPO: {3}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo);
                    
                    var customIdList = customId.ToList();
                    customIdList.Add(new KeyValuePair { key = MetadatiAnagraficheConstants.TypeId, value = vert.TypeIdAnagraficaCustom });

                    var metadati = CreaAnagraficheMetadatiRequestAdapter.Adatta(anagrafe, vert);
                    var response = ws.updateAnagraficaCustom(_token, customIdList.ToArray(), metadati);

                    if (!response)
                        throw new System.Exception();

                    _logs.InfoFormat("AGGIORNAMENTO DELL'ANAGRAFICA AVVENUTA CON SUCCESSO, CODICE: {0}, DESCRIZIONE: {1}", anagrafe.CodiceFiscalePartitaIva, anagrafe.Nominativo);
                }
            }
            catch (System.Exception ex)
            {
                if(vert.IgnoraWarnAnag)
                    _logs.ErrorFormat("ERRORE RESTITUITO DAL WEB SERVICE DURANTE L'AGGIORNAMENTO DELL'ANAGRAFICA CODICE {0}, NOMINATIVO {1}, CODICE FISCALE {2}, TIPO: {3}, ERRORE: {4}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo, ex.Message);
                else
                    _logs.WarnFormat("ERRORE RESTITUITO DAL WEB SERVICE DURANTE L'AGGIORNAMENTO DELL'ANAGRAFICA CODICE {0}, NOMINATIVO {1}, CODICE FISCALE {2}, TIPO: {3}, ERRORE: {4}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo, ex.Message);
            }
        }

        public void CreaAnagrafica(IAmministrazioneAnagraficaVbg anagrafe, VerticalizzazioniConfiguration vert)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CREAZIONE DELL'ANAGRAFICA CODICE: {0}, NOMINATIVO: {1}, CODICE FISCALE: {2}, TIPO: {3}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo);

                    var metadati = CreaAnagraficheMetadatiRequestAdapter.Adatta(anagrafe, vert);
                    var response = ws.createAnagraficaCustom(_token, metadati);

                    if (!response)
                        throw new System.Exception();

                    _logs.InfoFormat("CREAZIONE DELL'ANAGRAFICA AVVENUTA CON SUCCESSO, CODICE: {0}, DESCRIZIONE: {1}", anagrafe.CodiceFiscalePartitaIva, anagrafe.Nominativo);
                }
            }
            catch (System.Exception ex)
            {
                if(vert.IgnoraWarnAnag)
                    _logs.ErrorFormat("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA CREAZIONE DELL'ANAGRAFICA CODICE: {0}, NOMINATIVO: {1} CODICE FISCALE: {2}, TIPO: {3}, ERRORE: {4}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo, ex.Message);
                else
                    _logs.WarnFormat("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA CREAZIONE DELL'ANAGRAFICA CODICE: {0}, NOMINATIVO: {1} CODICE FISCALE: {2}, TIPO: {3}, ERRORE: {4}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo, ex.Message);
            }
        }

        /// <summary>
        /// Ritorna l'Id del documento primario legato al protocollo passato.
        /// </summary>
        /// <param name="numeroProtocollo"></param>
        /// <param name="annoProtocollo"></param>
        /// <param name="codiceEnte"></param>
        /// <param name="codiceAoo"></param>
        /// <returns></returns>
        public string CercaProtocollo(string numeroProtocollo, string annoProtocollo, string codiceEnte, string codiceAoo, int? padLength, char padChar)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (padLength.HasValue)
                        numeroProtocollo = numeroProtocollo.PadLeft(padLength.Value, padChar);

                    _logs.InfoFormat("RICERCA DEL PROTOCOLLO NUMERO {0}, ANNO {1}, CODICE AOO {2}, CODICE ENTE {3}", numeroProtocollo, annoProtocollo, codiceAoo, codiceEnte);
                    
                    var metadati = SearchDocumentsMetadatiRequestAdapter.Adatta(numeroProtocollo, annoProtocollo, codiceAoo, codiceEnte);
                    var response = ws.searchDocuments(_token, metadati, null, 1, null);

                    if (response == null)
                    {
                        _logs.InfoFormat("NESSUN DOCUMENTO TROVATO CON NUMERO PROTOCOLLO {0} ANNO PROTOCOLLO {1}", numeroProtocollo, annoProtocollo);
                        return null;
                    }

                    var dic = response[0].metadata.ToDictionary(x => x.key, y => y.value);
                    _logs.InfoFormat("DOCUMENT ID RITORNATO DAL PROTOCOLLO NUMERO {0}, ANNO {1}", numeroProtocollo, annoProtocollo);

                    return dic["DOCNUM"];
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA RICERCA DEL PROTOCOLLO, metodo searchDocuments PROTOCOLLO NUMERO: {0}, ANNO: {1}, CODICE ENTE: {2}, CODICE AOO: {3}, ERRORE: {4}", numeroProtocollo, annoProtocollo, codiceEnte, codiceAoo, ex.Message), ex);

            }
        }

        public SearchItem[] SearchAnagrafica(IAmministrazioneAnagraficaVbg anagrafe, VerticalizzazioniConfiguration vert)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("RICERCA DELL'ANAGRAFICA CODICE: {0}, NOMINATIVO (RAGIONE SOCIALE): {1}, CODICE FISCALE: {2}, TIPO: {3}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo);

                    var metadati = SearchAnagraficheMetadatiRequestAdapter.Adatta(anagrafe.CodiceFiscalePartitaIva, vert);
                    var response = ws.searchAnagrafiche(_token, vert.TypeIdAnagraficaCustom, metadati);

                    if (response == null)
                    {
                        _logs.InfoFormat("0 ANAGRAFICHE TROVATE, CODICE ANAGRAFICA VBG: {0}, CODICE FISCALE: {1}", anagrafe.CodiceVbg, anagrafe.CodiceFiscalePartitaIva);
                        return null;
                    }

                    _logs.InfoFormat("RISULTATO DELLA RICERCA DELL'ANAGRAFICA CODICE: {0}, NUMERO RECORDS TROVATI: {1}", anagrafe.CodiceVbg, response.Length);

                    return response;
                }
            }
            catch(System.Exception ex)
            {
                if(vert.IgnoraWarnAnag)
                    _logs.ErrorFormat("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA RICERCA DELL'ANAGRAFICA CODICE: {0}, NOMINATIVO: {1}, CODICE FISCALE: {2}, TIPO: {3}, ERRORE: {4}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo, ex.Message);
                else
                    _logs.WarnFormat("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA RICERCA DELL'ANAGRAFICA CODICE: {0}, NOMINATIVO: {1}, CODICE FISCALE: {2}, TIPO: {3}, ERRORE: {4}", anagrafe.CodiceVbg, anagrafe.Nominativo, anagrafe.CodiceFiscalePartitaIva, anagrafe.Tipo, ex.Message);

                return null;
            }
        }

        public KeyValuePair[] GetAclDocument(string idDocumento)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A GETACLDOCUMENT, documento: {0}", idDocumento);
                    var response = ws.getACLDocument(_token, idDocumento);
                    _logs.InfoFormat("CHIAMATA A GETACLDOCUMENT del documento {0} AVVENUTA CORRETTAMENTE", idDocumento);

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DOCUMENTALE DURANTE IL RECUPERO DELLE ACL DEL DOCUMENTO {0}, ERRORE: {1}", idDocumento, ex.Message), ex);
            }
        }
    }
}
