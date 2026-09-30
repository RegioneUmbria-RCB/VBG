using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public class CausaliDaPagare
    {
        public IEnumerable<OnereNodoPagamentiDTO> Oneri { get; }

        public CausaliDaPagare(IEnumerable<OnereNodoPagamentiDTO> oneri)
        {
            Oneri = oneri;
        }
    }
}
