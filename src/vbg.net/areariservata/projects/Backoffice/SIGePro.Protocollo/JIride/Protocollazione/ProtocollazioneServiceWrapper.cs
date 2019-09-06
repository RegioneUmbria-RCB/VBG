using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocollazioneJIrideService;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Validation;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    public class ProtocollazioneServiceWrapper : IProtocollazione
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _codiceAmministrazione;
        string _url;
        string _codiceAoo;

        public ProtocollazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string codiceAmministrazione, string codiceAoo)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._url = url;
            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
        }

        private ProtocolloSoapClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione J-IRIDE");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_JIRIDE NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new ProtocolloSoapClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di protocollazione J-IRIDE");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        public ProtocolloOutXml InserisciDocumento(ProtocolloInXml protocolloIn)
        {
            try
            {
                var requestXml = _serializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protocolloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                _logs.Info("CHIAMATA A INSERISCI DOCUMENTO STRING DI J-IRIDE");

                using (var ws = CreaWebService())
                {
                    var responseXml = ws.InserisciDocumentoEAnagraficheString(requestXml, this._codiceAmministrazione, this._codiceAoo);
                    _logs.InfoFormat("RISPOSTA A INSERISCI DOCUMENTO STRING DI J-IRIDE, {0}", responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA");
                    var response = _serializer.Deserialize<ProtocolloOutXml>(responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE L'INSERIMENTO DEL DOCUMENTO {ex.Message}", ex);
            }
        }

        public ProtocolloOutXml InserisciProtocollo(ProtocolloInXml protocolloIn)
        {
            try
            {
                var requestXml = _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, protocolloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                _logs.Info("CHIAMATA A INSERISCI PROTOCOLLO STRING DI J-IRIDE");

                using (var ws = CreaWebService())
                {
                    var responseXml = ws.InserisciProtocolloEAnagraficheString(requestXml, this._codiceAmministrazione, this._codiceAoo);
                    _logs.InfoFormat("RISPOSTA A INSERISCI PROTOCOLLO STRING DI J-IRIDE, {0}", responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA");
                    var response = _serializer.Deserialize<ProtocolloOutXml>(responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CON SUCCESSO");

                    if (!String.IsNullOrEmpty(response.Errore))
                    {
                        throw new Exception(response.Errore);
                    }

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE L'INSERIMENTO DEL PROTOCOLLO {ex.Message}", ex);
            }
        }

        public string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo)
        {
            throw new NotImplementedException();
        }

        public DocumentoOutXml LeggiDocumento(string idProtocollo, string operatore, string ruolo)
        {
            bool isCopia = false;
            string idProtocolloFormattato = idProtocollo;

            if (!String.IsNullOrEmpty(idProtocollo))
            {
                var arrIdProtocollo = idProtocollo.Split('-');
                isCopia = arrIdProtocollo.Length > 1 && arrIdProtocollo[1] == "COPIA";
                _logs.InfoFormat("idProtocollo: {0}, idProtocollo dopo formattazione: {1}", idProtocollo, arrIdProtocollo[0]);
                idProtocolloFormattato = arrIdProtocollo[0];
            }

            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A LEGGI DOCUMENTO STRING DI J-IRIDE, IDPROTOCOLLO: {0}, OPERATORE: {1}, RUOLO: {2}, CODICE AMMINISTRAZIONE: {3}", idProtocolloFormattato, operatore, ruolo, this._codiceAmministrazione);
                    var responseXml = ws.LeggiDocumentoString(Convert.ToInt32(idProtocolloFormattato), operatore, ruolo, this._codiceAmministrazione, this._codiceAoo);
                    //_logs.InfoFormat("RISPOSTA A LEGGI DOCUMENTO STRING DI J-IRIDE, XML: {0}", responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING DI J-IRIDE");
                    var response = _serializer.Deserialize<DocumentoOutXml>(responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA CHIAMATA A LEGGI DOCUMENTO, {ex.Message}", ex);
            }
        }

        public DocumentoOutXml LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO STRING DI J-IRIDE, ANNO PROTOCOLLO: {0}, NUMERO PROTOCOLLO: {1}, OPERATORE: {2}, RUOLO: {3}, CODICE AMMINISTRAZIONE: {4}", annoProtocollo, numeroProtocollo, operatore, ruolo, this._codiceAmministrazione);
                    var responseXml = ws.LeggiProtocolloString(annoProtocollo, numeroProtocollo, operatore, ruolo, this._codiceAmministrazione, this._codiceAoo, "");
                    //_logs.InfoFormat("RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE, XML: {0}", responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE");
                    var response = _serializer.Deserialize<DocumentoOutXml>(responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA LETTURA DEL PROTOCOLLO NUMERO {numeroProtocollo}, ANNO {annoProtocollo}, {ex.Message}", ex);
            }
        }

        public void CreaCopiePerAmministrazioniInterne(DatiProtocolloIn protocolloInput, ProtocolloOutXml protocolloOutputOrigine, string operatore)
        {
            try
            {
                _logs.InfoFormat("INIZIO FUNZIONALITÀ CREA COPIE J-IRIDE PER AMMINISTRAZIONI INTERNE DEL PROTOCOLLO NUMERO: {0}, DATA: {1}, ID: {2}", protocolloOutputOrigine.NumeroProtocollo.ToString(), protocolloOutputOrigine.DataProtocollo.Value.ToString("dd/MM/yyyy"), protocolloOutputOrigine.IdDocumento.ToString());

                var ammList = protocolloInput.Destinatari.Amministrazione.Where(amm => !String.IsNullOrEmpty(amm.PROT_UO) && !String.IsNullOrEmpty(amm.PROT_RUOLO)).ToList();
                if (ammList.Count > 0)
                {
                    if (protocolloInput.Destinatari.Anagrafe.Count == 0 && protocolloInput.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).Count() == 0)
                        ammList = ammList.Skip(1).ToList();

                    var uoDestinatari = ammList.Select(x => new UODestinatariaXml
                    {
                        Carico = x.PROT_UO,
                        Data = DateTime.Now.ToString("dd/MM/yyyy"),
                        TipoUO = "UO",
                        NumeroCopie = "1"
                    });

                    var creaCopieIn = new CreaCopieInXml
                    {
                        AnnoProtocollo = protocolloOutputOrigine.AnnoProtocollo.ToString(),
                        NumeroProtocollo = protocolloOutputOrigine.NumeroProtocollo.ToString(),
                        IdDocumento = protocolloOutputOrigine.IdDocumento.ToString(),
                        UODestinatarie = uoDestinatari.ToArray(),
                        Utente = operatore,
                        Ruolo = protocolloInput.Mittenti.Amministrazione[0].PROT_RUOLO
                    };

                    using (var ws = CreaWebService())
                    {
                        var requestXml = _serializer.Serialize(ProtocolloLogsConstants.CreaCopieRequestFileName, creaCopieIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                        _logs.InfoFormat("CHIAMATA A CREA COPIE PER AMMINISTRAZIONI INTERNE, REQUEST XML: {0}", requestXml);
                        var creaCopieOutXml = ws.CreaCopieString(requestXml, this._codiceAmministrazione, this._codiceAoo);
                        _logs.InfoFormat("RISPOSTA DA CREA COPIE, RESPONSE XML: {0}", creaCopieOutXml);
                        _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREA COPIE");
                        var creaCopieOut = _serializer.Deserialize<CreaCopieOutXml>(creaCopieOutXml);
                        _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREA COPIE AVVENUTA CON SUCCESSO");

                        if (creaCopieOut.CopieCreate == null || creaCopieOut.CopieCreate.Length == 0)
                        {
                            this._logs.ErrorFormat("E' STATO RESTITUITO UN ERRORE DAL WEB SERVICE DURANTE LA FUNZIONALITÀ CREACOPIE ESEGUITA DOPO LA PROTOCOLLAZIONE, ID PROTOCOLLO ORIGINALE: {0}, NUMERO/ANNO {1}/{2}, ERRORE: {3}",
                                                        protocolloOutputOrigine.IdDocumento,
                                                        protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                        protocolloOutputOrigine.AnnoProtocollo.ToString(),
                                                        creaCopieOut.Errore);
                        }
                        else
                        {
                            this._logs.InfoFormat("E' STATA CREATA UNA COPIA CON LA FUNZIONALITÀ CREACOPIE ESEGUITA DOPO LA PROTOCOLLAZIONE, ID PROTOCOLLO ORIGINALE:{0}, ID PROTOCOLLO COPIA: {1}, NUMERO/ANNO PROTOCOLLO: {2}/{3}",
                                                        creaCopieOut.IdDocumentoSorgente.ToString(),
                                                        protocolloOutputOrigine.IdDocumento.ToString(),
                                                        protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                        protocolloOutputOrigine.AnnoProtocollo.ToString()
                                                        );
                        }

                        this._logs.Info("COPIA CREATA CON SUCCESSO");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DELLE COPIE PER LE AMMINISTRAZIONI INTERNE, COPIA DOPO PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        public DocumentoOutXml GeneraCopia(CreaCopieInfo info)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    info.ProtocolloLogs.Info("INIZIO FUNZIONALITÀ CREACOPIE J-IRIDE");
                    var documentoOut = new DocumentoOutXml();

                    if (!String.IsNullOrEmpty(info.IdProtocolloSorgente))
                    {
                        documentoOut = this.LeggiDocumento(info.IdProtocolloSorgente, info.Operatore, info.Ruolo);
                    }
                    else
                    {
                        documentoOut = this.LeggiProtocollo(Convert.ToInt16(info.AnnoProtocolloSorgente), Convert.ToInt32(info.NumeroProtocolloSorgente), info.Operatore, info.Ruolo);
                    }

                    if (documentoOut.IdDocumento == 0)
                    {
                        throw new Exception($"DOCUMENTO SORGENTE NON TROVATO, MESSAGGIO: {documentoOut.Messaggio}, ERRORE: {documentoOut.Errore}");
                    }

                    if (String.IsNullOrEmpty(info.Uo))
                        throw new Exception("UO NON VALORIZZATA");

                    var creaCopieIn = new CreaCopieInXml
                    {
                        AnnoProtocollo = documentoOut.AnnoProtocollo.ToString(),
                        NumeroProtocollo = documentoOut.NumeroProtocollo.ToString(),
                        //IdDocumento = documentoOut.IdDocumento.ToString(),
                        UODestinatarie = new UODestinatariaXml[]
                        {
                        new UODestinatariaXml
                        {
                            Carico = info.Uo,
                            TipoAssegnazione = info.TipologiaAssegnazione.ToString()
                        }
                        },
                        Utente = info.Operatore,
                        Ruolo = info.Ruolo
                    };

                    var creaCopieRequestXml = info.ProtocolloSerializer.Serialize(ProtocolloLogsConstants.CreaCopieRequestFileName, creaCopieIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);

                    info.ProtocolloLogs.InfoFormat("CHIAMATA A CREA COPIE, REQUEST XML: {0}", creaCopieRequestXml);
                    var creaCopieOutXml = ws.CreaCopieString(creaCopieRequestXml, info.Vert.CodiceAmministrazione, this._codiceAoo);
                    info.ProtocolloLogs.InfoFormat("RISPOSTA DA CREA COPIE, RESPONSE XML: {0}", creaCopieOutXml);
                    var creaCopieOut = info.ProtocolloSerializer.Deserialize<CreaCopieOut>(creaCopieOutXml);

                    if ((creaCopieOut.CopieCreate == null) || (creaCopieOut.CopieCreate.Count != 1))
                    {
                        throw new Exception($"MESSAGGIO: {creaCopieOut.Messaggio}, ERRORE: {creaCopieOut.Errore}");
                    }

                    info.ProtocolloLogs.InfoFormat("COPIA CREATA CON SUCCESSO");
                    return documentoOut;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA GENERAZIONE DELLA COPIA DEL DOCUMENTO SORGENTE ID: {info.IdProtocolloSorgente}, PROTOCOLLO NUMERO: {info.NumeroProtocolloSorgente}, ANNO: {info.AnnoProtocolloSorgente}, ERRORE: {ex.Message}", ex);
            }
        }

        public bool IsCopia(string idProtocollo)
        {
            if (!String.IsNullOrEmpty(idProtocollo))
            {
                var arrIdProtocollo = idProtocollo.Split('-');
                if (arrIdProtocollo.Length > 1 && arrIdProtocollo[1] == "COPIA")
                {
                    _logs.Info("E' UNA COPIA");
                    return true;
                }
            }

            return false;
        }
    }
}
