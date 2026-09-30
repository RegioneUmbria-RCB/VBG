
namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs.Services
{
    public interface ILogPathResolverService
    {
        string LogPath { get; }
        void EliminaLogPath();
    }
}
