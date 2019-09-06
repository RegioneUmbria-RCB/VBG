using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Interfaces
{
    public interface IDocAreaSegnaturaNominativoPersonaBuilder
    {
        string Nome { get; }
        string Cognome { get; }
        string Denominazione { get; }
    }
}
