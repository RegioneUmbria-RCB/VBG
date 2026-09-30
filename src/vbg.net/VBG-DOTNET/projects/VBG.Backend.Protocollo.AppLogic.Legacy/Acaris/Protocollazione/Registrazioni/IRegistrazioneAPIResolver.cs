using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Registrazioni
{
    public interface IRegistrazioneAPIResolver
    {
        RegistrazioneAPI ResolveRegistrazione();
        string ClassificazioneID { get; }
    }
}
