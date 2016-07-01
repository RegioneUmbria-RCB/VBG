using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocPro.Interfaces
{
    public interface IDatiDocProSegnaturaApplicativoProtocollo
    {
        string Indirizzo {get;}
        string Localita { get; }
        string Provincia { get; }
        string Cap { get; }
    }
}
