using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class RiferimentiOperazioneEntraNext
    {
        public readonly string NumeroOperazione;
        public readonly string AnnoDocumento;
        public readonly string Valuta = "EUR";
        // public readonly IEnumerable<KeyValuePair<string, double>> Oneri;
        public readonly IEnumerable<OneriEntraNextDTO> Oneri;

        public RiferimentiOperazioneEntraNext(string numeroOperazione, IEnumerable<OneriEntraNextDTO> oneri)
        {
            this.NumeroOperazione = numeroOperazione;
            this.AnnoDocumento = DateTime.Now.Year.ToString();
            this.Oneri = oneri;

        }
    }
}
