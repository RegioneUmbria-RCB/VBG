using AreaRiservataCore.Pages.Visura;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.SIGePro.Manager.DTO.Scadenzario;
using Microsoft.AspNetCore.Components;
using static AreaRiservataCore.Pages.Visura.DatiGenerali;
using static AreaRiservataCore.Pages.Visura.VisuraDocumenti;
using static AreaRiservataCore.Pages.Visura.VisuraLocalizzazioni;
using static AreaRiservataCore.Pages.Visura.VisuraMovimenti;

namespace AreaRiservataCore.Pages.EntiTerzi
{
    public partial class DettaglioPratica
    {

        [Parameter]
        public string GoBackUrl { get; set; }

        public string GoBackUrlQueryString { get; set; }

        [Parameter]
        public string UuidIstanza { get; set; }

        [Inject]
        public IVisuraService _visuraService { get; set; } = default!;

        [Inject]
        public IScadenzeService _scadenzeService { get; set; } = default!;

        [Inject]
        public IScrivaniaEntiTerziService _etService { get; set; } = default!;

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; } = default!;
        [Inject]
        public IVisuraDatiDinamiciService _datiDinamiciService { get; set; } = default!;

        public VisuraTabList TabsPagina { get; set; } = new VisuraTabList(new List<VisuraTabListItem> {
            new() { Descrizione= "Dati generali", Id=VisuraTabListNames.DatiGenerali, VisibileDaArchivio=true, IsActive=true },
            new() { Descrizione= "Localizzazioni", Id=VisuraTabListNames.Localizzazioni, VisibileDaArchivio=true },
            new() { Descrizione= "Schede", Id=VisuraTabListNames.Schede, VisibileDaArchivio=false },
            new() { Descrizione= "Documenti", Id=VisuraTabListNames.Documenti, VisibileDaArchivio=false },
            new() { Descrizione= "Scadenze", Id=VisuraTabListNames.Scadenze, VisibileDaArchivio=false, HasBadge = true }
        });

        public int NumeroScadenze { get; set; } = 0;
        public int CodiceIstanza { get; set; } = -1;

        private bool _btnSetNotProcessedVisible = true;
        private bool _btnSetProcessedVisible = true;
        private bool _divProcessedVisible = true;

        private IEnumerable<VisuraTitoloModelloDinamicoIstanza> _titoliModelli = Enumerable.Empty<VisuraTitoloModelloDinamicoIstanza>();

        #region DataSources

        private Istanze _istanza { get; set; }

        private VisuraDatiGeneraliDataSource? _visuraDatiGeneraliDataSource { get; set; }

        private IEnumerable<VisuraMovimentiListItem> _visuraMovimentiDataSource { get; set; } = Enumerable.Empty<VisuraMovimentiListItem>();

        private IEnumerable<VisuraSoggettiListItem> _visuraSoggettiDataSource { get; set; } = Enumerable.Empty<VisuraSoggettiListItem>();

        private VisuraLocalizzazioniDataSource? _visuraLocalizzazioniDataSource { get; set; }

        private IEnumerable<VisuraDocumentiListItem> _visuraDocumentiDataSource { get; set; } = Enumerable.Empty<VisuraDocumentiListItem>();

        private IEnumerable<ElementoListaScadenzeDto> _scadenzeDataSource { get; set; } = Enumerable.Empty<ElementoListaScadenzeDto>();



