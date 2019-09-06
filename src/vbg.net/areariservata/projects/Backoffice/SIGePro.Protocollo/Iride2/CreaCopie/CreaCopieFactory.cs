using Init.SIGePro.Protocollo.Iride;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride2.CreaCopie
{
    public static class CreaCopieFactory
    {
        public static ICreaCopie Create(CreaCopieInfo info)
        {
            info.ProtocolloLogs.InfoFormat("VERSIONE: {0}", info.Vert.Versione.ToString());

            if (info.Vert.Versione == ProtocolloIrideEnumerators.VersioneEnum.J_IRIDE)
                return new CreaCopieJIride(info);
            else if (info.Vert.Versione == ProtocolloIrideEnumerators.VersioneEnum.IRIDE)
                return new CreaCopieIride(info);
            else
                return new CreaCopieIride(info);
        }
    }
}
