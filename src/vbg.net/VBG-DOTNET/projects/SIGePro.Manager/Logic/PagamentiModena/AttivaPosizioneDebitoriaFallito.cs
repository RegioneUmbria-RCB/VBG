using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiModena
{
    public class AttivaPosizioneDebitoriaFallito : AttivaPosizioniDebitorieResult
    {
        public AttivaPosizioneDebitoriaFallito(IEnumerable<int> idOneri, IEnumerable<string> errori)
            :base(false,Enumerable.Empty<int>(), idOneri, errori)
        {
        }
    }
}
