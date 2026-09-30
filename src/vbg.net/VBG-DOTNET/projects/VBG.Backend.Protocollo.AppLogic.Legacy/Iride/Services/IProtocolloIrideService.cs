using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Proxies;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Services
{
    public interface IProtocolloIrideService
    {
        ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn);
        DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo);
        DocumentoOut LeggiDocumento(int idProtocollo, string operatore, string ruolo);
    }
}
