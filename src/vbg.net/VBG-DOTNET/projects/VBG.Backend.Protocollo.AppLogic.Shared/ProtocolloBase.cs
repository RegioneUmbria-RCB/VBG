using Init.SIGePro.Manager.Authentication;
using PersonalLib2.Data;
using System.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared
{
    /// <summary>
    /// Descrizione di riepilogo per ProtocolloBase.
    /// </summary>
    public abstract class ProtocolloBase : IProtocollazioneService, IUnitaDocumentaleService, IRegistrazioneService,
                                            IAllegatiService, Interfaces.IProtocolloStorico, IPecService, IAttoService,
                                            IStampaEtichetteService, IClassificheService, IFirmatariService,
                                            IFascicolazioneService, ITipiDocumentoService, IUfficiRegistriService,
                                            IAccettazioneProtocolliService
    {
        public DateTime? DataProtocollo { get; set; }
        public string CodAmministrazione { get; set; }
        //public string MotivoAnnullamento { get; set; }
        public string Stampante { get; set; }
        public int NumeroCopie { get; set; }
        public string NumProtocollo { get; set; }
        public string IdProtocollo { get; set; }
        public string Operatore { get; set; }
        public string Ruolo { get; set; }
        public string Uo { get; set; }
        public bool AggiungiAnno { get; set; }
        public string TempPath { get; set; }
        public string ProxyAddress { get; set; }
        public Enums.TipoProvenienza Provenienza { get; set; }
        public bool ModificaNumero { get; set; }
        public bool GestisciFascicolazione { get; set; }
        public string IdAllegato { get; set; }
        public Enums.Source TipoInserimento { get; set; }
        public ResolveDatiProtocollazioneService DatiProtocollo { get; set; }
        public List<IAnagraficaAmministrazione> Anagrafiche { get; set; }
        public bool EstraiEml { get; set; }
        public string[] EscludiFileDaEml { get; set; }
        public bool EstraiZip { get; set; }
        public string[] ZipExtensions { get; set; }
        public bool ValorizzaDataRicezioneSpedizione { get; set; }
        public string EncodingCharset { get; set; }

        private string _annoProtocollo = "";

        public string AnnoProtocollo
        {
            get
            {
                if (String.IsNullOrEmpty(this._annoProtocollo))
                    return this.DataProtocollo.HasValue ? this.DataProtocollo.Value.Year.ToString() : "";

                return this._annoProtocollo;
            }
            set { this._annoProtocollo = value; }
        }

        protected ProtocolloLogs _protocolloLogs = null;
        protected ProtocolloSerializer _protocolloSerializer = null;
        protected ProtocolloValidation _protocolloValidation = null;


        public virtual void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            this.DatiProtocollo = datiProtocolloService;

            var factory = ProtocolloFactoryProvider.Factory
                ?? throw new InvalidOperationException("ProtocolloFactory non inizializzata");

            this._protocolloLogs = factory.CreateLogs(this.DatiProtocollo, this.GetType());
            this._protocolloValidation = factory.CreateValidation();
            this._protocolloSerializer = factory.CreateSerializer(this._protocolloLogs);
        }

        public void EliminaTempLogs()
        {
            if (this._protocolloLogs != null)
            {
                this._protocolloLogs.DeleteTempFolder();
            }
        }

        #region Metodi IProtocollazioneService
        public abstract DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn);
        public virtual List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            const string message = "Il sistema di protocollazione attivo non supporta il metodo per la lettura di un protocollo";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }
        public virtual DatiProtocolloResponseType CreaCopie()
        {
            var pProtocollo = new DatiProtocolloResponseType();
            return pProtocollo;
        }
        public virtual ListaMotiviAnnullamentoResponseType GetMotivoAnnullamento()
        {
            var pListaMotiviAnnullamento = new ListaMotiviAnnullamentoResponseType();
            return pListaMotiviAnnullamento;
        }
        public virtual DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var datiProtAnn = new DatiProtocolloAnnullatoResponseType();
            datiProtAnn.NoteAnnullamento = "";
            return datiProtAnn;
        }
        public virtual void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            var message = "Il sistema di protocollazione attivo non supporta il metodo per l'annullamento di un protocollo";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }
        /// <summary>
        /// Controlla se il numero e anno passato coincidono con i dati letti dal sistema di protocollazione.
        /// In Iride non c'è bisogno di questo controllo perchè il numero restituito se viene creata la copia è 0/0
        /// </summary>
        /// <param name="annoProtocollo"></param>
        /// <param name="numeroProtocollo"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="pDatiProtocolloLetto"></param>
        public virtual void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLettoResponseType pDatiProtocolloLetto)
        {
            try
            {
                if (!string.IsNullOrEmpty(idProtocollo) && !string.IsNullOrEmpty(annoProtocollo) && !string.IsNullOrEmpty(numeroProtocollo))
                {
                    var aNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });

                    int resNum;
                    var parse = int.TryParse(aNumProtSplit[0], out resNum);

                    if (!parse)
                    {
                        throw new Exception("IL NUMERO DI PROTOCOLLO INVIATO NON E' DI TIPO NUMERICO");
                    }

                    int resNumLetto;
                    var parseLetto = int.TryParse(pDatiProtocolloLetto.NumeroProtocollo, out resNumLetto);

                    if (!parseLetto)
                    {
                        throw new Exception("IL NUMERO DI PROTOCOLLO RILETTO NON E' DI TIPO NUMERICO");
                    }

                    if (resNumLetto != resNum)
                    {
                        throw new Exception("IL NUMERO DEL PROTOCOLLO RILETTO NON COINCIDE CON QUELLO PASSATO!" + "NUMERO RILETTO/ANNO RILETTO: " + pDatiProtocolloLetto.NumeroProtocollo + "/" + pDatiProtocolloLetto.AnnoProtocollo + ", numero passato/anno passato: " + aNumProtSplit[0] + "/" + annoProtocollo);
                    }

                    if (pDatiProtocolloLetto.AnnoProtocollo != annoProtocollo)
                    {
                        throw new Exception("L'ANNO DEL PROTOCOLLO RILETTO NON COINCIDE CON QUELLO PASSATO!" + "NUMERO RILETTO/ANNO RILETTO: " + pDatiProtocolloLetto.NumeroProtocollo + "/" + pDatiProtocolloLetto.AnnoProtocollo + ", numero passato/anno passato: " + aNumProtSplit[0] + "/" + annoProtocollo);
                    }
                }
            }
            catch (Exception)
            {
                throw;
            }
        }

        public virtual List<MetadatoType> RecuperaMetadati()
        {
            return new List<MetadatoType>();
        }

        #endregion

        #region Metodi IUnitaDocumentaleService
        public virtual CreaUnitaDocumentaleResponseType CreaUnitaDocumentale(string tipoDocumento, IEnumerable<ProtocolloAllegati> allegati)
        {
            var message = "Metodo CreaUnitaDocumentale non implementato";
            this._protocolloLogs.Error(message);

            throw new NotImplementedException(message);
        }
        #endregion

        #region Metodi IRegistrazioneService
        public virtual DatiProtocolloResponseType Registrazione(string registro, DatiProtocolloIn request)
        {
            const string message = "Metodo Registrazione non implementato";
            this._protocolloLogs.Error(message);
            throw new NotImplementedException(message);
        }
        #endregion

        #region Metodi IAllegatiService
        public virtual void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati)
        {
            const string message = "Metodo AggiungiAllegati non implementato";
            this._protocolloLogs.Error(message);
            throw new NotImplementedException(message);
        }
        public virtual AllegatoResponseType LeggiAllegato()
        {
            const string message = "IL SISTEMA DI PROTOCOLLAZIONE ATTIVO NON SUPPORTA IL METODO PER LA LETTURA DEGLI ALLEGATI";
            throw new Exception(message);
        }
        public AllegatoResponseType LeggiAllegatoDaLeggiProtocollo()
        {
            var protocolliLetti = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = this.IdProtocollo, AnnoProtocollo = this.AnnoProtocollo, NumeroProtocollo = this.NumProtocollo });

            foreach (var protocollo in protocolliLetti)
            {
                foreach (AllegatoResponseType allegati in protocollo.Allegati)
                {
                    if (allegati.IDBase == this.IdAllegato)
                    {
                        return allegati;
                    }
                }
            }
            return null;
        }
        public virtual DatiProtocolloResponseType MettiAllaFirma(DatiProtocolloIn pProt)
        {
            var message = "il sistema di protocollazione attivo non supporta la possibilità di mettere alla firma un documento";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }
        #endregion

        #region Metodi IPecService
        public virtual void InvioPec(string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            var message = "Metodo Invio Pec";
            this._protocolloLogs.Error(message);

            throw new NotImplementedException(message);
        }
        #endregion

        #region Metodi IAttoService
        public virtual DatiAttoLetto LeggiAtto(string idAtto, int? annoAtto, string numeroAtto)
        {
            var message = "Il sistema di protocollazione attivo non supporta il metodo per la lettura dei dati di un atto";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }
        public virtual void CheckAttoLetto(string idAtto, int? annoAtto, string numeroAtto, DatiAttoLetto attoLetto)
        {
            try
            {
                if (!string.IsNullOrEmpty(idAtto) && annoAtto.HasValue && !string.IsNullOrEmpty(numeroAtto))
                {
                    var aNumAttoSplit = numeroAtto.Split(new Char[] { '/' });

                    int resNum;
                    var parse = int.TryParse(aNumAttoSplit[0], out resNum);

                    if (!parse)
                    {
                        throw new Exception("IL NUMERO DI ATTO INVIATO NON E' DI TIPO NUMERICO");
                    }

                    int resNumLetto;
                    var parseLetto = int.TryParse(attoLetto.NumeroAtto, out resNumLetto);

                    if (!parseLetto)
                    {
                        throw new Exception("IL NUMERO DI ATTO RILETTO NON E' DI TIPO NUMERICO");
                    }

                    if (resNumLetto != resNum)
                    {
                        throw new Exception($"IL NUMERO DI ATTO RILETTO NON COINCIDE CON QUELLO PASSATO! NUMERO RILETTO/ANNO: {attoLetto.NumeroAtto}/{attoLetto.AnnoAtto}, NUMERO PASSATO/ANNO: {aNumAttoSplit[0]}/{annoAtto}");
                    }

                    if (attoLetto.AnnoAtto != annoAtto)
                    {
                        throw new Exception($"L'ANNO DELL'ATTO RILETTO NON COINCIDE CON QUELLO PASSATO! NUMERO RILETTO/ANNO RILETTO: {attoLetto.NumeroAtto}/{attoLetto.AnnoAtto}, NUMERO PASSATO/ANNO: {aNumAttoSplit[0]}/{annoAtto}");
                    }
                }
            }
            catch (Exception)
            {
                throw;
            }
        }
        #endregion

        #region Metodi IProtocolloStorico
        public virtual DatiProtocolloLettoResponseType LeggiProtocolloStorico(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            throw new NotImplementedException("Fare l'ovveride del LeggiProtocolloStorico nello specifico connettore attivato in Verticalizzazione");
        }
        public virtual AllegatoResponseType LeggiAllegatoStorico()
        {
            throw new NotImplementedException("Fare l'ovveride del LeggiAllegatoStorico nello specifico connettore attivato in Verticalizzazione");
        }
        public AllegatoResponseType LeggiAllegatoStoricoDaLeggiProtocolloStorico()
        {
            this._protocolloLogs.Debug($"Inizio recupero allegato storico con IDBase: {this.IdAllegato}");
            this._protocolloLogs.Debug($"---- Inizio Chiamata a leggiprotocollostorico con IdProtocollo: {this.IdProtocollo}, AnnoProtocollo: {this.AnnoProtocollo}, NumProtocollo: {this.NumProtocollo}");
            DatiProtocolloLettoResponseType protLetto = this.LeggiProtocolloStorico(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);
            this._protocolloLogs.Debug($"---- Fine Chiamata a leggiprotocollostorico con IdProtocollo: {this.IdProtocollo}, AnnoProtocollo: {this.AnnoProtocollo}, NumProtocollo: {this.NumProtocollo}");

            foreach (AllegatoResponseType allegati in protLetto.Allegati)
            {
                this._protocolloLogs.Debug($"IDBase allegato: {allegati.IDBase}");
                if (allegati.IDBase == this.IdAllegato)
                {
                    this._protocolloLogs.Debug($"Fine recupero allegato storico trovato IDBase: {this.IdAllegato}");
                    return allegati;
                }
            }
            this._protocolloLogs.Debug($"Fine recupero allegato storico con IDBase: {this.IdAllegato}, ALLEGATO NON TROVATO");
            return null;
        }
        #endregion

        #region Metodi IStampaEtichetteService
        public virtual EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            var message = "Il sistema di protocollazione attivo non supporta il metodo per la stampa dell'etichetta";
            this._protocolloLogs.Error(message);

            throw new Exception(message);
        }
        #endregion

        #region Metodi IClassificheService
        public virtual int GetIdClassificaByCodice(string codiceClassifica)
        {
            this._protocolloLogs.Debug("Recupero PROTOCOLLO_CLASSIFICHE.ID di una singola classifica partendo da PROTOCOLLO_CLASSIFICHE.CODICE");
            var mgr = new ProtocolloClassificheMgr(this.DatiProtocollo.Db);

            var classifica = mgr.GetByCodice(this.DatiProtocollo.IdComune, this.DatiProtocollo.Software, codiceClassifica.ToUpper());

            if (classifica == null)
            {
                classifica = mgr.GetByCodice(this.DatiProtocollo.IdComune, "TT", codiceClassifica.ToUpper());
            }

            if (classifica == null)
            {
                var m = $"LA CLASSIFICA CON IDCOMUNE {this.DatiProtocollo.IdComune} E CODICE {codiceClassifica} NON ESISTE NELLA TABELLA PROTOCOLLO_CLASSIFICHE";
                this._protocolloLogs.Debug(m);
                throw new Exception(m);
            }

            return classifica.Id.Value;
        }

        public virtual ListaTipiClassificaType GetClassifiche()
        {
            this._protocolloLogs.Debug("Recupero del titolario dalla tabella PROTOCOLLO_CLASSIFICHE");

            var mgr = new ProtocolloClassificheMgr(this.DatiProtocollo.Db);
            var list = mgr.GetBySoftwareCodiceComune(this.DatiProtocollo.IdComune, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

            var titolario = list.Select(x => new ListaTipiClassificaClassifica { Codice = x.Codice, Descrizione = x.Descrizione, Ordinamento = String.IsNullOrEmpty(x.Ordinamento) ? 0 : Convert.ToInt32(x.Ordinamento) }).OrderBy(x => x.Ordinamento);

            this._protocolloLogs.DebugFormat("Numero classifiche recuperate: {0}", titolario.Count());

            if (!titolario.Any())
            {
                this._protocolloLogs.Debug("LA TABELLA PROTOCOLLO_CLASSIFICHE E' VUOTA");
                return new ListaTipiClassificaType();
            }

            return new ListaTipiClassificaType { Classifica = titolario.ToArray() };
        }
        #endregion

        #region Metodi IFirmatariService
        public virtual ListaFirmatari GetFirmatari()
        {
            throw new NotImplementedException("L'elenco dei firmatari non è disponibile per il connettore utilizzato");
        }
        #endregion

        #region Metodi IFascicolazioneService
        public virtual ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo)
        {
            var listaFascicolo = new ListaFascicoliResponseType();
            return listaFascicolo;
        }
        public virtual DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var datiProtFasc = new DatiProtocolloFascicolatoResponseType();
            datiProtFasc.NoteFascicolo = "";
            return datiProtFasc;
        }
        public virtual DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            var message = "il sistema di protocollazione attivo non supporta il metodo per la creazione di un fascicolo";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }
        public virtual DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            var message = "il sistema di protocollazione attivo non supporta il metodo per il cambiamento di un fascicolo";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }
        #endregion

        #region Metodi ITipiDocumentoService
        public virtual ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            this._protocolloLogs.Debug("Recupero dei tipi documento dalla tabella PROTOCOLLO_TIPIDOCUMENTO");

            var mgr = new ProtocolloTipiDocumentoMgr(this.DatiProtocollo.Db);
            var list = mgr.GetBySoftwareCodiceComune(this.DatiProtocollo.IdComune, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

            var tipiDocs = list.Select(x => new ListaTipiDocumentoDocumentoType { Codice = x.Codice, Descrizione = x.Descrizione });

            this._protocolloLogs.DebugFormat("Numero documenti recuperato: {0}", tipiDocs.Count());
            if (tipiDocs.Count() == 0)
                return new ListaTipiDocumentoResponseType();

            return new ListaTipiDocumentoResponseType { Documento = tipiDocs.ToArray() };
        }
        public virtual ListaTipiDocumentoResponseType CreaListaTipiDocumento(object ds)
        {
            var pListaTipiDocumento = new ListaTipiDocumentoResponseType();

            pListaTipiDocumento.Documento = new ListaTipiDocumentoDocumentoType[((DataSet)ds).Tables[0].Rows.Count];

            var iCount = 0;
            foreach (DataRow dr in ((DataSet)ds).Tables[0].Rows)
            {
                pListaTipiDocumento.Documento[iCount] = new ListaTipiDocumentoDocumentoType();

                pListaTipiDocumento.Documento[iCount].Codice = dr["CODICE"].ToString();
                pListaTipiDocumento.Documento[iCount].Descrizione = dr["DESCRIZIONE"].ToString();
                iCount++;
            }

            return pListaTipiDocumento;
        }
        #endregion

        #region Metodi IUfficiRegistriService
        public virtual string GetUfficioRegistro(string codiceRegistro)
        {
            if (String.IsNullOrEmpty(codiceRegistro))
                throw new Exception("IL CODICE REGISTRO NON E' STATO VALORIZZATO");

            var mgr = new ProtocolloUfficiRegistriMgr(this.DatiProtocollo.Db);
            var uffReg = mgr.GetById(codiceRegistro, this.DatiProtocollo.IdComune, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

            if (uffReg == null)
                throw new Exception(String.Format("UFFICIO NON TROVATO PER IL REGISTRO {0}", codiceRegistro));

            if (String.IsNullOrEmpty(uffReg.Codiceufficio))
                throw new Exception(String.Format("CODICE UFFICIO NON VALORIZZATO PER IL REGISTRO {0}", codiceRegistro));

            return uffReg.Codiceufficio;
        }

        #endregion

        #region IAccettazioneService

        public virtual EseguiAccettazioneResponseType EseguiAccettazione(AuthenticationInfo auth, string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var message = "Il sistema di protocollazione attivo non supporta il metodo per l'accettazione di un protocollo";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }

        public virtual DatiProtocolloEsitatoResponseType IsEsitato(AuthenticationInfo auth, string annoProtocollo, string numeroProtocollo)
        {
            var message = "Il sistema di protocollazione attivo non supporta il metodo per verificare se un protocollo è esitato";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }

        public virtual DatiProtocolloEsitatoResponseType IsEsitato(AuthenticationInfo auth, string idProtocollo)
        {
            var message = "Il sistema di protocollazione attivo non supporta il metodo per verificare se un protocollo è esitato";
            this._protocolloLogs.Error(message);
            throw new Exception(message);
        }
        #endregion
    }
}