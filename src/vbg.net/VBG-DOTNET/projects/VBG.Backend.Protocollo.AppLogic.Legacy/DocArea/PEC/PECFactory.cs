using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.PEC
{
    public class PECFactory
    {
        public static IPecService Create(IEnumerable<IAnagraficaAmministrazione> protoAnagrafe, string utente, FornitoreDocAreaEnum fornitore, ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password)
        {
            if (fornitore == FornitoreDocAreaEnum.ADS)
            {
                return new Ads.PecService(logs, serializer, protoAnagrafe, utente, username, password);
            }

            return null;
        }
    }
}
