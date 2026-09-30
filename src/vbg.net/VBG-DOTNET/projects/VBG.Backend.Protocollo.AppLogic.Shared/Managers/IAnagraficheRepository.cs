using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public interface IAnagraficheRepository
    {
        ProtocolloAmministrazioni RepositoryGetAmministrazioneByIdProtocollo(int v);
        ProtocolloAnagrafe RepositoryGetAnagrafeById(string cod);
    }
}