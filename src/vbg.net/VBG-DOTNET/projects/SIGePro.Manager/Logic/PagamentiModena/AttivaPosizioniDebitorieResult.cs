using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiModena
{
    public class AttivaPosizioniDebitorieResult
    {
        public readonly bool Esito;
        public readonly IEnumerable<int> IdPosizioniDebitorie;
        public readonly IEnumerable<int> IdOneri;
        public readonly IEnumerable<string> Errori;

        protected AttivaPosizioniDebitorieResult(bool esito, IEnumerable<int> idPosizioniDebitorie, IEnumerable<int> idOneri, IEnumerable<string> errori)
        {
            Esito = esito;
            IdPosizioniDebitorie = idPosizioniDebitorie;
            IdOneri = idOneri;
            Errori = errori;
        }
    }
}
