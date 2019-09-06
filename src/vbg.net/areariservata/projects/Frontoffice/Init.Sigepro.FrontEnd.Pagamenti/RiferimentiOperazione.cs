using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti
{
    public class RiferimentiOperazione
    {
        public readonly string NumeroOperazione;
        public readonly string NumeroDocumento;
        public readonly string AnnoDocumento;
        public readonly string Valuta = "EUR";
        public readonly string Importo;

        public RiferimentiOperazione(string numeroOperazione, int importo, string numeroDocumento, string annoDocumento)
        {
            this.NumeroOperazione = numeroOperazione;
            this.Importo = importo.ToString();
        }

        public RiferimentiOperazione(string numeroOperazione, int importo) : this(numeroOperazione, importo, numeroOperazione, DateTime.Now.Year.ToString())
        {
            
        }
    }
}
