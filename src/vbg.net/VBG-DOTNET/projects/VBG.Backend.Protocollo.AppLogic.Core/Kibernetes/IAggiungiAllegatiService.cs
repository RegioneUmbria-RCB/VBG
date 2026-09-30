using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public interface IAggiungiAllegatiService
    {
        InviaAllegatoResponse AggiungiAllegati(IEnumerable<ProtocolloAllegati> allegati, long numeroProtocollo, short annoProtocollo);
    }
}
