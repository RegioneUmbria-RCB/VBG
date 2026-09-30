namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Protocollazione
{
    public interface IProtocollazioneResolver
    {
        string ProtocollazioneUrl { get; }
        string CasellaEmail { get; }
        string UserName { get; }
        string Password { get; }
    }
}
