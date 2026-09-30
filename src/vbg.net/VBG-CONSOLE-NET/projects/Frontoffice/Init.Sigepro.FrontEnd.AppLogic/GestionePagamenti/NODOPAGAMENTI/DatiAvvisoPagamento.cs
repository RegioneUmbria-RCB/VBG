using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI
{
    public class DatiAvvisoPagamento
    {
        public AvvisoDiPagamento.StatoAvvisoEnum Stato { get; internal set; }
        public BinaryFile File { get; internal set; }
        public string Descrizione { get; internal set; }
    }
}