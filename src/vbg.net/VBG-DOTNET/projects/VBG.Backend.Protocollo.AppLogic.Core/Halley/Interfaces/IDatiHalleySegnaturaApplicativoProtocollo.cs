using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Interfaces
{
    public interface IDatiHalleySegnaturaApplicativoProtocollo
    {
        string Indirizzo {get;}
        string Localita { get; }
        string Provincia { get; }
        string Cap { get; }
    }
}
