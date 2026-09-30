using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno
{
    public interface ISuapSueGenerator
    {
        bool Genera();
        string NomeFile { get; }
    }
}
