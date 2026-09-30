using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneOneri.v2
{
    public class GestioneOneriBindingModel
    {
        public GestioneOneriBindingModel(IEnumerable<GrigliaOneriItem> oneriIntervento, IEnumerable<GrigliaOneriItem> oneriEndo, int? codiceOggettoAttestazionePagamento)
        {
            this.OneriIntervento = oneriIntervento;
            this.OneriEndo = oneriEndo;
            this.CodiceOggettoAttestazionePagamento = codiceOggettoAttestazionePagamento;

            TuttiGliOneri = [.. OneriIntervento, .. OneriEndo];

            foreach (var onere in this.TuttiGliOneri)
            {
                onere.OnImportoPagatoModificato = () => this.AggiornaTotaleDaPagare();
                onere.OnModalitaPagamentoModificata = (item) => this.AggiornaModalitaPagamento(item);
            }

            this.AggiornaTotaleDaPagare();
        }

        public int? CodiceOggettoAttestazionePagamento { get; }
        public bool DichiaraDiNonAvereOneriDaPagare { get; set; } = false;

        public decimal TotaleDaPagare { get; private set; } = 0.0m;

        public IEnumerable<GrigliaOneriItem> OneriEndo { get; }

        public IEnumerable<GrigliaOneriItem> OneriIntervento { get; }

        public List<GrigliaOneriItem> TuttiGliOneri { get; private set; }

        public IEnumerable<GrigliaOneriItem> OneriGiaPagati => this.TuttiGliOneri.Where(x => x.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.GiaPagato);

        public IEnumerable<GrigliaOneriItem> OneriDaPagareOnline => this.TuttiGliOneri.Where(x => x.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.Online);

        public bool TuttiGliOneriSonoNonDovuti => this.TuttiGliOneri.Count(x => x.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.NonDovuto) == this.TuttiGliOneri.Count();
        public bool TuttiGliOneriDovutiSonoPagati =>
            this.TuttiGliOneri.Count(x => x.ModalitaPagamentoAsEnum != ModalitaPagamentoOnereEnum.NonDovuto && x.PagamentoCompletato) == this.TuttiGliOneri.Count(x => x.ModalitaPagamentoAsEnum != ModalitaPagamentoOnereEnum.NonDovuto);
        public bool ContieneOneriIntervento =>
            this.TuttiGliOneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Intervento).Count() > 0;

        public bool ContieneOneriEndo =>
            this.TuttiGliOneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento).Count() > 0;

        private void AggiornaTotaleDaPagare()
        {
            this.TotaleDaPagare = this.TuttiGliOneri.Sum(x => x.ImportoPagato);
        }

        private void AggiornaModalitaPagamento(GrigliaOneriItem item)
        {
            foreach (var i in this.TuttiGliOneri)
            {
                if (i == item
                    || item.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.NonDovuto
                    || i.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.NonDovuto
                    || i.ModalitaPagamentoAsEnum == item.ModalitaPagamentoAsEnum)
                {
                    continue;
                }

                i.SetModalitaPagamentoNoCallback(item.ModalitaPagamento);
            }
        }

        public bool ContieneErrori => this.TuttiGliOneri.Where(x => x.Errori.Any()).Any();
    }
}
