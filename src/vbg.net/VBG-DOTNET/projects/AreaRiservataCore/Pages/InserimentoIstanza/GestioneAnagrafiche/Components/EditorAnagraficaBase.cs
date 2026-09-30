using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.SIGePro.Manager.DTO.Comuni;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche.Components
{
    public class EditorAnagraficaBase : ComponentWithSpinner
    {
        [CascadingParameter]
        public IDomandaOnlineReadInterface? DomandaCorrente { get; set; }

        [CascadingParameter]
        public ImpostazioniStepAnagrafiche ImpostazioniStep { get; set; } = default!;

        [Inject]
        public IComuniService _comuniService { get; set; } = default!;

        [Parameter, EditorRequired]
        public IEnumerable<DropDownItem> TipiSoggetto { get; set; } = new List<DropDownItem>();

        [Parameter, EditorRequired]
        public bool PermettiModificaTipoSoggetto { get; set; } = true;

        [Parameter, EditorRequired]
        public bool PermettiModificaDatiAnagrafici { get; set; } = true;
        [Parameter]
        public int? IdTipoSoggettoDefault { get; set; }






        protected Task<AnagraficaDomanda> GetAnagraficaDomandaByIdAsync(int idAnagrafica)
        {
            if (this.DomandaCorrente is null)
            {
                throw new ArgumentException($"Cascading value {nameof(this.DomandaCorrente)} non impostato nello step {this.GetType().Name}");
            }

            var anagrafica = this.DomandaCorrente.Anagrafiche.GetById(idAnagrafica);

            if (anagrafica != null)
                return Task.FromResult(anagrafica);

            return Task.FromResult(AnagraficaDomanda.New(idAnagrafica));
        }

        protected Task<IEnumerable<DatiComuneCompatto>> FindComuniAsync(string match)
        {
            return Task.FromResult(this._comuniService.FindComuneDaMatchParziale(match));
        }

        protected Task<DatiComuneCompatto?> GetComuneDaIdAsync(string? codiceComune)
        {
            if (String.IsNullOrEmpty(codiceComune))
            {
                return Task.FromResult((DatiComuneCompatto?)null);
            }

            return Task.FromResult((DatiComuneCompatto?)this._comuniService.GetByCodiceComune(codiceComune));
        }

        protected Task<IEnumerable<DatiProvinciaCompatto>> FindProvincieAsync(string match)
        {
            return Task.FromResult(this._comuniService.FindProvinciaDaMatchParziale(match));
        }

        protected Task<DatiProvinciaCompatto> GetProvinciaByIdAsync(string codiceProvincia)
        {
            return Task.FromResult(this._comuniService.GetDatiProvincia(codiceProvincia));
        }

        protected AutocompleteFormResult? ComuneToAutocompleteResult(DatiComuneCompatto dati)
        {
            if (dati == null)
            {
                return null;
            }

            return new(dati.CodiceComune, $"{dati.Comune} ({dati.SiglaProvincia})");
        }

        protected async Task<AutocompleteFormResult?> ComuneToAutocompleteResultAsync(string? codiceComune)
        {
            var dati = await this.GetComuneDaIdAsync(codiceComune);

            if (dati == null)
            {
                return null;
            }

            return new(dati.CodiceComune, $"{dati.Comune} ({dati.SiglaProvincia})");
        }

        protected AutocompleteFormResult? ProvinciaToAutocompleteResult(DatiProvinciaCompatto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.SiglaProvincia, dati.Provincia);
        }

        protected async Task<AutocompleteFormResult?> ProvinciaToAutocompleteResultAsync(string siglaProvincia)
        {
            var dati = await this.GetProvinciaByIdAsync(siglaProvincia);

            return dati == null ? null : new AutocompleteFormResult(dati.SiglaProvincia, dati.Provincia);
        }
    }
}
