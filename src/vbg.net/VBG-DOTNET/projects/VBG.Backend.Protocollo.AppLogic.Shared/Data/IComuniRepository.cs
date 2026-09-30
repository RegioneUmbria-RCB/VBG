namespace VBG.Backend.Protocollo.AppLogic.Shared.Data
{
    public interface IComuniRepository
    {
        ProtocolloComune RepositoryGetComuneById(string codiceComune);
    }
}