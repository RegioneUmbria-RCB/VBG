using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    public interface ILeggiProtoMittentiDestinatari
    {
        string InCaricoA { get; }
        string InCaricoADescrizione { get; }

        MittDestOutType[] GetMittenteDestinatario();

        string Flusso { get; }
    }
}
