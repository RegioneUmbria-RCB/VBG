
using ProtocolloInsielService3;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari
{
    public interface IProtocollazioneInsiel3
    {
        MittenteInsProto[] GetMittenti();
        DestinatarioIOPInsProto[] GetDestinatari();
        verso Flusso { get; }
        bool InvioTelematicoAttivo { get; }
        UfficioInsProto[] GetUffici();
        string MittentePec { get; }




























































    }
}
