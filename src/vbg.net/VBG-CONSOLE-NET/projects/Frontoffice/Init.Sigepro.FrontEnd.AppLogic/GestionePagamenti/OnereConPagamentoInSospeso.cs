using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti
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
