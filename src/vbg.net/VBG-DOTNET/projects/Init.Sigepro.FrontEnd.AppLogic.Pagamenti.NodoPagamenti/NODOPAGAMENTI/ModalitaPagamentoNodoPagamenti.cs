using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti
{
    public class ModalitaPagamentoNodoPagamenti
    {
        public int Codice => (int)this.CodiceEnum;
        public ModalitaPagamentoOnereEnum CodiceEnum { get; set; }
        public string Descrizione { get; set; }

        public ModalitaPagamentoNodoPagamenti(ModalitaPagamentoOnereEnum codiceEnum, string descrizione)
        {
            this.CodiceEnum = codiceEnum;
            this.Descrizione = descrizione;
        }
    }
}
