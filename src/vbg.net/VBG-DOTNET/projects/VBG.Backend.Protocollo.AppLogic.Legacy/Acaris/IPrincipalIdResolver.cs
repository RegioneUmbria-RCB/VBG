using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris
{
    public interface IPrincipalIdResolver
    {
        PrincipalId PrincipalId { get; }
    }
}
