using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloFilesInsielService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo
{
    /// <summary>
    /// Protocollo utilizzato a Trieste
    /// </summary>
    internal class PROTOCOLLO_INSIEL : ProtocolloBase
    {

        #region Membri privati della classe

        private string _url;
        private string _codiceUtente;
        private string _passwordUtente;
        private string _urlFilesUpload;
        private string _codiceUfficioOperante;
        private string _codiceRegistro;
        private bool _usaWsPerTipiDoc = false;
        private bool _escludiClassifica = false;
        private bool _disabilitaAnnullaProtocollo = false;
        private List<string> _warnings;


        private const string SEPARATORE_ID_PROTOCOLLO = ";";
        private const string TIPI_DOCUMENTO_WS_NAME = "TIPI_DOCUMENTO_WS";
        private const string ESCLUDI_CLASSIFICA_PARAM_NAME = "ESCLUDI_CLASSIFICA";
        private const string DISABILITA_ANNULLA_PROTOCOLLO_PARAM_NAME = "DISABILITA_ANNULLA_PROTOCOLLO";

        #endregion

        public PROTOCOLLO_INSIEL()
        {

        }

        #region Creazione WebServices

        private ProtocolloPTClient CreaWebService()
        {
            try
            {
                _protocolloLogs.Debug("Creazione del webservice di protocollazione INSIEL");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL NON È STATO VALORIZZATO.");

                var ws = new ProtocolloPTClient(binding, endPointAddress);

                _protocolloLogs.Debug("Fine creazione del web service di protocollazione INSIEL");

                return ws;

            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE INSIEL", ex);
            }
        }

        private ProtocolloFilesPortTypeClient CreaWebServiceUpload()
        {
            try
            {
                _protocolloLogs.Debug("Creazione del webservice di upload allegati INSIEL");

                var endPointAddress = new EndpointAddress(_urlFilesUpload);
                var binding = new BasicHttpBinding("insielUploadHttpBinding");

                if (String.IsNullOrEmpty(_urlFilesUpload))
                    throw new Exception("IL PARAMETRO URL_WSUPLOAD DELLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL NON È STATO VALORIZZATO.");

                var ws = new ProtocolloFilesPortTypeClient(binding, endPointAddress);

                _protocolloLogs.Debug("Fine creazione del web service di upload allegati INSIEL");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI UPLOAD DEI FILES ALLEGATI", ex);
            }
        }

        #endregion

        #region Protocollazione

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn pProt)
        {

            try
            {
                SetParamFromVertInsiel();
                using (var ws = CreaWebService())
                {
                    _warnings = new List<string>();
                    var request = new InserimentoProtocolloRequest();
                    request.utente = GetUtenteProtocollo();

                    switch (pProt.Flusso)
                    {
                        case "A":
                            SetDatiProtocolloInArrivo(request, pProt);
                            break;
                        case "P":
                            SetDatiProtocolloInPartenza(request, pProt);
                            break;
                        case "I":
                            throw new Exception("PROTOCOLLAZIONE INTERNA NON GESTITA DAL SISTEMA DI PROTOCOLLAZIONE");
                        default:
                            throw new Exception("I FLUSSI GESTITI SONO SOLAMENTE IN ARRIVO E PARTENZA, FLUSSO RICHIESTO: " + pProt.Flusso);
                    }

                    request.oggetto = pProt.Oggetto;
                    _protocolloLogs.DebugFormat("Recupero della classifica: {0}", pProt.Classifica);

                    if (!_escludiClassifica)
                    {
                        var classifica = pProt.Classifica;

                        if (!String.IsNullOrEmpty(pProt.Classifica))
                            classifica = pProt.Classifica.Replace("x", " ");

                        request.classifiche = new Classifica[] { new Classifica { Item = classifica } };
                    }

                    request.estremi_documento = new EstremiDocumento { tipo = pProt.TipoDocumento };

                    _protocolloLogs.Debug("Recupero degli allegati");

                    request.documenti = GetDocumenti(pProt);

                    _protocolloLogs.Debug("Allegati recuperati");
                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);

                    _protocolloLogs.InfoFormat("Chiamata a web method inserisciProtocollo, request: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                    var res = ws.inserisciProtocollo(request);
                    _protocolloLogs.Debug("Risposta da parte del web service");

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, res);

                    DatiProtocolloRes retVal = null;

                    _protocolloLogs.DebugFormat("Esito protocollazione: {0}", res.esito);

                    if (res.Items != null && res.Items.Length > 0)
                    {
                        if (res.Items[0] is ProtocolloInsielService.ProtocolloResponse)
                        {
                            var protRes = (ProtocolloResponse)res.Items[0];
                            _protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE");

                            retVal = CreaDatiProtocollo(protRes);
                        }

                        if (res.Items[0] is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)res.Items[0];
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL SISTEMA DI PROTOCOLLAZIONE INSIEL, CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                        }
                    }
                    else
                        throw new Exception("LA PROPRIETA' ITEMS NON E' STATA VALORIZZATA");

                    return retVal;
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        private DatiProtocolloRes CreaDatiProtocollo(ProtocolloResponse res)
        {
            try
            {
                var datiProtocollo = new DatiProtocolloRes();
                datiProtocollo.AnnoProtocollo = res.Anno;
                datiProtocollo.NumeroProtocollo = res.Numero;
                datiProtocollo.DataProtocollo = res.Data.ToString("dd/MM/yyyy");
                datiProtocollo.IdProtocollo = String.Concat(res.ProgDoc.ToString(), SEPARATORE_ID_PROTOCOLLO, res.ProgMovi);

                if (_warnings.Count > 0)
                    datiProtocollo.Warning = String.Join("; ", _warnings.ToArray());

                _protocolloLogs.InfoFormat("DATI PROTOCOLLAZIONE, Id Protocollo: {0}, Numero: {1}, Data: {2}, Anno: {3}", datiProtocollo.IdProtocollo, datiProtocollo.NumeroProtocollo, datiProtocollo.DataProtocollo, datiProtocollo.AnnoProtocollo);

                return datiProtocollo;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO", ex);
            }
        }

        private void SetDatiProtocolloInArrivo(InserimentoProtocolloRequest request, DatiProtocolloIn pProt)
        {
            try
            {
                if (pProt.Destinatari.Amministrazione.Count == 0)
                    throw new Exception("NON E' STATA SPECIFICATA L'AMMINISTRAZIONE IN FASE DI PROTOCOLLAZIONE");


                if (pProt.Destinatari.Amministrazione[0].PROT_UO.Length == 0)
                    throw new Exception(String.Format("NON E' STATA SPECIFICATA L'UO NELL'AMMINISTRAZIONE ({0}) {1} CHE E' MAPPATA CON L'UFFICIO DEL PROTOCOLLO.", pProt.Destinatari.Amministrazione[0].CODICEAMMINISTRAZIONE, pProt.Destinatari.Amministrazione[0].AMMINISTRAZIONE));

                var ufficio = base.GetUfficioRegistro(_codiceRegistro);

                request.codice_ufficio = ufficio;
                request.codice_registro = _codiceRegistro;

                request.codice_ufficio_operante = _codiceUfficioOperante;
                request.uffici = new UfficioInsProto[] { new UfficioInsProto { codice = pProt.Destinatari.Amministrazione[0].PROT_UO, giaInviato = true, giaInviatoSpecified = true } };

                request.verso = ProtocolloInsielService.verso.A;

                var mittenti = new List<MittenteInsProto>();

                foreach (var amm in pProt.Mittenti.Amministrazione)
                {
                    if (String.IsNullOrEmpty(amm.PROT_UO) && String.IsNullOrEmpty(amm.PROT_RUOLO))
                    {

                        if (mittenti.Where(x => x.descrizione == amm.AMMINISTRAZIONE).Count() == 0)
                        {
                            var datiAnag = GetDatiAmministrazione(amm);
                            mittenti.Add(new MittenteInsProto()
                            {
                                dati_anagrafica = datiAnag,
                                descrizione = amm.AMMINISTRAZIONE.Replace("  ", " "),
                                inserisci = true,
                                inserisciSpecified = true
                            });
                        }
                        else
                        {
                            _protocolloLogs.WarnFormat("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE);
                            _warnings.Add(String.Format("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE));
                        }
                    }
                }

                foreach (var anag in pProt.Mittenti.Anagrafe)
                {
                    string nomeCompleto = anag.GetNomeCompleto();
                    if (mittenti.Where(x => x.descrizione == nomeCompleto).Count() == 0)
                    {
                        //In teoria non è obbligatorio popolare questi dati, però avendoceli li inserisco.
                        var datiAnag = GetDatiAnagrafici(anag);

                        //L'unico dato obbligatorio da valorizzare è la descrizione
                        mittenti.Add(new MittenteInsProto()
                        {
                            descrizione = anag.GetNomeCompleto().Replace("  ", " "),
                            dati_anagrafica = datiAnag,
                            inserisci = true,
                            inserisciSpecified = true
                        });
                    }
                    else
                    {
                        _protocolloLogs.WarnFormat("L'ANAGRAFICA {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", nomeCompleto);
                        _warnings.Add(String.Format("L'ANAGRAFICA {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", nomeCompleto));
                    }
                }


                if (mittenti.Count == 0)
                    throw new Exception("NON E' STATO VALORIZZATO NESSUN MITTENTE");

                request.mittenti = mittenti.ToArray();
            }
            catch (Exception)
            {
                throw;
            }
        }

        private void SetDatiProtocolloInPartenza(InserimentoProtocolloRequest request, DatiProtocolloIn pProt)
        {
            try
            {
                if (pProt.Mittenti.Amministrazione.Count == 0)
                    throw new Exception("NON E' STATA SPECIFICATA L'AMMINISTRAZIONE IN FASE DI PROTOCOLLAZIONE.");

                if (String.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PROT_UO))
                    throw new Exception(String.Format("NON E' STATA SPECIFICATA L'UO NELL'AMMINISTRAZIONE ({0}) {1} CHE E' MAPPATA CON L'UFFICIO DEL PROTOCOLLO.", pProt.Mittenti.Amministrazione[0].CODICEAMMINISTRAZIONE, pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE));

                var ufficio = base.GetUfficioRegistro(_codiceRegistro);

                request.codice_ufficio = ufficio;
                request.codice_registro = _codiceRegistro;

                request.codice_ufficio_operante = _codiceUfficioOperante;

                var amministrazioniInterne = pProt.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO));
                if (amministrazioniInterne.Count() > 0)
                {
                    var uffici = amministrazioniInterne.Select(x => new UfficioInsProto { codice = x.PROT_UO, giaInviato = true, giaInviatoSpecified = true });
                    request.uffici = uffici.ToArray();
                }

                request.verso = ProtocolloInsielService.verso.P;

                var destinatari = new List<DestinatarioIOPInsProto>();

                foreach (var amm in pProt.Destinatari.Amministrazione)
                {
                    if (String.IsNullOrEmpty(amm.PROT_UO) && String.IsNullOrEmpty(amm.PROT_RUOLO))
                    {
                        if (destinatari.Where(x => x.descrizione == amm.AMMINISTRAZIONE).Count() == 0)
                        {
                            destinatari.Add(new DestinatarioIOPInsProto()
                            {
                                dati_anagrafica = GetDatiAmministrazione(amm),
                                descrizione = amm.AMMINISTRAZIONE.Replace("  ", " "),
                                inserisci = true,
                                inserisciSpecified = true
                            });
                        }
                        else
                        {
                            _protocolloLogs.WarnFormat("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE);
                            _warnings.Add(String.Format("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE));
                        }
                    }
                }


                foreach (var anag in pProt.Destinatari.Anagrafe)
                {
                    //In teoria non è obbligatorio popolare questi dati, però avendoceli li inserisco.

                    var datiAnag = GetDatiAnagrafici(anag);
                    var nomeCompleto = anag.GetNomeCompleto().Replace("  ", " ");

                    if (destinatari.Where(x => x.descrizione == nomeCompleto).Count() == 0)
                    {
                        //L'unico dato obbligatorio da valorizzare è la descrizione
                        destinatari.Add(new DestinatarioIOPInsProto()
                        {
                            descrizione = anag.GetNomeCompleto().Replace("  ", " "),
                            dati_anagrafica = datiAnag,
                            inserisci = true,
                            inserisciSpecified = true
                        });
                    }
                    else
                    {
                        _protocolloLogs.WarnFormat("L'ANAGRAFICA {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", nomeCompleto);
                        _warnings.Add(String.Format("L'ANAGRAFICA {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", nomeCompleto));
                    }
                }

                if (destinatari.Count == 0)
                    throw new Exception("NON E' STATO VALORIZZATO NESSUN DESTINATARIO");


                request.destinatari = destinatari.ToArray();
            }
            catch (Exception)
            {
                throw;
            }
        }

        private DatiAnagrafica GetDatiAmministrazione(Amministrazioni amm)
        {
            return new DatiAnagrafica
            {
                cap = amm.CAP,
                denominaz = amm.AMMINISTRAZIONE.Replace("  ", " "),
                indirizzo = amm.INDIRIZZO,
                localita = amm.CITTA,
                piva = !String.IsNullOrEmpty(amm.PARTITAIVA) ? (amm.PARTITAIVA.Length == 11 ? amm.PARTITAIVA : null) : null
            };
        }

        private DatiAnagrafica GetDatiAnagrafici(ProtocolloAnagrafe anag)
        {
            return new DatiAnagrafica()
            {
                cap = anag.CAP,
                codfis = !String.IsNullOrEmpty(anag.CODICEFISCALE) ? (anag.CODICEFISCALE.Length == 16 ? anag.CODICEFISCALE : null) : null,
                cognome = anag.TIPOANAGRAFE == "F" ? anag.NOMINATIVO.Replace("  ", " ") : null,
                nome = anag.TIPOANAGRAFE == "F" ? anag.NOME.Replace("  ", " ") : null,
                denominaz = anag.TIPOANAGRAFE == "G" ? anag.NOMINATIVO.Replace("  ", " ") : null,
                indirizzo = anag.INDIRIZZO,
                localita = anag.ComuneResidenza != null ? anag.ComuneResidenza.COMUNE : null,
                provincia = anag.ComuneResidenza != null ? anag.ComuneResidenza.SIGLAPROVINCIA : null,
                piva = !String.IsNullOrEmpty(anag.PARTITAIVA) ? (anag.PARTITAIVA.Length == 11 ? anag.PARTITAIVA : null) : null
            };

        }

        private documentoInsProto[] GetDocumenti(DatiProtocolloIn pProt)
        {
            try
            {
                var res = new List<documentoInsProto>();

                using (var wsu = CreaWebServiceUpload())
                {
                    var request = new UploadRequest();
                    bool isPrimario = true;
                    foreach (var all in pProt.Allegati)
                    {

                        _protocolloLogs.DebugFormat("file da allegare, Id: {0}, CodiceOggetto: {1}, NomeFile: {2}", all.ID, all.CODICEOGGETTO, all.NOMEFILE);

                        request.codiceUtente = _codiceUtente;
                        request.passwordUtente = _passwordUtente;

                        var att = new AttachmentData();

                        att.tipoFile = String.Empty;
                        att.fileName = all.NOMEFILE;
                        att.binaryData = all.OGGETTO;

                        request.documento = att;

                        _protocolloLogs.InfoFormat("Chiamata a web method updload del web service di upload allegati, codice oggetto: {0}, nome file: {1}, descrizione file: {2}", all.CODICEOGGETTO, all.NOMEFILE, all.Descrizione);
                        var upl = wsu.upload(request);

                        if (!upl.esito.GetValueOrDefault(false))
                            throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", upl.Errore.codice, upl.Errore.descrizione));

                        _protocolloLogs.Info("UPLOAD AVVENUTO CON SUCCESSO");

                        var docInsProto = new documentoInsProto();
                        docInsProto.id = upl.idDocumento;
                        docInsProto.is_primario = isPrimario;
                        docInsProto.nome = all.NOMEFILE;
                        isPrimario = false;

                        res.Add(docInsProto);
                    }
                }

                return res.ToArray();
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'UPLOAD DEI FILE", ex);
            }
        }

        public void AggiornaProtocollo(string numero, string annoProtocollo)
        {
            throw new Exception("LA FUNZIONALITA' DI AGGIORNA PROTOCOLLO NON E' STATA IMPLEMENTATA");
        }

        private void Riprotocolla(DatiProtocolloLetto proto, long progDocPrat, string progMoviPrat)
        {
            throw new Exception("LA FUNZIONALITA' DI RIPROTOCOLLAZIONE NON E' STATA IMPLEMENTATA");
            /*try
            {
                _protocolloLogs.DebugFormat("Inizio Riprotocollazione, progDocPratica: {0}, progMoviPratica: {1}", progDocPrat, progMoviPrat);

                using (var ws = CreaWebService())
                {

                    var request = new RiprotocollazioneRequest();
                    request.utente = GetUtenteProtocollo();

                    var arrIdProto = GetIdProtocollo(proto.IdProtocollo);
                    request.estremiRegistrazione = new ProtocolloInsielService.ProtocolloRequest
                    {
                        Item = new ProtocolloInsielService.IdProtocollo
                        {
                            ProgDoc = long.Parse(arrIdProto[0]),
                            ProgMovi = arrIdProto[1]
                        }
                    };

                    request.codice_ufficio = proto.InCaricoA;
                    //request.codice_registro = proto.;

                    if (proto.Origine == "A")
                        request.verso = ProtocolloInsielService.verso.A;
                    else if (proto.Origine == "P")
                        request.verso = ProtocolloInsielService.verso.P;

                    request.InserisciInPratica = new PraticaRequest[] 
                    { 
                        new PraticaRequest 
                        { 
                            Item = new ProtocolloInsielService.IdProtocollo 
                            { 
                                ProgDoc = progDocPrat, 
                                ProgMovi = progMoviPrat                            
                            } 
                        } 
                    };

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.RiprotocollazioneRequestFileName, request);

                    var response = ws.riprotocolla(request);

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.RiprotocollazioneResponseFileName, response);

                    if (response.Item != null)
                    {
                        if (response.Item is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)response.Item;
                            _protocolloLogs.DebugFormat("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione);
                            throw new Exception(String.Format("SI E' VERIFICATO UN ERRORE DURANTE LA RIPROTOCOLLAZIONE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione));
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("SI E' VERIFICATO UN ERRORE DURANTE LA FASCICOLAZIONE", ex);
            }*/
        }

        #endregion

        #region Fascicolazione

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {

            try
            {
                var proto = LeggiProtocollo(idProtocollo, annoProtocollo, numeroProtocollo);
                SetParamFromVertInsiel();
                using (var ws = CreaWebService())
                {
                    var request = new DettagliPraticaRequest();
                    request.Utente = GetUtenteProtocollo();
                    string[] arrIdProtocollo = GetIdProtocollo(proto.NumeroPratica);
                    request.Pratica = new PraticaRequest
                    {
                        Item = new ProtocolloInsielService.IdProtocollo
                        {
                            ProgDoc = long.Parse(arrIdProtocollo[0]),
                            ProgMovi = arrIdProtocollo[1]
                        }
                    };

                    _protocolloLogs.DebugFormat("Chiamata a web method dettagliPratica, username: {0}, password: {1}, id protocollo: {2}, numero protocollo: {3}, anno protocollo: {4}", _codiceUtente, _passwordUtente, idProtocollo, numeroProtocollo, annoProtocollo);
                    var response = ws.dettagliPratica(request);

                    DatiProtocolloFascicolato datiProtoFasc = null;

                    if (response.Item != null)
                    {
                        if (response.Item is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)response.Item;
                            _protocolloLogs.DebugFormat("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione);

                            throw new Exception(String.Format("ERRORE DURANTE LA FASCICOLAZIONE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione));
                        }
                        else if (response.Item is PraticaAperta)
                        {
                            var praticaAperta = (PraticaAperta)response.Item;
                            datiProtoFasc = CreaDatiFascicoloLetto(praticaAperta);
                        }
                    }
                    return datiProtoFasc;
                }

            }
            catch (Exception ex)
            {

                throw _protocolloLogs.LogErrorException("SI E' VERIFICATO UN ERRORE DURANTE LA VERIFICA DELLA FASCICOLAZIONE", ex);
            }
        }

        private DatiProtocolloFascicolato CreaDatiFascicoloLetto(PraticaAperta praticaAperta)
        {
            var datiProtoFascLetto = new DatiProtocolloFascicolato();
            datiProtoFascLetto.AnnoFascicolo = praticaAperta.Anno;
            datiProtoFascLetto.Classifica = praticaAperta.CodiceRegistro;
            datiProtoFascLetto.DataFascicolo = praticaAperta.dataApertura.ToString("dd/MM/yyyy");
            datiProtoFascLetto.NumeroFascicolo = praticaAperta.Numero;
            datiProtoFascLetto.Fascicolato = EnumFascicolato.si;

            return datiProtoFascLetto;
        }

        public override DatiFascicolo Fascicola(Fascicolo fascicolo)
        {
            try
            {
                if (fascicolo == null)
                    return null;

                _protocolloLogs.DebugFormat("Inizio fascicolazione fascicolo numero: {0}, data: {1}, classifica: {2}, anno: {3}", fascicolo.NumeroFascicolo, fascicolo.DataFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);

                string idProto = String.Empty;
                string numProto = String.Empty;
                string annoProto = String.Empty;

                try
                {
                    _protocolloLogs.DebugFormat("IDPROTOCOLLO: {0}, NUMEROPROTOCOLLO: {1}, DATAPROTOCOLLO: {2}", this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.HasValue ? this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : "null");

                    idProto = DatiProtocollo.Istanza.FKIDPROTOCOLLO;
                    numProto = DatiProtocollo.Istanza.NUMEROPROTOCOLLO;
                    annoProto = DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.ToString("yyyy");
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE DURANTE IL RECUPERO DELLE INFORMAZIONI DELL'ISTANZA", ex);
                }

                var proto = LeggiProtocollo(idProto, annoProto, numProto);

                SetParamFromVertInsiel();
                using (var ws = CreaWebService())
                {
                    DatiFascicolo datiFascicolo = new DatiFascicolo();

                    var request = new AperturaPraticaRequest();
                    request.Utente = GetUtenteProtocollo();
                    request.codiceUfficio = proto.InCaricoA;
                    request.codiceRegistro = new Classifica { Item = fascicolo.Classifica };

                    request.oggetto = fascicolo.Oggetto;
                    request.anno = fascicolo.AnnoFascicolo.ToString();
                    if (!String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
                    {
                        request.numerazioneManuale = true;
                        request.numerazioneManualeSpecified = true;
                        request.numero = fascicolo.NumeroFascicolo;
                    }

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, request);
                    _protocolloLogs.InfoFormat("Chiamata a web method aperturaPratica (Fascicolazione), username: {0}, password: {1}, file request fascicolazione: {2}", _codiceUtente, _passwordUtente, ProtocolloLogsConstants.CreaFascicoloRequestFileName);
                    var response = ws.aperturaPratica(request);
                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaFascicoloResponseFileName, response);

                    if (response.Item != null)
                    {
                        if (response.Item is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)response.Item;

                            var errorWarning = String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione);
                            _protocolloLogs.Warn(errorWarning);

                            datiFascicolo.Warning = errorWarning;
                        }
                        else if (response.Item is PraticaAperta)
                        {
                            var praticaAperta = (PraticaAperta)response.Item;
                            datiFascicolo = CreaDatiFascicolazione(praticaAperta);
                            Riprotocolla(proto, praticaAperta.ProgDoc, praticaAperta.ProgMovi);
                        }
                    }

                    return datiFascicolo;
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE VERIFICATO DURANTE LA FASCICOLAZIONE", ex);
            }
        }

        private DatiFascicolo CreaDatiFascicolazione(PraticaAperta praticaAperta)
        {
            var datiFascicolo = new DatiFascicolo();
            datiFascicolo.AnnoFascicolo = praticaAperta.Anno;
            datiFascicolo.DataFascicolo = praticaAperta.dataApertura.ToString("dd/MM/yyyy");
            datiFascicolo.NumeroFascicolo = praticaAperta.Numero;

            return datiFascicolo;
        }

        #endregion

        #region Utilities

        private string[] GetIdProtocollo(string idProtocollo)
        {
            if (String.IsNullOrEmpty(idProtocollo))
                throw new Exception("L'ID DEL PROTOCOLLO NON E' VALORIZZATO, NON E' POSSIBILE LEGGERE IL PROTOCOLLO");

            var arrIdProtocollo = idProtocollo.Split(SEPARATORE_ID_PROTOCOLLO.ToCharArray());
            if (arrIdProtocollo.Length == 1)
                throw new Exception(String.Format("L'ID DEL PROTOCOLLO DEVE CONTENERE IL PROGDOC E IL PROGMOVI SEPARATI DAL CARATTERE PIPE ('{0}') AD ESEMPIO 3{0}1, IN QUESTO CASO NON E' PRESENTE IL VALORE PROGMOVI", SEPARATORE_ID_PROTOCOLLO));

            return arrIdProtocollo;
        }

        private ProtocolloInsielService.Utente GetUtenteProtocollo()
        {
            return new ProtocolloInsielService.Utente()
            {
                codice = _codiceUtente,
                password = _passwordUtente
            };
        }

        private ProtocolloFilesInsielService.Utente GetUtenteFilesProtocollo()
        {
            return new ProtocolloFilesInsielService.Utente()
            {
                codice = _codiceUtente,
                password = _passwordUtente
            };

        }


        #endregion

        #region Annulla Protocollo

        public override void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            try
            {
                SetParamFromVertInsiel();
                using (var ws = CreaWebService())
                {
                    var request = new AnnullamentoProtocolloRequest();
                    request.Utente = GetUtenteProtocollo();

                    var arrIdProtocollo = GetIdProtocollo(idProtocollo);

                    request.Registrazione = new ProtocolloInsielService.ProtocolloRequest
                    {
                        Item = new ProtocolloInsielService.IdProtocollo
                        {
                            ProgDoc = long.Parse(arrIdProtocollo[0]),
                            ProgMovi = arrIdProtocollo[1]
                        }
                    };

                    request.Provvedimento = new EstremiProvvedimento
                    {
                        motivo = String.Concat(motivoAnnullamento, " - ", noteAnnullamento),
                        data = DateTime.Now
                    };

                    var strXmlRequest = _protocolloSerializer.Serialize(ProtocolloLogsConstants.AnnullaProtocolloSoapRequestFileName, request);

                    _protocolloLogs.InfoFormat("Chiamata a web method annullaProtocollo, request: {0}, file request: {1}", strXmlRequest, ProtocolloLogsConstants.AnnullaProtocolloSoapRequestFileName);
                    var response = ws.annullaProtocollo(request);
                    _protocolloLogs.Debug("AnnullaProtocollo, risposta ricevuta da annullaProtocollo");

                    var strXmlResponse = _protocolloSerializer.Serialize(ProtocolloLogsConstants.AnnullaProtocolloSoapResponseFileName, response);

                    if (!response.esito)
                        throw new Exception(String.Format("ERRORE CODICE: {0}, DESCRIZIONE: {1}", response.Errore.codice, response.Errore.descrizione));

                    _protocolloLogs.InfoFormat("ANNULLAMENTE PROTOCOLLO AVVENUTO CON SUCCESSO, response: {0}, file response: {1}", strXmlResponse, ProtocolloLogsConstants.AnnullaProtocolloSoapResponseFileName);
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("SI E' VERIFICATO UN ERRORE DURANTE L'ANNULLAMENTO DEL PROTOCOLLO", ex);
            }
        }

        public override DatiProtocolloAnnullato IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                SetParamFromVertInsiel();

                if (_disabilitaAnnullaProtocollo)
                    return base.IsAnnullato(idProtocollo, annoProtocollo, numeroProtocollo);

                _protocolloLogs.Debug("Verifica dell'annullamento del protocollo");
                DatiProtocolloLetto protoLetto = LeggiProtocollo(idProtocollo, annoProtocollo, numeroProtocollo);
                _protocolloLogs.DebugFormat("Protocollo annullato, dato proveniente dalla lettura del protocollo: {0}", protoLetto.Annullato);


                var res = new DatiProtocolloAnnullato();
                res.Annullato = EnumAnnullato.no;

                if (protoLetto.Annullato == "1")
                    res.Annullato = EnumAnnullato.si;

                _protocolloLogs.DebugFormat("Protocollo annullato: {0}", res.Annullato.ToString());

                return res;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI RIGUARDANTI L'ANNULLAMENTO DEL PROTOCOLLO", ex);
            }
        }

        #endregion

        #region Leggi Protocollo

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                SetParamFromVertInsiel();
                using (var ws = CreaWebService())
                {

                    var arrIdProtocollo = GetIdProtocollo(idProtocollo);

                    var progDoc = long.Parse(arrIdProtocollo[0]);
                    var progMovi = arrIdProtocollo[1];

                    var request = new DettagliProtocolloRequest();

                    request.Utente = GetUtenteProtocollo();

                    if (!String.IsNullOrEmpty(idProtocollo))
                        request.Registrazione = new ProtocolloInsielService.ProtocolloRequest
                        {
                            Item = new ProtocolloInsielService.IdProtocollo
                            {
                                ProgDoc = progDoc,
                                ProgMovi = progMovi
                            }
                        };

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);
                    _protocolloLogs.InfoFormat("Chiamata a web method dettagliProtocollo (LeggiProtocollo), id protocollo: {0}, numero protocollo: {1}, anno protocollo: {2}", idProtocollo, numeroProtocollo, annoProtocollo);

                    var response = ws.dettagliProtocolllo(request);

                    _protocolloLogs.Debug("LeggiProtocollo, risposta ricevuta da dettagliProtocollo");
                    //_protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

                    DatiProtocolloLetto retVal = null;

                    _protocolloLogs.DebugFormat("Esito lettura protocollo: {0}", response.esito);

                    if (response.Item != null)
                    {
                        if (response.Item is ProtocolloInsielService.DettagliProtocollo)
                        {
                            var protRes = (DettagliProtocollo)response.Item;
                            retVal = CreaDatiProtocolloLetto(protRes, ws);
                        }

                        if (response.Item is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)response.Item;
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL SISTEMA DI PROTOCOLLAZIONE INSIEL, CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                        }
                    }
                    else
                        throw new Exception("LA PROPRIETA' ITEMS NON E' STATA VALORIZZATA");

                    return retVal;
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, IdProtocollo {0}, NumeroProtocollo: {1}, AnnoProtocollo: {2}", idProtocollo, numeroProtocollo, annoProtocollo), ex);
            }
        }

        private DatiProtocolloLetto CreaDatiProtocolloLetto(DettagliProtocollo res, ProtocolloPTClient ws)
        {
            try
            {
                var rVal = new DatiProtocolloLetto();

                rVal.IdProtocollo = String.Concat(res.InfoGenerali.protoProgDoc.Value.ToString(), SEPARATORE_ID_PROTOCOLLO, res.InfoGenerali.protoProgMovi.Value.ToString());
                rVal.NumeroProtocollo = res.InfoGenerali.protoNumProt.Value.ToString();
                rVal.DataProtocollo = res.InfoGenerali.protoDataOraAgg.Value.ToString("dd/MM/yyyy");
                rVal.AnnoProtocollo = res.InfoGenerali.protoAnnoProt.Value.ToString();
                rVal.TipoDocumento = res.InfoGenerali.docCodTipoDoc;
                rVal.TipoDocumento_Descrizione = res.InfoGenerali.tipoDocDescTipoDoc;
                rVal.Oggetto = res.InfoGenerali.docDescOgge;

                rVal.InCaricoA = res.InfoGenerali.regCodAna;
                rVal.InCaricoA_Descrizione = res.InfoGenerali.regDescAna;

                if (res.InfoGenerali.protoApProt == ProtocolloConstants.COD_ARRIVO && res.Uffici != null && res.Uffici.Count() > 0)
                {
                    rVal.InCaricoA = res.Uffici[0].codUff;
                    rVal.InCaricoA_Descrizione = res.Uffici[0].descUff;
                }

                

                if (res.Pratiche.Length > 0)
                {
                    rVal.AnnoNumeroPratica = String.Format("{0}/{1}", res.Pratiche[res.Pratiche.Length - 1].anno, res.Pratiche[res.Pratiche.Length - 1].numero);
                    rVal.NumeroPratica = String.Concat(res.Pratiche[res.Pratiche.Length - 1].prog_doc, SEPARATORE_ID_PROTOCOLLO, res.Pratiche[res.Pratiche.Length - 1].prog_movi);
                }

                if (res.Classifiche.Length > 0)
                {
                    rVal.Classifica = res.Classifiche[res.Classifiche.Length - 1].codClas;
                    rVal.Classifica_Descrizione = String.Format("{0} - {1}", res.Classifiche[res.Classifiche.Length - 1].codClas, res.Classifiche[res.Classifiche.Length - 1].descClas);
                }

                rVal.Annullato = EnumAnnullato.no.ToString();

                if (res.InfoGenerali.protoStato.GetValueOrDefault(0) == 1)
                    rVal.Annullato = EnumAnnullato.si.ToString();

                rVal.Origine = res.InfoGenerali.protoApProt;

                #region Mittenti / Destinatari

                List<MittDestOut> mittDestList = new List<MittDestOut>();

                if (res.InfoGenerali.protoApProt == "A")
                {
                    List<Corrispondente> mittListProto = res.Mittenti.ToList();
                    mittListProto.ForEach(x => mittDestList.Add(new MittDestOut { IdSoggetto = x.codUff, CognomeNome = x.descUff }));
                }
                else if (res.InfoGenerali.protoApProt == "P")
                {
                    List<Corrispondente> destListProto = res.Destinatari.ToList();
                    destListProto.ForEach(x => mittDestList.Add(new MittDestOut { IdSoggetto = x.codUff, CognomeNome = x.descUff }));
                }

                rVal.MittentiDestinatari = mittDestList.ToArray();

                #endregion

                #region Allegati

                List<AllOut> allegati = new List<AllOut>();
                List<DocumentoAllegato> docRes = res.Documenti.ToList();

                rVal.Allegati = docRes.Select(x => new AllOut
                {
                    IDBase = x.idDoc.Value.ToString(),
                    Serial = x.nome,
                    TipoFile = x.tipoDoc,
                    Commento = x.nome
                }).ToArray();

                #endregion

                #region Dati Fascicolazione



                #endregion

                return rVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA VALORIZZAZIONE DEI DATI DI PROTOCOLLO DOPO LA LETTURA", ex);
            }
        }

        #endregion

        #region Leggi Allegato

        public override AllOut LeggiAllegato()
        {
            try
            {
                SetParamFromVertInsiel();
                using (var ws = CreaWebServiceUpload())
                {
                    _protocolloLogs.Debug("Chiamata a DownloadDocumento");
                    _protocolloLogs.DebugFormat("IdAllegato: {0}, CodiceUtente: {1}, Password: {2}", IdAllegato, _codiceUtente, _passwordUtente);
                    var request = new ProtocolloFilesInsielService.DownloadDocumentoRequest();
                    request.idDoc = long.Parse(IdAllegato);
                    request.Utente = GetUtenteFilesProtocollo();

                    var arrIdProtocollo = GetIdProtocollo(IdProtocollo);

                    request.Registrazione = new ProtocolloFilesInsielService.ProtocolloRequest
                    {
                        Item = new ProtocolloFilesInsielService.IdProtocollo
                        {
                            ProgDoc = long.Parse(arrIdProtocollo[0]),
                            ProgMovi = arrIdProtocollo[1]
                        }
                    };

                    _protocolloLogs.InfoFormat("Chiamata a downloadDocumento (Leggi Allegato), Id Allegato: {0}, CodiceUtente: {1}, Password: {2}", IdAllegato, _codiceUtente, _passwordUtente);
                    var response = ws.downloadDocumento(request);

                    _protocolloSerializer.Serialize("downloadFile", response);
                    _protocolloLogs.Debug("Fine Chiamata a DownloadDocumento");

                    if (response.esito.GetValueOrDefault(false) && response.Errore != null)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.Errore.codice, response.Errore.descrizione));

                    var prot = LeggiProtocollo(IdProtocollo, AnnoProtocollo, NumProtocollo);

                    var all = prot.Allegati.Where(x => x.IDBase == IdAllegato).FirstOrDefault();

                    if (all == null)
                        throw new Exception("ALLEGATO NON TROVATO");

                    all.Image = response.documento.binaryData;

                    return all;
                }

            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("SI E' VERIFICATO UN ERRORE DURANTE IL DOWNLOAD DEL DOCUMENTO ", ex);
            }
        }

        #endregion

        #region Verticalizzazioni

        private void SetParamFromVertInsiel()
        {
            try
            {
                var vert = new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (vert.Attiva)
                {
                    _protocolloLogs.DebugFormat(@"Valori parametri verticalizzazioni: url: {0}, 
                                                                                     operatore: {1}, 
                                                                                     password: {2}, 
                                                                                     Url Upload File: {3},
                                                                                     Codice Ufficio Operante: {4},
                                                                                     Tipi Documento Ws: {5},
                                                                                     Escludi Classifica: {6},
                                                                                     Disabilita Annulla Protocollo: {7}",
                    vert.Url,
                    vert.Codiceutente,
                    vert.Password,
                    vert.UrlUploadfile,
                    vert.CodiceUfficioOperante,
                    vert.TipiDocumentoWs,
                    vert.EscludiClassifica,
                    vert.DisabilitaAnnullaProtocollo);

                    _codiceUtente = vert.Codiceutente;
                    _passwordUtente = vert.Password;
                    _url = vert.Url;
                    _urlFilesUpload = vert.UrlUploadfile;
                    _codiceUfficioOperante = vert.CodiceUfficioOperante;
                    _usaWsPerTipiDoc = vert.GetBool(TIPI_DOCUMENTO_WS_NAME);
                    _escludiClassifica = vert.GetBool(ESCLUDI_CLASSIFICA_PARAM_NAME);
                    _disabilitaAnnullaProtocollo = vert.GetBool(DISABILITA_ANNULLA_PROTOCOLLO_PARAM_NAME);
                    _codiceRegistro = vert.Codiceregistro;
                }
                else
                    throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_INSIEL NON È ATTIVA.");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI DATI DALLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL", ex);
            }
        }

        #endregion

        #region Tipi Documento

        public override ListaTipiDocumento GetTipiDocumento()
        {
            try
            {
                SetParamFromVertInsiel();
                if (_usaWsPerTipiDoc)
                {
                    using (var ws = CreaWebService())
                    {
                        var request = new getTipiDocRequest();

                        request.Utente = GetUtenteProtocollo();

                        _protocolloLogs.DebugFormat("Chiamata a getTipiDoc (Recupero Tipi Documento) CodiceUtente: {1}, Password: {2}", _codiceUtente, _passwordUtente);

                        var response = ws.getTipiDoc(request);
                        _protocolloLogs.Debug("Ricevuta risposta da getTipiDoc");

                        _protocolloSerializer.Serialize(ProtocolloLogsConstants.TipiDocumentoSoapResponseFileName, response);
                        ListaTipiDocumento listaTipiDocumento = null;
                        if (response.esito)
                            listaTipiDocumento = CreaTipiDocumento(response);
                        else
                        {
                            var err = response.Items as ProtocolloInsielService.Errore[];
                            throw new Exception(String.Format("CODICE ERRORE RESTITUITO DAL WEB SERVICE: {0}, DESCRIZIONE ERRORE RESTITUIRO DAL WEB SERVICE: {1}", err[0].codice, err[1].descrizione));
                        }

                        return listaTipiDocumento;
                    }
                }
                else
                    return base.GetTipiDocumento();
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEI TIPI DOCUMENTO DAL WEB SERVICE.", ex);
            }
        }

        private ListaTipiDocumento CreaTipiDocumento(getTipiDocResponse response)
        {
            try
            {
                _protocolloLogs.Debug("Valorizzazione tipi documento recuperati da web method getTipiDoc() del web service Insiel");
                var res = new ListaTipiDocumento();
                var listaTipiDocs = new List<ListaTipiDocumentoDocumento>();

                //var items = (ProtocolloInsielService.TipiDocumento[])response.Items;

                foreach (var el in response.Items)
                {
                    var tipoDoc = new ListaTipiDocumentoDocumento();
                    var tipoDocSource = (ProtocolloInsielService.TipiDocumento)el;
                    tipoDoc.Codice = tipoDocSource.codice;
                    tipoDoc.Descrizione = tipoDocSource.descrizione.ToUpper();

                    listaTipiDocs.Add(tipoDoc);
                }

                res.Documento = listaTipiDocs.ToArray();

                _protocolloLogs.DebugFormat("Fine valorizzazione tipi documento recuperati da web method getTipiDoc() del web service Insiel, numero tipi documento tornati: {0}", res.Documento.Length);

                return res;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("VALORIZZAZIONE DEI TIPI DOCUMENTO RECUPERATI DA WEB SERVICE NON CORRETTA", ex);
            }
        }

        #endregion

    }
}
