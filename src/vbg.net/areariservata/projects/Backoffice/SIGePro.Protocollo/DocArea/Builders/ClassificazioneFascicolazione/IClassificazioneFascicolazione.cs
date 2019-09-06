using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ClassificazioneFascicolazione
{
    public interface IClassificazioneFascicolazione
    {
        Classifica GetClassificazione();
        Fascicolo GetFascicolo();
    }
}
