using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces
{
    public interface IDocAreaSegnaturaNominativoPersonaBuilder
    {
        string Nome { get; }
        string Cognome { get; }
        string Denominazione { get; }
    }
}
