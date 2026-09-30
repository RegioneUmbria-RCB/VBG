using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Halley.Interfaces
{
    public interface IDatiHalleySegnaturaApplicativoProtocollo
    {
        string Indirizzo {get;}
        string Localita { get; }
        string Provincia { get; }
        string Cap { get; }
    }
}
