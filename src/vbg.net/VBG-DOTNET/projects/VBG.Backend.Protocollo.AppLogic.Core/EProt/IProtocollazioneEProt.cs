using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.EProt
{
    public interface IProtocollazioneEprot
    {
        IEnumerable<KeyValuePair<string, string>> GetParametri();
        string Metodo { get; }
        void Valida(IEnumerable<KeyValuePair<string, string>> parametri, ProtocolloLogs log);
    }
}
