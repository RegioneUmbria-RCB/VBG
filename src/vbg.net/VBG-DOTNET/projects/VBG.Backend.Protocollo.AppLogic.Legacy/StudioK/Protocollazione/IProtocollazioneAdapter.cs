using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.Protocollazione
{
    public interface IProtocollazioneAdapter
    {
        Origine GetMittente();
        Destinazione[] GetDestinatari();
        CustomMetadata GetCustomMetadata();
    }
}
