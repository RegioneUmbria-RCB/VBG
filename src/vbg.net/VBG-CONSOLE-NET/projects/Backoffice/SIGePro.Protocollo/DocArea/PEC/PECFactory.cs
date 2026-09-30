using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.PEC
{
    public class PECFactory
    {
        public static IPecService Create(IEnumerable<IAnagraficaAmministrazione> protoAnagrafe, string utente, ProtocolloEnum.FornitoreDocAreaEnum fornitore, ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password)
        {
            if (fornitore == ProtocolloEnum.FornitoreDocAreaEnum.ADS)
            {
                return new Ads.PecService(logs, serializer, protoAnagrafe, utente, username, password);
            }

            return null;
        }
    }
}
