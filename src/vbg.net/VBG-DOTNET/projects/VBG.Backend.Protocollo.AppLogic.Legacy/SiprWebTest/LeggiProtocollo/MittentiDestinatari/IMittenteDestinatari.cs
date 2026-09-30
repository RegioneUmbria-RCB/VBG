using System;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.LeggiProtocollo.MittentiDestinatari
{
    public interface IMittenteDestinatari
    {
        string InCaricoADescrizione { get; }
        MittDestOutType[] MittentiDestintari { get; }
    }
}
