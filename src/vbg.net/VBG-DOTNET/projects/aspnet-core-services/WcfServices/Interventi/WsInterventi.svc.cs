using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.DTO.AllegatiDomandaOnline;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione;
using Init.SIGePro.Manager.Logic.GestioneWorkflowDomandaOnLine;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System.ServiceModel.Activation;
using static Init.SIGePro.Manager.DTO.AllegatiDomandaOnline.AllegatoInterventoDomandaOnlineDto;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Interventi
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsInterventi" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsInterventi.svc or WsInterventi.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsInterventi : WcfServiceBase, IWsInterventi
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        public WsInterventi(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver, IVerticalizzazioniFactory verticalizzazioniFactory) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public NodoAlberoInterventiDto GetAlberaturaDaIdNodo(string token, int codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            var listaNodiAlbero = this.RisaliStrutturaAlbero(authInfo, codiceIntervento);

            return CreaAlberoSenzaSchedeDinamicheDaListaNodi(listaNodiAlbero);
        }

        public static NodoAlberoInterventiDto CreaAlberoSenzaSchedeDinamicheDaListaNodi(ClassTree<AlberoProc> listaNodiAlbero)
        {
            return CreaAlberoConSchedeDinamicheDaListaNodi(listaNodiAlbero, new Dictionary<int, List<SchedaDinamicaDto>>());
        }

        private static List<ClassTree<InterventoDto>> PopolaNodiFiglio(List<ClassTree<AlberoProc>> nodiFiglio, Dictionary<int, List<SchedaDinamicaDto>> schedeDinamiche)
        {
            var rVal = new List<ClassTree<InterventoDto>>();

            for (var i = 0; i < nodiFiglio.Count; i++)
            {
                var nf = nodiFiglio[i];

                var el = new ClassTree<InterventoDto>(PopolaIntervento(nf.Elemento, schedeDinamiche));
                el.NodiFiglio = PopolaNodiFiglio(nf.NodiFiglio, schedeDinamiche);

                rVal.Add(el);
            }

            return rVal;
        }

        private static InterventoDto PopolaIntervento(AlberoProc sc, Dictionary<int, List<SchedaDinamicaDto>> schedeDinamiche)
        {
            var rVal = new InterventoDto
            {
                Codice = sc.Sc_id.Value,
                Descrizione = sc.SC_DESCRIZIONE,
                Note = sc.SC_NOTE,
                HaNote = !String.IsNullOrEmpty(sc.SC_NOTE)
            };

            if (schedeDinamiche.ContainsKey(rVal.Codice))
            {
                var listaSchedeDinamiche = schedeDinamiche[rVal.Codice];
                rVal.SchedeDinamiche = listaSchedeDinamiche;
            }

            return rVal;
        }

        public static NodoAlberoInterventiDto CreaAlberoConSchedeDinamicheDaListaNodi(ClassTree<AlberoProc> strutturaAlberoOrdinata, Dictionary<int, List<SchedaDinamicaDto>> schedeDinamiche)
        {
            var root = new NodoAlberoInterventiDto();

            root.Elemento = PopolaIntervento(strutturaAlberoOrdinata.Elemento, schedeDinamiche);

            if (strutturaAlberoOrdinata.NodiFiglio.Count > 0)
                root.NodiFiglio = PopolaNodiFiglio(strutturaAlberoOrdinata.NodiFiglio, schedeDinamiche);

            return root;
        }


        private ClassTree<AlberoProc> RisaliStrutturaAlbero(AuthenticationInfo authInfo, int codiceIntervento)
        {
            var mgr = new AlberoProcMgr(authInfo.CreateDatabase());
            var ramoAlbero = mgr.GetById(codiceIntervento, authInfo.IdComune);

            var verticalizzazioneCart = this._verticalizzazioniFactory.Create<VerticalizzazioneCart>(authInfo.Alias, ramoAlbero.SOFTWARE);

            return mgr.RisaliStrutturaAlbero(authInfo.IdComune, codiceIntervento, verticalizzazioneCart.Attiva);
        }

        public NodoAlberoInterventiDto GetStrutturaAlberoInterventi(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                FormattableString query = $"select * from albero_proc where idcomune = {authInfo.IdComune} and software = {software} and sc_attivo = 0 and (sc_pubblica = 1 or sc_pubblica = 2) order by SC_CODICE ASC";

                var listaNodi = db.GetClassList<AlberoProc>(query);

                return new GeneratoreAlberoInterventiDaListaInterventi(listaNodi).GeneraAlbero();
            }
        }


        public List<InterventoDto> GetSottonodiIntervento(string token, string software, int idNodo, AmbitoRicerca ambitoRicerca, string codiceComune)
        {
            var authInfo = this.CheckToken(token);

            // var verticalizzazioneCart = this._verticalizzazioniFactory.Create<VerticalizzazioneCart>(authInfo.Alias, software);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).GetNodiFiglio(authInfo.IdComune, software, idNodo, ambitoRicerca, codiceComune);
            }
        }


        public List<InterventoDto> GetSottonodiInterventoDaIdAteco(string token, string software, int idNodoPadre, int idAteco, AmbitoRicerca ambitoRicerca, string codiceComune)
        {
            var authInfo = this.CheckToken(token);

            var idComune = authInfo.IdComune;

            using (var db = authInfo.CreateDatabase())
            {
                var rVal = new AtecoMgr(db).GetListaInterventiRootDaIdAteco(idComune, idNodoPadre, idAteco, software, ambitoRicerca);

                if (rVal.Count == 0)
                    rVal = new AlberoProcMgr(db).GetNodiFiglio(idComune, software, idNodoPadre, ambitoRicerca, codiceComune);

                return rVal;
            }
        }

        public TitoloInterventoDto GetTitoloIntervento(string token, int codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new AlberoProcMgr(db);

                return mgr.GetTitoloInterventoDaMetadati(authInfo.IdComune, codiceIntervento);
            }
        }

        public InterventoDto GetDettagliIntervento(string token, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumenti, bool leggiNoteEstese)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();

                    var mgr = new AlberoProcMgr(db);
                    var idComune = authInfo.IdComune;

                    var intervento = mgr.GetById(codiceIntervento, idComune);

                    var strutturaAlbero = this.RisaliStrutturaAlbero(authInfo, codiceIntervento);
                    var listaDocumenti = mgr.GetDocumentiDaIdIntervento(idComune, codiceIntervento, ambitoRicercaDocumenti);
                    var listaSchede = mgr.GetSchedeDinamicheFoDaIdIntervento(idComune, codiceIntervento);
                    var listaOneri = mgr.GetListaOneriDaIdIntervento(idComune, codiceIntervento);
                    //var listaEndo = mgr.GetEndoDaIdIntervento(idComune, codiceIntervento, ambitoRicercaDocumenti);
                    var listaLeggi = mgr.GetListaNormativeDaIdIntervento(idComune, codiceIntervento);
                    var listaFasi = mgr.GetListaFasiAttuativeDaIdIntervento(idComune, codiceIntervento);

                    var rVal = new InterventoDto
                    {
                        Codice = intervento.Sc_id.Value,
                        Descrizione = intervento.SC_DESCRIZIONE,
                        Documenti = listaDocumenti,
                        Note = leggiNoteEstese ? mgr.GetTestoCompletoNote(idComune, codiceIntervento) : mgr.GetNoteSingolaFoglia(idComune, codiceIntervento),
                        //Endoprocedimenti = listaEndo,
                        FasiAttuative = listaFasi,
                        Normative = listaLeggi,
                        Oneri = listaOneri
                    };

                    foreach (var schedaIntervento in listaSchede)
                        rVal.SchedeDinamiche.Add(schedaIntervento);

                    return rVal;
                }
                finally
                {
                    db.Connection.Close();
                }
            }
        }


        public List<AllegatoInterventoDomandaOnlineDto> GetDocumentiDaCodiceIntervento(string token, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumenti)
        {
            var authInfo = this.CheckToken(token);
            var idcomune = authInfo.IdComune;

            using (var db = authInfo.CreateDatabase())
            {
                var risoluzioneNomeFile = new Func<int, string>((x) => { return new OggettiMgr(db).GetNomeFile(idcomune, x); });

                // Documenti dell'intervento
                var alberoProcDocumentiMgr = new AlberoProcDocumentiMgr(db);
                var allegatiIntervento = alberoProcDocumentiMgr.GetListDaCodiceIntervento(authInfo.IdComune, codiceIntervento, ambitoRicercaDocumenti)
                                                               .Select(doc => FromAllegatoIntervento(doc, risoluzioneNomeFile));

                // Documenti della procedura
                var codiceProcedura = new AlberoProcMgr(db).CodiceProceduraDaIdIntervento(authInfo.IdComune, codiceIntervento);

                if (!codiceProcedura.HasValue)
                    return allegatiIntervento.ToList();

                var allegatiProcedura = new TipiProcedureDocumentiMgr(db).GetByIdProcedura(authInfo.IdComune, codiceProcedura.Value, ambitoRicercaDocumenti)
                                                                         .Select(doc => FromAllegatoProcedura(doc, risoluzioneNomeFile));

                return allegatiIntervento.Union(allegatiProcedura).ToList();
            }
        }


        public static AllegatoInterventoDomandaOnlineDto FromAllegatoIntervento(AlberoProcDocumenti documento, Func<int, string> risolviNomeFile)
        {
            var codiceOggetto = String.IsNullOrEmpty(documento.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(documento.CODICEOGGETTO);

            var allegato = new AllegatoInterventoDomandaOnlineDto
            {
                Codice = Convert.ToInt32(documento.SM_ID),
                Descrizione = documento.DESCRIZIONE,
                LinkInformazioni = String.Empty,
                CodiceOggettoModello = codiceOggetto,
                Richiesto = documento.RICHIESTO == "1",
                RichiedeFirma = documento.FO_RICHIEDEFIRMA.GetValueOrDefault(0) == 1,
                TipoDownload = documento.FO_TIPODOWNLOAD,
                Ordine = documento.ORDINE.GetValueOrDefault(0),
                NomeFileModello = codiceOggetto.HasValue ? risolviNomeFile(codiceOggetto.Value) : String.Empty,
                Categoria = null,
                RiepilogoDomanda = documento.FLG_DOMANDAFO.GetValueOrDefault(0) == 1,
                Note = documento.NoteFrontend,
                DimensioneMassima = documento.FoDimensioneMassima,
                EstensioniAmmesse = documento.FoEstensioniAmmesse
            };

            if (documento.Categoria != null && documento.Categoria.Id.HasValue)
            {
                allegato.Categoria = new CategoriaAllegatoIntervento
                {
                    Codice = documento.Categoria.Id.Value,
                    Descrizione = documento.Categoria.Descrizione
                };
            }

            return allegato;
        }


        public static AllegatoInterventoDomandaOnlineDto FromAllegatoProcedura(TipiProcedureDocumenti documento, Func<int, string> risolviNomeFile)
        {
            var codiceOggetto = String.IsNullOrEmpty(documento.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(documento.CODICEOGGETTO);

            var allegato = new AllegatoInterventoDomandaOnlineDto
            {
                Codice = Convert.ToInt32(documento.TP_ID) * -1,
                Descrizione = documento.DESCRIZIONE,
                LinkInformazioni = String.Empty,
                CodiceOggettoModello = codiceOggetto,
                Richiesto = documento.Richiesto.GetValueOrDefault(0) == 1,
                RichiedeFirma = documento.FoRichiedefirma.GetValueOrDefault(0) == 1,
                TipoDownload = documento.FoTipodownload,
                Ordine = 0,
                NomeFileModello = codiceOggetto.HasValue ? risolviNomeFile(codiceOggetto.Value) : String.Empty,
                Categoria = null,
                RiepilogoDomanda = false,
                Note = documento.NoteFrontend/*,
                DimensioneMassima = documento.FoDimensioneMassima,
                EstensioniAmmesse = documento.FoEstensioniAmmesse
                */
            };

            return allegato;
        }

        public string GetIdDrupalDaCodiceIntervento(string token, int codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).GetIdDrupalDaCodiceIntervento(authInfo.IdComune, codiceIntervento);
            }
        }



        public List<AlberoProcDocumentiCat> GetCategorieAllegatiInterventoChePermettonoUpload(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            var filtro = new AlberoProcDocumentiCat();
            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;
            filtro.OthersWhereClause.Add("FO_NONPERMETTEUPLOAD<>1");
            filtro.OrderBy = "descrizione asc";

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcDocumentiCatMgr(db).GetList(filtro);
            }
        }


        public List<InterventoBreveDto> RicercaTestualeInterventi(string token, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).RicercaTestualeInterventi(authInfo.IdComune, software, matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca)
                    .Select(x => new InterventoBreveDto
                    {
                        Codice = x.Codice,
                        Descrizione = x.Descrizione
                    }).ToList();
            }

        }


        public List<int> GetListaIdNodiPadreIntervento(string token, int codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).GetListaIdNodiPadre(authInfo.IdComune, codiceIntervento);
            }
        }


        public int? GetIdCertificatoDiInvioDomandaDaIdIntervento(string token, int idIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var alberoMgr = new AlberoProcMgr(db);
                var ramo = alberoMgr.GetById(idIntervento, authInfo.IdComune);
                var certificatoInvio = alberoMgr.GetIdCertificatoDiInvioDomandaDaIdIntervento(authInfo.IdComune, idIntervento);

                if (certificatoInvio.HasValue)
                {
                    return certificatoInvio.Value;
                }

                var parametriArCfg = new FoArConfigurazioneMgr(db).LeggiDati(authInfo.IdComune, ramo.SOFTWARE);

                return parametriArCfg?.CodiceoggettoFirma;
            }
        }


        public int? GetIdRiepilogoDomandaDaIdIntervento(string token, int idIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).GetIdRiepilogoDomandaDaIdIntervento(authInfo.IdComune, idIntervento);
            }
        }

        public NodoConWorkflowDto GetNodoConWorkflowDaIdIntervento(string token, int idIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).GetNodoConWorkflowDaIdIntervento(authInfo.IdComune, idIntervento);
            }
        }

        public int? GetCodiceOggettoWorkflowDaCodiceIntervento(string token, int idIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).GetCodiceOggettoWorkflowDaIdIntervento(authInfo.IdComune, idIntervento);
            }
        }


        public bool InterventoSupportaRedirect(string token, int idIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).InterventoSupportaRedirect(authInfo.IdComune, idIntervento);
            }
        }


        public bool HaPresentatoDomandePerIntervento(string token, int idIntervento, string codiceFiscaleRichiedente)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).HaPresentatoDomandePerIntervento(authInfo.IdComune, idIntervento, codiceFiscaleRichiedente);
            }
        }

        public RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(string token, LivelloAutenticazioneBOEnum livelloAutenticazione, int codiceIntervento, string codiceComune)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var attivazioneInterventoService = new AttivazioneInterventoService(db, ai.IdComune);

                return attivazioneInterventoService.VerificaAccessoIntervento(TipoPubblicazione.AreaRiservata, livelloAutenticazione, codiceIntervento, codiceComune);
            }
        }

        public RisultatoVerificaAccessoIntervento VerificaAccessoInterventoDomandaOnLine(string token, LivelloAutenticazioneBOEnum livelloAutenticazione, int codiceIntervento, string codiceComune)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var attivazioneInterventoService = new AttivazioneInterventoService(db, ai.IdComune);

                return attivazioneInterventoService.VerificaAccessoIntervento(TipoPubblicazione.DomandaOnLine, livelloAutenticazione, codiceIntervento, codiceComune);
            }
        }

        public int GetLivelloDiAccessoIntervento(string token, int codiceIntervento)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var attivazioneInterventoService = new AttivazioneInterventoService(db, ai.IdComune);

                return attivazioneInterventoService.GetLivelloDiAutenticazioneRichiesto(codiceIntervento);
            }
        }

        public WorkflowV3Dto GetWorkflowDomandaOnLineByIdIntervento(string token, int idIntervento)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var svc = new WorkflowDomandaOnlineService(db, ai.IdComune);

                return svc.GetByIdIntervento(idIntervento);
            }
        }

        public void AggiornaWorkflowIntervento(string token, int idIntervento, int codiceOggettoWorkflow)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var svc = new WorkflowDomandaOnlineService(db, ai.IdComune);

                svc.AggiornaWorkflowIntervento(idIntervento, codiceOggettoWorkflow);
            }
        }
    }
}
