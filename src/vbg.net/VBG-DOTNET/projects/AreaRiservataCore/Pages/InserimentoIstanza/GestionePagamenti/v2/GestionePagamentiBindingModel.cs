using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestionePagamenti.v2
{
    public class GestionePagamentiBindingModel
    {
        public GestionePagamentiBindingModel(IEnumerable<GrigliaPagamentiItem> oneriIntervento, IEnumerable<GrigliaPagamentiItem> oneriEndo, int? codiceOggettoAttestazionePagamento)
        {
            this.OneriIntervento = oneriIntervento;
            this.OneriEndo = oneriEndo;
            this.CodiceOggettoAttestazionePagamento = codiceOggettoAttestazionePagamento;
            this.TuttiGliOneri = this.OneriIntervento.Union(this.OneriEndo);

            foreach (var onere in this.TuttiGliOneri)
            {
                onere.OnImportoPagatoModificato = () => this.AggiornaTotaleDaPagare();
                onere.OnModalitaPagamentoModificata = (item) => this.AggiornaModalitaPagamento(item);
            }

            this.AggiornaTotaleDaPagare();
        }

        public IEnumerable<GrigliaPagamentiItem> OneriIntervento { get; }
        public IEnumerable<GrigliaPagamentiItem> OneriEndo { get; }
        public int? CodiceOggettoAttestazionePagamento { get; }
        public bool DichiaraDiNonAvereOneriDaPagare { get; set; } = false;

        public decimal TotaleDaPagare { get; private set; } = 0.0m;

        public decimal GetTotaleDaPagare() => this.OneriIntervento.Sum(x => x.ImportoPagato) + this.OneriEndo.Sum(x => x.ImportoPagato);

        public void SetImportoChangedHandler(Action callback)
        {
            foreach (var item in TuttiGliOneri)
                item.OnImportoPagatoModificato = callback;
        }

        public void SetModalitaPagamentoChangedHandler(Action<GrigliaPagamentiItem> callback)
        {
            foreach (var item in TuttiGliOneri)
                item.OnModalitaPagamentoModificata = callback;
        }

        public bool ContieneOneriIntervento =>
            this.OneriIntervento.Any();

        public bool ContieneOneriEndo =>
            this.OneriEndo.Any();

        public bool ContieneOneriGiaPagati =>
            this.OneriGiaPagati.Any();

        public IEnumerable<GrigliaPagamentiItem> TuttiGliOneri { get; }

        public IEnumerable<GrigliaPagamentiItem> OneriGiaPagati => this.TuttiGliOneri.Where(x => x.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.GiaPagato);

        public IEnumerable<GrigliaPagamentiItem> OneriDaPagareOnline => this.TuttiGliOneri.Where(x => x.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.Online);

        public bool TuttiGliOneriSonoNonDovuti => !this.TuttiGliOneri.Where(x => x.ModalitaPagamentoAsEnum != ModalitaPagamentoOnereEnum.NonDovuto).Any();

        private void AggiornaTotaleDaPagare()
        {
            this.TotaleDaPagare = this.TuttiGliOneri.Sum(x => x.ImportoPagato);
        }

        private void AggiornaModalitaPagamento(GrigliaPagamentiItem item)
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
    }
}
