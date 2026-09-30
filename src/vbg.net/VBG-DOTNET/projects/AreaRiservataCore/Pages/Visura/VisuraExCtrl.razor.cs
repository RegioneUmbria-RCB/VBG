using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.SIGePro.Manager.DTO.Scadenzario;
using Microsoft.AspNetCore.Components;
using static AreaRiservataCore.Pages.Visura.DatiGenerali;
using static AreaRiservataCore.Pages.Visura.VisuraAutorizzazioni;
using static AreaRiservataCore.Pages.Visura.VisuraDocumenti;
using static AreaRiservataCore.Pages.Visura.VisuraEndoprocedimenti;
using static AreaRiservataCore.Pages.Visura.VisuraLocalizzazioni;
using static AreaRiservataCore.Pages.Visura.VisuraMovimenti;
using static AreaRiservataCore.Pages.Visura.VisuraOneri;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraExCtrl
    {
        [Inject]
        public IVisuraService _visuraService { get; set; } = default!;

        [Inject]
        public IScadenzeService _scadenzeService { get; set; } = default!;

        [Inject]
        public IAuthenticationDataResolver _authDataResolver { get; set; } = default!;

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; } = default!;

        [Inject]
        public IVisuraDatiDinamiciService _datiDinamiciService { get; set; } = default!;

        //[Inject]
        //public IVbgAccessoAttiService _vbgAccessoAttiService { get; set; } = default!;

        [Parameter]
        public RenderFragment Bottoni { get; set; } = default!;

        [Parameter, EditorRequired]
        public string IdComune { get; set; }

        [Parameter, EditorRequired]
        public string Software { get; set; }

        [Parameter]
        public string CodiceIstanza { get; set; } = "";

        [Parameter]
        public string GoBackUrl { get; set; }

        [Parameter, EditorRequired]
        public LivelloAccessoVisura LivelloAccessoVisura { get; set; } = LivelloAccessoVisura.AccessoAnonimo;

        [Parameter]
        public bool MostraScadenze { get; set; } = true;

        [Parameter]
        public bool MostraPraticheCollegate { get; set; } = true;

        [Parameter]
        public bool EffettuaSubVisuraMovimenti { get; set; } = true;

        [Parameter]
        public Func<IDocumentoIstanzaOggettoDiVerifica, bool> CallbackValidazioneAllegati { get; set; } = (x) => true;

        [Parameter]
        public Istanze? Istanza
        {
            get => this._dataSource;
            set
            {
                this._istanzaImpostataEsplicitamente = true;
                this._dataSource = value;
            }
        }
        [Parameter]
        public RenderFragment? ChildContent { get; set; }


        private bool _istanzaImpostataEsplicitamente = false;
        private Istanze? _dataSource;


        public bool MostraDatiCatastaliEstesi
        {
            get
            {
                var obj = System.Configuration.ConfigurationManager.AppSettings["MostraDatiCatastaliEstesi"];

                if (String.IsNullOrEmpty(obj))
                    return false;

                try
                {
                    return Convert.ToBoolean(obj);
                }
                catch (Exception)
                {
                    return false;
                }
            }
        }


        public int NumeroMovimenti { get; set; } = 0;
        public int NumeroLocalizzazioni { get; set; } = 0;
        public int NumeroDocumenti { get; set; } = 0;
        public int ConteggioSchede { get; set; } = 0;
        public int NumeroEndo { get; set; } = 0;
        public int NumeroOneri { get; set; } = 0;
        public int NumeroAutorizzazioni { get; set; } = 0;
        public int NumeroScadenze { get; set; } = 0;

        public VisuraTabList TabsPagina { get; set; } = VisuraTabList.Default;

        public void InizializzaControlliIstanza()
        {
            this.VisuraDatiGeneraliDataSource = this.GetVisuraDatiGeneraliDataSource();
            this.VisuraMovimentiDataSource = this.GetVisuraMovimentiDataSource();
            this.VisuraSoggettiDataSource = this.GetVisuraSoggettiDataSource();
            this.VisuraLocalizzazioniDataSource = this.GetVisuraLocalizzazioniDataSource();
            this.VisuraDocumentiDataSource = this.GetVisuraDocumentiDataSource();
            this.SchedeDinamicheDataSource = this.GetSchedeDinamicheDataSource();
            this.VisuraEndoprocedimentiDataSource = this.GetVisuraEndoprocedimentiDataSource();
            this.VisuraOneriDataSource = this.GetVisuraOneriDataSource();
            this.VisuraAutorizzazioniDataSource = this.GetVisuraAutorizzazioniDataSource();
            this.ScadenzeDataSource = this.GetScadenzeDataSource();

            this.NumeroMovimenti = this.VisuraMovimentiDataSource?.Count() ?? 0;

            this.NumeroLocalizzazioni = (this.VisuraLocalizzazioniDataSource?.Stradario?.Count() ?? 0) + (this.VisuraLocalizzazioniDataSource?.Mappali?.Count() ?? 0);
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Localizzazioni, this.NumeroLocalizzazioni);

            this.NumeroDocumenti = this.VisuraDocumentiDataSource?.Count() ?? 0;
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Documenti, this.NumeroDocumenti);

            this.ConteggioSchede = this.SchedeDinamicheDataSource?.Count() ?? 0;
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Schede, this.ConteggioSchede);

            this.NumeroEndo = this.VisuraEndoprocedimentiDataSource?.Count() ?? 0;
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Endoprocedimenti, this.NumeroEndo);

            this.NumeroOneri = this.VisuraOneriDataSource?.Count() ?? 0;
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Oneri, this.NumeroOneri);

            this.NumeroAutorizzazioni = this.VisuraAutorizzazioniDataSource?.Count() ?? 0;
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Autorizzazioni, this.NumeroAutorizzazioni);

            this.NumeroScadenze = this.ScadenzeDataSource?.Count() ?? 0;
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Scadenze, this.NumeroScadenze);
        }

        protected override async Task OnInitializedAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                await base.OnInitializedAsync();

                if (!this._istanzaImpostataEsplicitamente && String.IsNullOrEmpty(this.CodiceIstanza))
                {
                    throw new ArgumentException("Il controllo VisuraExControl deve essere inizializzato con un'istanza o con un uuidIstanza");
                }

                if (!this._istanzaImpostataEsplicitamente)
                {
                    this._dataSource = this._visuraService.GetByUuid(this.CodiceIstanza, this.EffettuaSubVisuraMovimenti);
                }

                this.InizializzaControlliIstanza();
            });
        }


        private bool ShowTab(int tabIndex)
        {
            if (this.LivelloAccessoVisura == LivelloAccessoVisura.AccessoAnonimo)
                return this.TabsPagina.Tabs[tabIndex].VisibileDaArchivio;

            return true;
        }

        private bool MostraDatiProtocollo()
        {
            return this.LivelloAccessoVisura != LivelloAccessoVisura.AccessoAnonimo;
        }

        private bool GetVisuraSuStessoComune()
        {
            if (this._dataSource == null)
                return false;

            return this._dataSource.IDCOMUNE == this.UserAuthenticationResult.IdComuneDb;
        }

        private async Task ApriScadenzaAsync(int codiceScadenza)
        {
            await this.GotoAsync($"effettuamovimento/{codiceScadenza}/0/{this.GoBackUrl}");
        }

        #region DataSources



        private VisuraDatiGeneraliDataSource? VisuraDatiGeneraliDataSource { get; set; }

        private IEnumerable<VisuraMovimentiListItem> VisuraMovimentiDataSource { get; set; } = Enumerable.Empty<VisuraMovimentiListItem>();

        private IEnumerable<VisuraSoggettiListItem> VisuraSoggettiDataSource { get; set; } = Enumerable.Empty<VisuraSoggettiListItem>();

        private VisuraLocalizzazioniDataSource? VisuraLocalizzazioniDataSource { get; set; }

        private IEnumerable<VisuraDocumentiListItem> VisuraDocumentiDataSource { get; set; } = Enumerable.Empty<VisuraDocumentiListItem>();

        private IEnumerable<VisuraTitoloModelloDinamicoIstanza> SchedeDinamicheDataSource { get; set; } = Enumerable.Empty<VisuraTitoloModelloDinamicoIstanza>();

        private IEnumerable<VisuraEndoListItem> VisuraEndoprocedimentiDataSource { get; set; } = Enumerable.Empty<VisuraEndoListItem>();

        private IEnumerable<VisuraOneriListItem> VisuraOneriDataSource { get; set; } = Enumerable.Empty<VisuraOneriListItem>();

        private IEnumerable<VisuraAutorizzazioniListItem> VisuraAutorizzazioniDataSource { get; set; } = Enumerable.Empty<VisuraAutorizzazioniListItem>();

        private IEnumerable<ElementoListaScadenzeDto> ScadenzeDataSource { get; set; } = Enumerable.Empty<ElementoListaScadenzeDto>();



        private VisuraDatiGeneraliDataSource? GetVisuraDatiGeneraliDataSource()
        {
            if (this._dataSource == null)
                return null;

            return new VisuraDatiGeneraliDataSource
            {
                Uuid = this._dataSource.UUID,
                ComunePratica = this._dataSource.ComuneIstanza.COMUNE,
                DataPratica = this._dataSource.DATA,
                DataProtocollo = this._dataSource.DATAPROTOCOLLO,
                Intervento = this._dataSource.Intervento?.SC_DESCRIZIONE ?? "",
                Istruttore = this._dataSource.Istruttore?.RESPONSABILE ?? "",
                NumeroPratica = this._dataSource.NUMEROISTANZA,
                NumeroProtocollo = this._dataSource.NUMEROPROTOCOLLO,
                Oggetto = this._dataSource.LAVORI,
                Operatore = this._dataSource.Operatore?.RESPONSABILE ?? "",
                PosizioneArchivio = this._dataSource.POSIZIONEARCHIVIO,
                ResponsabileProcedimento = this._dataSource.ResponsabileProc?.RESPONSABILE ?? "",
                Stato = this._dataSource.Stato.Stato
            };
        }

        private IEnumerable<VisuraMovimentiListItem> GetVisuraMovimentiDataSource()
        {
            if (this._dataSource == null)
                return Enumerable.Empty<VisuraMovimentiListItem>();


            var movimenti = this._dataSource.Movimenti
               .GetMovimentiPubblicati()
               .Select(x => new VisuraMovimentiListItem
               {
                   Id = Convert.ToInt32(x.CODICEMOVIMENTO),
                   CodiceIstanza = Convert.ToInt32(this._dataSource.CODICEISTANZA),
                   Descrizione = x.MOVIMENTO,
                   Data = x.DATA,
                   Parere = x.PUBBLICAPARERE == "1" ? x.PARERE : String.Empty,
                   NumeroProtocollo = x.NUMEROPROTOCOLLO,
                   DataProtocollo = x.DATAPROTOCOLLO,
                   UuidPraticaCollegata = x.UuidPraticaCollegata,
                   Allegati = x.GetAllegatiPubbliciConCodiceOggetto()
                                .Where(y => this.CallbackValidazioneAllegati(y)
                                /*this._vbgAccessoAttiService.IsAllegatoValido(y, this.LivelloAccessoDocumenti)*/)
                                ?? Enumerable.Empty<MovimentiAllegati>()
               });

            return movimenti;
        }

        private IEnumerable<VisuraSoggettiListItem> GetVisuraSoggettiDataSource()
        {
            if (this._dataSource == null)
                return Enumerable.Empty<VisuraSoggettiListItem>();

            var soggetti = new List<VisuraSoggettiListItem>();

            soggetti.Add(new VisuraSoggettiListItem(this._dataSource.Richiedente, this._dataSource.TipoSoggetto, this._dataSource.AziendaRichiedente));

            if (this._dataSource.Professionista != null)
            {
                var ts = new TipiSoggetto();
                ts.TIPOSOGGETTO = "Intermediario";
                soggetti.Add(new VisuraSoggettiListItem(this._dataSource.Professionista, ts));
            }

            if (this._dataSource.Richiedenti != null)
            {
                var richiedenti = this._dataSource.Richiedenti.Select(x => new VisuraSoggettiListItem(x.Richiedente, x.TipoSoggetto, x.AnagrafeCollegata, x.Procuratore));

                soggetti.AddRange(richiedenti);
            }

            if (!this.LivelloAccessoVisura.DatiGenerali || !this.LivelloAccessoVisura.SogettiPratica)
            {
                soggetti = new List<VisuraSoggettiListItem>();
            }

            return soggetti;
        }

        private VisuraLocalizzazioniDataSource? GetVisuraLocalizzazioniDataSource()
        {
            if (this._dataSource is null)
            {
                return null;
            }

            return new VisuraLocalizzazioniDataSource
            {
                Stradario = this._dataSource.Stradario,
                Mappali = this._dataSource.Mappali
            };
        }

        private IEnumerable<VisuraDocumentiListItem> GetVisuraDocumentiDataSource()
        {
            if (this._dataSource == null)
                return Enumerable.Empty<VisuraDocumentiListItem>();


            var documenti = this._dataSource
                                .DocumentiIstanza
                                .Where(x => x.ContieneOggetto && this.CallbackValidazioneAllegati(x) /*this._vbgAccessoAttiService.IsAllegatoValido(x, this.LivelloAccessoDocumenti)*/);


            var documentiVisura = documenti.Select(x => new VisuraDocumentiListItem
            {
                CodiceOggetto = Convert.ToInt32(x.CODICEOGGETTO),
                Data = x.DATA,
                Descrizione = x.DOCUMENTO,
                Md5 = x.Oggetto.Md5,
                NomeFile = x.Oggetto.NOMEFILE,
                UrlDownload = this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(x.CODICEOGGETTO))
            });

            return documentiVisura;
        }

        private IEnumerable<VisuraTitoloModelloDinamicoIstanza> GetSchedeDinamicheDataSource()
        {
            if (this._dataSource == null)
                return Enumerable.Empty<VisuraTitoloModelloDinamicoIstanza>();

            var schedeIstanza = this._datiDinamiciService.GetTitoliModelli(this._dataSource);

            return schedeIstanza;
        }

        private IEnumerable<VisuraEndoListItem> GetVisuraEndoprocedimentiDataSource()
        {
            if (this._dataSource == null)
                return Enumerable.Empty<VisuraEndoListItem>();

            return this._dataSource.EndoProcedimenti.Select(x => new VisuraEndoListItem
            {
                Id = Convert.ToInt32(x.CODICEINVENTARIO),
                CodiceIstanza = Convert.ToInt32(this._dataSource.CODICEISTANZA),
                Endoprocedimento = x.Endoprocedimento.Procedimento,
                Allegati = x.IstanzeAllegati?.Where(y => y.ContieneOggetto && this.CallbackValidazioneAllegati(y) /*this._vbgAccessoAttiService.IsAllegatoValido(y, this.LivelloAccessoDocumenti)*/)
                                ?? Enumerable.Empty<IstanzeAllegati>()
            });
        }

        private IEnumerable<VisuraOneriListItem> GetVisuraOneriDataSource()
        {
            if (this._dataSource == null ||
                this._dataSource.Oneri == null)
                return Enumerable.Empty<VisuraOneriListItem>();

            return this._dataSource.Oneri
                .Where(x => x.DATAPAGAMENTO.HasValue && x.ImportoPagato.GetValueOrDefault(0) > 0)
                .Select(x => new VisuraOneriListItem
                {
                    Causale = x.CausaleOnere.CoDescrizione,
                    Importo = (float)x.ImportoPagato.GetValueOrDefault(0),
                    DataPagamento = x.DATAPAGAMENTO,
                    DataScadenza = x.DATASCADENZA
                });
        }

        private IEnumerable<VisuraAutorizzazioniListItem> GetVisuraAutorizzazioniDataSource()
        {
            if (this._dataSource == null)
                return Enumerable.Empty<VisuraAutorizzazioniListItem>();

            return this._dataSource.Autorizzazioni?.Select(x => new VisuraAutorizzazioniListItem
            {
                Data = x.AUTORIZDATA,
                Descrizione = x.Registro.TR_DESCRIZIONE,
                Note = x.AUTORIZRESPONSABILE,
                Numero = x.AUTORIZNUMERO,
                DataScadenza = x.DataScadenza,
                DataCessazione = x.DataCessazione,
                Attiva = x.FlagAttiva.GetValueOrDefault(0) == 1
            }) ?? Enumerable.Empty<VisuraAutorizzazioniListItem>();
        }

        private IEnumerable<ElementoListaScadenzeDto> GetScadenzeDataSource()
        {
            var codiceUtente = this._authDataResolver.IsAuthenticated ? this._authDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale : null;

            if (this._dataSource is null || string.IsNullOrEmpty(codiceUtente))
            {
                return Enumerable.Empty<ElementoListaScadenzeDto>();
            }

            return this._scadenzeService.GetListaScadenzeByCodiceIstanza(this._dataSource.SOFTWARE, Convert.ToInt32(this._dataSource.CODICEISTANZA), codiceUtente);
        }

        #endregion
    }
}

