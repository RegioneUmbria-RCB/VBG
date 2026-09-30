using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloFilesInsielService;
using ProtocolloInsielService;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    /// <summary>
    /// Protocollo utilizzato a Trieste
    /// </summary>
    internal class PROTOCOLLO_INSIEL : ProtocolloBase
    {

        #region Membri privati della classe
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly ProtocollazioneClientServiceCreator _protocollazioneClientServiceCreator;
        private readonly ClientUploadServiceCreator _clientUploadServiceCreator;

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

        public PROTOCOLLO_INSIEL(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._protocollazioneClientServiceCreator = new ProtocollazioneClientServiceCreator(this._protocolloLogs, bindingFactory, this._url);
            this._clientUploadServiceCreator = new ClientUploadServiceCreator(this._protocolloLogs, bindingFactory, this._urlFilesUpload);
        }


        #region Protocollazione

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn pProt)
        {

            try
            {
                this.SetParamFromVertInsiel();
                using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                {
                    this._warnings = new List<string>();
                    var request = new InserimentoProtocolloRequest();
                    request.utente = this.GetUtenteProtocollo();

                    switch (pProt.Flusso)
                    {
                        case "A":
                            this.SetDatiProtocolloInArrivo(request, pProt);
                            break;
                        case "P":
                            this.SetDatiProtocolloInPartenza(request, pProt);
                            break;
                        case "I":
                            throw new Exception("PROTOCOLLAZIONE INTERNA NON GESTITA DAL SISTEMA DI PROTOCOLLAZIONE");
                        default:
                            throw new Exception("I FLUSSI GESTITI SONO SOLAMENTE IN ARRIVO E PARTENZA, FLUSSO RICHIESTO: " + pProt.Flusso);
                    }

                    request.oggetto = pProt.Oggetto;
                    this._protocolloLogs.DebugFormat("Recupero della classifica: {0}", pProt.Classifica);

                    if (!this._escludiClassifica)
                    {
                        var classifica = pProt.Classifica;

                        if (!String.IsNullOrEmpty(pProt.Classifica))
                            classifica = pProt.Classifica.Replace("x", " ");

                        request.classifiche = new Classifica[] { new Classifica { Item = classifica } };
                    }

                    request.estremi_documento = new EstremiDocumento { tipo = pProt.TipoDocumento };

                    this._protocolloLogs.Debug("Recupero degli allegati");

                    request.documenti = this.GetDocumenti(pProt);

                    this._protocolloLogs.Debug("Allegati recuperati");
                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);

                    this._protocolloLogs.InfoFormat("Chiamata a web method inserisciProtocollo, request: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                    var res = ws.Service.inserisciProtocollo(request);
                    this._protocolloLogs.Debug("Risposta da parte del web service");

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, res);

                    DatiProtocolloResponseType retVal = null;

                    this._protocolloLogs.DebugFormat("Esito protocollazione: {0}", res.esito);

                    if (res.Items != null && res.Items.Length > 0)
                    {
                        if (res.Items[0] is ProtocolloInsielService.ProtocolloResponse)
                        {
                            var protRes = (ProtocolloResponse)res.Items[0];
                            this._protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE");

                            retVal = this.CreaDatiProtocollo(protRes);
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
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        private DatiProtocolloResponseType CreaDatiProtocollo(ProtocolloResponse res)
        {
            try
            {
                var datiProtocollo = new DatiProtocolloResponseType();
                datiProtocollo.AnnoProtocollo = res.Anno;
                datiProtocollo.NumeroProtocollo = res.Numero;
                datiProtocollo.DataProtocollo = res.Data.ToString("dd/MM/yyyy");
                datiProtocollo.IdProtocollo = String.Concat(res.ProgDoc.ToString(), SEPARATORE_ID_PROTOCOLLO, res.ProgMovi);

                if (this._warnings.Count > 0)
                    datiProtocollo.Warning = String.Join("; ", this._warnings.ToArray());

                this._protocolloLogs.InfoFormat("DATI PROTOCOLLAZIONE, Id Protocollo: {0}, Numero: {1}, Data: {2}, Anno: {3}", datiProtocollo.IdProtocollo, datiProtocollo.NumeroProtocollo, datiProtocollo.DataProtocollo, datiProtocollo.AnnoProtocollo);

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

                var ufficio = base.GetUfficioRegistro(this._codiceRegistro);

                request.codice_ufficio = ufficio;
                request.codice_registro = this._codiceRegistro;

                request.codice_ufficio_operante = this._codiceUfficioOperante;
                request.uffici = new UfficioInsProto[] { new UfficioInsProto { codice = pProt.Destinatari.Amministrazione[0].PROT_UO, giaInviato = true, giaInviatoSpecified = true } };

                request.verso = ProtocolloInsielService.verso.A;

                var mittenti = new List<MittenteInsProto>();

                foreach (var amm in pProt.Mittenti.Amministrazione)
                {
                    if (String.IsNullOrEmpty(amm.PROT_UO) && String.IsNullOrEmpty(amm.PROT_RUOLO))
                    {

                        if (mittenti.Where(x => x.descrizione == amm.AMMINISTRAZIONE).Count() == 0)
                        {
                            var datiAnag = this.GetDatiAmministrazione(amm);
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
                            this._protocolloLogs.WarnFormat("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE);
                            this._warnings.Add(String.Format("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE));
                        }
                    }
                }

                foreach (var anag in pProt.Mittenti.Anagrafe)
                {
                    var nomeCompleto = anag.GetNomeCompleto();
                    if (mittenti.Where(x => x.descrizione == nomeCompleto).Count() == 0)
                    {
                        //In teoria non è obbligatorio popolare questi dati, però avendoceli li inserisco.
                        var datiAnag = this.GetDatiAnagrafici(anag);

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
                        this._protocolloLogs.WarnFormat("L'ANAGRAFICA {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", nomeCompleto);
                        this._warnings.Add(String.Format("L'ANAGRAFICA {0} NON E' STATA INSERITA COME MITTENTE IN QUANTO GIA' PRESENTE", nomeCompleto));
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

                var ufficio = base.GetUfficioRegistro(this._codiceRegistro);

                request.codice_ufficio = ufficio;
                request.codice_registro = this._codiceRegistro;

                request.codice_ufficio_operante = this._codiceUfficioOperante;

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
                                dati_anagrafica = this.GetDatiAmministrazione(amm),
                                descrizione = amm.AMMINISTRAZIONE.Replace("  ", " "),
                                inserisci = true,
                                inserisciSpecified = true
                            });
                        }
                        else
                        {
                            this._protocolloLogs.WarnFormat("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE);
                            this._warnings.Add(String.Format("L'AMMINISTRAZIONE {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", amm.AMMINISTRAZIONE));
                        }
                    }
                }


                foreach (var anag in pProt.Destinatari.Anagrafe)
                {
                    //In teoria non è obbligatorio popolare questi dati, però avendoceli li inserisco.

                    var datiAnag = this.GetDatiAnagrafici(anag);
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
                        this._protocolloLogs.WarnFormat("L'ANAGRAFICA {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", nomeCompleto);
                        this._warnings.Add(String.Format("L'ANAGRAFICA {0} NON E' STATA INSERITA COME DESTINATARIO IN QUANTO GIA' PRESENTE", nomeCompleto));
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

        private DatiAnagrafica GetDatiAmministrazione(ProtocolloAmministrazioni amm)
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
                localita = anag.ComuneResidenza != null ? anag.ComuneResidenza.DenominazioneComune : null,
                provincia = anag.ComuneResidenza != null ? anag.ComuneResidenza.SiglaProvincia : null,
                piva = !String.IsNullOrEmpty(anag.PARTITAIVA) ? (anag.PARTITAIVA.Length == 11 ? anag.PARTITAIVA : null) : null
            };

        }

        private documentoInsProto[] GetDocumenti(DatiProtocolloIn pProt)
        {
            try
            {
                var res = new List<documentoInsProto>();

                using (var wsu = this._clientUploadServiceCreator.CreateClient())
                {
                    var request = new UploadRequest();
                    var isPrimario = true;
                    foreach (var all in pProt.RecuperaAllegati())
                    {

                        this._protocolloLogs.DebugFormat("file da allegare, Id: {0}, CodiceOggetto: {1}, NomeFile: {2}", all.ID, all.CODICEOGGETTO, all.NOMEFILE);

                        request.codiceUtente = this._codiceUtente;
                        request.passwordUtente = this._passwordUtente;

                        var att = new AttachmentData();

                        att.tipoFile = String.Empty;
                        att.fileName = all.NOMEFILE;
                        att.binaryData = all.OGGETTO;

                        request.documento = att;

                        this._protocolloLogs.InfoFormat("Chiamata a web method updload del web service di upload allegati, codice oggetto: {0}, nome file: {1}, descrizione file: {2}", all.CODICEOGGETTO, all.NOMEFILE, all.Descrizione);
                        var upl = wsu.Service.upload(request);

                        if (!upl.esito.GetValueOrDefault(false))
                            throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", upl.Errore.codice, upl.Errore.descrizione));

                        this._protocolloLogs.Info("UPLOAD AVVENUTO CON SUCCESSO");

                        var docInsProto = new documentoInsProto();
                        docInsProto.id = upl.idDocumento;
                        docInsProto.is_primario = isPrimario;
                        docInsProto.is_primarioSpecified = true;
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

        private void Riprotocolla(DatiProtocolloLettoResponseType proto, long progDocPrat, string progMoviPrat)
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

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {

            try
            {
                var proto = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = idProtocollo, AnnoProtocollo = annoProtocollo, NumeroProtocollo = numeroProtocollo });
                this.SetParamFromVertInsiel();
                using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                {
                    var request = new DettagliPraticaRequest();
                    request.Utente = this.GetUtenteProtocollo();
                    var arrIdProtocollo = this.GetIdProtocollo(proto.FirstOrDefault().NumeroPratica);
                    request.Pratica = new PraticaRequest
                    {
                        Item = new ProtocolloInsielService.IdProtocollo
                        {
                            ProgDoc = long.Parse(arrIdProtocollo[0]),
                            ProgMovi = arrIdProtocollo[1]
                        }
                    };

                    this._protocolloLogs.DebugFormat("Chiamata a web method dettagliPratica, username: {0}, password: {1}, id protocollo: {2}, numero protocollo: {3}, anno protocollo: {4}", this._codiceUtente, this._passwordUtente, idProtocollo, numeroProtocollo, annoProtocollo);
                    var response = ws.Service.dettagliPratica(request);

                    DatiProtocolloFascicolatoResponseType datiProtoFasc = null;

                    if (response.Item != null)
                    {
                        if (response.Item is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)response.Item;
                            this._protocolloLogs.DebugFormat("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione);

                            throw new Exception(String.Format("ERRORE DURANTE LA FASCICOLAZIONE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione));
                        }
                        else if (response.Item is PraticaAperta)
                        {
                            var praticaAperta = (PraticaAperta)response.Item;
                            datiProtoFasc = this.CreaDatiFascicoloLetto(praticaAperta);
                        }
                    }
                    return datiProtoFasc;
                }

            }
            catch (Exception ex)
            {

                throw this._protocolloLogs.LogErrorException("SI E' VERIFICATO UN ERRORE DURANTE LA VERIFICA DELLA FASCICOLAZIONE", ex);
            }
        }

        private DatiProtocolloFascicolatoResponseType CreaDatiFascicoloLetto(PraticaAperta praticaAperta)
        {
            var datiProtoFascLetto = new DatiProtocolloFascicolatoResponseType();
            datiProtoFascLetto.AnnoFascicolo = praticaAperta.Anno;
            datiProtoFascLetto.Classifica = praticaAperta.CodiceRegistro;
            datiProtoFascLetto.DataFascicolo = praticaAperta.dataApertura.ToString("dd/MM/yyyy");
            datiProtoFascLetto.NumeroFascicolo = praticaAperta.Numero;
            datiProtoFascLetto.Fascicolato = EnumFascicolatoType.si;

            return datiProtoFascLetto;
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            try
            {
                if (fascicolo == null)
                    return null;

                this._protocolloLogs.DebugFormat("Inizio fascicolazione fascicolo numero: {0}, data: {1}, classifica: {2}, anno: {3}", fascicolo.NumeroFascicolo, fascicolo.DataFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);

                var idProto = String.Empty;
                var numProto = String.Empty;
                var annoProto = String.Empty;

                try
                {
                    this._protocolloLogs.DebugFormat("IDPROTOCOLLO: {0}, NUMEROPROTOCOLLO: {1}, DATAPROTOCOLLO: {2}", this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.HasValue ? this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : "null");

                    idProto = this.DatiProtocollo.Istanza.FKIDPROTOCOLLO;
                    numProto = this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO;
                    annoProto = this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.ToString("yyyy");
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE DURANTE IL RECUPERO DELLE INFORMAZIONI DELL'ISTANZA", ex);
                }

                var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = idProto, AnnoProtocollo = annoProto, NumeroProtocollo = numProto });
                var proto = protocolli.FirstOrDefault();

                this.SetParamFromVertInsiel();
                using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                {
                    DatiFascicoloResponseType datiFascicolo = new DatiFascicoloResponseType();

                    var request = new AperturaPraticaRequest();
                    request.Utente = this.GetUtenteProtocollo();
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

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.CreaFascicoloRequestFileName, request);
                    this._protocolloLogs.InfoFormat("Chiamata a web method aperturaPratica (Fascicolazione), username: {0}, password: {1}, file request fascicolazione: {2}", this._codiceUtente, this._passwordUtente, ProtocolloLogsConstants.CreaFascicoloRequestFileName);
                    var response = ws.Service.aperturaPratica(request);
                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.CreaFascicoloResponseFileName, response);

                    if (response.Item != null)
                    {
                        if (response.Item is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)response.Item;

                            var errorWarning = String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", err.codice, err.descrizione);
                            this._protocolloLogs.Warn(errorWarning);

                            datiFascicolo.Warning = errorWarning;
                        }
                        else if (response.Item is PraticaAperta)
                        {
                            var praticaAperta = (PraticaAperta)response.Item;
                            datiFascicolo = this.CreaDatiFascicolazione(praticaAperta);
                            this.Riprotocolla(proto, praticaAperta.ProgDoc, praticaAperta.ProgMovi);
                        }
                    }

                    return datiFascicolo;
                }
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE VERIFICATO DURANTE LA FASCICOLAZIONE", ex);
            }
        }

        private DatiFascicoloResponseType CreaDatiFascicolazione(PraticaAperta praticaAperta)
        {
            var datiFascicolo = new DatiFascicoloResponseType();
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
                codice = this._codiceUtente,
                password = this._passwordUtente
            };
        }

        private ProtocolloFilesInsielService.Utente GetUtenteFilesProtocollo()
        {
            return new ProtocolloFilesInsielService.Utente()
            {
                codice = this._codiceUtente,
                password = this._passwordUtente
            };

        }


        #endregion

        #region Annulla Protocollo

        public override void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            try
            {
                this.SetParamFromVertInsiel();
                using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                {
                    var request = new AnnullamentoProtocolloRequest();
                    request.Utente = this.GetUtenteProtocollo();

                    var arrIdProtocollo = this.GetIdProtocollo(idProtocollo);

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

                    var strXmlRequest = this._protocolloSerializer.Serialize(ProtocolloLogsConstants.AnnullaProtocolloSoapRequestFileName, request);

                    this._protocolloLogs.InfoFormat("Chiamata a web method annullaProtocollo, request: {0}, file request: {1}", strXmlRequest, ProtocolloLogsConstants.AnnullaProtocolloSoapRequestFileName);
                    var response = ws.Service.annullaProtocollo(request);
                    this._protocolloLogs.Debug("AnnullaProtocollo, risposta ricevuta da annullaProtocollo");

                    var strXmlResponse = this._protocolloSerializer.Serialize(ProtocolloLogsConstants.AnnullaProtocolloSoapResponseFileName, response);

                    if (!response.esito)
                        throw new Exception(String.Format("ERRORE CODICE: {0}, DESCRIZIONE: {1}", response.Errore.codice, response.Errore.descrizione));

                    this._protocolloLogs.InfoFormat("ANNULLAMENTE PROTOCOLLO AVVENUTO CON SUCCESSO, response: {0}, file response: {1}", strXmlResponse, ProtocolloLogsConstants.AnnullaProtocolloSoapResponseFileName);
                }
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("SI E' VERIFICATO UN ERRORE DURANTE L'ANNULLAMENTO DEL PROTOCOLLO", ex);
            }
        }

        public override DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                this.SetParamFromVertInsiel();

                if (this._disabilitaAnnullaProtocollo)
                    return base.IsAnnullato(idProtocollo, annoProtocollo, numeroProtocollo);

                this._protocolloLogs.Debug("Verifica dell'annullamento del protocollo");
                var protoLetto = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = idProtocollo, AnnoProtocollo = annoProtocollo, NumeroProtocollo = numeroProtocollo });
                var singoloProtocollo = protoLetto.FirstOrDefault();
                this._protocolloLogs.DebugFormat("Protocollo annullato, dato proveniente dalla lettura del protocollo: {0}", singoloProtocollo.Annullato);


                var res = new DatiProtocolloAnnullatoResponseType();
                res.Annullato = EnumAnnullatoType.no;

                if (singoloProtocollo.Annullato == "1")
                    res.Annullato = EnumAnnullatoType.si;

                this._protocolloLogs.DebugFormat("Protocollo annullato: {0}", res.Annullato.ToString());

                return res;
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI RIGUARDANTI L'ANNULLAMENTO DEL PROTOCOLLO", ex);
            }
        }

        #endregion

        #region Leggi Protocollo

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                this.SetParamFromVertInsiel();
                using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                {

                    var arrIdProtocollo = this.GetIdProtocollo(leggiProtocolloRequest.IdProtocollo);

                    var progDoc = long.Parse(arrIdProtocollo[0]);
                    var progMovi = arrIdProtocollo[1];

                    var request = new DettagliProtocolloRequest();

                    request.Utente = this.GetUtenteProtocollo();

                    if (!String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo))
                        request.Registrazione = new ProtocolloInsielService.ProtocolloRequest
                        {
                            Item = new ProtocolloInsielService.IdProtocollo
                            {
                                ProgDoc = progDoc,
                                ProgMovi = progMovi
                            }
                        };

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);
                    this._protocolloLogs.InfoFormat("Chiamata a web method dettagliProtocollo (LeggiProtocollo), id protocollo: {0}, numero protocollo: {1}, anno protocollo: {2}", leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo);

                    var response = ws.Service.dettagliProtocolllo(request);

                    this._protocolloLogs.Debug("LeggiProtocollo, risposta ricevuta da dettagliProtocollo");
                    //_protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

                    DatiProtocolloLettoResponseType retVal = null;

                    this._protocolloLogs.DebugFormat("Esito lettura protocollo: {0}", response.esito);

                    if (response.Item != null)
                    {
                        if (response.Item is ProtocolloInsielService.DettagliProtocollo)
                        {
                            var protRes = (DettagliProtocollo)response.Item;
                            retVal = this.CreaDatiProtocolloLetto(protRes, ws.Service);
                        }

                        if (response.Item is ProtocolloInsielService.Errore)
                        {
                            var err = (ProtocolloInsielService.Errore)response.Item;
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL SISTEMA DI PROTOCOLLAZIONE INSIEL, CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                        }
                    }
                    else
                        throw new Exception("LA PROPRIETA' ITEMS NON E' STATA VALORIZZATA");

                    return new List<DatiProtocolloLettoResponseType>() { retVal };
                }
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, IdProtocollo {0}, NumeroProtocollo: {1}, AnnoProtocollo: {2}", leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo), ex);
            }
        }

        private DatiProtocolloLettoResponseType CreaDatiProtocolloLetto(DettagliProtocollo res, ProtocolloPTClient ws)
        {
            try
            {
                var rVal = new DatiProtocolloLettoResponseType();

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

                rVal.Annullato = EnumAnnullatoType.no.ToString();

                if (res.InfoGenerali.protoStato.GetValueOrDefault(0) == 1)
                    rVal.Annullato = EnumAnnullatoType.si.ToString();

                rVal.Origine = res.InfoGenerali.protoApProt;

                #region Mittenti / Destinatari

                List<MittDestOutType> mittDestList = new List<MittDestOutType>();

                if (res.InfoGenerali.protoApProt == "A")
                {
                    List<Corrispondente> mittListProto = res.Mittenti.ToList();
                    mittListProto.ForEach(x => mittDestList.Add(new MittDestOutType { IdSoggetto = x.codUff, CognomeNome = x.descUff }));
                }
                else if (res.InfoGenerali.protoApProt == "P")
                {
                    List<Corrispondente> destListProto = res.Destinatari.ToList();
                    destListProto.ForEach(x => mittDestList.Add(new MittDestOutType { IdSoggetto = x.codUff, CognomeNome = x.descUff }));
                }

                rVal.MittentiDestinatari = mittDestList.ToArray();

                #endregion

                #region Allegati

                List<AllegatoResponseType> allegati = new List<AllegatoResponseType>();
                List<DocumentoAllegato> docRes = res.Documenti.ToList();

                rVal.Allegati = docRes.Select(x => new AllegatoResponseType
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

        public override AllegatoResponseType LeggiAllegato()
        {
            try
            {
                this.SetParamFromVertInsiel();
                using (var ws = this._clientUploadServiceCreator.CreateClient())
                {
                    this._protocolloLogs.Debug("Chiamata a DownloadDocumento");
                    this._protocolloLogs.DebugFormat("IdAllegato: {0}, CodiceUtente: {1}, Password: {2}", this.IdAllegato, this._codiceUtente, this._passwordUtente);
                    var request = new ProtocolloFilesInsielService.DownloadDocumentoRequest();
                    request.idDoc = long.Parse(this.IdAllegato);
                    request.Utente = this.GetUtenteFilesProtocollo();

                    var arrIdProtocollo = this.GetIdProtocollo(this.IdProtocollo);

                    request.Registrazione = new ProtocolloFilesInsielService.ProtocolloRequest
                    {
                        Item = new ProtocolloFilesInsielService.IdProtocollo
                        {
                            ProgDoc = long.Parse(arrIdProtocollo[0]),
                            ProgMovi = arrIdProtocollo[1]
                        }
                    };

                    this._protocolloLogs.InfoFormat("Chiamata a downloadDocumento (Leggi Allegato), Id Allegato: {0}, CodiceUtente: {1}, Password: {2}", this.IdAllegato, this._codiceUtente, this._passwordUtente);
                    var response = ws.Service.downloadDocumento(request);

                    this._protocolloSerializer.LogAndValidate("downloadFile", response);
                    this._protocolloLogs.Debug("Fine Chiamata a DownloadDocumento");

                    if (response.esito.GetValueOrDefault(false) && response.Errore != null)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.Errore.codice, response.Errore.descrizione));

                    var prot = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = this.IdProtocollo, AnnoProtocollo = this.AnnoProtocollo, NumeroProtocollo = this.NumProtocollo });

                    var all = prot.FirstOrDefault().Allegati.Where(x => x.IDBase == this.IdAllegato).FirstOrDefault();

                    if (all == null)
                        throw new Exception("ALLEGATO NON TROVATO");

                    all.Image = response.documento.binaryData;

                    return all;
                }

            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("SI E' VERIFICATO UN ERRORE DURANTE IL DOWNLOAD DEL DOCUMENTO ", ex);
            }
        }

        #endregion

        #region Verticalizzazioni

        private void SetParamFromVertInsiel()
        {
            try
            {
                var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

                if (vert.Attiva)
                {
                    this._protocolloLogs.DebugFormat(@"Valori parametri verticalizzazioni: url: {0}, 
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

                    this._codiceUtente = vert.Codiceutente;
                    this._passwordUtente = vert.Password;
                    this._url = vert.Url;
                    this._urlFilesUpload = vert.UrlUploadfile;
                    this._codiceUfficioOperante = vert.CodiceUfficioOperante;
                    this._usaWsPerTipiDoc = vert.GetBool(TIPI_DOCUMENTO_WS_NAME);
                    this._escludiClassifica = vert.GetBool(ESCLUDI_CLASSIFICA_PARAM_NAME);
                    this._disabilitaAnnullaProtocollo = vert.GetBool(DISABILITA_ANNULLA_PROTOCOLLO_PARAM_NAME);
                    this._codiceRegistro = vert.Codiceregistro;
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

        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            try
            {
                this.SetParamFromVertInsiel();
                if (this._usaWsPerTipiDoc)
                {
                    using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                    {
                        var request = new getTipiDocRequest();

                        request.Utente = this.GetUtenteProtocollo();

                        this._protocolloLogs.DebugFormat("Chiamata a getTipiDoc (Recupero Tipi Documento) CodiceUtente: {1}, Password: {2}", this._codiceUtente, this._passwordUtente);

                        var response = ws.Service.getTipiDoc(request);
                        this._protocolloLogs.Debug("Ricevuta risposta da getTipiDoc");

                        this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.TipiDocumentoSoapResponseFileName, response);
                        ListaTipiDocumentoResponseType listaTipiDocumento = null;
                        if (response.esito)
                            listaTipiDocumento = this.CreaTipiDocumento(response);
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
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEI TIPI DOCUMENTO DAL WEB SERVICE.", ex);
            }
        }

        private ListaTipiDocumentoResponseType CreaTipiDocumento(getTipiDocResponse response)
        {
            try
            {
                this._protocolloLogs.Debug("Valorizzazione tipi documento recuperati da web method getTipiDoc() del web service Insiel");
                var res = new ListaTipiDocumentoResponseType();
                var listaTipiDocs = new List<ListaTipiDocumentoDocumentoType>();

                //var items = (ProtocolloInsielService.TipiDocumento[])response.Items;

                foreach (var el in response.Items)
                {
                    var tipoDoc = new ListaTipiDocumentoDocumentoType();
                    var tipoDocSource = (ProtocolloInsielService.TipiDocumento)el;
                    tipoDoc.Codice = tipoDocSource.codice;
                    tipoDoc.Descrizione = tipoDocSource.descrizione.ToUpper();

                    listaTipiDocs.Add(tipoDoc);
                }

                res.Documento = listaTipiDocs.ToArray();

                this._protocolloLogs.DebugFormat("Fine valorizzazione tipi documento recuperati da web method getTipiDoc() del web service Insiel, numero tipi documento tornati: {0}", res.Documento.Length);

                return res;
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("VALORIZZAZIONE DEI TIPI DOCUMENTO RECUPERATI DA WEB SERVICE NON CORRETTA", ex);
            }
        }

        #endregion

    }
}
