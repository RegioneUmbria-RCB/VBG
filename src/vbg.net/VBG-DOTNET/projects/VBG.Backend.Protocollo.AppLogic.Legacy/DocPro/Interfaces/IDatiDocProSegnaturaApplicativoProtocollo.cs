using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Interfaces
{
    public interface IDatiDocProSegnaturaApplicativoProtocollo
    {
        string Indirizzo {get;}
        string Localita { get; }
        string Provincia { get; }
        string Cap { get; }
    }
}