        private VisuraDatiGeneraliDataSource? GetVisuraDatiGeneraliDataSource()
        {
            if (this._istanza == null)
                return null;

            return new VisuraDatiGeneraliDataSource
            {
                Uuid = this._istanza.UUID,
                ComunePratica = this._istanza.ComuneIstanza.COMUNE,
                DataPratica = this._istanza.DATA,
                DataProtocollo = this._istanza.DATAPROTOCOLLO,
                Intervento = this._istanza.Intervento.SC_DESCRIZIONE,
                Istruttore = this._istanza.Istruttore?.RESPONSABILE ?? "",
                NumeroPratica = this._istanza.NUMEROISTANZA,
                NumeroProtocollo = this._istanza.NUMEROPROTOCOLLO,
                Oggetto = this._istanza.LAVORI,
                Operatore = this._istanza.Operatore?.RESPONSABILE ?? "",
                PosizioneArchivio = this._istanza.POSIZIONEARCHIVIO,
                ResponsabileProcedimento = this._istanza.ResponsabileProc?.RESPONSABILE ?? "",
                Stato = this._istanza.Stato.Stato
            };
        }

        private IEnumerable<VisuraMovimentiListItem> GetVisuraMovimentiDataSource()
        {
            if (this._istanza == null)
                return Enumerable.Empty<VisuraMovimentiListItem>();


            var movimenti = this._istanza.Movimenti
               .Where(x => x.PUBBLICA == "1" && x.DATA.HasValue &&
                    (x.Tipo.FkFoSoggettiesterni.GetValueOrDefault(0) == 1 ||
                    x.Tipo.FkFoSoggettiesterni.GetValueOrDefault(0) == 2))
               .Select(x => new VisuraMovimentiListItem
               {
                   Id = Convert.ToInt32(x.CODICEMOVIMENTO),
                   CodiceIstanza = Convert.ToInt32(this._istanza.CODICEISTANZA),
                   Descrizione = x.MOVIMENTO,
                   Data = x.DATA,
                   Parere = x.PUBBLICAPARERE == "1" ? x.PARERE : String.Empty,
                   NumeroProtocollo = x.NUMEROPROTOCOLLO,
                   DataProtocollo = x.DATAPROTOCOLLO,
                   UuidPraticaCollegata = String.Empty,//x.UuidPraticaCollegata,
                   Allegati = x.MovimentiAllegati?.Where(y => y.FlagPubblica.GetValueOrDefault(0) == 1 &&
                                                           !String.IsNullOrEmpty(y.CODICEOGGETTO)) ?? Enumerable.Empty<MovimentiAllegati>()
               });

            return movimenti;
        }

        private IEnumerable<VisuraSoggettiListItem> GetVisuraSoggettiDataSource()
        {
            if (this._istanza == null)
                return Enumerable.Empty<VisuraSoggettiListItem>();

            var soggetti = new List<VisuraSoggettiListItem>();

            soggetti.Add(new VisuraSoggettiListItem(this._istanza.Richiedente, this._istanza.TipoSoggetto, this._istanza.AziendaRichiedente));

            if (this._istanza.Professionista != null)
            {
                soggetti.Add(new VisuraSoggettiListItem(this._istanza.Professionista, new TipiSoggetto { TIPOSOGGETTO = "Intermediario" }));
            }

            if (this._istanza.Richiedenti != null)
            {
                var richiedenti = this._istanza.Richiedenti.Select(x => new VisuraSoggettiListItem(x.Richiedente, x.TipoSoggetto, x.AnagrafeCollegata, x.Procuratore));

                soggetti.AddRange(richiedenti);
            }

            return soggetti;
        }

        private VisuraLocalizzazioniDataSource? GetVisuraLocalizzazioniDataSource()
        {
            var ds = new VisuraLocalizzazioniDataSource
            {
                Stradario = this._istanza.Stradario ?? Enumerable.Empty<IstanzeStradario>(),
                Mappali = this._istanza.Mappali ?? Enumerable.Empty<IstanzeMappali>()
            };

            return ds.HasData ? ds : null;
        }

        private IEnumerable<VisuraDocumentiListItem> GetVisuraDocumentiDataSource()
        {
            if (this._istanza == null)
                return Enumerable.Empty<VisuraDocumentiListItem>();


            var documenti = this._istanza
                                .DocumentiIstanza
                                .Where(x => !String.IsNullOrEmpty(x.CODICEOGGETTO))
                                .Select(x => new VisuraDocumentiListItem
                                {
                                    CodiceOggetto = Convert.ToInt32(x.CODICEOGGETTO),
                                    Data = x.DATA,
                                    Descrizione = x.DOCUMENTO,
                                    Md5 = x.Oggetto.Md5,
                                    NomeFile = x.Oggetto.NOMEFILE,
                                    UrlDownload = this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(x.CODICEOGGETTO))
                                });

