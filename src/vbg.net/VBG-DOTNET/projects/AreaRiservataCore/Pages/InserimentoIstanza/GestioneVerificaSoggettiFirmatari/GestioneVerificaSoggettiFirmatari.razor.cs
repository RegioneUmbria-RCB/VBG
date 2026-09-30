using Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneVerificaSoggettiFirmatari
{
    public partial class GestioneVerificaSoggettiFirmatari
    {
        [Inject]
        protected VerificaSoggettiFirmatariService _verificaSoggettiFirmatariService { get; set; } = default!;
        [Inject]
        private PaginatoreStateService _paginatoreService { get; set; } = default!;

        #region dati letti dai parametri del workflow
        [StepProperty]
        public bool Bloccante { get; set; } = false;
        #endregion

        protected EsitoVerificaSoggetti EsitoVerifica { get; set; }

        protected override bool CanEnterStep()
        {
            this.EsitoVerifica = this._verificaSoggettiFirmatariService.Verifica(IdDomanda.Value);

            if (!this.EsitoVerifica.VerificaRiuscita && Bloccante)
            {
                this._paginatoreService.NascondiBottoneAvanti();
            }

            return !this.EsitoVerifica.VerificaRiuscita;
        }
    }
}