using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti
{
    public interface IRiferimentiDomandaPerPagamenti
    {
        string IdComune { get; }
        string Software { get; }
        int IdPresentazione { get; }
        string CodiceUnivocoDomanda { get; }
    }
}
