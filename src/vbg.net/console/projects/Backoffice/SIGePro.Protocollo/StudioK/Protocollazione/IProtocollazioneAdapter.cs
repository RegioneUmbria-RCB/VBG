using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.Protocollazione
{
    public interface IProtocollazioneAdapter
    {
        Origine GetMittente();
        Destinazione[] GetDestinatari();
        CustomMetadata GetCustomMetadata();
    }
}
