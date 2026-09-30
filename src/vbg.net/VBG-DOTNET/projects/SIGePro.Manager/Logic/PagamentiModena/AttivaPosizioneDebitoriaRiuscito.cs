using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.PagamentiModena
{
    internal class AttivaPosizioneDebitoriaRiuscito : AttivaPosizioniDebitorieResult
    {
        public AttivaPosizioneDebitoriaRiuscito(IEnumerable<int> idOneri, IEnumerable<int> idPosizioniDebitorie)
            :base(true, idPosizioniDebitorie, idOneri, Enumerable.Empty<string>())
        {
        }
    }
}