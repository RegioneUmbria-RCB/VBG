using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.Halley.Builders.Errors
{
    public interface IErrori
    {
        HalleySegnaturaBuilder.SegnaturaRequest GetSegnatura();
    }
}
