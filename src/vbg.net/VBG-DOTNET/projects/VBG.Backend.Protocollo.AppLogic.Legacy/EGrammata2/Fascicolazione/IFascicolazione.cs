using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Fascicolazione
{
    public interface IFascicolazione
    {
        string IdFascicolo { get; }
        string NumeroFascicolo { get; }
        string AnnoFascicolo { get; }
    }
}
