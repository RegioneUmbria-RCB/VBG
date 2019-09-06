using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext
{
    public class DatiAvvioPagamentiEntraNext
    {
        public readonly string UrlAvvioPagamento;
        public readonly string NumeroOperazione;
        public readonly OnereFrontoffice[] Oneri;

        public DatiAvvioPagamentiEntraNext(string urlAvvioPagamento, string numeroOperazione, IEnumerable<OnereFrontoffice> oneri)
        {
            this.UrlAvvioPagamento = urlAvvioPagamento;
            this.NumeroOperazione = numeroOperazione;
            this.Oneri = oneri.ToArray();

        }
    }
}
