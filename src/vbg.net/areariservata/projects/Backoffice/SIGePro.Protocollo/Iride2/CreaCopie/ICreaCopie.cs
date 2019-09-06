using Init.SIGePro.Protocollo.Iride2.Proxies;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride2.CreaCopie
{
    public interface ICreaCopie
    {
        DocumentoOut GeneraCopia();
    }
}
