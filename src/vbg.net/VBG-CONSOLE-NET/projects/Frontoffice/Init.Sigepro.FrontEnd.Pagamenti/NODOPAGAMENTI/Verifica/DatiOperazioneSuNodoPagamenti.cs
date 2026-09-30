using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica
{
    public class DatiOperazioneSuNodoPagamenti
    {
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

        public bool PermetteDownloadAvviso => !OTF;
    }
}
