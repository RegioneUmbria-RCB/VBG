using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public interface IProtocollazioneRequest
    {
        CoordinateArchivio Coordinate { get; }
        RecapitoInterno MittenteInternoInfo { get; }
        RecapitoEsterno[] MittentiEsterniInfo { get; }
        DestinatarioInterno[] DestinatariInterniInfo { get; }
        DestinatarioEsterno[] DestinatariEsterniInfo { get; }
        Allegato[] Allegati { get; }
        IEnumerable<byte[]> AllegatiBuffer { get; }
    }
}
