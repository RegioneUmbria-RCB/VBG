using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneEndoPresenti
{
    public class EndoPresentiBindingModel
    {
        public EndoPresentiBindingModel(IEndoprocedimentiReadInterface endoprocedimenti, IEndoAcquisitiService endoprocedimentiService, bool IgnoraCaricamentoAllegati)
        {
            var endoprocedimentiPrincipale = endoprocedimenti.Principale;
            var endoprocedimentiAcquisibili = endoprocedimenti.Secondari.Where(x => x.PermetteVerificaAcquisizione).ToArray();

            var tipiTitolo = endoprocedimentiService.TipiTitoloWhereCodiceInventarioIn(endoprocedimentiAcquisibili.Select(x => x.Codice).ToArray());

            var tipiTitoloDictionary = tipiTitolo.ToDictionary(
            x => x.Key,
            x => x.Value.Select(tt =>
            {
                if (IgnoraCaricamentoAllegati)
                {
                    tt.Flags.RichiedeAllegato = false;
                }
                var flags = new TipoTitoloFlags(tt.Flags);

                return new DropDownItem(tt.Codice.ToString(), tt.Descrizione, flags.ToDictionary().Select(kvp => new KeyValuePair<string, object>(kvp.Key, kvp.Value)));
            }));

            this.EndoPresenti = endoprocedimentiAcquisibili.Select(r => new EndoPresenteBindingItem(tipiTitoloDictionary[r.Codice], r.Riferimenti?.TipoTitolo?.Codice)
            {
                CodiceInventario = r.Codice,
                DataAtto = r.Riferimenti?.DataAtto,
                Descrizione = VisualizzaAsterisco(endoprocedimentiPrincipale == null ? -1 : endoprocedimentiPrincipale.BinarioDipendenze, r.BinarioDipendenze) + " " + r.Descrizione,
                Note = r.Riferimenti?.Note ?? "",
                NumeroAtto = r.Riferimenti?.NumeroAtto ?? "",
                Presente = r.TipoTitoloObbligatorio ? true : r.Presente,
                TipoTitoloObbligatorio = r.TipoTitoloObbligatorio,
                RilasciatoDa = r.Riferimenti?.RilasciatoDa ?? "",
                CodiceOggetto = r.Riferimenti?.Allegato?.CodiceOggetto
            }).ToList();
        }

        protected string VisualizzaAsterisco(int binarioDipendenzeEndoPrincipale, int binarioDipendenzeEndo)
        {
            if (binarioDipendenzeEndoPrincipale < 0)
                return String.Empty;

            if ((binarioDipendenzeEndoPrincipale & binarioDipendenzeEndo) != binarioDipendenzeEndo)
                return "*";

            return String.Empty;
        }

        public List<EndoPresenteBindingItem> EndoPresenti { get; private set; }
    }
}
