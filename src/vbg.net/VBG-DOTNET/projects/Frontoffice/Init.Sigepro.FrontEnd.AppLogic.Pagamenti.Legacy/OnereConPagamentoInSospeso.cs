using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy
{
    public class OnereConPagamentoInSospeso
    {
        public string Causale { get; set; }
        public string IdPosizioneNodoPagamenti { get; set; }
        public string UniqueId { get; set; }
        public string IUV { get; set; }
        public StatoPagamentoOnereEnum Stato { get; set; }
        public string StatoNativo { get; set; }
        public string Importo { get; set; }
    }
}
