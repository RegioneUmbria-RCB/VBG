using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari
{
    public interface IProtocollazioneInsiel4
    {
        MittenteInsProto[] GetMittenti();
        DestinatarioIOPInsProto[] GetDestinatari();
        Verso Flusso { get; }
        bool InvioTelematicoAttivo { get; }
        UfficioInsProto[] GetUffici();
        string MittentePec { get; }

    }
}