            return documenti;
        }

        private IEnumerable<ElementoListaScadenzeDto> GetScadenzeDataSource()
        {
            var codiceAnagrafe = new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

            var listaScadenze = Enumerable.Empty<ElementoListaScadenzeDto>();

            if (this._etService.PuoEffettuareMovimenti(codiceAnagrafe))
            {
                var amministrazione = this._etService.GetDatiAmministrazioneCollegata(codiceAnagrafe);
                listaScadenze = this._scadenzeService.GetListaScadenzeEntiTerziByNumeroIstanza(this._istanza.SOFTWARE, this._istanza.NUMEROISTANZA, amministrazione.PartitaIva);
            }

            return listaScadenze;
        }

        #endregion

        protected override async Task OnInitializedAsync()
        {
            this.GoBackUrlQueryString = new Uri(this._navigationManager.Uri).Query;

            await this.DataBindAsync();
        }

        private async Task DataBindAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this.EffettuaVisuraIstanza(this.UuidIstanza);
            });
        }

        public void EffettuaVisuraIstanza(string uuidIstanza)
        {
            this._istanza = this._visuraService.GetByUuid(uuidIstanza, false);
            this.CodiceIstanza = Convert.ToInt32(this._istanza.CODICEISTANZA);

            this._visuraDatiGeneraliDataSource = this.GetVisuraDatiGeneraliDataSource();
            this._visuraMovimentiDataSource = this.GetVisuraMovimentiDataSource();
            this._visuraSoggettiDataSource = this.GetVisuraSoggettiDataSource();
            this._visuraLocalizzazioniDataSource = this.GetVisuraLocalizzazioniDataSource();
            this._visuraDocumentiDataSource = this.GetVisuraDocumentiDataSource();
            this._scadenzeDataSource = this.GetScadenzeDataSource();

            this.NumeroScadenze = this._scadenzeDataSource?.Count() ?? 0;
            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Scadenze, this.NumeroScadenze);

            var elaborata = this._etService.PraticaElaborata(new ETCodiceIstanza(Convert.ToInt32(this._istanza.CODICEISTANZA)), new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value));

            this._btnSetProcessedVisible = !elaborata;
            this._btnSetNotProcessedVisible = elaborata;
            this._divProcessedVisible = elaborata;

            this._titoliModelli = this._datiDinamiciService.GetTitoliModelli(this._istanza);

            this.StateHasChanged();
        }
        private string GetGoBackUrl()
        {
            return $"entiterzidettagliopratica/{this.UuidIstanza}/{this.GoBackUrl}";
        }

        public async Task OnBtnCloseAsync()
        {
            if (!string.IsNullOrEmpty(this.GoBackUrl))
                await this.GotoAsync($"{this.GoBackUrl}{this.GoBackUrlQueryString}");
            else
                await this.GotoAsync("entiterzilistapratiche");
        }

        public async Task OnBtnSetNotProcessedAsync()
        {
            await this.ProcessAsync(true);
        }

        public async Task OnBtnSetProcessedAsync()
        {
            await this.ProcessAsync();
        }

        private async Task ProcessAsync(bool undo = false)
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                var codiceIstanza = new ETCodiceIstanza(this.CodiceIstanza);
                var codiceAnagrafe = new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

                if (undo)
                    this._etService.MarcaPraticaComeNonElaborata(codiceIstanza, codiceAnagrafe);
                else
                    this._etService.MarcaPraticaComeElaborata(codiceIstanza, codiceAnagrafe);

                await this.DataBindAsync();
            });
        }
    }
}
