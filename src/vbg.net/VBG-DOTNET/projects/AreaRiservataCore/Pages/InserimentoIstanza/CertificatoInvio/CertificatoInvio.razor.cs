using DocumentFormat.OpenXml.Office2010.Excel;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Microsoft.AspNetCore.Components;
using static AreaRiservataCore.Pages.Visura.DatiGenerali;

namespace AreaRiservataCore.Pages.InserimentoIstanza.CertificatoInvio
{
    public partial class CertificatoInvio
    {
        [Inject]
        public CertificatoDiInvioService _certificatoDiInvioService { get; set; } = default!;

        [Inject]
        public IOggettiService _oggettiService { get; set; } = default!;

        [Inject]
        public IConfigurazione<ParametriAspetto> _parametriConfigurazione { get; set; } = default!;

        [Inject]
        public IRedirectFineDomandaService _redirectFineDomandaService { get; set; } = default!;

        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioService { get; set; } = default!;

        [Inject]
        public IQuestionarioSoddisfazioneService _questionarioSoddisfazioneService { get; set; } = default!;

        [Inject]
        public IVisuraService _visuraService { get; set; } = default!;
        [Inject]
        public PaginatoreStateService _paginatoreStateService { get; private set; } = default!;

        [Parameter]
        public string CodiceIstanza { get; set; }
        [Parameter]
        public int? IdDomanda { get; set; } = -1;

        public int CodiceOggettoRiepilogo { get; set; } = -1;

        private bool _redirectAFineDomandaAttivo = false;
        private string _redirectTitolo = "";
        private string _redirectMessaggio = "";
        private string _redirectBtnProcediText = "";
        private string _intestazioneCertificatoInvio = "";
        private bool _questionarioGradimentoVisible = false;
        private Istanze? _dataSource;
        private VisuraDatiGeneraliDataSource? _datiPratica;

        protected override void OnInitialized()
        {
            base.OnInitialized();
            this._paginatoreStateService.NascondiPaginatore();
            this.DataBind();
        }

        private void DataBind()
        {
            this._datiPratica = this.GetDatiPraticaFromVisura();

            this._intestazioneCertificatoInvio = this._parametriConfigurazione.Parametri.IntestazioneCertificatoInvio;

            int? codiceOggetto = this._certificatoDiInvioService.GetCodiceOggettoCertificatoDiInvioDaIdDomandaBackoffice(Convert.ToInt32(this.CodiceIstanza));

            if (codiceOggetto.GetValueOrDefault(-1) == -1)
            {
                return;
            }

            this.CodiceOggettoRiepilogo = codiceOggetto.Value;


            // Gestione del redirect a fine domanda
            var domanda = this._salvataggioService.GetById(this.IdDomanda.Value);
            var idIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;

            this._redirectAFineDomandaAttivo = this._redirectFineDomandaService.RedirectAFineDomandaAttivo(idIntervento);

            if (this._redirectAFineDomandaAttivo)
            {
                var testi = this._redirectFineDomandaService.GetTestiBox();

                if (testi == null)
                {
                    this._redirectAFineDomandaAttivo = false;
                    // this.redirectTitolo = "Redirect a fine domanda";
                    // this.redirectMessaggio = "Attenzione! Impossibile trovare il file di risorse specificato in configurazione";
                    // this.redirectBtnProcediText = "Procedi";
                }
                else
                {
                    this._redirectTitolo = testi.Titolo;
                    this._redirectMessaggio = testi.Messaggio;
                    this._redirectBtnProcediText = testi.TestoBottone;
                }
            }

            this._questionarioGradimentoVisible = this._questionarioSoddisfazioneService.CompilazioneQuestionarioAttiva;
        }

        private async Task btnProcedi_ClickAsync()
        {
            await this.GotoAsync(this._redirectFineDomandaService.GeneraUrlRedirect(this.IdDomanda.Value));
        }

        private async Task btnCompilaQuestionario_ClickAsync()
        {
            var uuid = this._visuraService.GetUUIDDaCodiceIstanza(Convert.ToInt32(this.CodiceIstanza));

            await this.GotoAsync("sondaggio", uuid);
        }

        private VisuraDatiGeneraliDataSource? GetDatiPraticaFromVisura()
        {
            try
            {
                var uuid = this._visuraService.GetUUIDDaCodiceIstanza(Convert.ToInt32(this.CodiceIstanza));

                this._dataSource = this._visuraService.GetByUuid(uuid, false);

                if (this._dataSource == null)
                    return null;

                return new VisuraDatiGeneraliDataSource
                {
                    Uuid = uuid,
                    ComunePratica = this._dataSource.ComuneIstanza.COMUNE,
                    DataPratica = this._dataSource.DATA,
                    DataProtocollo = this._dataSource.DATAPROTOCOLLO,
                    Intervento = this._dataSource.Intervento.SC_DESCRIZIONE,
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
            catch (Exception)
            {

                throw;
            }

        }

        private async Task<BinaryFile> GeneraCertificatoAsync()
        {
            return await this._oggettiService.GetByIdAsync(this.CodiceOggettoRiepilogo);
        }

        private async Task GoToHomeAsync()
        {
            await this.GotoAsync("home");
        }
    }
}