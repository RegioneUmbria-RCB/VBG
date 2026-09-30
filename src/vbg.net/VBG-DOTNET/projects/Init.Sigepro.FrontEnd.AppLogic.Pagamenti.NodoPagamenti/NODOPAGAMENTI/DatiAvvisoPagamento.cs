using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using VBG.Pagamenti.NodoPagamenti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti
{

    public class DatiAvvisoPagamento
    {
        public AvvisoDiPagamento.StatoAvvisoEnum Stato { get; internal set; }
        public BinaryFile File { get; internal set; }
        public string Descrizione { get; internal set; }
    }
}