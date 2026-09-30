using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    public interface IMittenteDestinatarioService
    {
        List<ProtocolloAnagrafe> GetSoggetti();
    }
}
