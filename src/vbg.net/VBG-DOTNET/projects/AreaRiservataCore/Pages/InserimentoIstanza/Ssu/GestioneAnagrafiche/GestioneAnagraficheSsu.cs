using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Microsoft.AspNetCore.Components;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;
using VBG.AppLogic.SSU.GestioneTipiSoggetto;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Ssu.GestioneAnagrafiche
{
    [Route("/{IdComune}/{Software}/inserimento-istanza/ssu/gestione-anagrafiche/{idDomanda:int}/{stepId:int}")]
    public class GestioneAnagraficheSsu : InserimentoIstanza.GestioneAnagrafiche.GestioneAnagrafiche
    {
        [Inject]
        public SsuTipiSoggettoService SsuTipiSoggettoService { get; set; } = default!;

        private IEnumerable<TipoSoggetto> _tipiSoggetto = Enumerable.Empty<TipoSoggetto>();

        private SsuLogicaSincronizzazioneTipiSoggetto? _logicaSincronizzazioneTipiSoggetto;

        protected override async Task OnInitializeStepAsync()
        {
            try
            {
                await base.OnInitializeStepAsync();

                await this._spinnerService.ShowSpinnerAsync(async () =>
                {
                    this._tipiSoggetto = await this.GetTipiSoggettoAsync();
                    this._logicaSincronizzazioneTipiSoggetto = new SsuLogicaSincronizzazioneTipiSoggetto(this._tipiSoggetto);

                    this._condizioneUscita.FlagVerificaPecObbligatoria = this.VerificaPecObbligatoria;
                    this._condizioneUscita.MessaggioUtenteNonPresente = this.MessaggioUtenteNonPresente;

                    this.AnagraficheService.SincronizzaFlagsTipiSoggetto(this.IdDomanda.Value, this._logicaSincronizzazioneTipiSoggetto);
                    this.AnagraficheService.VerificaFlagsCittadiniExtracomunitari(this.IdDomanda.Value);

                    this._testoDescrizioneStep = this.TestoDescrizioneSteps;
                });
            }
            catch (Exception)
            {
                this.MessageContainer.AddError("Errore durante il recupero dei tipi soggetto");
                this._paginatoreStateService.NascondiBottoneAvanti();
            }

        }

        protected override Task SalvaAnagraficaAsync(AnagraficaDomanda row)
        {
            return this.AnagraficheService.SalvaAnagraficaAsync(this.IdDomanda!.Value, row, this._logicaSincronizzazioneTipiSoggetto);
        }

        protected async Task<IEnumerable<TipoSoggetto>> GetTipiSoggettoAsync()
        {
            if (!this.IdDomanda.HasValue)
            {
                throw new InvalidOperationException("Id domanda non ancora impostato");
            }

            return await this.SsuTipiSoggettoService.GetTipiSoggettoAsync(this.IdDomanda.Value);
        }

        protected override IEnumerable<Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.TipoSoggetto> GetTipiSoggettoPersFisica()
        {
            var tipiSoggetto = this._tipiSoggetto.Where(x => x.TipoPersona == VBG.AppLogic.SSU.APICatalogoServizi.Client.TipoPersonaEnum.Fisica);
            return tipiSoggetto.ToTipiSoggettoStandard();
        }

        protected override IEnumerable<Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.TipoSoggetto> GetTipiSoggettoPersGiuridica()
        {
            var tipiSoggetto = this._tipiSoggetto.Where(x => x.TipoPersona == VBG.AppLogic.SSU.APICatalogoServizi.Client.TipoPersonaEnum.Giuridica);
            return tipiSoggetto.ToTipiSoggettoStandard();
        }

        protected override Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.TipoSoggetto? GetTipoSoggetto(int idTipoSoggetto)
        {
            if (idTipoSoggetto == -1)
                return null;

            return this._tipiSoggetto.FirstOrDefault(x => x.Id == idTipoSoggetto).ToTipoSoggettoStandard();
        }
    }
}
