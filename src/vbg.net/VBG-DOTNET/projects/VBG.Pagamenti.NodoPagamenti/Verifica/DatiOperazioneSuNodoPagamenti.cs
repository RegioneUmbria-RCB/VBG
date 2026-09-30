namespace VBG.Pagamenti.NodoPagamenti.Verifica
{
    public class DatiOperazioneSuNodoPagamenti
    {
        public bool ConnettoreSupportaRicevuta { get; internal set; }
        public string NominativoSoggettoDebitore { get; internal set; }
        public string CfSoggettoDebitore { get; internal set; }
        public string CfEnteCreditore { get; internal set; }
        public string CodiceAvviso { get; internal set; }
        public string IUV { get; internal set; }
        public string Scadenza { get; internal set; }
        public decimal Importo { get; internal set; }
        public string Causale { get; internal set; }
        public string UniqueId { get; internal set; }
        public bool OTF { get; internal set; }
        public string Stato { get; internal set; }
        public string StatoNodoPagamenti { get; internal set; }
        public bool PermetteDownloadAvviso => !this.OTF;
        public bool PermetteDownloadRicevuta => this.ConnettoreSupportaRicevuta && this.StatoPagamentoEnum == StatoPagamentoEnum.PagamentoRiuscito;

        public StatoPagamentoEnum StatoPagamentoEnum => StatoPagamentoOnere.TraduciStato(this.StatoNodoPagamenti);


    }
}
