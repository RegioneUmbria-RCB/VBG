using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.ImpresaInUnGiorno
{
    public interface ISuapSueGenerator
    {
        bool Genera();
        string NomeFile { get; }
    }
}
